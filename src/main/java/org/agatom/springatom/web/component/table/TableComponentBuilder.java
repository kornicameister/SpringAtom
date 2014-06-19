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

package org.agatom.springatom.web.component.table;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.SRepository;
import org.agatom.springatom.web.component.core.builders.AbstractComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.exception.ComponentException;
import org.agatom.springatom.web.component.core.builders.exception.ComponentPathEvaluationException;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.request.TableComponentRequest;
import org.agatom.springatom.web.component.table.request.TableRequestColumnDef;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revision;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code TableComponentBuilder} provides generic functionality for all the implementing classes
 * that eases and helps to build a table component {@link org.agatom.springatom.web.component.table.elements.TableComponent}.
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
 * <li>removed method used previously in data transforming operation, now handled through {@link org.agatom.springatom.webmvc.converters.du.WebDataGenericConverter}</li>
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
	protected ApplicationContext        context           = null;
	@Autowired
	protected SBasicRepository<Y, Long> repository        = null;
	protected Class<Y>                  entity            = null;
	@Autowired
	private   EntityDescriptors         entityDescriptors = null;

	@PostConstruct
	@SuppressWarnings("unchecked")
	private void postConstruct() {
		this.entity = (Class<Y>) GenericTypeResolver.resolveTypeArguments(getClass(), TableComponentBuilder.class)[1];
	}

	/** {@inheritDoc} */
	@Override
	protected Object buildData(final ComponentDataRequest dataRequest) throws ComponentException {
		final TableComponentRequest request = (TableComponentRequest) dataRequest.getComponentRequest();
		final long revision = request.getRevision();
		final Set<ComponentRequestAttribute> attributes = request.getAttributes();
		if (revision > -1) {
			// TODO how to merge predicate and revision ?
			final SRepository<Y, Long, Long> sRepository = (SRepository<Y, Long, Long>) this.repository;
			final Revision<Long, Y> inRevision = sRepository.findInRevision(request.getId(), request.getRevision());
		} else {
			final Predicate predicate = this.getPredicate(request);
			final long countInContext = predicate != null ?
					this.repository.count(predicate) :
					this.repository.count();
			final List<TableResponseRow> data = Lists.newArrayListWithExpectedSize((int) countInContext);

			if (countInContext != 0) {
				final Page<Y> all = this.getAllEntities(request, predicate, countInContext);
				for (final Y object : all) {
					final BeanWrapper wrapper = new BeanWrapperImpl(object);
					final Map<String, Object> rowData = Maps.newHashMapWithExpectedSize(attributes.size());
					for (ComponentRequestAttribute attribute : attributes) {
						final String path = attribute.getPath();
						rowData.put(path, wrapper.getPropertyValue(path));
					}
					data.add(new TableResponseRow().setSource(object).setRowData(rowData));
				}
			}
			return new TableResponseWrapper().setRows(data);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected EntityDescriptor getEntityDescriptor() {
		return this.getEntityDescriptor(this.entity);
	}

	/** {@inheritDoc} */
	@Override
	protected EntityDescriptor getEntityDescriptor(final Class<?> forClass) {
		return this.entityDescriptors.getDescriptor(forClass);
	}

	/**
	 * Using passed {@link org.agatom.springatom.web.component.table.request.TableComponentRequest} method
	 * constructs {@link com.mysema.query.types.Predicate} for the {@link org.springframework.data.repository.Repository}
	 *
	 * @param dc current request data holder
	 *
	 * @return valid predicate
	 *
	 * @throws ComponentPathEvaluationException if predicate was impossible to built
	 */
	private Predicate getPredicate(final TableComponentRequest dc) throws ComponentPathEvaluationException {
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
		final Predicate predicate = this.getPredicate(dc.getId(), dc.getDomain());
		if (predicate == null) {
			throw new ComponentPathEvaluationException(String
					.format("failed to evaluate %s for contextClass=%s", ClassUtils.getShortName(Predicate.class), ClassUtils
							.getShortName(dc.getDomain())));
		}
		logger.debug(String.format("processing with predicate %s", predicate));
		return predicate;
	}

	/**
	 * Method checks if table builder was called in some specific context
	 *
	 * @param dc current request data holder
	 *
	 * @return true if in context, false otherwise
	 */
	private boolean isInContext(final TableComponentRequest dc) {
		return (dc.getId() != null && dc.getId() > 0) && (dc.getClass() != null);
	}

	/**
	 * Internal method for {@link org.agatom.springatom.web.component.table.TableComponentBuilder}'s that extends this base class
	 *
	 * @param contextKey   value set in another {@link org.agatom.springatom.web.component.core.builders.Builder} which is most likely the {@link
	 *                     org.springframework.data.domain.Persistable#getId()} of a {@code contextClass}
	 * @param contextClass points to a {@link Class} correlating to the {@code contextKey}
	 *
	 * @return valid {@link com.mysema.query.types.Predicate} or null
	 *
	 * @see org.agatom.springatom.web.component.table.TableComponentBuilder#getPredicate(org.agatom.springatom.web.component.table.request.TableComponentRequest)
	 */
	protected abstract Predicate getPredicate(final Long contextKey, final Class<?> contextClass);

	private Page<Y> getAllEntities(final TableComponentRequest dc, final Predicate predicate, final long countInContext) {
		final PageRequest pageable = this.getPageable(countInContext, dc.getPage(), dc.getLimit(), dc.getSortingColumnDefs());
		Page<Y> page;

		if (predicate != null) {
			page = this.repository.findAll(predicate, pageable);
		} else {
			page = this.repository.findAll(pageable);
		}

		return page;
	}

	/**
	 * <p>getPageable.</p>
	 *
	 * @param total a long.
	 * @param limit a int.
	 * @param sort  a {@link java.util.List} object.
	 *
	 * @return a {@link org.springframework.data.domain.PageRequest} object.
	 */
	protected PageRequest getPageable(final long total, int pageNumber, final int limit, final List<TableRequestColumnDef> sort) {
		pageNumber = pageNumber - 1;
		if (!CollectionUtils.isEmpty(sort)) {
			for (final TableRequestColumnDef columnDef : sort) {
				if (columnDef.isSortable() && columnDef.isSorted()) {
					continue;
				}
				final Sort.Direction direction = Sort.Direction.fromStringOrNull(columnDef.getSortDirection().toString());
				columnDef.setSorted(true);
				return new PageRequest(pageNumber, limit, direction, columnDef.getName());
			}
		}
		return new PageRequest(pageNumber, limit);
	}

	/**
	 * <p>getPageNumber.</p>
	 *
	 * @param totalObjects a long.
	 * @param pageSize     a int.
	 *
	 * @return a int.
	 */
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

	/**
	 * Returns {@link org.agatom.springatom.web.locale.beans.LocalizedClassModel} for {@link #entity}
	 *
	 * @return localized class model
	 */
	protected LocalizedClassModel<Y> getLocalizedClassModel() {
		return this.messageSource.getLocalizedClassModel(this.entity, LocaleContextHolder.getLocale());
	}

}
