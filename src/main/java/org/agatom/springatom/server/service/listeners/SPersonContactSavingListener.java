/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.service.listeners;

import com.google.common.collect.Lists;
import org.agatom.springatom.core.annotations.EventListener;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.service.domain.SPersonContactService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * {@code SUserAuthoritiesSavingListener} is designed to persist {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority}
 * connected with given user over {@link org.agatom.springatom.server.model.beans.user.authority.SUserAuthority} link.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 05.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EventListener
class SPersonContactSavingListener
        extends AbstractRepositoryEventListener<SPerson> {
    private static final Logger                LOGGER  = Logger.getLogger(SPersonContactSavingListener.class);
    @Autowired
    private              SPersonContactService service = null;

    /** {@inheritDoc} */
    @Override
    protected void onAfterCreate(final SPerson person) {
        LOGGER.trace(String.format("onAfterCreate(person=%s)", person));
        final List<SPersonContact> contacts = person.getContacts();
        if (!CollectionUtils.isEmpty(contacts)) {
            try {
                final List<SPersonContact> toSave = Lists.newArrayList();
                for (SPersonContact contact : contacts) {
                    if (contact.isNew()) {
                        toSave.add(this.service.save(contact));
                    }
                }
            } catch (Exception exp) {
                LOGGER.error(String.format("Failed to save contact data for %s", person), exp);
                throw new RuntimeException(exp);
            }
        } else {
            LOGGER.trace(String.format("%s has no contacts, skipping", person));
        }
    }

}
