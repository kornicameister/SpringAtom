package org.agatom.springatom.data.services.ref;

import org.agatom.springatom.data.types.reference.EntityReference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface EntityReferenceHelper {
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    @Cacheable("entityReferenceHelper")
    Persistable<Long> fromReference(final EntityReference entityReference);

    EntityReference toReference(final Persistable<Long> persistable);
}
