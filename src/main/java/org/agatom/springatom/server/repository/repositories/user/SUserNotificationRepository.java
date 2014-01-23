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

package org.agatom.springatom.server.repository.repositories.user;

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.notification.SUserNotification;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Qualifier(value = SUserNotificationRepository.REPO_NAME)
@RestResource(rel = SUserNotificationRepository.REST_REPO_REL, path = SUserNotificationRepository.REST_REPO_PATH)
public interface SUserNotificationRepository
        extends SBasicRepository<SUserNotification, Long> {
    String REPO_NAME      = "UserNotificationsRepo";
    String REST_REPO_REL  = "rest.user.notification";
    String REST_REPO_PATH = "user_notification";

    @RestResource(rel = "byUser", path = "user")
    Page<SUserNotification> findByUserOrderByUserCredentialsDesc(
            @Param("user") final SUser user,
            final Pageable pageable
    );

    @RestResource(rel = "byRead", path = "read")
    Page<SUserNotification> findByReadTrue(
            final Pageable pageable
    );

    @RestResource(rel = "byUnread", path = "unread")
    Page<SUserNotification> findByReadFalse(
            final Pageable pageable
    );

    @RestResource(rel = "byReadAndSentBefore", path = "readAndSentBefore")
    Page<SUserNotification> findByReadTrueAndSentBefore(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byReadAndSentAfter", path = "readAndSentAfter")
    Page<SUserNotification> findByReadTrueAndSentAfter(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byUnreadAndSentAfter", path = "unreadAndSentAfter")
    Page<SUserNotification> findByReadFalseAndSentAfter(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byUnreadAndSentBefore", path = "unreadAndSentBefore")
    Page<SUserNotification> findByReadFalseAndSentBefore(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byReadAndSentBetween", path = "readAndSentBetween")
    Page<SUserNotification> findByReadTrueAndSentBetween(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byUnreadAndSentBetween", path = "unreadAndSentBetween")
    Page<SUserNotification> findByReadFalseAndSentBetween(
            @Param("date") final DateTime time,
            final Pageable pageable
    );

    @RestResource(rel = "byUnreadForUser", path = "unreadByUser")
    Page<SUserNotification> findByReadFalseAndUser(
            @Param("user") final SUser user,
            final Pageable pageable
    );
}
