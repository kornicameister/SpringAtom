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

package org.agatom.springatom.web.tables;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.cmp.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.table.TableComponentBuilder;
import org.agatom.springatom.cmp.component.table.elements.ng.NgTable;
import org.agatom.springatom.cmp.component.table.elements.ng.NgTableColumn;
import org.agatom.springatom.cmp.locale.beans.LocalizedClassModel;
import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.model.person.NPersonContact;
import org.agatom.springatom.data.hades.model.person.QNPersonContact;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <p>ContactsForPersonTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@EntityBased(entity = NPersonContact.class)
@ComponentBuilder(ContactsForPersonTableBuilder.BUILDER_ID)
public class ContactsForPersonTableBuilder
        extends TableComponentBuilder<NgTable, NPersonContact> {
    /** Constant <code>BUILDER_ID="contactsForPersonTableBuilder"</code> */
    protected static final String BUILDER_ID = "contactsForPersonTableBuilder";
    private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(ClassUtils.getShortName(NPersonContact.class)));

    /** {@inheritDoc} */
    @Override
    protected NgTable buildDefinition(final ComponentDataRequest dataRequest) {
        final QNPersonContact contact = QNPersonContact.nPersonContact;
        final NgTable table = new NgTable(TABLE_ID, BUILDER_ID);
        final LocalizedClassModel<NPersonContact> lModel = this.getLocalizedClassModel();

        table.setBorder(false)
                .setSortable(true)
                .setCollapsible(false);

        table.addContent(
                new NgTableColumn()
                        .setDataIndex(this.getAttributeName(contact.id))
                        .setText(lModel.getLocalizedAttribute(this.getAttributeName(contact.id)))
        );
        table.addContent(
                new NgTableColumn()
                        .setDataIndex(this.getAttributeName(contact.type))
                        .setText(lModel.getLocalizedAttribute(this.getAttributeName(contact.id)))
        );
        table.addContent(
                new NgTableColumn()
                        .setDataIndex(this.getAttributeName(contact.contact))
                        .setText(lModel.getLocalizedAttribute(this.getAttributeName(contact.contact)))
        );

        return table;
    }

    /** {@inheritDoc} */
    @Override
    protected Predicate getPredicate(final Long id, final Class<?> contextClass) {
        final QNPersonContact personContact = QNPersonContact.nPersonContact;
        if (ClassUtils.isAssignable(NPerson.class, contextClass)) {
            return personContact.assignee.id.eq(id);
        } else if (ClassUtils.isAssignable(NPersonContact.class, contextClass)) {
            return personContact.id.eq(id);
        }
        return null;
    }
}
