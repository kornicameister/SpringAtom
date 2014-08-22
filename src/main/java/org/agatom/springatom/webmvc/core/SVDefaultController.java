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

package org.agatom.springatom.webmvc.core;

import org.apache.log4j.Logger;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * <p>Abstract SVDefaultController class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SVDefaultController
        extends WebApplicationObjectSupport
        implements SController {
    private static final Logger LOGGER = Logger.getLogger(SVDefaultController.class);
    private final String controllerName;

    /**
     * <p>Constructor for SVDefaultController.</p>
     *
     * @param controllerName a {@link java.lang.String} object.
     */
    protected SVDefaultController(final String controllerName) {
        this.controllerName = controllerName;
    }

    /** {@inheritDoc} */
    @Override
    public final String getControllerName() {
        return this.controllerName;
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
    protected <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final T throwable, final HttpStatus status) {
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
    protected <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final HttpHeaders headers, final T throwable, final HttpStatus status) {
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

}
