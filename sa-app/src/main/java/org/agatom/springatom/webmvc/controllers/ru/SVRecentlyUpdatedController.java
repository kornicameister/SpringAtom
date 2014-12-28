package org.agatom.springatom.webmvc.controllers.ru;

import org.agatom.springatom.core.annotations.profile.DevProfile;
import org.agatom.springatom.core.annotations.profile.ProductionProfile;
import org.agatom.springatom.core.web.CollectionDataResource;
import org.agatom.springatom.core.web.PageDataResource;
import org.agatom.springatom.data.services.SRecentlyUpdatedService;
import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
import org.agatom.springatom.web.api.RecentlyUpdatedController;
import org.agatom.springatom.web.controller.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code SVRecentlyUpdatedController} is a controller responsible for retrieving the data for
 * recently updated items.
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-30</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@DevProfile
@ProductionProfile
public class SVRecentlyUpdatedController
        extends SVDefaultController
        implements RecentlyUpdatedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SVRecentlyUpdatedController.class);
    @Autowired
    private              SRecentlyUpdatedService recentlyUpdatedService = null;

    @Override
    @PreAuthorize("isAuthenticated()")
    public Resource<?> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getAll()");
        }
        final Collection<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated();
        return new CollectionDataResource<>(recentlyUpdated)
                .addLink(linkTo(methodOn(SVRecentlyUpdatedController.class).getAll()).withSelfRel())
                .addLink(linkTo(methodOn(SVRecentlyUpdatedController.class).getPage(null)).withRel("page"));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Resource<?> getPage(final Pageable pageable) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getPage(pageable={})", pageable);
        }
        final Page<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated(pageable);
        return new PageDataResource<>(recentlyUpdated)
                .addLink(linkTo(methodOn(SVRecentlyUpdatedController.class).getAll()).withRel("all"))
                .addLink(linkTo(methodOn(SVRecentlyUpdatedController.class).getPage(null)).withSelfRel());
    }
}
