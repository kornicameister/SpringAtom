package org.agatom.springatom.web.api;

import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

@Description(value = "Controller allows to access and work with recently updated objects in the application")
@RequestMapping(value = RecentlyUpdatedController.Api.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
public interface RecentlyUpdatedController {

    @ResponseBody
    @RequestMapping(value = Api.GET_ALL)
    Resource<?> getAll();

    @ResponseBody
    @RequestMapping(value = Api.GET_PAGE)
    Resource<?> getPage(final Pageable pageable);

    final static class Api {
        public static final String ROOT     = "/rest/ru";
        public static final String GET_PAGE = "/get/page";
        public static final String GET_ALL  = "/get/all";

        private Api() {

        }
    }

}
