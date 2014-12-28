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

package org.agatom.springatom.webmvc.controllers;

import org.agatom.springatom.core.web.DataResource;
import org.agatom.springatom.data.vin.decoder.VinDecoder;
import org.agatom.springatom.data.vin.exception.VinDecodingException;
import org.agatom.springatom.data.vin.model.VinNumberData;
import org.agatom.springatom.web.controller.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-21</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/data/vin",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class SVVinController
        extends SVDefaultController {
    private static final   Logger     LOGGER     = LoggerFactory.getLogger(SVVinController.class);
    @Autowired
    private                VinDecoder vinDecoder = null;

    @ResponseBody
    @RequestMapping(value = "/decode/{vin}", method = RequestMethod.GET)
    public VinNumberDataResource getVinNumberData(@PathVariable("vin") String vin) throws VinDecodingException {
        LOGGER.debug(String.format("getVinNumberData(vin=%s)", vin));

        final long startTime = System.currentTimeMillis();
        final VinNumberDataResource resource = new VinNumberDataResource(this.vinDecoder.decode(vin));

        resource.add(linkTo(methodOn(SVVinController.class, vin).getVinNumberData(vin)).withRel("vinNumberData"));
        resource.setSuccess(true);
        resource.setTime(System.currentTimeMillis() - startTime);
        resource.setMessage(String.format("Decoded VinNumberData out of %s", vin));
        resource.setSize(1);

        return resource;
    }


    /**
     * <p>handleVinDecodingException.</p>
     *
     * @param error a {@link org.agatom.springatom.web.exceptions.ControllerTierException} object.
     *
     * @return a {@link org.springframework.http.ResponseEntity} object.
     */
    @ResponseBody
    @ExceptionHandler({VinDecodingException.class})
    public ResponseEntity<?> handleVinDecodingException(final VinDecodingException error) {
        return this.errorResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected static class VinNumberDataResource
            extends DataResource<VinNumberData> {
        private static final long serialVersionUID = 2843894781929853120L;

        public VinNumberDataResource(final VinNumberData content) {
            super(content);
        }
    }

}
