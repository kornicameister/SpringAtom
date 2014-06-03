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

package org.agatom.springatom.server.model.beans.report;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.support.EntityColumn;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.model.types.report.ReportResource;
import org.agatom.springatom.server.model.types.report.ReportSetting;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * {@code SReport} describes {@code report} by specyfying following properties:
 * <ul>
 * <li>mandatory {@link org.agatom.springatom.server.model.beans.report.SReport#title}</li>
 * <li>mandatory {@link org.agatom.springatom.server.model.beans.report.SReport#resource}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#subtitle}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#description}</li>
 * <li>optional {@link org.agatom.springatom.server.model.beans.report.SReport#settings}</li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReport.TABLE_NAME)
@Entity(name = SReport.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = SReport.ID_COLUMN, nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@JsonSerialize(include = Inclusion.NON_NULL, typing = Typing.DYNAMIC)
public class SReport
		extends PersistentVersionedObject
		implements Report {
	/** Constant <code>TABLE_NAME="reports"</code> */
	public static final  String TABLE_NAME       = "reports";
	/** Constant <code>ENTITY_NAME="SReport"</code> */
	public static final  String ENTITY_NAME      = "SReport";
	/** Constant <code>ID_COLUMN="idSReport"</code> */
	public static final  String ID_COLUMN        = "idSReport";
	private static final long   serialVersionUID = -2542100437128982892L;
	@Transient
	private final transient Map<String, Serializable> mappedSettings;
	@NotNull
	@Length(min = 5, max = 50)
	@NaturalId(mutable = false)
	@Column(name = "report_title", nullable = false, unique = true, updatable = false, length = 50)
	protected               String                    title;
	@Length(min = 5, max = 50)
	@Column(name = "report_subtitle", nullable = true, insertable = true, updatable = true, length = 50)
	protected               String                    subtitle;
	@Length(max = 200)
	@Column(name = "report_description", nullable = true, updatable = true, insertable = true, length = 200)
	protected               String                    description;
	@Embedded
	protected               SReportResource           resource;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.DETACH)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected               Set<SReportSetting<?>>    settings;
	@JoinColumn(name = "reports_master_id")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected               SReport                   reportMaster;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "reportMaster")
	protected               Set<SReport>              subReports;
	@Column(name = "report_isDynamic", nullable = true, updatable = true, insertable = true)
	protected               Boolean                   dynamic;
	@NotNull
	@Column(name = "report_class", nullable = false, updatable = true, insertable = true)
	protected               Class<?>                  reportedClass;

	/**
	 * <p>Constructor for SReport.</p>
	 */
	public SReport() {
		this.mappedSettings = Maps.newHashMap();
	}

	/** {@inheritDoc} */
	@Override
	public ReportResource getResource() {
		return this.resource;
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return title;
	}

	/** {@inheritDoc} */
	@Override
	public String getSubtitle() {
		return subtitle;
	}

	/** {@inheritDoc} */
	@Override
	public Set<ReportSetting<?>> getSettings() {
		this.requireSettings();
		return FluentIterable
				.from(this.settings)
				.transform(new Function<SReportSetting<?>, ReportSetting<?>>() {
					@Nullable
					@Override
					public ReportSetting<?> apply(@Nullable final SReportSetting<?> input) {
						return input;
					}
				}).toSet();
	}

	/** {@inheritDoc} */
	@Override
	public Serializable getSetting(final String key) {
		return this.asMap().get(key);
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, Serializable> asMap() {
		this.getSettings();
		if (this.settings.size() != this.mappedSettings.size()) {
			this.mappedSettings.clear();
		}
		for (final SReportSetting<?> setting : this.settings) {
			this.mappedSettings.put(setting.getName(), setting.getValue());
		}
		return this.mappedSettings;
	}

	private void requireSettings() {
		if (this.settings == null) {
			this.settings = Sets.newHashSet();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasSetting(@Nonnull final String key) {
		return this.asMap().containsKey(key);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasSettings() {
		return this.settings != null && !this.settings.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public Report getReportMaster() {
		return reportMaster;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Report> getSubReports() {
		this.requireSubReports();
		return FluentIterable
				.from(this.subReports)
				.transform(new Function<SReport, Report>() {
					@Nullable
					@Override
					public Report apply(@Nullable final SReport input) {
						return input;
					}
				})
				.toSet();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDynamic() {
		if (this.dynamic == null) {
			return false;
		}
		return this.dynamic;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getReportedClass() {
		return this.reportedClass;
	}

	/**
	 * <p>Setter for the field <code>reportedClass</code>.</p>
	 *
	 * @param reportedClass a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setReportedClass(final Class<?> reportedClass) {
		this.reportedClass = reportedClass;
		return this;
	}

	/**
	 * <p>Setter for the field <code>dynamic</code>.</p>
	 *
	 * @param dynamic a {@link java.lang.Boolean} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setDynamic(final Boolean dynamic) {
		this.dynamic = dynamic;
		return this;
	}

	/**
	 * <p>Setter for the field <code>subReports</code>.</p>
	 *
	 * @param subReports a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setSubReports(final Set<SReport> subReports) {
		this.subReports = subReports;
		return this;
	}

	private void requireSubReports() {
		if (this.subReports == null) {
			this.subReports = Sets.newHashSet();
		}
	}

	/**
	 * <p>Setter for the field <code>reportMaster</code>.</p>
	 *
	 * @param reportMaster a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setReportMaster(final SReport reportMaster) {
		this.reportMaster = reportMaster;
		if (!reportMaster.hasSubReport(this)) {
			reportMaster.putSubReport(this);
		}
		return this;
	}

	/**
	 * <p>Setter for the field <code>subtitle</code>.</p>
	 *
	 * @param subtitle a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setSubtitle(final String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	/**
	 * <p>Setter for the field <code>title</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setTitle(final String name) {
		this.title = name;
		return this;
	}

	/**
	 * <p>Setter for the field <code>description</code>.</p>
	 *
	 * @param description a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setDescription(final String description) {
		this.description = description;
		return this;
	}

	private boolean hasSubReport(final SReport report) {
		return !this.equals(report) && (this.subReports != null && !this.subReports.contains(report));
	}

	/**
	 * <p>Setter for the field <code>resource</code>.</p>
	 *
	 * @param jasperPath    a {@link java.lang.String} object.
	 * @param reportCfgPath a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport setResource(final String jasperPath, final String reportCfgPath) {
		this.resource = new SReportResource().setJasperPath(jasperPath).setConfigurationPath(reportCfgPath);
		return this;
	}

	/**
	 * <p>putSubReport.</p>
	 *
	 * @param report a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReport} object.
	 */
	public SReport putSubReport(final SReport report) {
		this.requireSubReports();
		if (report.reportMaster == null || !report.reportMaster.equals(this)) {
			report.setReportMaster(this);
		}
		this.subReports.add(report);
		return this;
	}

	/**
	 * <p>putSetting.</p>
	 *
	 * @param key   a {@link java.lang.String} object.
	 * @param value a VAL object.
	 * @param <VAL> a VAL object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReportSetting} object.
	 *
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public <VAL extends Serializable> SReportSetting<?> putSetting(final String key, final VAL value) throws IllegalArgumentException {
		final Class<? extends Serializable> valueClass = value.getClass();
		SReportSetting<?> setting = null;
		if (ClassUtils.isAssignable(String.class, valueClass)) {
			setting = new SReportStringSetting().setValue((String) value).setName(key);
		} else if (ClassUtils.isAssignable(Number.class, valueClass)) {
			setting = new SReportNumberSetting().setValue((Number) value).setName(key);
		} else if (ClassUtils.isAssignable(Boolean.class, valueClass)) {
			setting = new SReportBooleanSetting().setValue((Boolean) value).setName(key);
		}
		if (setting == null) {
			throw new IllegalArgumentException(String.format("%s is not assignable to any of following types = [%s]",
					ClassUtils.getShortName(valueClass),
					Lists.newArrayList(
							ClassUtils.getShortName(String.class),
							ClassUtils.getShortName(Number.class),
							ClassUtils.getShortName(Boolean.class)
					)
			));
		}
		return this.putSetting(setting);
	}

	/**
	 * <p>putSetting.</p>
	 *
	 * @param setting a {@link org.agatom.springatom.server.model.beans.report.SReportSetting} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.report.SReportSetting} object.
	 */
	public SReportSetting<?> putSetting(final SReportSetting<?> setting) {
		this.requireSettings();
		this.settings.add(setting.setReport(this));
		this.mappedSettings.put(setting.getName(), setting.getValue());
		return setting;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.getTitle();
	}

	public static enum Columns
			implements EntityColumn {
		NAME {
			@Override
			public String getName() {
				return "name";
			}
		},
		DESCRIPTION {
			@Override
			public String getName() {
				return "description";
			}
		},
		CREATED_AT {
			@Override
			public String getName() {
				return "createdAt";
			}
		},
		ENTITIES {
			@Override
			public String getName() {
				return "entities";
			}
		}
	}
}
