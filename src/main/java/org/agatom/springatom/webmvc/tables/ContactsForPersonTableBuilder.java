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

package org.agatom.springatom.webmvc.tables;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.server.model.beans.person.QSPersonContact;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.elements.table.dandelion.DandelionTableComponent;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <p>ContactsForPersonTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SPersonContact.class)
@ComponentBuilder(ContactsForPersonTableBuilder.BUILDER_ID)
public class ContactsForPersonTableBuilder
		extends TableComponentBuilder<DandelionTableComponent, SPersonContact> {
	/** Constant <code>BUILDER_ID="contactsForPersonTableBuilder"</code> */
	protected static final String BUILDER_ID = "contactsForPersonTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SPersonContact.ENTITY_NAME));

	/** {@inheritDoc} */
	@Override
	protected Predicate getPredicate(final Long id, final Class<?> contextClass) {
		if (ClassUtils.isAssignable(SPerson.class, contextClass)) {
			return QSPersonContact.sPersonContact.assigned.id.eq(id);
		} else if (ClassUtils.isAssignable(SPersonContact.class, contextClass)) {
			return QSPersonContact.sPersonContact.id.eq(id);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected DandelionTableComponent buildDefinition(final ComponentDataRequest dataRequest) {
		final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
		this.helper.newTableColumn(component, "id", "persistentobject.id");
		this.helper.newTableColumn(component, "contact", "scontact.contact");
		this.helper.newTableColumn(component, "type", "scontact.type");
		return component;
	}
}
