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

package org.agatom.springatom.cmp.component.infopages.builder;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.component.core.builders.AbstractComponentDefinitionBuilder;
import org.agatom.springatom.cmp.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.core.builders.multi.MultiComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.multi.MultiComponentDescriptor;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPageAttributeComponent;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPagePanelComponent;
import org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMapping;
import org.agatom.springatom.cmp.component.infopages.provider.InfoPageProviderService;
import org.agatom.springatom.cmp.component.infopages.provider.builder.InfoPageComponentBuilderService;
import org.agatom.springatom.data.oid.creators.PersistableOid;
import org.agatom.springatom.data.repositories.provider.RepositoriesHelper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * {@code InfoPageComponentBuilder} is abstract builder designed to to be proxied
 * with specific class as an generic type. Scoped as {@link org.springframework.beans.factory.config.BeanDefinition#SCOPE_PROTOTYPE}
 * takes advantage from dependency injection process.
 *
 * @author kornicameister
 * @version 0.0.5
 * @since 0.0.1
 */
@Lazy
@EntityBased(entity = Persistable.class)
@ComponentBuilder(InfoPageComponentBuilder.BUILDER_ID)
class InfoPageComponentBuilder
        extends AbstractComponentDefinitionBuilder<InfoPageComponent>
        implements MultiComponentBuilder {
    /** Constant <code>BUILDER_ID="genericInfoPageBuilder"</code> */
    protected static final String                                                            BUILDER_ID         = "genericInfoPageBuilder";
    private static final   Function<InfoPagePanelComponent, Set<InfoPageAttributeComponent>> GIPCR_FUNC         = new Function<InfoPagePanelComponent, Set<InfoPageAttributeComponent>>() {
        @Nullable
        @Override
        public Set<InfoPageAttributeComponent> apply(@Nullable final InfoPagePanelComponent input) {
            final Set<InfoPageAttributeComponent> attributes = Sets.newHashSet();
            if (input == null) {
                return attributes;
            }
            for (final InfoPageAttributeComponent attr : input) {
                attributes.add(attr);
            }
            return attributes;
        }
    };
    @Autowired
    private                InfoPageProviderService                                           providerService    = null;
    @Autowired
    private                InfoPageComponentBuilderService                                   builderService     = null;
    @Autowired
    private                RepositoriesHelper                                                repositoriesHelper = null;

    /**
     * {@inheritDoc}
     *
     * Returns fixed <b>ID</b> of {@link org.agatom.springatom.cmp.component.infopages.builder.InfoPageComponentBuilder}
     */
    @Override
    public String getId() {
        return BUILDER_ID;
    }

    @Override
    public Collection<MultiComponentDescriptor> getDescriptors() {
        final Collection<InfoPageMapping> registeredInfoPages = this.providerService.getRegisteredInfoPages();
        return FluentIterable.from(registeredInfoPages)
                .transform(new Function<InfoPageMapping, MultiComponentDescriptor>() {
                    @Nullable
                    @Override
                    public MultiComponentDescriptor apply(@Nullable final InfoPageMapping input) {
                        assert input != null;
                        final String alias = String.format("%sInfoPageBuilder", input.getRel());
                        final Class<?> type = input.getType();
                        return new MultiComponentDescriptor()
                                .setAlias(alias)
                                .setSupportedClasses(type);
                    }
                })
                .toSet();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected InfoPageComponent buildDefinition(final ComponentDataRequest dataRequest) throws ComponentException {
        final PersistableOid oid = (PersistableOid) dataRequest.getOid();
        try {
            return this.builderService.buildInfoPage(oid.getObjectClass());
        } catch (Exception e) {
            throw new ComponentException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected Object buildData(final ComponentDataRequest request) throws ComponentException {

        this.logger.trace(String.format("buildData(dataRequest=%s)", request));
        final PersistableOid oid = (PersistableOid) request.getOid();
        final Persistable<?> object = this.repositoriesHelper.getCrudInvoker(oid.getObjectClass()).invokeFindOne(oid.getObjectId());

        if (object == null) {
            throw new ComponentException(String.format("For request=%s found no object of type %s", request, ClassUtils.getShortName(this.getClass())));
        }

        final InfoPageComponent component = (InfoPageComponent) request.getComponent();

        final Set<InfoPageAttributeComponent> attributes = FluentIterable
                .from(component.getContent())
                .transformAndConcat(GIPCR_FUNC)
                .toSet();
        final BeanWrapper bw = new BeanWrapperImpl(object);
        final Map<String, PropertyDescriptor> properties = FluentIterable
                .from(Lists.newArrayList(bw.getPropertyDescriptors()))
                .transform(new Function<PropertyDescriptor, String>() {

                    @Nullable
                    @Override
                    public String apply(@Nullable final PropertyDescriptor input) {
                        assert input != null;
                        return input.getName();
                    }
                })
                .toMap(new Function<String, PropertyDescriptor>() {
                    @Nullable
                    @Override
                    public PropertyDescriptor apply(@Nullable final String input) {
                        return bw.getPropertyDescriptor(input);
                    }
                });

        this.logger.trace(String.format("processing object %s=%s", object, object.getId()));

        final Map<String, Object> data = Maps.newHashMap();

        for (final InfoPageAttributeComponent attribute : attributes) {
            this.logger.trace(String.format("Processing attribute for path=%s from %s", attribute.getPath(), attribute));

            final String path = attribute.getPath();
            final Object propertyValue = properties.containsKey(path) ? bw.getPropertyValue(path) : null;

            this.logger.trace(String.format("Processed attribute for path=%s to %s", path, propertyValue));

            data.put(path, propertyValue);
        }

        return data;
    }
}
