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

package org.agatom.springatom.server.model.descriptors.association;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.descriptors.EntityAssociation;
import org.agatom.springatom.server.model.descriptors.SlimEntityDescriptor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

/**
 * {@code AssociationInformation} is a support bean for {@link org.agatom.springatom.web.flows.wizards.wizard.rbuilder.ReportWizard}
 * which marks single entity further recognized by {@link org.agatom.springatom.web.rbuilder.bean.ReportableEntity#getId()} being
 * possible to create {@code query} with other entities
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SimpleEntityAssociation
        implements EntityAssociation {
    private static final long                         serialVersionUID = 2095287608399749140L;
    private              SlimEntityDescriptor<?>      master           = null;
    private              Set<SlimEntityDescriptor<?>> associations     = Sets.newHashSet();

    @Override
    public String getId() {
        return this.master.getName();
    }

    @Override
    public SlimEntityDescriptor<?> getMaster() {
        return master;
    }

    @Override
    public Set<SlimEntityDescriptor<?>> getAssociations() {
        return associations;
    }

    public SlimEntityDescriptor<?> getAssociation(final Class<?> clazz) {
        final Optional<SlimEntityDescriptor<?>> match = this.getSlimEntityDescriptorOptional(clazz);
        if (match.isPresent()) {
            return match.get();
        }
        return null;
    }

    @Override
    public boolean containsAssociation(final SlimEntityDescriptor<?> association) {
        return associations.contains(association);
    }

    @Override
    public boolean containsAssociation(final Class<?> clazz) {
        final Optional<SlimEntityDescriptor<?>> match = this.getSlimEntityDescriptorOptional(clazz);
        return match.isPresent();
    }


    @Override
    public <V extends SlimEntityDescriptor<?>> boolean containsAssociations(@NotNull final Collection<V> associations) {
        return this.associations.containsAll(associations);
    }

    @Override
    public int size() {
        return associations.size();
    }

    @Override
    public boolean isEmpty() {
        return associations.isEmpty();
    }

    public SimpleEntityAssociation setMaster(final SlimEntityDescriptor<?> master) {
        this.master = master;
        return this;
    }

    public SimpleEntityAssociation setAssociations(final Set<SlimEntityDescriptor<?>> associations) {
        this.associations = associations;
        return this;
    }

    public boolean addAssociation(final SlimEntityDescriptor<?> slimEntityDescriptor) {
        return slimEntityDescriptor != null && associations.add(slimEntityDescriptor);
    }

    public boolean removeAssociation(final SlimEntityDescriptor<?> association) {
        return associations.remove(association);
    }

    private Optional<SlimEntityDescriptor<?>> getSlimEntityDescriptorOptional(final Class<?> clazz) {
        return (Optional<SlimEntityDescriptor<?>>) FluentIterable.from(this.associations)
                                                                 .firstMatch(new Predicate<SlimEntityDescriptor<?>>() {
                                                                     @Override
                                                                     public boolean apply(@Nullable
                                                                                          final SlimEntityDescriptor<?> input) {
                                                                         return input != null && input.getJavaClass()
                                                                                                      .equals(clazz);
                                                                     }
                                                                 });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleEntityAssociation that = (SimpleEntityAssociation) o;

        return Objects.equal(this.master, that.master) &&
                Objects.equal(this.associations, that.associations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(master, associations);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(master)
                      .addValue(associations)
                      .toString();
    }
}
