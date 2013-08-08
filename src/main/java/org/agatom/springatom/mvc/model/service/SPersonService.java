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

package org.agatom.springatom.mvc.model.service;

import org.agatom.springatom.model.beans.person.SPerson;
import org.agatom.springatom.model.beans.person.contact.SPersonCellPhoneContact;
import org.agatom.springatom.model.beans.person.contact.SPersonEmailContact;
import org.agatom.springatom.model.beans.person.contact.SPersonFaxContact;
import org.agatom.springatom.model.beans.person.contact.SPersonPhoneContact;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.model.types.contact.SContact;
import org.agatom.springatom.mvc.model.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.mvc.model.service.base.SService;
import org.agatom.springatom.mvc.model.service.constraints.PhoneNumber;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SPersonService<T extends SPerson, R extends JpaRepository>
        extends SService<T, Long, Integer, R> {

    SPersonPhoneContact newPhone(
            @PhoneNumber
            final String contact,
            final long assignTo) throws SEntityDoesNotExists;

    SContact newContactData(
            @NotNull
            final String contact,
            final long assignTo,
            @NotNull
            final SContact assignToContact) throws SEntityDoesNotExists;

    SPersonEmailContact newEmail(
            @Email
            final String contact,
            final long assignTo) throws SEntityDoesNotExists;

    SPersonCellPhoneContact newCellPhone(
            @PhoneNumber
            final String contact,
            final long assignTo) throws SEntityDoesNotExists;

    SPersonFaxContact newFax(
            @PhoneNumber
            final String contact,
            final long assignTo) throws SEntityDoesNotExists;

    <X extends SContact> List<X> findAllContacts(final Long idClient);

    List<T> findByPersonalInformation(
            @NotNull
            final SPersonalInformation information);

    List<T> findByFirstName(
            @NotNull
            final String firstName);

    List<T> findByLastName(
            @NotNull
            final String lastName);

    T findByEmail(
            @NotNull
            final String email);
}
