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

package org.agatom.springatom.webmvc.controllers.data;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.agatom.springatom.server.service.vinNumber.decoder.VinDecoder;
import org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.agatom.springatom.webmvc.data.DataResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlAnyElement;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-21</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = SVVinController.CTRL_NAME)
@RequestMapping(value = "/data/vin",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class SVVinController
        extends SVDefaultController {
    protected static final String     CTRL_NAME  = "vinController";
    private static final   Logger     LOGGER     = Logger.getLogger(SVVinController.class);
    @Autowired
    private                VinDecoder vinDecoder = null;

    public SVVinController() {
        super(CTRL_NAME);
    }

    @ResponseBody
    @RequestMapping(value = "/decode/{vin}", method = RequestMethod.GET)
    protected VinNumberDataResource getVinNumberData(@PathVariable("vin") String vin) throws VinDecodingException {
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
     * @param error a {@link org.agatom.springatom.webmvc.exceptions.ControllerTierException} object.
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

        @Override
        @JsonUnwrapped(enabled = false)
        @XmlAnyElement
        public VinNumberData getContent() {
            return super.getContent();
        }
    }

}
