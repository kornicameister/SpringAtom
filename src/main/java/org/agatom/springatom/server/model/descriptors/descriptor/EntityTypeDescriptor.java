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

package org.agatom.springatom.server.model.descriptors.descriptor;

import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.EntityDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.BasicPropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.ManyToOnePropertyyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.OneToManyPropertyDescriptor;
import org.agatom.springatom.server.model.descriptors.properties.SystemPropertyDescriptor;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EntityTypeDescriptor<X>
        implements EntityDescriptor<X> {
    private EntityType<X>    entityType    = null;
    private AttributesHolder attributes    = new AttributesHolder();
    private String           localizedName = null;

    public EntityTypeDescriptor(final EntityType<X> entityType) {
        this.entityType = entityType;
    }

    @Override
    public EntityType<X> getEntityType() {
        return entityType;
    }

    @Override
    public String getLocalizedName() {
        return this.localizedName;
    }

    public void setLocalizedName(final String localizedName) {
        this.localizedName = localizedName;
    }

    @Override
    public Set<BasicPropertyDescriptor> getBasicProperties() {
        if (!this.attributes.hasProperties(BasicPropertyDescriptor.class)) {
            final Set<BasicPropertyDescriptor> pds = Sets.newHashSet();
            final Set<? extends SingularAttribute<? super X, ?>> attributeSet = this.entityType.getSingularAttributes();

            for (final SingularAttribute<?, ?> attribute : attributeSet) {
                if (attribute.getPersistentAttributeType().equals(Attribute.PersistentAttributeType.BASIC)) {
                    pds.add(new BasicPropertyDescriptor(attribute));
                }
            }

            this.attributes.addBasicProperties(pds);
        }
        return this.attributes.getBasicProperties();
    }

    @Override
    public Set<OneToManyPropertyDescriptor> getOneToManyProperties() {
        if (!this.attributes.hasProperties(OneToManyPropertyDescriptor.class)) {
            final Set<OneToManyPropertyDescriptor> pds = Sets.newHashSet();
            final Set<? extends SingularAttribute<? super X, ?>> attributeSet = this.entityType.getSingularAttributes();

            for (final SingularAttribute<?, ?> attribute : attributeSet) {
                if (attribute.getPersistentAttributeType().equals(Attribute.PersistentAttributeType.ONE_TO_MANY)) {
                    pds.add(new OneToManyPropertyDescriptor(attribute));
                }
            }

            this.attributes.addAllOneToManyProperties(pds);
        }
        return this.attributes.getOneToManyProperties();
    }

    @Override
    public Set<ManyToOnePropertyyDescriptor> getManyToOneProperties() {
        if (!this.attributes.hasProperties(ManyToOnePropertyyDescriptor.class)) {
            final Set<ManyToOnePropertyyDescriptor> pds = Sets.newHashSet();
            final Set<? extends SingularAttribute<? super X, ?>> attributeSet = this.entityType.getSingularAttributes();

            for (final SingularAttribute<?, ?> attribute : attributeSet) {
                if (attribute.getPersistentAttributeType().equals(Attribute.PersistentAttributeType.MANY_TO_ONE)) {
                    pds.add(new ManyToOnePropertyyDescriptor(attribute));
                }
            }

            this.attributes.addAllManyToOneProperties(pds);
        }
        return this.attributes.getManyToOneProperties();
    }

    @Override
    public Set<SystemPropertyDescriptor> getSystemProperties() {
        if (!this.attributes.hasProperties(SystemPropertyDescriptor.class)) {
//            final Set<? super SystemPropertyDescriptor> pds = Sets.newHashSet();
//
//            Set<SingularAttribute<? super X, ?>> attributeSet;
//            IdentifiableType<? super X> type = this.entityType;
//
//            do {
//                // resolve block
//                attributeSet = this.entityType.getSingularAttributes();
//                if (attributeSet != null && attributeSet.size() > 0) {
//                    for (final SingularAttribute<?, ?> attribute : attributeSet) {
//                        if (!attribute.isAssociation()) {
//                            pds.add(new SystemPropertyDescriptor(attribute));
//                        }
//                    }
//                }
//                // resoulve block
//            } while ((type = type.getSupertype()) != null);
//
//            this.attributes.addSystemProperties(pds);
        }
        return this.attributes.getSystemProperties();
    }

    @Override
    public void initialize() {
        this.getBasicProperties();
        this.getSystemProperties();
        this.getOneToManyProperties();
        this.getManyToOneProperties();
    }
}
