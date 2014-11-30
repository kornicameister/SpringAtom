package org.agatom.springatom.data.services;

import org.agatom.springatom.data.types.notification.Notification;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface SNotificationService<T extends Notification & Persistable<Long>>
        extends SDomainService<T> {

    String MAIN_CACHE    = "notificationService.notifications";
    String TARGETS_CACHE = "notificationService.targets";
    String SUBJECT_CACHE = "notificationService.subjects";

    @Override
    @Cacheable(MAIN_CACHE)
    Iterable<T> findAll();

    @Override
    @CacheEvict(MAIN_CACHE)
    void deleteAll();

    @Override
    @CacheEvict(MAIN_CACHE)
    void delete(final Iterable<T> toDelete);

    T sendNotification(final Persistable<Long> target, final Persistable<Long> subject);

    T sendNotification(final String message, final Persistable<Long> target, final Persistable<Long> subject);

    @Cacheable(value = TARGETS_CACHE, key = "#notification.id")
    Persistable<Long> getTarget(@NotNull final T notification);

    @Cacheable(value = SUBJECT_CACHE, key = "#notification.id")
    Persistable<Long> getSubject(@NotNull final T notification);

    /**
     * Retrieves notifications prior (inclusively) to the date specified as the argument of this method
     *
     * @return notifications prior the date
     */
    @CacheEvict(MAIN_CACHE)
    Iterable<T> getNotificationsBefore(@Nonnull final DateTime time);

}
