package org.agatom.springatom.data.services;

import org.agatom.springatom.data.support.notification.UserNotification;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface SUserNotificationsService {

    @NotNull
    Collection<UserNotification> getNotifications(@NotNull final String username);

    @NotNull
    Collection<UserNotification> getRecentNotifications(@NotNull final String username);
}
