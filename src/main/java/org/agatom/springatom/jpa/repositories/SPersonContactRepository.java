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

package org.agatom.springatom.jpa.repositories;

import org.agatom.springatom.jpa.SBasicRepository;
import org.agatom.springatom.model.beans.person.contact.SPersonContact;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier("SPersonContactRepository")
@RepositoryDefinition(domainClass = SPersonContact.class, idClass = Long.class)
public interface SPersonContactRepository
        extends SBasicRepository<SPersonContact, Long> {

}
