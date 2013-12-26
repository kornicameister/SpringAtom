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

package org.agatom.springatom.component.builders.table;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Predicate;
import org.agatom.springatom.component.ComponentConstants;
import org.agatom.springatom.component.builders.DefaultComponentBuilder;
import org.agatom.springatom.component.builders.EntityAware;
import org.agatom.springatom.component.builders.exception.ComponentException;
import org.agatom.springatom.component.builders.exception.ComponentTableException;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.agatom.springatom.component.elements.table.TableComponent;
import org.agatom.springatom.component.helper.TableComponentHelper;
import org.agatom.springatom.component.request.beans.ComponentTableRequest;
import org.agatom.springatom.core.invoke.InvokeUtils;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
 * that eases and helps to build a table component {@link org.agatom.springatom.component.elements.table.TableComponent}.
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

            final Predicate predicate = this.getPredicate(Long.valueOf(dc.getContextKey()));
            final long countInContext = this.repository.count(predicate);

            final Page<Y> all = this.repository.findAll(
                    predicate,
                    this.getPageable(countInContext, dc.getPageSize(), dc.getSortingColumnDefs())
            );

            final List<Map<String, Object>> data = Lists.newArrayList();

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
                    if (value == null) {
                        value = "???";
                    }
                    map.put(path, value);

                    this.logger.trace(String.format("processed column %s to %s", columnDef.getName(), value));
                }
                if (!map.isEmpty()) {
                    data.add(map);
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

    protected abstract Predicate getPredicate(final Long contextKey);
}
