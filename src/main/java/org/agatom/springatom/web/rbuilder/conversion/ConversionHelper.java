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

package org.agatom.springatom.web.rbuilder.conversion;

import org.agatom.springatom.web.rbuilder.bean.RBuilderBean;
import org.agatom.springatom.web.rbuilder.conversion.type.RBuilderConvertiblePair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * {@code ConversionHelper} is a utility class designed to resolve set of matched
 * types that one type can be converted to in context of data rendering in {@code RBuilder}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface ConversionHelper<T extends RBuilderBean> {

	/** Constant <code>CACHE_NAME="org.agatom.springatom.web.rbuilder.conv"{trunked}</code> */
	String CACHE_NAME = "org.agatom.springatom.web.rbuilder.conversion.ConversionHelper";

	/**
	 * <p>getConvertiblePairs.</p>
	 *
	 * @param column a T object.
	 *
	 * @return a {@link java.util.Set} object.
	 */
	@Cacheable(value = CACHE_NAME, key = "#column.columnClass + '.ConvertiblePairs'")
	Set<RBuilderConvertiblePair> getConvertiblePairs(@NotNull final T column);

	/**
	 * <p>getPossibleColumnType.</p>
	 *
	 * @param columnClass a {@link java.lang.Class} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch} object.
	 */
	@Cacheable(value = CACHE_NAME, key = "#column.columnClass + '.PossibleColumnType'")
	ColumnTypeConversionBranch getPossibleColumnType(Class<?> columnClass);

}
