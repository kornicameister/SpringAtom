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

package org.agatom.springatom.server.repository.repositories.user;

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SUserRepository.REPO_NAME)
@RepositoryDefinition(domainClass = SUser.class, idClass = Long.class)
public interface SUserRepository
        extends SRepository<SUser, Long, Integer> {
    String REPO_NAME = "UserRepo";
}
