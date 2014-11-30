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

package org.agatom.springatom.webmvc.converters.du.converters;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.cmp.component.core.EmbeddableComponent;
import org.agatom.springatom.cmp.component.core.builders.ComponentProduces;
import org.agatom.springatom.cmp.component.core.context.ComponentContextFactory;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.core.elements.ContentComponent;
import org.agatom.springatom.cmp.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPageAttributeComponent;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPagePanelComponent;
import org.agatom.springatom.cmp.component.table.meta.TableRenderInformation;
import org.agatom.springatom.data.oid.SOidService;
import org.agatom.springatom.webmvc.controllers.components.SVComponentController;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.TableRequestComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

/**
 * {@link org.agatom.springatom.webmvc.converters.du.converters.ToTableRequestWebConverter}
 * crates instance of {@link org.agatom.springatom.webmvc.converters.du.component.TableRequestComponent} component.
 * Provides information required to make a request to retrieve table configuration and data.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 01.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@WebConverter(key = ToTableRequestWebConverter.SELECTOR)
public class ToTableRequestWebConverter
        extends AbstractWebConverter {
    /** Constant <code>SELECTOR="tableRequestInitMaker"</code> */
    public static final  String                     SELECTOR                = "tableRequestInitMaker";
    private static final Logger                     LOGGER                  = Logger.getLogger(ToTableRequestWebConverter.class);
    @Autowired
    protected            ComponentBuilderRepository builderRepository       = null;
    @Autowired
    protected            ComponentContextFactory    componentContextFactory = null;
    @Autowired
    protected            SOidService                oidService              = null;

    /** {@inheritDoc} */
    @Override
    protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) throws Exception {
        LOGGER.trace(String.format("doConverter(key=%s,value=%s)", key, value));

        final String builderId = this.getBuilderId(key, persistable, webRequest);

        if (StringUtils.hasText(builderId)) {
            final TableRequestComponent request = new TableRequestComponent();
            final TableRenderInformation renderInformation = new TableRenderInformation();

            renderInformation.setBuilderId(builderId)
                    .setUrl(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SVComponentController.class).onComponentRequest(builderId, null, null)).withRel("definition"))
                    .setContext(this.componentContextFactory.buildContext(persistable))
                    .setOid(this.oidService.getOid(persistable).getOid());

            request.setValue(renderInformation);
            return request;
        }

        final TextGuiComponent component = new TextGuiComponent();
        component.setValue("[missing-builder]");

        return null;
    }

    /**
     * Retrieves {@code builderId}. Tries to find explicitly set value or implicitly defined from value type
     *
     * @param key         property path
     * @param persistable persistable to get {@link javax.persistence.metamodel.Attribute} definition for {@code path}
     * @param webRequest  web request to get {@link org.agatom.springatom.cmp.component.infopages.elements.InfoPageComponent} from
     *
     * @return builderId
     *
     * @see #getBuilderIdFromTableDataType(String, org.springframework.data.domain.Persistable)
     * @see #getExplicitlyDefinedBuilderId(String, ComponentDataRequest)
     */
    protected String getBuilderId(final String key, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        String builderId = this.getExplicitlyDefinedBuilderId(key, webRequest);
        if (!StringUtils.hasText(builderId)) {
            builderId = this.getBuilderIdFromTableDataType(key, persistable);
        }
        return builderId;
    }

    /**
     * Tries to locate explicitly defined {@code builderId} if such was set in {@link InfoPageAttributeComponent}
     * during compile time for {@link org.agatom.springatom.cmp.component.infopages.elements.InfoPageComponent} template
     *
     * @param key        property path
     * @param webRequest web request to get {@link org.agatom.springatom.cmp.component.infopages.elements.InfoPageComponent} from
     *
     * @return builderId if found or null
     *
     * @see org.agatom.springatom.cmp.component.infopages.provider.builder.InfoPageComponentBuilderService
     * @see org.agatom.springatom.cmp.component.infopages.elements.InfoPageAttributeComponent#dynamicProperties
     */
    protected String getExplicitlyDefinedBuilderId(final String key, final ComponentDataRequest webRequest) {
        String builderId = null;

        final ContentComponent<?> component = (ContentComponent<?>) webRequest.getComponent();
        final Optional<? extends EmbeddableComponent> match = FluentIterable.from(component).firstMatch(new Predicate<EmbeddableComponent>() {
            @Override
            public boolean apply(@Nullable final EmbeddableComponent input) {
                if (input == null) {
                    return false;
                }
                if (ClassUtils.isAssignableValue(InfoPagePanelComponent.class, input)) {
                    final InfoPagePanelComponent panelComponent = (InfoPagePanelComponent) input;
                    return panelComponent.containsAttributeForPath(key);
                }
                return false;
            }
        });
        if (match.isPresent()) {
            final InfoPagePanelComponent tmp = (InfoPagePanelComponent) match.get();
            final InfoPageAttributeComponent forPath = tmp.getAttributeForPath(key);
            final Map<String, Object> dynamicProperties = forPath.getDynamicProperties();
            builderId = (String) dynamicProperties.get("builderId");
        }
        return builderId;
    }

    /**
     * Resolves {@code builderId} using {@code key} and {@link org.springframework.data.domain.Persistable}.
     * If {@code key} is a property of {@code persistable} therefore {@link javax.persistence.metamodel.EntityType#getAttribute(String)} returns
     * valid {@link javax.persistence.metamodel.PluralAttribute} for {@code key}, this method is able to determine {@code builderId} by calling
     * {@link ComponentBuilderRepository#getBuilderId(Class, ComponentProduces)}
     * where:
     * <ol>
     * <li>{@code class} corresponds to attribute type</li>
     * <li>{@link ComponentProduces} is {@link ComponentProduces#TABLE_COMPONENT}</li>
     * </ol>
     *
     * <b>Note</b>
     * This method will throw {@link org.agatom.springatom.webmvc.converters.du.exception.WebConverterException} if found {@link javax.persistence.metamodel.Attribute} is neither {@link javax.persistence.metamodel.Attribute#isCollection()}
     * nor {@link javax.persistence.metamodel.Attribute#isAssociation()}
     *
     * @param key         property path
     * @param persistable persistable to get {@link javax.persistence.metamodel.Attribute} definition for {@code path}
     *
     * @return builderId
     */
    protected String getBuilderIdFromTableDataType(final String key, final Persistable<?> persistable) {
//        String builderId = null;
//        final PersistentEntity<?, ?> descriptor = this.mappingContext.getPersistentEntity(persistable.getClass());
//        assert descriptor != null;
//
//        final PersistentProperty<?> attribute = descriptor.getPersistentProperty(key);
//        if (!(attribute.isAssociation() && attribute.isCollectionLike())) {
//            throw new WebConverterException(String.format("Requested to convert %s as table request, but %s was not recognized as association for %s", key, key, ClassUtils.getUserClass(persistable.getClass())));
//        }
//
//        final PluralAttribute<?, ?, ?> pluralAttribute = (PluralAttribute<?, ?, ?>) attribute;
//        final Class<?> associatedType = ClassUtils.getUserClass(pluralAttribute.getElementType().getJavaType());
//
//        LOGGER.trace(String.format("%s corresponds to %s", key, ClassUtils.getUserClass(associatedType)));
//
//        final boolean hasBuilder = this.builderRepository.hasBuilder(associatedType, ComponentProduces.TABLE_COMPONENT);
//        if (hasBuilder) {
//            builderId = this.builderRepository.getBuilderId(associatedType, ComponentProduces.TABLE_COMPONENT);
//        }
//
//        LOGGER.trace(String.format("%s %s to existing %s builder", associatedType, (hasBuilder ? "corresponds" : "does not corresponds"), ComponentProduces.TABLE_COMPONENT));
//        return builderId;
        return null;
    }

}
