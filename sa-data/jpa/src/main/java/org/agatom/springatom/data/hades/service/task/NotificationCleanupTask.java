package org.agatom.springatom.data.hades.service.task;

import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

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
    private static final Logger               LOGGER              = LoggerFactory.getLogger(NotificationCleanupTask.class);
    private static final int                  DELAY               = 5000;
    private static final int                  RATE                = 600000;
    @Autowired
    private              NNotificationService notificationService = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearNotRead']}")
    private              Boolean              clearNotRead        = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearOlderThen']}")
    private              Integer              clearOlderThen      = null;
    @Value("#{applicationProperties['org.agatom.springatom.data.hades.service.task.NotificationCleanupTask.clearTimeUnit']}")
    private              TimeUnit             clearTimeUnit       = null;

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
        this.notificationService.delete(toDelete);

        return FluentIterable.from(toDelete).size();
    }

    protected List<NNotification> filter(final DateTime now, final Iterable<NNotification> notificationsBefore) {
        return FluentIterable
                .from(notificationsBefore)
                .filter(this.getNotReadPredicate())
                .filter(this.getMillisPredicate(now))
                .toList();
    }

    protected Predicate<NNotification> getNotReadPredicate() {
        return new Predicate<NNotification>() {
            @Override
            public boolean apply(final NNotification input) {
                if (!clearNotRead) {
                    return true;
                } else if (!input.isRead() && clearNotRead) {
                    return false;
                }
                return true;
            }
        };
    }

    protected Predicate<NNotification> getMillisPredicate(final DateTime now) {
        final long olderThenMillis = this.getOlderThenMillis();
        return new Predicate<NNotification>() {
            @Override
            public boolean apply(final NNotification input) {
                final DateTime sent = input.getSent();
                final DateTime minus = now.minus(sent.getMillis());
                return minus.getMillis() >= olderThenMillis;
            }
        };
    }

    protected long getOlderThenMillis() {
        return this.clearTimeUnit.toMillis(this.clearOlderThen);
    }
}
