/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.server.service.impl;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.QSUser;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.repositories.SClientRepository;
import org.agatom.springatom.server.repository.repositories.SMechanicRepository;
import org.agatom.springatom.server.repository.repositories.SUserRepository;
import org.agatom.springatom.server.service.SUserService;
import org.agatom.springatom.server.service.exceptions.SEntityDoesNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = SUserServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SUserServiceImpl
        extends SServiceImpl<SUser, Long, Integer, SUserRepository>
        implements SUserService {
    public static final String SERVICE_NAME = "sa.service.UserService";
    private SUserRepository     repository;
    @Autowired
    @Qualifier(value = "clientRepository")
    private SClientRepository   clientRepository;
    @Autowired
    @Qualifier(value = "mechanicRepository")
    private SMechanicRepository mechanicRepository;
    @Autowired
    @Qualifier(value = "securedPasswordEncoder")
    private PasswordEncoder     passwordEncoder;

    @Override
    @Autowired(required = true)
    public void autoWireRepository(@Qualifier("userRepo") final SUserRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final SUser user = this.repository.findOne(QSUser.sUser.credentials.username.eq(username));
        if (user == null) {
            throw new UsernameNotFoundException(String
                    .format("SUser with userName=%s not found", username), new SEntityDoesNotExists(SUser.class, username));
        }
        return user;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SEntityDoesNotExists.class) //TODO figure out possible exceptions
    public SUser registerNewUser(final String userName,
                                 final String password,
                                 final long personId) throws SEntityDoesNotExists {

        // TODO add user registration
        final SPerson person = this.retrievePerson(personId);

        return null;
    }

    private SPerson retrievePerson(final long personId) throws SEntityDoesNotExists {
        SPerson person;

        person = this.clientRepository.findOne(personId);
        if (person == null) {
            person = this.mechanicRepository.findOne(personId);
        }

        if (person == null) {
            throw new SEntityDoesNotExists(SPerson.class, personId);
        }

        return person;
    }
}
