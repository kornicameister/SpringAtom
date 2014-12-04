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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ContextConfiguration
public class NotificationCleanupTaskTest {

    @Spy
    private NotificationCleanupTask cleanupTask         = null;
    @Mock
    private NNotificationService    notificationService = null;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(this.cleanupTask, "notificationService", this.notificationService);
    }


    @Test
    public void testClean() throws Exception {

        // expected
        final DateTime now = DateTime.now();
        final boolean clearNotRead = true;
        final int clearOlderThen = 30;
        final TimeUnit clearTimeUnit = TimeUnit.DAYS;
        final int count = 100;
        // expected

        // set up fields
        this.setServiceProperties(clearNotRead, clearOlderThen, clearTimeUnit);
        // set up fields

        // set up mocks
        final Iterable<NNotification> notifications = this.getNotifications(count);
        int idx = 0;
        final int readThreshlod = 30;
        final int millisThreshold = 10;
        for (NNotification notification : notifications) {
            if (idx < readThreshlod) {
                ReflectionTestUtils.setField(notification, "read", true);
            } else if (idx < millisThreshold + readThreshlod) {
                ReflectionTestUtils.setField(notification, "sent", this.getDateTimeBefore(now, clearOlderThen, clearTimeUnit));
            }
            idx++;
        }
        Mockito.when(this.notificationService.getNotificationsBefore(now)).thenReturn(notifications);
        // set up mocks

        final int expected = ((count - readThreshlod) + (count - (millisThreshold + readThreshlod))) - count;
        final int actual = this.cleanupTask.clean(now);

        Assert.assertEquals(expected, actual);

    }

    private void setServiceProperties(final boolean clearNotRead, final int clearOlderThen, final TimeUnit clearTimeUnit) {
        ReflectionTestUtils.setField(this.cleanupTask, "clearNotRead", clearNotRead);
        ReflectionTestUtils.setField(this.cleanupTask, "clearOlderThen", clearOlderThen);
        ReflectionTestUtils.setField(this.cleanupTask, "clearTimeUnit", clearTimeUnit);
    }

    protected Iterable<NNotification> getNotifications(final int count) {
        final Collection<NNotification> collection = Lists.newArrayListWithExpectedSize(count);
        for (int i = 0; i < count; i++) {
            collection.add(new NNotification().setMessage(String.valueOf(i)));
        }
        return collection;
    }

    private DateTime getDateTimeBefore(final DateTime now, final int clearOlderThen, final TimeUnit clearTimeUnit) {
        final long millis = clearTimeUnit.toMillis(clearOlderThen) - 1000;
        return now.minus(millis);
    }

    @Test
    public void testFilter_ClearNotRead() throws Exception {
        // expected
        final DateTime now = DateTime.now();
        final boolean clearNotRead = true;
        final int clearOlderThen = 30;
        final TimeUnit clearTimeUnit = TimeUnit.DAYS;
        final int count = 100;
        // expected

        this.setServiceProperties(clearNotRead, clearOlderThen, clearTimeUnit);

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
        Mockito.when(cleanupTask.getMillisPredicate(now)).thenReturn(new Predicate<NNotification>() {
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
    public void testFilter_DoNotClearNotRead() throws Exception {
        // expected
        final DateTime now = DateTime.now();
        final boolean clearNotRead = false;
        final int clearOlderThen = 30;
        final TimeUnit clearTimeUnit = TimeUnit.DAYS;
        final int count = 100;
        // expected

        this.setServiceProperties(clearNotRead, clearOlderThen, clearTimeUnit);

        final Iterable<NNotification> notifications = this.getNotifications(count);
        int idx = 0;
        for (NNotification notification : notifications) {
            if (idx++ % 6 == 0) {
                ReflectionTestUtils.setField(notification, "read", true);
            }
        }

        // set up mock for getMillisPredicate, does not affect this test
        Mockito.when(cleanupTask.getMillisPredicate(now)).thenReturn(new Predicate<NNotification>() {
            @Override
            public boolean apply(final NNotification input) {
                return true;
            }
        });

        final List<NNotification> filter = this.cleanupTask.filter(now, notifications);

        Assert.assertEquals(String.format("#filter(...) should return different amount then initial"), count, filter.size());
    }

    @Test
    public void testGetClearOlderThenInMillis() {
        final int clearOlderThen = 30;
        final TimeUnit clearTimeUnit = TimeUnit.DAYS;

        ReflectionTestUtils.setField(this.cleanupTask, "clearOlderThen", clearOlderThen);
        ReflectionTestUtils.setField(this.cleanupTask, "clearTimeUnit", clearTimeUnit);

        // expected
        final long expectedClearOlderThen = TimeUnit.DAYS.toMillis(clearOlderThen);
        final long actualOlderThenMillis = this.cleanupTask.getOlderThenMillis();

        Assert.assertEquals("Times in MS are not the same", expectedClearOlderThen, actualOlderThenMillis);
    }
}