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

package org.agatom.springatom.ip.component.builder;

import org.agatom.springatom.ip.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DomainInfoPageComponentBuilder<Y extends Persistable<?>>
        extends InfoPageComponentBuilder {

    private static final Logger LOGGER = Logger.getLogger(DomainInfoPageComponentBuilder.class);
    @Autowired
    protected EntityDescriptors descriptors;
    private   Class<Y>          domainClass;

    public Class<Y> getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(final Class<Y> domainClass) {
        this.domainClass = domainClass;
    }

    protected EntityDescriptor<Y> getEntityDescriptor() {
        return this.descriptors.getDomainDescriptor(this.domainClass);
    }

    protected String getEntityName() {
        return this.getEntityDescriptor().getEntityType().getName();
    }

    public Attribute<? super Y, ?> getEntityAttribute(final String path) {
        final EntityType<Y> entityType = getEntityDescriptor().getEntityType();
        Attribute<? super Y, ?> attribute = null;
        try {
            attribute = entityType.getAttribute(path);
        } finally {
            LOGGER.trace(String.format("%s has no attribute %s", entityType.getName(), path));
        }
        return attribute;
    }

    protected abstract void populateInfoPagePanel(InfoPagePanelComponent panel);

    protected abstract void populateBasicPanel(InfoPagePanelComponent panel);

    protected abstract void populateTablePanel(InfoPagePanelComponent panel);
}
