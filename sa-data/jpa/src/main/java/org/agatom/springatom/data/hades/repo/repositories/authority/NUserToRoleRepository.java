package org.agatom.springatom.data.hades.repo.repositories.authority;

import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.model.user.authority.UserToRoleLink;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@RepositoryRestResource
public interface NUserToRoleRepository
        extends NRepository<UserToRoleLink> {

    @RestResource(rel = "byUser", path = "user")
    @Query(name = "rolesByUser", value = "select SM from UserToRoleLink as SM where SM.pk.roleA = :user")
    Collection<UserToRoleLink> findByUser(@Param("user") final NUser user);

}
