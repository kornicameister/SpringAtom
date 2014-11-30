package org.agatom.springatom.data.hades.service;

import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.model.user.authority.NRole;
import org.agatom.springatom.data.services.SUserService;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NUserService
        extends SUserService<NUser, NRole> {

    NPerson getPerson(final NUser user);

}
