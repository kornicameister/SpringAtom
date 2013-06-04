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

package org.agatom.springatom.mvc.model.bo;

import org.agatom.springatom.model.beans.client.SClient;
import org.agatom.springatom.model.beans.client.SClientContact;
import org.agatom.springatom.model.beans.client.SClientProblemReport;
import org.agatom.springatom.mvc.model.dao.SMetaDataDAO;
import org.agatom.springatom.mvc.model.exceptions.EntityDoesNotExists;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SClientBO extends GenericBO<SClient, Long> {

    SClient disable(@NotNull final Long pk) throws EntityDoesNotExists;

    SClient enable(@NotNull final Long pk) throws EntityDoesNotExists;

    SClientProblemReport newProblemReport(@NotNull final String problem,
                                          @NotNull final Long client,
                                          @NotNull final Long mechanic,
                                          @NotNull final SMetaDataDAO.MetaType problemReportType) throws
            EntityDoesNotExists;

    SClientContact newPhone(@NotNull final String contact,
                            @NotNull final Long client) throws EntityDoesNotExists;

    SClientContact newEmail(@NotNull final String contact,
                            @NotNull final Long client) throws EntityDoesNotExists;

    SClientContact newCellPhone(@NotNull final String contact,
                                @NotNull final Long client) throws EntityDoesNotExists;

    SClientContact newFax(@NotNull final String contact,
                          @NotNull final Long client) throws EntityDoesNotExists;

    Set<SClientContact> findAllContacts(final Long idClient);
}
