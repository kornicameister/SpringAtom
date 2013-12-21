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

package org.agatom.springatom.webmvc.pages.builders;

import org.agatom.springatom.component.builders.ComponentBuilders;
import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.agatom.springatom.component.elements.value.DelegatedLink;
import org.agatom.springatom.component.elements.value.InContextBuilderLink;
import org.agatom.springatom.component.meta.LayoutType;
import org.agatom.springatom.ip.SDomainInfoPage;
import org.agatom.springatom.ip.component.builder.DomainInfoPageComponentBuilder;
import org.agatom.springatom.ip.component.elements.InfoPageComponent;
import org.agatom.springatom.ip.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.ip.component.elements.attributes.InfoPageAttributeComponent;
import org.agatom.springatom.ip.component.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.ip.data.EntityDataResponse;
import org.agatom.springatom.ip.mapping.InfoPageMappings;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.domain.SAppointmentService;
import org.agatom.springatom.webmvc.pages.infopage.AppointmentInfoPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@ComponentBuilds(id = "appointmentInfopage", builds = AppointmentInfoPage.class)
public class AppointmentInfoPageComponentBuilder
        extends DomainInfoPageComponentBuilder<SAppointment> {

    private static final Logger LOGGER = Logger.getLogger(AppointmentInfoPageComponentBuilder.class);
    @Autowired
    private SAppointmentService service;
    @Autowired
    private InfoPageMappings    pageMappings;
    @Autowired
    private ComponentBuilders   componentBuilders;

    @Override
    protected void populateInfoPagePanel(final InfoPagePanelComponent panel) {
        this.helper.newLinkAttribute(panel, "car", this.getEntityName());
        this.helper.newLinkAttribute(panel, "assignee", this.getEntityName());
        this.helper.newLinkAttribute(panel, "reporter", this.getEntityName());
    }

    @Override
    protected void populateBasicPanel(final InfoPagePanelComponent panel) {
        this.helper.newAttribute(panel, "id", "persistentobject.id", AttributeDisplayAs.VALUE);
        this.helper.newValueAttribute(panel, "begin", this.getEntityName());
        this.helper.newValueAttribute(panel, "end", this.getEntityName());
        this.helper.newValueAttribute(panel, "interval", this.getEntityName());
        this.helper.newValueAttribute(panel, "assigned", this.getEntityName());
    }

    @Override
    protected void populateTablePanel(final InfoPagePanelComponent panel) {
        this.helper.newTableAttribute(panel, "tasks", this.getEntityName());
    }

    @Override
    protected InfoPageComponent buildDefinition() {
        LOGGER.trace("/buildDefinition...");
        final InfoPageComponent cmp = new InfoPageComponent();

        this.populateBasicPanel(helper.newBasicPanel(cmp, LayoutType.VERTICAL));
        this.populateTablePanel(helper.newOneToManyPanel(cmp, LayoutType.VERTICAL));
        this.populateInfoPagePanel(helper.newManyToOnePanel(cmp, LayoutType.VERTICAL));

        LOGGER.trace(String.format("/buildDefinition -> %s", cmp.getPanelsCount()));
        return cmp;
    }

    @Override
    protected ComponentDataResponse buildData(final ComponentDataRequest dataRequest) {
        final Long objectId = dataRequest.getLong("objectId");
        final SAppointment appointment = this.service.findOne(objectId);
        return new EntityDataResponse() {
            @Override
            public Object getValueForPath(final String path) {
                Object value = null;
                try {
                    final Method method = ClassUtils.getMethod(getDomainClass(), String.format("get%s", StringUtils.capitalize(path)));
                    value = method.invoke(appointment);
                } catch (IllegalAccessException | InvocationTargetException ignore) {
                }

                if (value != null) {
                    final InfoPageAttributeComponent attributeComponent = getAttributeForPath(path);

                    switch (attributeComponent.getDisplayAs()) {
                        case INFOPAGE: {
                            if (value instanceof Persistable) {
                                String label = null;
                                Class<?> assocClass = null;
                                if (value instanceof SCar) {
                                    label = ((SCar) value).getLicencePlate();
                                    assocClass = SCar.class;
                                } else if (value instanceof SUser) {
                                    label = ((SUser) value).getPerson().getIdentity();
                                    assocClass = SUser.class;
                                }
                                final SDomainInfoPage domain = pageMappings.getInfoPageForDomain(assocClass);
                                if (domain != null) {
                                    final Link link = helper.getInfoPageLink(
                                            domain.getPath(),
                                            Long.parseLong(String.valueOf(((Persistable) value).getId()))
                                    );
                                    value = new DelegatedLink(link)
                                            .withLabel(label)
                                            .withRel(domain.getRel());
                                } else {
                                    value = label;
                                }
                            }
                        }
                        break;
                        case TABLE: {
                            if (componentBuilders.hasBuilder(SAppointmentTask.class, ComponentBuilds.Produces.TABLE_COMPONENT)) {
                                final String builderId = componentBuilders.getBuilderId(
                                        SAppointmentTask.class,
                                        ComponentBuilds.Produces.TABLE_COMPONENT
                                );
                                value = new InContextBuilderLink(
                                        builderId,
                                        SAppointment.class,
                                        appointment.getId(),
                                        helper.getBuilderLink()
                                );
                            }
                        }
                    }
                }

                return value != null ? value : "???";
            }
        }.setValue(appointment);
    }
}
