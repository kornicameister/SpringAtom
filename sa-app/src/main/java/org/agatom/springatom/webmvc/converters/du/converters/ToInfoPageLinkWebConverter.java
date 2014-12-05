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

import org.agatom.springatom.cmp.component.core.builders.ComponentProduces;
import org.agatom.springatom.cmp.component.core.context.ComponentContextFactory;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMappingService;
import org.agatom.springatom.data.oid.SOidService;
import org.agatom.springatom.data.types.PersistentIdentity;
import org.agatom.springatom.webmvc.controllers.components.SVComponentController;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.InfoPageLinkGuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * {@code ToInfoPageLinkWebConverter}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = ToInfoPageLinkWebConverter.SELECTOR, types = Persistable.class)
public class ToInfoPageLinkWebConverter
        extends AbstractWebConverter {
    /** Constant <code>SELECTOR="infoPageLink"</code> */
    public static final  String                     SELECTOR          = "infoPageLink";
    private static final Logger                     LOGGER            = LoggerFactory.getLogger(ToInfoPageLinkWebConverter.class);
    @Autowired
    private              ComponentBuilderRepository builderRepository = null;
    @Autowired
    private              InfoPageMappingService     mappingService    = null;
    @Autowired
    private              ComponentContextFactory    contextFactory    = null;
    @Autowired
    private              SOidService                oidService        = null;

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
        if (!ClassUtils.isAssignableValue(Persistable.class, value)) {
            return null;
        }

        try {
            final String linkLabel = this.getLinkLabel(key, value, persistable);
            final String mappedRel = this.mappingService.getMappedRel((Class<Persistable<?>>) ClassUtils.getUserClass(value));
            final String builderId = this.builderRepository.getBuilderId(ClassUtils.getUserClass(value), ComponentProduces.PAGE_COMPONENT);
            Assert.hasText(builderId, "BuilderID not found");

            final InfoPageLinkGuiComponent cmp = new InfoPageLinkGuiComponent();
            cmp.setHref(linkTo(methodOn(SVComponentController.class).onComponentRequest(builderId, null, null)).withRel("builder"));
            cmp.setRel(mappedRel);
            cmp.setLabel(linkLabel);
            cmp.setContext(this.contextFactory.buildContext((Persistable<?>) value));
            cmp.setOid(this.oidService.getOid(value));

            return cmp;
        } catch (Exception exp) {
            LOGGER.warn(String.format("Failed to find InfoPage for key=%s,value=%s", key, value), exp);
            final TextGuiComponent component = new TextGuiComponent();
            component.setValue(((PersistentIdentity) value).getIdentity());
            return component;
        }

    }

    protected String getLinkLabel(final String key, final Object value, final Persistable<?> persistable) {
        return ((PersistentIdentity) value).getIdentity();
    }
}
