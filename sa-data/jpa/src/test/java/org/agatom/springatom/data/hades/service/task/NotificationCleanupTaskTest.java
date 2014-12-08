package org.agatom.springatom.data.hades.service.task;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ContextConfiguration
public class NotificationCleanupTaskTest {
    private static final boolean                 CLEAR_NOT_READ               = true;
    private static final int                     CLEAR_OLDER_THEN             = 30;
    private static final int                     CLEAR_OLDER_THEN_IF_NOT_READ = 60;
    private static final TimeUnit                CLEAR_TIME_UNIT              = TimeUnit.DAYS;
    @Spy
    private              NotificationCleanupTask cleanupTask                  = null;
    @Mock
    private              NNotificationService    notificationService          = null;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(this.cleanupTask, "notificationService", this.notificationService);
        ReflectionTestUtils.setField(this.cleanupTask, "clearNotRead", CLEAR_NOT_READ);
        ReflectionTestUtils.setField(this.cleanupTask, "clearOlderThen", CLEAR_OLDER_THEN);
        ReflectionTestUtils.setField(this.cleanupTask, "clearTimeUnit", CLEAR_TIME_UNIT);
        ReflectionTestUtils.setField(this.cleanupTask, "clearOlderThenIfNotRead", CLEAR_OLDER_THEN_IF_NOT_READ);
    }


    @Test
    @DirtiesContext
    public void testClean() throws Exception {
        // expected
        final DateTime now = DateTime.now();
        final int count = 10;
        // expected

        // set up mocks
        final List<NNotification> notifications = this.getNotifications(count);

        notifications.get(0).setRead().setSent(this.getDateTimeBefore(now, CLEAR_OLDER_THEN, CLEAR_TIME_UNIT));
        notifications.get(1).setRead().setSent(this.getDateTimeBefore(now, CLEAR_OLDER_THEN, CLEAR_TIME_UNIT));
        notifications.get(2).setRead().setSent(this.getDateTimeBefore(now, CLEAR_OLDER_THEN, CLEAR_TIME_UNIT));
        notifications.get(3).setNotRead();
        notifications.get(4).setNotRead();
        notifications.get(5).setNotRead();
        notifications.get(6).setNotRead();
        notifications.get(7).setNotRead();
        notifications.get(8).setNotRead().setSent(this.getDateTimeBefore(now, CLEAR_OLDER_THEN_IF_NOT_READ, CLEAR_TIME_UNIT));
        notifications.get(9).setNotRead().setSent(this.getDateTimeBefore(now, CLEAR_OLDER_THEN_IF_NOT_READ, CLEAR_TIME_UNIT));

        Mockito.when(this.notificationService.getNotificationsBefore(now)).thenReturn(notifications);
        // set up mocks

        final int expected = 5;
        final int actual = this.cleanupTask.clean(now);

        Assert.assertEquals(expected, actual);

    }

    @Test
    @DirtiesContext
    public void testFilter_ClearNotRead() throws Exception {
        // expected
        final DateTime now = DateTime.now();
        final int count = 100;
        // expected

        final Iterable<NNotification> notifications = this.getNotifications(count);
        int idx = 0;
        int readCount = 0;
        for (NNotification notification : notifications) {
            if (idx++ % 6 == 0) {
                readCount++;
                ReflectionTestUtils.setField(notification, "read", true);
            }
        }

        // set up mock for getMillisPredicate, does not affect this test
        Mockito.when(cleanupTask.timePredicate(now)).thenReturn(new Predicate<NNotification>() {
            @Override
            public boolean apply(final NNotification input) {
                return true;
            }
        });

        final List<NNotification> filter = this.cleanupTask.filter(now, notifications);

        Assert.assertNotEquals(String.format("#filter(...) should return different amount then initial"), count, filter.size());
        Assert.assertEquals(String.format("#filter(...) returned %d where it should return %d", filter.size(), readCount), readCount, filter.size());

    }

    @Test
    @DirtiesContext
    public void testFilter_DoNotClearNotRead() throws Exception {
        // expected
        final DateTime now = DateTime.now();
        final int count = 5;
        // expected

        ReflectionTestUtils.setField(this.cleanupTask, "clearNotRead", false);

        final Iterable<NNotification> notifications = this.getNotifications(count);
        int idx = 0;
        for (NNotification notification : notifications) {
            if (idx++ % 2 == 0) {
                ReflectionTestUtils.setField(notification, "read", true);
            }
        }

        // set up mock for getMillisPredicate, does not affect this test
        Mockito.when(this.cleanupTask.timePredicate(now)).thenReturn(new Predicate<NNotification>() {
            @Override
            public boolean apply(final NNotification input) {
                // dont execute time predicate
                return true;
            }
        });

        final List<NNotification> filter = this.cleanupTask.filter(now, notifications);

        Assert.assertEquals(String.format("#filter(...) should return different amount then initial"), count, filter.size());
    }

    protected List<NNotification> getNotifications(final int count) {
        final List<NNotification> collection = Lists.newArrayListWithExpectedSize(count);
        for (int i = 0; i < count; i++) {
            collection.add(new NNotification().setMessage(String.valueOf(i)));
        }
        return collection;
    }

    private DateTime getDateTimeBefore(final DateTime now, final int clearOlderThen, final TimeUnit clearTimeUnit) {
        final int nextInt = Math.abs(new Random().nextInt(1000));
        switch (clearTimeUnit) {
            case DAYS:
                return now.minusDays(clearOlderThen + nextInt);
            case HOURS:
                return now.minusDays(clearOlderThen + nextInt);
        }
        throw new IllegalArgumentException("Failed to calculate prior date");
    }
}