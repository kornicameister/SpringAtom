package org.agatom.springatom.data.hades.scheduled;

import org.agatom.springatom.data.hades.model.error.NError;
import org.agatom.springatom.data.hades.repo.repositories.error.NErrorRepository;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.agatom.springatom.data.hades.service.NUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
class ErrorCapturingHandler
        implements UncaughtExceptionHandler, RejectedExecutionHandler {
    private static final Logger               LOGGER              = LoggerFactory.getLogger(ErrorCapturingHandler.class);
    @Autowired
    private              NNotificationService notificationService = null;
    @Autowired
    private              NUserService         userService         = null;
    @Autowired
    private              NErrorRepository     errorRepository     = null;

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        LOGGER.error(String.format("During execution of Thread=%s error was captured=%s", t, e.getMessage()));
        this.sendUncaughtException(t, e);
    }

    private void sendUncaughtException(final Thread t, final Throwable e) {
        try {
            this.notificationService.sendNotification(
                    String.format("Uncaught exception occured for thread=%s", t.getName()),
                    this.userService.getAdministrator(),
                    this.errorRepository.save(new NError().setError(e))
            );
        } catch (Exception ignore) {
            LOGGER.warn(ignore.getMessage());
        }
    }

    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
        LOGGER.error(String.format("Execution of Runnable=%s was rejected from executor=%s", r, executor));
    }
}
