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

package org.agatom.springatom.webmvc.controllers.components;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.infopages.builder.InfoPageResponseWrapper;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.table.TableResponseRow;
import org.agatom.springatom.web.component.table.TableResponseWrapper;
import org.agatom.springatom.web.component.table.elements.TableComponent;
import org.agatom.springatom.web.component.table.elements.extjs.ExtJSTable;
import org.agatom.springatom.webmvc.converters.du.ConvertibleValueWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.binding.convert.ConversionExecutionException;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * {@code CDRReturnValueConverter} is a customized {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor}
 * that gets single {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} and process received values
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_APPLICATION)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CDRReturnValueConverter {
	private static final Logger                      LOGGER            = Logger.getLogger(CDRReturnValueConverter.class);
	private static final String                      BUILT_BY          = "builtBy";
	private static final String                      SUCCESS           = "success";
	private static final String                      TIME              = "time";
	private static final String                      ERROR             = "error";
	private static final String                      SIZE              = "size";
	private static final String                      DATA              = "data";
	private static final String                      META_DATA         = "metaData";
	private static final String                      NO_DATA           = "[no_data_found]";
	private static final String                      MESSAGE           = "message";
	@Autowired
	@Qualifier("springAtomConversionService")
	private              FormattingConversionService conversionService = null;

	/**
	 * <p>convert.</p>
	 *
	 * @param response a {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} object.
	 * @param request  a {@link org.agatom.springatom.web.component.core.data.ComponentDataRequest} object.
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> convert(final ComponentDataResponse response, final ComponentDataRequest request) {
		LOGGER.trace(String.format("convert(returnValue=%s)", response));

		final Map<String, Object> value = Maps.newHashMap();
		final Object data = response.getData();

		try {
			value.put(BUILT_BY, response.getBuiltBy());
			value.put(SUCCESS, response.isSuccess());
			value.put(TIME, response.getTime());
			if (!response.isSuccess() && data == null) {
				value.put(ERROR, response.getError());
				value.put(SIZE, 0);
			} else {
				this.getConvertedValue(data, request, value);
				value.put(SIZE, this.getSizeOfData(data, request.getComponent()));
			}
			value.put(MESSAGE, this.getConversionMessage(data, response, request));
		} catch (Exception exp) {
			LOGGER.fatal(String.format("Failed to convert(data=%s)...", data), exp);
			throw new ConversionExecutionException(data, response.getClass(), Map.class, exp);
		}

		return value;
	}

	private String getConversionMessage(final Object data, final ComponentDataResponse response, final ComponentDataRequest request) {
		return String.format("%s\ndataType=%s\nresponseType=%s\nrequestType=%s",
				ClassUtils.getShortName(this.getClass()),
				data == null ? "null" : ClassUtils.getShortName(data.getClass()),
				ClassUtils.getShortName(response.getClass()),
				ClassUtils.getShortName(request.getClass())
		);
	}

	private Object getConvertedValue(final Object value, final ComponentDataRequest request, final Map<String, Object> ret) {
		if (value == null) {
			return NO_DATA;
		}
		if (ClassUtils.isAssignableValue(InfoPageResponseWrapper.class, value)) {
			return ret.put(DATA, this.convertFromInfoPageDataResponse((InfoPageResponseWrapper) value, request));
		} else if (ClassUtils.isAssignableValue(ExtJSTable.class, value)) {
			return ret.put(META_DATA, value);
		} else if (ClassUtils.isAssignableValue(TableResponseWrapper.class, value)) {
			return ret.put(DATA, this.convertFromTableResponse((TableResponseWrapper) value, request));
		}
		throw new IllegalArgumentException(String.format("%s not supported", ClassUtils.getShortName(value.getClass())));
	}

	private Object convertFromTableResponse(final TableResponseWrapper value, final ComponentDataRequest request) {
		final List<TableResponseRow> data = value.getRows();
		if (CollectionUtils.isEmpty(data)) {
			return null;
		}
		return FluentIterable.from(data).transform(new Function<TableResponseRow, Object>() {
			@Nullable
			@Override
			public Object apply(@Nullable final TableResponseRow row) {
				if (row == null) {
					return null;
				}
				final Map<String, Object> rowData = row.getRowData();
				for (final String key : rowData.keySet()) {
					final ConvertibleValueWrapper webData = new ConvertibleValueWrapper();

					webData.setValue(rowData.get(key));
					webData.setKey(key);
					webData.setSource(row.getSource());
					webData.setRequest(request);

					rowData.put(key, conversionService.convert(webData, Object.class));
				}

				return rowData;
			}
		}).toList();
	}

	private Object convertFromInfoPageDataResponse(final InfoPageResponseWrapper value, final ComponentDataRequest request) {
		final Map<String, Object> data = value.getData();
		final Map<String, Object> retData = Maps.newHashMap();

		for (final String key : data.keySet()) {
			final ConvertibleValueWrapper webData = new ConvertibleValueWrapper();

			webData.setValue(data.get(key));
			webData.setKey(key);
			webData.setSource(value.getSource());
			webData.setRequest(request);

			retData.put(key, this.conversionService.convert(webData, Object.class));
		}

		return retData;
	}

	private Object getSizeOfData(final Object data, final org.agatom.springatom.web.component.core.Component component) {
		if (ClassUtils.isAssignableValue(TableResponseWrapper.class, data)) {
			return ((TableResponseWrapper) data).getRows().size();
		}
		if (ClassUtils.isAssignableValue(InfoPageComponent.class, component) || ClassUtils.isAssignableValue(TableComponent.class, data)) {
			return 1;
		}
		return ((Map<?, ?>) data).size();
	}
}
