package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.data.NDataInstallationStatus;
import org.agatom.springatom.data.hades.repo.repositories.data.NDataInstallationRepository;
import org.agatom.springatom.data.hades.service.NDataInstallationStatusService;
import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
class DataInstallationStatusDomainService
        extends AbstractDomainService<NDataInstallationStatus>
        implements NDataInstallationStatusService {

    @Override
    public NDataInstallationStatus onSuccessfulInstallation(final Long hash, final String path, final Class<?> handler) {
        return this.newDIS(hash, path, handler, null);
    }

    @Override
    public NDataInstallationStatus onFailureInstallation(final Long hash, final String path, final Class<?> handler, final Throwable failureReason) {
        return this.newDIS(hash, path, handler, failureReason);
    }

    private NDataInstallationStatus newDIS(final Long hash, final String path, final Class<?> handler, final Throwable failureReason) {
        final NDataInstallationStatus status = new NDataInstallationStatus();
        status.setInstallationFailure(failureReason);
        status.setInstallationHandler(handler);
        status.setInstallationHash(hash);
        status.setInstallationPath(path);
        status.setInstallationTimestamp(DateTime.now());
        status.setInstallationStatus(failureReason == null ? DataInstallationStatus.InstallStatus.SUCCESS : DataInstallationStatus.InstallStatus.FAILED);
        return this.repo().save(status);
    }

    private NDataInstallationRepository.NDataInstallationStatusRepository repo() {
        return (NDataInstallationRepository.NDataInstallationStatusRepository) this.repository;
    }
}
