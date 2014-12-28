package org.agatom.springatom.webmvc.controllers.user;

import org.agatom.springatom.core.web.CollectionDataResource;
import org.agatom.springatom.core.web.PrimitiveDataSource;
import org.agatom.springatom.data.services.SUserNotificationsService;
import org.agatom.springatom.data.services.SUserService;
import org.agatom.springatom.data.support.notification.UserNotification;
import org.agatom.springatom.data.support.user.AuthenticatedUserBean;
import org.agatom.springatom.data.support.user.NotAuthenticatedUserBean;
import org.agatom.springatom.web.controller.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/user",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@Description(value = "Controller to manage user state")
public class SVUserController
        extends SVDefaultController {
    private static final Logger                    LOGGER                   = LoggerFactory.getLogger(SVUserController.class);
    private static final Class<SVUserController>   CLAZZ                    = SVUserController.class;
    @Autowired
    private              SUserService<?, ?>        userService              = null;
    @Autowired
    private              SUserNotificationsService userNotificationsService = null;

    @ResponseBody
    @RequestMapping(value = "/is/authenticated", method = RequestMethod.GET)
    public PrimitiveDataSource<Boolean> isAuthenticated() {
        final PrimitiveDataSource<Boolean> resource = new PrimitiveDataSource<>(this.isAuthenticated(this.getAuthentication()));
        resource.add(linkTo(methodOn(CLAZZ).isAuthenticated()).withSelfRel());
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserBean()).withRel("user"));
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserNotifications()).withRel("notifications"));
        return resource;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object getCurrentUserBean() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getCurrentUserBean()"));
        }

        final Authentication authentication = this.getAuthentication();
        Object resourceContent;

        final boolean authenticated = this.isAuthenticated(authentication);

        if (authenticated) {
            final AuthenticatedUserBean bean = new AuthenticatedUserBean();

            final UserDetails userDetails = this.userService.loadUserByUsername(authentication.getName());
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Loaded UserDetails={} for authenticated user", userDetails);
            }

            BeanUtils.copyProperties(userDetails, bean, "password");

            resourceContent = bean;
        } else {
            resourceContent = new NotAuthenticatedUserBean();
        }

        final UserResource resource = new UserResource(resourceContent);
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserBean()).withSelfRel());
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserNotifications()).withRel("notifications"));
        resource.add(linkTo(methodOn(CLAZZ).isAuthenticated()).withRel("isAuthenticated"));

        return resource;
    }

    private boolean isAuthenticated(final Authentication authentication) {
        return authentication.isAuthenticated() && !ClassUtils.isAssignableValue(AnonymousAuthenticationToken.class, authentication);
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public Object getCurrentUserNotifications() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getCurrentUserNotifications()"));
        }
        final String name = this.getAuthentication().getName();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving notifications for user={}", name);
        }
        final CollectionDataResource<UserNotification> resource = new CollectionDataResource<>(this.userNotificationsService.getRecentNotifications(name));
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserBean()).withRel("user"));
        resource.add(linkTo(methodOn(CLAZZ).getCurrentUserNotifications()).withSelfRel());
        resource.add(linkTo(methodOn(CLAZZ).isAuthenticated()).withRel("isAuthenticated"));
        return resource;
    }

    private Authentication getAuthentication() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Returning authentication is={}", authentication);
        }
        return authentication;
    }
}
