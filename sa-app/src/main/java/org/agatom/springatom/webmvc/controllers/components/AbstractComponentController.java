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
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.SOidService;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Iterator;
import java.util.Map;

/**
 * {@code AbstractComponentController} is a foundation for <b>components controller</b>
 * Contains logic required to create {@link ComponentDataRequest}
 * and for exception handling
 *
 * Changelog:
 * 0.0.3 - 0.0.4: extends SVDefaultController due to capabilities of producing common responses for exceptions
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 02.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.5
 * @since 0.0.1
 */
abstract class AbstractComponentController
        extends SVDefaultController {
    private static final Logger                     LOGGER            = LoggerFactory.getLogger(AbstractComponentController.class);
    @Autowired
    protected            ComponentBuilderRepository builderRepository = null;
    @Autowired
    protected            CDRReturnValueConverter    converter         = null;
    @Autowired
    protected            SOidService                oidService        = null;

    /**
     * Transforms @link org.springframework.web.context.request.WebRequest}
     * into valid {@link ComponentDataRequest}.
     *
     * @param webRequest web request
     *
     * @return {@link ComponentDataRequest} instance
     */
    protected ComponentDataRequest getComponentDataRequest(final NativeWebRequest webRequest) throws Exception {
        LOGGER.trace(String.format("Combining request for webRequest=%s", webRequest.getClass()));
        final ComponentDataRequest componentDataRequest = new ComponentDataRequest();

        ModelMap localMap = new ModelMap(webRequest.getParameterMap());
        localMap.addAttribute("locale", webRequest.getLocale());
        localMap.addAttribute("user", webRequest.getUserPrincipal());

        componentDataRequest.setParametersMap(localMap);

        localMap = new ModelMap();

        final Iterator<String> headerNames = webRequest.getHeaderNames();
        while (headerNames.hasNext()) {
            final String key = headerNames.next();
            localMap.put(key, webRequest.getHeaderValues(key));
        }
        componentDataRequest.setHeadersMap(localMap);

        localMap = new ModelMap();
        final int scopes[] = {
                RequestAttributes.SCOPE_REQUEST,
                RequestAttributes.SCOPE_SESSION,
                RequestAttributes.SCOPE_GLOBAL_SESSION
        };
        for (int scope : scopes) {
            final Map<String, Object> localHM = Maps.newHashMap();
            final String[] attributeNames = webRequest.getAttributeNames(scope);
            for (String attributeName : attributeNames) {
                localHM.put(attributeName, webRequest.getAttribute(attributeName, scope));
            }
            localMap.put(scope == 0 ? "requestAttributes" : (scope == 1 ? "sessionAttributes" : "globalSessionAttributes"), localHM);
        }
        componentDataRequest.setAttributesMap(localMap);

        return componentDataRequest
                .setOid(this.getOid(webRequest));
    }

    protected SOid getOid(final WebRequest request) throws Exception {
        final String oid = request.getParameter("oid");
        if (!StringUtils.hasText(oid)) {
            return null;
        }
        return this.oidService.getOid(oid);
    }

    @ResponseBody
    @ExceptionHandler({ConversionExecutionException.class})
    public ResponseEntity<?> handleConversionExecutionException(final ConversionExecutionException npe) {
        return this.errorResponse(npe, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
