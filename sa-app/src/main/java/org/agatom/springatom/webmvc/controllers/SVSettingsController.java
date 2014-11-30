package org.agatom.springatom.webmvc.controllers;

import com.google.common.collect.Maps;
import org.agatom.springatom.core.web.DataResource;
import org.agatom.springatom.core.web.MapDataResource;
import org.agatom.springatom.core.web.PrimitiveDataSource;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Properties;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-20</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@ResponseBody
public class SVSettingsController
        extends SVDefaultController {
    private static final Class<SVSettingsController> CLASS                 = SVSettingsController.class;
    @Autowired
    @Qualifier("applicationProperties")
    private              Properties                  applicationProperties = null;

    @RequestMapping(
            value = "/info/meta",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public DataResource<Map<String, Object>> getApplicationInformation() {
        final Map<String, Object> info = Maps.newHashMap();

        info.put("name", "SA");
        info.put("longName", "SpringAtom");
        info.put("version", "1.0 Alpha");

        final MapDataResource<String, Object> resource = new MapDataResource<>(info);
        resource.add(linkTo(methodOn(CLASS).getApplicationInformation()).withSelfRel());
        return resource;

    }

    @RequestMapping(
            value = "/property",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public PrimitiveDataSource<String> getProperty(final String key) {
        final String property = this.applicationProperties.getProperty(key, "");

        final PrimitiveDataSource<String> resource = new PrimitiveDataSource<>(property);
        resource.add(linkTo(methodOn(CLASS).getProperty(key)).withSelfRel());

        return resource;
    }

}
