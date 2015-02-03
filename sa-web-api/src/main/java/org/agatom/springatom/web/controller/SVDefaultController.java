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

package org.agatom.springatom.web.controller;

import org.agatom.springatom.web.exceptions.ControllerTierException;
import org.agatom.springatom.web.exceptions.WebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;

/**
 * <p>Abstract SVDefaultController class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
// TODO extrac advices of exception to controller advice and if necessary remove WebApplicationObjectSupport
public abstract class SVDefaultController
        extends WebApplicationObjectSupport {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final Path getTempPath() {
        final File tempDir = this.getTempDir();
        return tempDir != null ? tempDir.toPath() : null;
    }

    @ResponseBody
    @ExceptionHandler({ControllerTierException.class, Exception.class})
    public ResponseEntity<?> handleException(final Exception exp) {
        return this.errorResponse(exp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final T throwable, final HttpStatus status) {
        return this.errorResponse(null, throwable, status);
    }

    protected <T extends Throwable> ResponseEntity<ExceptionMessage> errorResponse(final HttpHeaders headers, final T throwable, final HttpStatus status) {
        if (null != throwable && null != throwable.getMessage()) {
            logger.error("errorResponse(err={})", throwable.getMessage());
            return this.response(headers, new ExceptionMessage(throwable), status);
        } else {
            return this.response(headers, null, status);
        }
    }

    protected <T> ResponseEntity<T> response(final HttpHeaders headers, final T body, final HttpStatus status) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        if (null != headers) {
            httpHeaders.putAll(headers);
        }

        httpHeaders.put("sa-error", Collections.singletonList(Boolean.TRUE.toString()));

        return new ResponseEntity<>(body, httpHeaders, status);
    }

    @ResponseBody
    @ExceptionHandler(WebException.class)
    public ResponseEntity<?> handleWebException(final WebException exp) {
        return this.errorResponse(exp, exp.getHttpStatus());
    }

    protected <T> ResponseEntity<T> response(final T body, final HttpStatus status) {
        return this.response(null, body, status);
    }

}
