package org.agatom.springatom.data.services;

import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.springframework.data.domain.Persistable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-03</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SDataInstallationStatusService<T extends DataInstallationStatus & Persistable<Long>>
        extends SDomainService<T> {

    T onSuccessfulInstallation(final Long hash, final String path, final Class<?> handler);

    T onFailureInstallation(final Long hash, final String path, final Class<?> handler, final Throwable failureReason);

}
