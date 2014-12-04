package org.agatom.springatom.data.hades.scheduled;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@EnableAsync
@EnableScheduling
@Configuration
@ComponentScan(
        basePackageClasses = AsyncSchedulingConfiguration.class,
        excludeFilters = {
                @Filter(value = Configuration.class)
        }
)
@PropertySource(
        value = "classpath:org/agatom/springatom/data/hades/scheduled/scheduling.properties"
)
public class AsyncSchedulingConfiguration
        implements AsyncConfigurer, SchedulingConfigurer {
    private static final ClassLoader CLASS_LOADER = AsyncSchedulingConfiguration.class.getClassLoader();
    @Autowired
    private Environment env;
    @Autowired
    private ErrorCapturingHandler errorCapturingHandler = null;

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(this.env.getProperty("async.pool.size", Integer.class));
        executor.setMaxPoolSize(this.env.getProperty("async.pool.maxSize", Integer.class));
        executor.setQueueCapacity(this.env.getProperty("async.queue.capacity", Integer.class));
        executor.setBeanName(this.env.getProperty("async.thread.name.prefix"));
        executor.setRejectedExecutionHandler(this.errorCapturingHandler);
        executor.initialize();

        return executor;
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(this.getThreadPoolTaskScheduler());
        taskRegistrar.setFixedDelayTasksList(this.getIntervalTasks(this.env.getProperty("async.periodic.tasks")));
    }

    public ScheduledThreadPoolExecutor getThreadPoolTaskScheduler() {
        final ScheduledThreadPoolExecutor threadPoolTaskScheduler = new ScheduledThreadPoolExecutor(
                this.env.getProperty("schedule.pool.size", Integer.class),
                new ThreadFactoryBuilder()
                        .setNameFormat(this.env.getProperty("schedule.thread.name.format"))
                        .setUncaughtExceptionHandler(this.errorCapturingHandler)
                        .build()
        );
        threadPoolTaskScheduler.setMaximumPoolSize(this.env.getProperty("schedule.pool.maxSize", Integer.class));
        threadPoolTaskScheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(this.env.getProperty("schedule.continueExistingAfterShutdownPolicy", Boolean.class));
        return threadPoolTaskScheduler;
    }

    private List<IntervalTask> getIntervalTasks(final String property) {
        if (StringUtils.isEmpty(property)) {
            return Lists.newArrayList();
        }
        final Set<String> strings = StringUtils.commaDelimitedListToSet(property);
        return FluentIterable.from(strings).transform(new Function<String, IntervalTask>() {

            @Nullable
            @Override
            public IntervalTask apply(final String input) {
                return new IntervalTask(this.getRunnable(input), TimeUnit.HOURS.toMillis(1), TimeUnit.MINUTES.toMillis(10));
            }

            private Runnable getRunnable(final String className) {
                try {
                    return (Runnable) ClassUtils.resolveClassName(className, CLASS_LOADER).newInstance();
                } catch (Exception exp) {
                    throw new IllegalStateException(String.format("Failed to initialized Runnable cron instance"));
                }
            }

        }).toList();
    }
}
