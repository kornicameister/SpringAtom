package org.agatom.springatom.data.hades.repo.repositories.notification;

import org.agatom.springatom.data.hades.model.notification.NNotification;
import org.agatom.springatom.data.hades.model.notification.NNotificationSubject;
import org.agatom.springatom.data.hades.model.notification.NNotificationTarget;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@RepositoryRestResource(
        path = "notifications",
        itemResourceRel = "notification",
        collectionResourceRel = "notifications"
)
public interface NNotificationRepository
        extends NRepository<NNotification> {

    Collection<NNotification> findBySubject(final NNotificationSubject associate);

    Collection<NNotification> findByTarget(final NNotificationTarget target);

    @RestResource(path = "subject", rel = "bySubject")
    Page<NNotification> findBySubject(@Param("subject") final NNotificationSubject subject, final Pageable pageable);

    @RestResource(path = "target", rel = "byTarget")
    Page<NNotification> findByTarget(@Param("target") final NNotificationTarget subject, final Pageable pageable);
}
