/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.component.builders.table;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.web.component.ComponentConstants;
import org.agatom.springatom.web.component.builders.DefaultComponentBuilder;
import org.agatom.springatom.web.component.builders.EntityAware;
import org.agatom.springatom.web.component.builders.exception.ComponentException;
import org.agatom.springatom.web.component.builders.exception.ComponentPathEvaluationException;
import org.agatom.springatom.web.component.builders.exception.ComponentTableException;
import org.agatom.springatom.web.component.data.ComponentDataRequest;
import org.agatom.springatom.web.component.data.ComponentDataResponse;
import org.agatom.springatom.web.component.elements.table.TableComponent;
import org.agatom.springatom.web.component.helper.TableComponentHelper;
import org.agatom.springatom.web.component.request.beans.ComponentTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * {@code TableComponentBuilder} provides generic functionality for all the implementing classes
 * that eases and helps to build a table component {@link org.agatom.springatom.web.component.elements.table.TableComponent}.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class TableComponentBuilder<COMP extends TableComponent, Y extends Persistable<?>>
        extends DefaultComponentBuilder<COMP>
        implements EntityAware<Y> {

    @Autowired
    protected TableComponentHelper              helper;
    @Autowired
    protected ApplicationContext                context;
    protected Class<Y>                          entity;
    protected SBasicRepository<Y, Serializable> repository;

    @Override
    public void setEntity(final Class<Y> entity) {
        this.entity = entity;
    }

    @Override
    public void setRepository(final SBasicRepository<Y, Serializable> repository) {
        this.repository = repository;
    }

    @Override
    protected ComponentDataResponse buildData(final ComponentDataRequest dataRequest) throws ComponentException {
        final ComponentTableRequest dc = (ComponentTableRequest) dataRequest.getValues().get(ComponentConstants.REQUEST_BEAN);
        if (dc != null) {

            logger.debug(String.format("/buildData -> %s=%s", ComponentConstants.REQUEST_BEAN, dc));

            final Predicate predicate = this.getPredicate(dc);
            final long countInContext = predicate != null ? this.repository.count(predicate) : this.repository.count();
            final List<Map<String, Object>> data = Lists.newArrayList();
            if (countInContext != 0) {
                final Page<Y> all = this.getAllEntities(dc, predicate, countInContext);

                for (final Y object : all) {
                    this.logger.trace(String.format("processing object %s=%s", ClassUtils.getShortName(object.getClass()), object.getId()));
                    final Map<String, Object> map = Maps.newHashMap();
                    for (ColumnDef columnDef : dc.getColumnDefs()) {
                        this.logger.trace(String.format("processing column %s", columnDef.getName()));

                        final String path = columnDef.getName();
                        Object value = InvokeUtils.invokeGetter(object, path);
                        if (value == null) {
                            value = this.handleDynamicColumn(object, path);
                        }
                        if (value != null) {
                            value = this.handleColumnConversion(object, value, path);
                        } else {
                            value = "???";
                        }
                        map.put(path, value);

                        this.logger.trace(String.format("processed column %s to %s", columnDef.getName(), value));
                    }
                    if (!map.isEmpty()) {
                        data.add(map);
                    }
                }

            }
            this.logger.debug(String.format("%s returning data %s", this.getId(), data));

            ComponentDataResponse<DataSet<Map<String, Object>>> response = new ComponentDataResponse<>();
            response.setValue(new DataSet<>(data, (long) data.size(), countInContext));

            return response;
        } else {
            throw new ComponentTableException(String
                    .format("%s - could not locate %s in passed %s", this.getId(), ComponentConstants.REQUEST_BEAN, dataRequest));
        }
    }

    private Page<Y> getAllEntities(final ComponentTableRequest dc, final Predicate predicate, final long countInContext) {
        final PageRequest pageable = this.getPageable(countInContext, dc.getPageSize(), dc.getSortingColumnDefs());
        if (predicate != null) {
            return this.repository.findAll(predicate, pageable);
        } else {
            return this.repository.findAll(pageable);
        }
    }

    /**
     * Method handles converting found {@code value} for {@code path} if necessary.
     * If value was found using path but there is a requirement to retrieve another value for it,
     * this method can be easily overridden hence give the possibility to override old value
     *
     * @param object
     *         current object being processed
     * @param value
     *         current value found for {@code path}
     * @param path
     *         current path
     *
     * @return new/old value for {@code path}
     */
    protected Object handleColumnConversion(final Y object, final Object value, final String path) {
        return value;
    }

    /**
     * Using passed {@link org.agatom.springatom.web.component.request.beans.ComponentTableRequest} method
     * constructs {@link com.mysema.query.types.Predicate} for the {@link org.springframework.data.repository.Repository}
     *
     * @param dc
     *         current request data holder
     *
     * @return valid predicate
     *
     * @throws ComponentPathEvaluationException
     *         if predicate was impossible to built
     */
    private Predicate getPredicate(final ComponentTableRequest dc) throws ComponentPathEvaluationException {
        if (!this.isInContext(dc)) {
            this.logger.debug("No context detected...looking for matching QueryDSL");
            final String shortName = ClassUtils.getShortName(this.entity);
            final ClassLoader classLoader = this.getClass().getClassLoader();
            final String qClassName = String.format("%s.%s", ClassUtils.getPackageName(this.entity), String.format("Q%s", shortName));
            try {
                if (ClassUtils.isPresent(qClassName, classLoader)) {
                    final Class<?> qClass = ClassUtils.resolveClassName(qClassName, classLoader);
                    if (ClassUtils.isAssignable(EntityPathBase.class, qClass)) {
                        // safe to return null
                        this.logger.debug(String.format("No context detect and found matching QueryDSL=>%s", qClassName));
                        return null;
                    }
                }
                throw new IllegalStateException(String.format(
                        "%s does not correspond to any QueryDSL class => %s", shortName, qClassName
                ));
            } catch (Exception e) {
                throw new ComponentPathEvaluationException(String
                        .format("failed to evaluate %s [no context]", ClassUtils.getShortName(Predicate.class)), e);
            }
        }
        final Predicate predicate = this.getPredicate(Long.valueOf(dc.getContextKey()), dc.getContextClass());
        if (predicate == null) {
            throw new ComponentPathEvaluationException(String
                    .format("failed to evaluate %s for contextClass=%s", ClassUtils.getShortName(Predicate.class), ClassUtils
                            .getShortName(dc.getContextClass())));
        }
        logger.debug(String.format("processing with predicate %s", predicate));
        return predicate;
    }

    /**
     * Method checks if table builder was called in some specific context
     *
     * @param dc
     *         current request data holder
     *
     * @return true if in context, false otherwise
     */
    private boolean isInContext(final ComponentTableRequest dc) {
        return (dc.getContextKey() != null && !dc.getContextKey().isEmpty()) && (dc.getContextClass() != null);
    }

    /**
     * Internal method for {@link org.agatom.springatom.web.component.builders.table.TableComponentBuilder}'s that extends this base class
     *
     * @param contextKey
     *         value set in another {@link org.agatom.springatom.web.component.builders.ComponentBuilder} which is most likely the {@link
     *         org.springframework.data.domain.Persistable#getId()} of a {@code contextClass}
     * @param contextClass
     *         points to a {@link java.lang.Class} correlating to the {@code contextKey}
     *
     * @return valid {@link com.mysema.query.types.Predicate} or null
     *
     * @see org.agatom.springatom.web.component.builders.table.TableComponentBuilder#getPredicate(org.agatom.springatom.web.component.request.beans.ComponentTableRequest)
     */
    protected abstract Predicate getPredicate(final Long contextKey, final Class<?> contextClass);

    /**
     * If value for current {@code path} was not found (therefore was null) this method will be called to retrieve value
     * for given {@code path} as for <b>dynamic column</b>.
     * In other words such pair <b>@{code path}</b> and <b>column</b> can be considered as <b>calculable attribute</b>
     *
     * @param object
     *         current object being processed
     * @param path
     *         current path
     *
     * @return value for path or null
     *
     * @see org.agatom.springatom.web.component.builders.table.TableComponentBuilder#handleColumnConversion(org.springframework.data.domain.Persistable,
     * Object, String)
     */
    protected abstract Object handleDynamicColumn(final Y object, final String path);

    protected PageRequest getPageable(final long totalObjects, final int pageSize, final List<ColumnDef> sort) {
        final int pageNumber = this.getPageNumber(totalObjects, pageSize);
        if (sort != null && !sort.isEmpty()) {
            for (final ColumnDef columnDef : sort) {
                if (columnDef.isSortable() && columnDef.isSorted()) {
                    continue;
                }
                final Sort.Direction direction = Sort.Direction.fromStringOrNull(columnDef.getSortDirection().toString());
                columnDef.setSorted(true);
                return new PageRequest(pageNumber, pageSize, direction, columnDef.getName());
            }
        }
        return new PageRequest(pageNumber, pageSize);
    }

    protected int getPageNumber(final long totalObjects, final int pageSize) {
        if (pageSize > totalObjects) {
            return 0;
        } else {
            final int pageNumber = (int) Math.floor(totalObjects / pageSize);
            if (pageNumber * pageSize < totalObjects) {
                return 0;
            }
            return pageNumber;
        }
    }

}
