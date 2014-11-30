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

package org.agatom.springatom.cmp.component.table;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import org.agatom.springatom.cmp.component.core.builders.AbstractComponentDefinitionBuilder;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentPathEvaluationException;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.cmp.component.table.elements.TableComponent;
import org.agatom.springatom.cmp.component.table.elements.ng.NgTable;
import org.agatom.springatom.cmp.component.table.elements.ng.NgTableColumn;
import org.agatom.springatom.cmp.locale.beans.LocalizedClassModel;
import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.creators.PersistableOid;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * {@code TableComponentBuilder} provides generic functionality for all the implementing classes
 * that eases and helps to build a table component {@link org.agatom.springatom.cmp.component.table.elements.TableComponent}.
 *
 * Changelog:
 * <ol>
 * <li>0.0.1 to 0.0.2
 * <ul>
 * <li>Removed EntityAware</li>
 * <li>Repository is set via dependency injection</li>
 * <li>entity is read via GenericTypeResolver</li>
 * </ul>
 * </li>
 * <li>0.0.2 to 0.0.3</li>
 * <ul>
 * <li>removed method used previously in data transforming operation</li>
 * </ul>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
abstract public class TableComponentBuilder<COMP extends TableComponent, Y extends Persistable<?>>
        extends AbstractComponentDefinitionBuilder<COMP> {
    @Autowired
    protected ApplicationContext context = null;
    //    @Autowired
//    protected SBasicRepository<Y, Long> repository = null;
    protected Class<Y>           entity  = null;

    @PostConstruct
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void postConstruct() {
        this.entity = (Class<Y>) GenericTypeResolver.resolveTypeArguments(getClass(), TableComponentBuilder.class)[1];
    }

    /** {@inheritDoc} */
    @Override
    protected Object buildData(final ComponentDataRequest request) throws ComponentException {
        final NgTable component = (NgTable) request.getComponent();
        final Predicate predicate = this.getPredicate(request);
        final long countInContext = /*predicate != null ?
                this.repository.count(predicate) :
                this.repository.count()*/ -1;
        final Collection<Map<String, Object>> data = Lists.newLinkedList();

        final Set<NgTableColumn> content = component.getContent();

        if (countInContext != 0) {
            final Page<Y> all = this.getAllEntities(request, predicate, countInContext);
            for (final Y object : all) {
                final BeanWrapper wrapper = new BeanWrapperImpl(object);
                final Map<String, Object> rowData = Maps.newHashMapWithExpectedSize(content.size());
                for (final NgTableColumn column : content) {
                    final String path = column.getDataIndex();
                    rowData.put(path, wrapper.getPropertyValue(path));
                }
                data.add(rowData);
            }
        }
        return data;
    }

    private Predicate getPredicate(final ComponentDataRequest dc) throws ComponentPathEvaluationException {
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
        final Predicate predicate = this.getPredicate(dc.getOid());
        if (predicate == null) {
            throw new ComponentPathEvaluationException(String.format("failed to evaluate %s for oid=%s", ClassUtils.getShortName(Predicate.class), dc.getOid()));
        }
        logger.debug(String.format("processing with predicate %s", predicate));
        return predicate;
    }

    @SuppressWarnings("ConstantConditions")
    private Page<Y> getAllEntities(final ComponentDataRequest dc, final Predicate predicate, final long countInContext) {

        Integer pageParam = (Integer) dc.getParametersMap().get("page");
        Integer pageSizeParam = (Integer) dc.getParametersMap().get("size");

        if (pageParam == null) {
            pageParam = 0;
        }
        if (pageSizeParam == null) {
            pageSizeParam = Long.valueOf(countInContext).intValue();
        }

        final PageRequest pageable = this.getPageable(pageParam, pageSizeParam);

        if (predicate != null) {
//            return this.repository.findAll(predicate, pageable);
        }

//        return this.repository.findAll(pageable);
        return null;
    }

    private boolean isInContext(final ComponentDataRequest dc) {
        return dc.getOid() != null;
    }

    /**
     * Method checks if table builder was called in some specific context
     *
     * @param oid OID from the request
     *
     * @return true if in context, false otherwise
     */

    private Predicate getPredicate(final SOid oid) {
        Assert.isInstanceOf(PersistableOid.class, oid);
        final PersistableOid persistableOid = (PersistableOid) oid;
        return this.getPredicate(persistableOid.getObjectId(), persistableOid.getObjectClass());
    }

    /**
     * <p>getPageable.</p>
     *
     * @param limit      a int.
     * @param pageNumber a int.
     *
     * @return a {@link org.springframework.data.domain.PageRequest} object.
     */
    protected PageRequest getPageable(int pageNumber, final int limit) {
        if (pageNumber - 1 == 0 && pageNumber == 1) {
            pageNumber -= 1;
        }
        return new PageRequest(pageNumber, limit);
    }

    /**
     * Internal method for {@link org.agatom.springatom.cmp.component.table.TableComponentBuilder}'s that extends this base class
     *
     * @param contextKey   value set in another {@link org.agatom.springatom.cmp.component.core.builders.Builder} which is most likely the {@link
     *                     org.springframework.data.domain.Persistable#getId()} of a {@code contextClass}
     * @param contextClass points to a {@link Class} correlating to the {@code contextKey}
     *
     * @return valid {@link com.mysema.query.types.Predicate} or null
     */
    protected abstract Predicate getPredicate(final Long contextKey, final Class<?> contextClass);

    /**
     * Returns {@link org.agatom.springatom.cmp.locale.beans.LocalizedClassModel} for {@link #entity}
     *
     * @return localized class model
     */
    protected LocalizedClassModel<Y> getLocalizedClassModel() {
        return this.messageSource.getLocalizedClassModel(this.entity, LocaleContextHolder.getLocale());
    }

    protected NgTableColumn newColumn(final Path<?> path, final AttributeDisplayAs displayAs, final LocalizedClassModel<Y> lModel) {
        return (NgTableColumn) new NgTableColumn()
                .setTooltip(lModel.getLocalizedAttribute(this.getAttributeName(path)))
                .setDataIndex(this.getAttributeName(path))
                .setText(lModel.getLocalizedAttribute(this.getAttributeName(path)))
                .setDisplayAs(displayAs);
    }

}
