package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.model.notification.NNotificationSubject;
import org.agatom.springatom.data.hades.model.notification.NNotificationTarget;
import org.agatom.springatom.data.hades.model.notification.QNNotification;
import org.agatom.springatom.data.hades.repo.repositories.notification.NNotificationRepository;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.agatom.springatom.data.repositories.provider.RepositoriesHelper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.core.CrudInvoker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

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
@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
class NotificationsDomainService
        extends AbstractDomainService<NNotification>
        implements NNotificationService {
    private static final Logger             LOGGER             = LoggerFactory.getLogger(NotificationsDomainService.class);
    @Autowired
    private              RepositoriesHelper repositoriesHelper = null;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public NNotification sendNotification(final Persistable<Long> target, final Persistable<Long> subject) {
        return this.sendNotification(null, target, subject);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public NNotification sendNotification(final String message, final Persistable<Long> target, final Persistable<Long> subject) {
        final NNotification notification = new NNotification();

        notification.setSubject(subject);
        notification.setTarget(target);
        notification.setMessage(message);

        return this.save(notification);
    }

    @Override
    public Persistable<Long> getTarget(@NotNull final NNotification notification) {
        final NNotificationTarget target = notification.getTarget();
        return this.getAssociatedObject(target.getAssociateClass(), target.getId());
    }

    @Override
    public Persistable<Long> getSubject(@NotNull final NNotification notification) {
        final NNotificationSubject subject = notification.getSubject();
        return this.getAssociatedObject(subject.getAssociateClass(), subject.getId());
    }

    @Override
    public Iterable<NNotification> getNotificationsBefore(@Nonnull final DateTime time) {
        final QNNotification notification = QNNotification.nNotification;
        return this.repo().findAll(notification.sent.loe(time), notification.read.desc());
    }

    private NNotificationRepository repo() {
        return (NNotificationRepository) this.repository;
    }

    @SuppressWarnings("unchecked")
    private Persistable<Long> getAssociatedObject(Class<?> associateClass, final Long id) {
        associateClass = ClassUtils.getUserClass(associateClass);
        final CrudInvoker<?> crudInvoker = this.repositoriesHelper.getCrudInvoker(associateClass);
        if (crudInvoker == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("No {} located for {}", ClassUtils.getShortName(CrudInvoker.class), ClassUtils.getShortName(associateClass));
            }
            return null;
        }
        return (Persistable<Long>) crudInvoker.invokeFindOne(id);
    }

}
