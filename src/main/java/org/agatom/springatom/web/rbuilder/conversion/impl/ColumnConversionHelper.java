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

package org.agatom.springatom.web.rbuilder.conversion.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch;
import org.agatom.springatom.web.rbuilder.conversion.ConversionHelper;
import org.agatom.springatom.web.rbuilder.conversion.type.RBuilderConvertiblePair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.ReadablePartial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * {@code ConversionHelper} is a utility class designed to resolve set of matched
 * types that one type can be converted to in context of data rendering in {@code RBuilder}.
 * Class relies on entries in {@code rbuilder.properties}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(ColumnConversionHelper.SERVICE_NAME)
@Description(value = "ConversionHelper eases with resolving the entities and their properties mapping for RBuilder")
public class ColumnConversionHelper
		implements ConversionHelper<RBuilderColumn> {
	/** Constant <code>SERVICE_NAME="reportBuilderConversionHelper"</code> */
	protected static final String                      SERVICE_NAME      = "reportBuilderConversionHelper";
	private static final   Logger                      LOGGER            = Logger.getLogger(ColumnConversionHelper.class);
	private static final   String                      BRANCH_ROOT_KEY   = "reports.objects.conversion.types";
	@Autowired
	private                FormattingConversionService conversionService = null;
	@Autowired
	@Qualifier("rbuilderProperties")
	private                Properties                  properties        = null;

	/** {@inheritDoc} */
	@Override
	public Set<RBuilderConvertiblePair> getConvertiblePairs(final RBuilderColumn column) {
		LOGGER.trace(String.format("Resolving convertible definition for %s", column.getColumnClass()));
		final Set<ConvertiblePair> classes = Sets.newHashSet();
		final Class<?> columnClass = column.getColumnClass();
		final ColumnTypeConversionBranch columnType = this.getPossibleColumnType(columnClass);

		LOGGER.trace(String.format("%s predicted with type %s", ClassUtils.getShortName(columnClass), columnType));

		classes.addAll(this.resolve(columnClass, this.getBranchTypes(columnType)));

		LOGGER.trace(String.format("Resolved %s convertible definitions for %s", classes.size(), column.getColumnClass()));
		return FluentIterable
				.from(classes)
				.transform(new Function<ConvertiblePair, RBuilderConvertiblePair>() {
					@Nullable
					@Override
					public RBuilderConvertiblePair apply(@Nullable final ConvertiblePair input) {
						assert input != null;
						return new RBuilderConvertiblePair().setConvertiblePair(input).setBranch(columnType);
					}
				}).toSet();
	}

	private Collection<ConvertiblePair> resolve(final Class<?> clazz, final Set<Class<?>> branchTypes) {
		return FluentIterable
				.from(branchTypes)
				.filter(new Predicate<Class<?>>() {
					@Override
					public boolean apply(@Nullable final Class<?> input) {
						return conversionService.canConvert(clazz, input);
					}
				})
				.transform(new Function<Class<?>, ConvertiblePair>() {
					@Nullable
					@Override
					public ConvertiblePair apply(@Nullable final Class<?> input) {
						return new ConvertiblePair(clazz, input);
					}
				})
				.toSet();
	}

	private Set<Class<?>> getBranchTypes(final ColumnTypeConversionBranch columnType) {
		final String property = this.properties.getProperty(this.getBranchKey(columnType));

		Assert.notNull(property);
		Assert.isTrue(!property.isEmpty());

		final Set<String> strings = StringUtils.commaDelimitedListToSet(property);
		Assert.notEmpty(strings);

		return FluentIterable
				.from(strings)
				.transform(new Function<String, Class<?>>() {
					@Nullable
					@Override
					public Class<?> apply(@Nullable final String input) {
						return ClassUtils.resolveClassName(input, getClass().getClassLoader());
					}
				}).toSet();
	}

	private String getBranchKey(final ColumnTypeConversionBranch columnType) {
		return String.format("%s.%s", BRANCH_ROOT_KEY, columnType.name().toLowerCase());
	}

	/** {@inheritDoc} */
	@Override
	public ColumnTypeConversionBranch getPossibleColumnType(final Class<?> columnClass) {
		final boolean entityAnnotationPresent = columnClass.isAnnotationPresent(Entity.class);
		if (ClassUtils.isAssignable(Collection.class, columnClass) && !entityAnnotationPresent) {
			return ColumnTypeConversionBranch.COLLECTION;
		} else if (entityAnnotationPresent) {
			return ColumnTypeConversionBranch.ENTITY;
		} else if (ClassUtils.isAssignable(DateTime.class, columnClass) || ClassUtils.isAssignable(ReadablePartial.class, columnClass)) {
			return ColumnTypeConversionBranch.DATE;
		}
		return ColumnTypeConversionBranch.BASIC;
	}
}
