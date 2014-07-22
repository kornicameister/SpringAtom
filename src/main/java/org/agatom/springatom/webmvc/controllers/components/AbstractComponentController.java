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

import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.web.component.core.request.ComponentRequest;
import org.agatom.springatom.webmvc.controllers.components.data.CmpResource;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionExecutionException;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Iterator;
import java.util.Map;

/**
 * {@code AbstractComponentController} is a foundation for <b>components controller</b>
 * Contains logic required to create {@link org.agatom.springatom.web.component.core.data.ComponentDataRequest}
 * and for exception handling
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 02.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.3
 * @since 0.0.1
 */
abstract class AbstractComponentController {
	private static final Logger                     LOGGER                  = Logger.getLogger(AbstractComponentController.class);
	@Autowired
	protected            ComponentBuilderRepository builderRepository       = null;
	@Autowired
	protected            InfoPageControllerUtils    infoPageControllerUtils = null;
	@Autowired
	private              CDRReturnValueConverter    converter               = null;

	/**
	 * Pushes data through {@link org.agatom.springatom.webmvc.controllers.components.CDRReturnValueConverter}
	 * which applies web converters logic to the data
	 *
	 * @param request component request
	 * @param data    component raw data
	 *
	 * @return adjusted data
	 *
	 * @throws ControllerTierException if any
	 */
	protected CmpResource<?> toComponentResource(final ComponentDataRequest request, final ComponentDataResponse data) throws ControllerTierException {
		try {
			return this.converter.convert(data, request);
		} catch (Exception exp) {
			throw new ControllerTierException("Failed to convert data using CDRReturnValueConverter", exp);
		}
	}

	/**
	 * Combines {@link org.agatom.springatom.web.component.core.request.ComponentRequest} and {@link org.springframework.web.context.request.WebRequest}
	 * into valid {@link org.agatom.springatom.web.component.core.data.ComponentDataRequest}.
	 *
	 * @param cmpRequest component request
	 * @param webRequest web request
	 *
	 * @return {@link org.agatom.springatom.web.component.core.data.ComponentDataRequest} instance
	 */
	protected ComponentDataRequest combineRequest(final ComponentRequest cmpRequest, final WebRequest webRequest) {
		LOGGER.trace(String.format("Combining request for cmpRequest=%s,webRequest=%s", cmpRequest, webRequest.getClass()));

		final ModelMap modelMap = new ModelMap(webRequest.getParameterMap());
		modelMap.addAttribute(webRequest.getLocale());
		modelMap.put("user", webRequest.getUserPrincipal());

		Map<String, Object> localMap = Maps.newHashMap();

		final Iterator<String> headerNames = webRequest.getHeaderNames();

		while (headerNames.hasNext()) {
			final String key = headerNames.next();
			localMap.put(key, webRequest.getHeaderValues(key));
		}
		modelMap.put("headers", localMap);

		final int scopes[] = {
				RequestAttributes.SCOPE_REQUEST,
				RequestAttributes.SCOPE_SESSION,
				RequestAttributes.SCOPE_GLOBAL_SESSION
		};
		for (int scope : scopes) {
			localMap = Maps.newHashMap();
			final String[] attributeNames = webRequest.getAttributeNames(scope);
			for (String attributeName : attributeNames) {
				localMap.put(attributeName, webRequest.getAttribute(attributeName, scope));
			}
			modelMap.put(scope == 0 ? "requestAttributes" : (scope == 1 ? "sessionAttributes" : "globalSessionAttributes"), localMap);
		}

		return new ComponentDataRequest(modelMap, cmpRequest);
	}

	/**
	 * <p>handleNPE.</p>
	 *
	 * @param npe a {@link org.agatom.springatom.webmvc.exceptions.ControllerTierException} object.
	 *
	 * @return a {@link org.springframework.http.ResponseEntity} object.
	 */
	@ResponseBody
	@ExceptionHandler({ControllerTierException.class})
	public ResponseEntity<?> handleNPE(final ControllerTierException npe) {
		return this.errorResponse(npe, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * <p>errorResponse.</p>
	 *
	 * @param throwable a T object.
	 * @param status    a {@link org.springframework.http.HttpStatus} object.
	 * @param <T>       a T object.
	 *
	 * @return a {@link org.springframework.http.ResponseEntity} object.
	 */
	public <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final T throwable, final HttpStatus status) {
		return errorResponse(null, throwable, status);
	}

	/**
	 * <p>errorResponse.</p>
	 *
	 * @param headers   a {@link org.springframework.http.HttpHeaders} object.
	 * @param throwable a T object.
	 * @param status    a {@link org.springframework.http.HttpStatus} object.
	 * @param <T>       a T object.
	 *
	 * @return a {@link org.springframework.http.ResponseEntity} object.
	 */
	public <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final HttpHeaders headers, final T throwable, final HttpStatus status) {
		if (null != throwable && null != throwable.getMessage()) {
			LOGGER.error(throwable.getMessage(), throwable);
			return response(headers, new ExceptionMessage(throwable), status);
		} else {
			return response(headers, null, status);
		}
	}

	/**
	 * <p>response.</p>
	 *
	 * @param headers a {@link org.springframework.http.HttpHeaders} object.
	 * @param body    a T object.
	 * @param status  a {@link org.springframework.http.HttpStatus} object.
	 * @param <T>     a T object.
	 *
	 * @return a {@link org.springframework.http.ResponseEntity} object.
	 */
	public <T> ResponseEntity<T> response(final HttpHeaders headers, final T body, final HttpStatus status) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		if (null != headers) {
			httpHeaders.putAll(headers);
		}
		return new ResponseEntity<>(body, httpHeaders, status);
	}

	@ResponseBody
	@ExceptionHandler({ConversionExecutionException.class})
	public ResponseEntity<?> handleConversionExecutionException(final ConversionExecutionException npe) {
		return this.errorResponse(npe, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
