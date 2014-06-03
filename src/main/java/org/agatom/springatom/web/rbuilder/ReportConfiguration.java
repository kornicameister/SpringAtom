/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.rbuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.beans.WebBean;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.springframework.binding.collection.MapAdaptable;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * {@code ReportConfiguration} is a {@code bean} that is used to carry the report configuration.
 * It means that such information like:
 * <ol>
 * <li>title, subtitle, description etc.</li>
 * <li>{@link org.agatom.springatom.web.rbuilder.ReportConfiguration#settings}</li>
 * <li>selected entities {@link org.agatom.springatom.web.rbuilder.ReportConfiguration#entities}</li>
 * <li>selected columns for the entities {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity#getColumns()}</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportConfiguration
		implements WebBean,
		Iterable<RBuilderEntity>,
		MapAdaptable<String, RBuilderEntity> {
	private static final String                    BEAN_ID          = "reportConfiguration";
	private static final Logger                    LOGGER           = Logger.getLogger(ReportConfiguration.class);
	private static final long                      serialVersionUID = 772657130362339934L;
	@NotNull
	@Length(min = 5, max = 50)
	protected            String                    title            = null;
	@Length(max = 50)
	protected            String                    subtitle         = null;
	@Length(max = 200)
	protected            String                    description      = null;
	/**
	 * {@link java.util.Map} with values to be used in generating the report instance
	 */
	protected            Map<String, Serializable> settings         = Maps.newHashMap();
	@NotNull
	@Size(min = 1)
	protected            Set<RBuilderEntity>       entities         = Sets.newTreeSet();

	/**
	 * <p>Getter for the field <code>title</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <p>Setter for the field <code>title</code>.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration setTitle(final String title) {
		this.title = title;
		return this;
	}

	/**
	 * <p>Getter for the field <code>subtitle</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * <p>Setter for the field <code>subtitle</code>.</p>
	 *
	 * @param subtitle a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration setSubtitle(final String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	/**
	 * <p>Getter for the field <code>description</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>Setter for the field <code>description</code>.</p>
	 *
	 * @param description a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration setDescription(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * <p>Getter for the field <code>settings</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Serializable> getSettings() {
		if (this.settings == null) {
			this.settings = Maps.newHashMap();
		}
		return settings;
	}

	/**
	 * <p>Setter for the field <code>settings</code>.</p>
	 *
	 * @param settings a {@link java.util.Map} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration setSettings(final Map<String, Serializable> settings) {
		this.settings = settings;
		return this;
	}

	/**
	 * <p>hasEntity.</p>
	 *
	 * @param entity a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 *
	 * @return a boolean.
	 */
	public boolean hasEntity(final RBuilderEntity entity) {
		return this.entities.contains(entity);
	}

	/**
	 * <p>hasEntity.</p>
	 *
	 * @param javaClass a {@link java.lang.Class} object.
	 *
	 * @return a boolean.
	 */
	public boolean hasEntity(final Class<?> javaClass) {
		return FluentIterable
				.from(this.entities)
				.firstMatch(new Predicate<RBuilderEntity>() {
					@Override
					public boolean apply(@Nullable final RBuilderEntity input) {
						return input != null && input.getJavaClass().equals(javaClass);
					}
				}).isPresent();
	}

	/**
	 * <p>hasEntities.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasEntities() {
		return !this.entities.isEmpty();
	}

	/**
	 * <p>clearEntities.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration clearEntities() {
		this.clearColumns();
		this.entities.clear();
		return this;
	}

	/**
	 * <p>clearColumns.</p>
	 */
	public void clearColumns() {
		for (final RBuilderEntity entity : this.entities) {
			if (this.hasColumn(entity)) {
				entity.clearColumns();
			}
		}
	}

	/**
	 * <p>hasColumn.</p>
	 *
	 * @param entity a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 *
	 * @return a boolean.
	 */
	public boolean hasColumn(final RBuilderEntity entity) {
		return entity.hasColumns();
	}

	/**
	 * <p>Getter for the field <code>entities</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<RBuilderEntity> getEntities() {
		return ImmutableList.copyOf(this.entities);
	}

	/**
	 * <p>Setter for the field <code>entities</code>.</p>
	 *
	 * @param entities a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration setEntities(final Set<RBuilderEntity> entities) {
		for (final RBuilderEntity entity : entities) {
			this.putEntity(entity);
		}
		return this;
	}

	/**
	 * <p>putEntity.</p>
	 *
	 * @param entity a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration putEntity(final RBuilderEntity entity) {
		final boolean add = this.entities.add(entity);
		if (!add) {
			LOGGER.trace(String.format("%s already exists in context", entity));
		}
		return this;
	}

	/**
	 * Removes all columns from {@code reportableColumns} for {@code entity}
	 *
	 * @param entity            entity to remove columns from
	 * @param reportableColumns columns to be removed
	 *
	 * @return this {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
	 *
	 * @see org.agatom.springatom.web.rbuilder.ReportConfiguration#popColumn(org.agatom.springatom.web.rbuilder.bean.RBuilderEntity,
	 * org.agatom.springatom.web.rbuilder.bean.RBuilderColumn)
	 */
	public ReportConfiguration popColumns(final RBuilderEntity entity, final Collection<RBuilderColumn> reportableColumns) {
		if (CollectionUtils.isEmpty(reportableColumns)) {
			return this;
		}
		for (final RBuilderColumn column : reportableColumns) {
			this.popColumn(entity, column);
		}
		return this;
	}

	/**
	 * Remove single {@code reportableColumn} from {@code entity}
	 *
	 * @param entity           entity to remove columns from
	 * @param reportableColumn columns to be removed
	 *
	 * @return this {@link org.agatom.springatom.web.rbuilder.ReportConfiguration}
	 *
	 * @see org.agatom.springatom.web.rbuilder.bean.RBuilderEntity#removeColumn(org.agatom.springatom.web.rbuilder.bean.RBuilderColumn)
	 */
	public ReportConfiguration popColumn(final RBuilderEntity entity, final RBuilderColumn reportableColumn) {
		entity.removeColumn(reportableColumn);
		return this;
	}

	/**
	 * <p>putColumns.</p>
	 *
	 * @param entity           a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 * @param reportableColumn a {@link java.util.Collection} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration putColumns(final RBuilderEntity entity, final Collection<RBuilderColumn> reportableColumn) {
		if (CollectionUtils.isEmpty(reportableColumn)) {
			return this;
		}
		this.putEntity(entity);
		for (final RBuilderColumn column : reportableColumn) {
			this.putColumn(entity, column);
		}
		return this;
	}

	/**
	 * <p>putColumn.</p>
	 *
	 * @param entity           a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 * @param reportableColumn a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.ReportConfiguration} object.
	 */
	public ReportConfiguration putColumn(final RBuilderEntity entity, final RBuilderColumn reportableColumn) {
		this.putEntity(entity);
		entity.addColumn(reportableColumn);
		return this;
	}

	/**
	 * <p>hasColumn.</p>
	 *
	 * @param reportableColumn a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderColumn} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 */
	public RBuilderEntity hasColumn(final RBuilderColumn reportableColumn) {
		final Optional<RBuilderEntity> present = FluentIterable
				.from(this.entities)
				.firstMatch(new Predicate<RBuilderEntity>() {
					@Override
					public boolean apply(@Nullable final RBuilderEntity input) {
						return input != null && input.hasColumn(reportableColumn);
					}
				});
		return present.get();
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}

	/**
	 * <p>getSize.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return this.entities.size();
	}

	/**
	 * <p>getTotalSize.</p>
	 *
	 * @return a int.
	 */
	public int getTotalSize() {
		int size = 0;
		for (final RBuilderEntity entity : this.entities) {
			size += entity.getColumns().size();
		}
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(entities)
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<RBuilderEntity> iterator() {
		return this.entities.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, RBuilderEntity> asMap() {
		final Map<String, RBuilderEntity> entityMap = Maps.newLinkedHashMap();
		for (final RBuilderEntity entity : this.entities) {
			entityMap.put(entity.getName(), entity);
		}
		return Collections.unmodifiableMap(entityMap);
	}
}
