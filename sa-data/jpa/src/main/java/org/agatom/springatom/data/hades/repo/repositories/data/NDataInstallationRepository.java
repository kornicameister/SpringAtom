package org.agatom.springatom.data.hades.repo.repositories.data;

import org.agatom.springatom.data.hades.model.data.NDataInstallationStatus;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.agatom.springatom.data.types.data.DataInstallationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@RepositoryRestResource
public interface NDataInstallationRepository {

    @RepositoryRestResource
    interface NDataInstallationStatusRepository
            extends NRepository<NDataInstallationStatus> {

        @RestResource(path = "byStatus", rel = "byStatus")
        Page<NDataInstallationStatus> getByInstallationStatus(@Param("status") final DataInstallationStatus.InstallStatus status, final Pageable pageable);

        Collection<NDataInstallationStatus> getByInstallationStatus(final DataInstallationStatus.InstallStatus status);

    }

}
