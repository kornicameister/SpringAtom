package org.agatom.springatom.data.hades.repo.repositories.authority;

import org.agatom.springatom.data.hades.model.user.authority.NRole;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@RepositoryRestResource
public interface NRoleRepository
        extends NRepository<NRole> {

    NRole findByAuthority(final String authority);

}
