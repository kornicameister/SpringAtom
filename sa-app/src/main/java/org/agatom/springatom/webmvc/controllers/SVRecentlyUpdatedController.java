package org.agatom.springatom.webmvc.controllers;

import org.agatom.springatom.core.web.DataResource;
import org.agatom.springatom.data.services.SRecentlyUpdatedService;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/data/recent/update", produces = {MediaType.APPLICATION_JSON_VALUE})
@Description(value = "Controller for components")
public class SVRecentlyUpdatedController
        extends SVDefaultController {

    @Autowired
    private SRecentlyUpdatedService recentlyUpdatedService = null;

    @RequestMapping(value = "/list")
    public DataResource<?> list(final Pageable pageable) {
        this.recentlyUpdatedService.getRecentlyUpdated(pageable);
        return null;
    }

}
