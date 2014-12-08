package org.agatom.springatom.data.hades.service.task;

import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.naming.ConfigurationException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
class NotificationCleanupTask {
    private static final Logger               LOGGER                  = LoggerFactory.getLogger(NotificationCleanupTask.class);
    private static final int                  DELAY                   = 50000;
    private static final int                  RATE                    = 6000000;
    @Autowired
    private              NNotificationService notificationService     = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearNotRead']}")
    private              Boolean              clearNotRead            = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearOlderThen']}")
    private              Integer              clearOlderThen          = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearOlderThen.ifNotRead']}")
    private              Integer              clearOlderThenIfNotRead = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearTimeUnit']}")
    private              TimeUnit             clearTimeUnit           = null;

    @PostConstruct
    private void validateTimeUnit() throws ConfigurationException {
        if (!(TimeUnit.DAYS.equals(clearTimeUnit) || TimeUnit.HOURS.equals(this.clearTimeUnit))) {
            throw new ConfigurationException(String.format("%s, %s are supported when defining interval for notifications", TimeUnit.DAYS, TimeUnit.HOURS));
        }
    }

    @Scheduled(initialDelay = DELAY, fixedRate = RATE)
    public void scheduledRun() {
        LOGGER.info(String.format("%s scheduled execution", ClassUtils.getShortName(NotificationCleanupTask.class)));
        try {
            final Stopwatch sw = Stopwatch.createStarted();
            final int deleted = this.clean(DateTime.now());
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("NotificationCleanupTask has just freed %d notifications in %d ms", deleted, sw.stop().elapsed(TimeUnit.MILLISECONDS)));
            }
        } catch (Exception exp) {
            LOGGER.error("Failed to execute cleanup task for notifications", exp);
        }
    }

    protected int clean(final DateTime now) {
        final Iterable<NNotification> notificationsBefore = this.notificationService.getNotificationsBefore(now);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Retrieved %s notifications prior to date %s", notificationsBefore, now));
        }

        final Iterable<NNotification> toDelete = this.filter(now, notificationsBefore);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("About to delete %d notifications", Iterables.size(toDelete)));
            for (NNotification notification : toDelete) {
                LOGGER.trace(notification.toString());
            }
        }
        this.notificationService.delete(toDelete);

        return FluentIterable.from(toDelete).size();
    }

    protected List<NNotification> filter(final DateTime now, final Iterable<NNotification> notificationsBefore) {
        /*
        1. Filter out all that are matching clearNotRead and in the same time are no longer then clearOlderThenIfNotRead
        2. For the remaining execute normal algorithm (to be deleted if created longer then clearOlderThen)
         */
        return FluentIterable
                .from(notificationsBefore)
                .filter(this.clearNotReadPredicate(now))
                .filter(this.timePredicate(now))
                .toList();
    }

    protected Predicate<NNotification> clearNotReadPredicate(final DateTime now) {
        return new NotReadPredicate(now);
    }

    protected Predicate<NNotification> timePredicate(final DateTime now) {
        return new TimePredicate(now);
    }

    protected int getTimeDifference(final DateTime sent, final DateTime now) {
        switch (this.clearTimeUnit) {
            case DAYS:
                return Days.daysBetween(now, sent).getDays();
            case HOURS:
                return Hours.hoursBetween(now, sent).getHours();
        }
        throw new IllegalStateException(String.format("%s is not supported", this.clearTimeUnit));
    }

    protected class NotReadPredicate
            implements Predicate<NNotification> {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        protected final DateTime now;

        public NotReadPredicate(final DateTime now) {
            this.now = now;
        }

        @Override
        public boolean apply(final NNotification input) {
            if (!clearNotRead) {
                return true;
            }
            final Boolean read = input.isRead();
            if (!read) {
                final int difference = getTimeDifference(this.now, input.getSent());
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s has been created %d days ago and it wasn't read", input, difference));
                }
                final boolean result = difference >= clearOlderThenIfNotRead;
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s will be included to be deleted because %d >= %d", input, difference, clearOlderThenIfNotRead));
                }
                return result;
            }
            return true;
        }
    }

    protected class TimePredicate
            implements Predicate<NNotification> {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        protected final DateTime now;

        public TimePredicate(final DateTime now) {
            this.now = now;
        }

        @Override
        public boolean apply(final NNotification input) {
            final DateTime sent = input.getSent();
            final int result = getTimeDifference(this.now, sent);
            if (logger.isDebugEnabled()) {
                logger.debug("{} has been created {} days ago");
            }
            return result >= clearOlderThen;
        }
    }
}
