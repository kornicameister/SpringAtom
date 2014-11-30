package org.agatom.springatom.data.hades.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.model.notification.NNotificationSubject;
import org.agatom.springatom.data.hades.model.notification.QNNotification;
import org.agatom.springatom.data.hades.model.notification.QNNotificationTarget;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.model.user.QNUser;
import org.agatom.springatom.data.hades.repo.repositories.notification.NNotificationRepository;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.agatom.springatom.data.hades.service.NUserNotificationService;
import org.agatom.springatom.data.support.notification.NotificationSubject;
import org.agatom.springatom.data.support.notification.UserNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class UserNotificationService
        extends BaseDomainService
        implements NUserNotificationService {
    private static final Logger                  LOGGER                 = LoggerFactory.getLogger(UserNotificationService.class);
    private static final String                  LIMIT                  = "10";
    private static final String                  RECENT_LIMIT_PROP_KEY  = "user.notifications.recent";
    @Autowired
    private              NNotificationRepository notificationRepository = null;
    @Autowired
    private              NNotificationService    notificationService    = null;
    @Autowired
    @Qualifier("applicationProperties")
    private              Properties              properties             = null;

    @Override
    public Collection<UserNotification> getNotifications(@NotNull final String username) {
        return this.getNotifications(username, -1);
    }

    @Override
    public Collection<UserNotification> getRecentNotifications(@NotNull final String username) {
        return this.getNotifications(username, Integer.parseInt(this.properties.getProperty(RECENT_LIMIT_PROP_KEY, LIMIT)));
    }

    private Collection<UserNotification> getNotifications(final String username, final int limit) {
        final List<NNotification> nNotifications = executeGetNotifications(username, limit, this.notificationRepository.query());
        final int size = nNotifications.size();
        if (size <= 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("No associated notifications found at this point");
            }
            return Lists.newArrayList();
        }
        final Collection<UserNotification> set = FluentIterable.from(nNotifications).transform(new Function<NNotification, UserNotification>() {
            @Nullable
            @Override
            public UserNotification apply(final NNotification input) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Processing NNotification={}", input);
                }
                final UserNotification userNotification = new UserNotification();
                userNotification.setId(input.getId());
                userNotification.setMessage(input.getMessage());
                userNotification.setSent(input.getSent());
                userNotification.setSubject(getAndTransformSubject(input));
                return userNotification;
            }
        }).toSet();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Located {} notifications for current user", set.size());
        }
        return set;
    }

    private static List<NNotification> executeGetNotifications(final String username, final int limit, final JPAQuery query) {
        QNNotification qnNotification = QNNotification.nNotification;
        final QNNotificationTarget target = qnNotification.target;
        final QNUser nUser = QNUser.nUser;

        query.from(qnNotification)
                .where(target.associateClass.eq(NUser.class)
                        .and(target.associateId.eq(new JPASubQuery().from(nUser).where(nUser.credentials.username.eq(username)).unique(nUser.id)))
                        .and(qnNotification.read.eq(false)))
                .orderBy(qnNotification.sent.asc());

        if (limit > 0) {
            query.limit(limit);
        }

        return query.list(qnNotification);
    }

    private NotificationSubject getAndTransformSubject(final NNotification notification) {
        final Persistable<Long> subject = this.notificationService.getSubject(notification);
        if (subject == null) {
            final NNotificationSubject notificationSubject = notification.getSubject();
            LOGGER.warn("Corresponding subject(ID={},CLAZZ={}) has no DB entity", notificationSubject.getAssociateId(), notificationSubject.getAssociateClass());
            return null;
        }
        return new NotificationSubject()
                .setSubject(subject)
                .setLabel(this.getMessage(subject.getClass().getSimpleName().toLowerCase()));
    }

    private String getMessage(final String code) {
        return this.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
