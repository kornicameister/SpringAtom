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

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Predicate;
import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.builders.table.TableComponentBuilder;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.agatom.springatom.component.elements.table.DandelionTableComponent;
import org.agatom.springatom.component.elements.value.DelegatedLink;
import org.agatom.springatom.component.elements.value.InContextBuilderLink;
import org.agatom.springatom.ip.SDomainInfoPage;
import org.agatom.springatom.ip.mapping.InfoPageMappings;
import org.agatom.springatom.server.model.beans.appointment.QSAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.repository.repositories.appointment.SAppointmentTaskRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@ComponentBuilds(
        id = AppointmentTaskTableBuilder.BUILDER_ID,
        builds = SAppointmentTask.class,
        produces = ComponentBuilds.Produces.TABLE_COMPONENT
)
public class AppointmentTaskTableBuilder
        extends TableComponentBuilder<DandelionTableComponent> {

    protected static final String BUILDER_ID = "appointmentTaskTableBuilder";
    private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SAppointmentTask.ENTITY_NAME));
    private static final   Logger LOGGER     = Logger.getLogger(AppointmentTaskTableBuilder.class);
    @Autowired
    @Qualifier("AppointmentTaskRepo")
    private SAppointmentTaskRepository service;
    @Autowired
    private InfoPageMappings           pageMappings;

    @Override
    protected DandelionTableComponent buildDefinition() {
        final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
        this.helper.newTableColumn(component, "id", "persistentobject.id");
        this.helper.newTableColumn(component, "infopage", "persistentobject.infopage").setRenderFunctionName("renderInfoPageLink");
        this.helper.newTableColumn(component, "type", "sappointment.task.type");
        this.helper.newTableColumn(component, "task", "sappointment.task.task");

        return component;
    }

    @Override
    protected ComponentDataResponse buildData(final ComponentDataRequest dataRequest) {

        final DatatablesCriterias dc = (DatatablesCriterias) dataRequest.getValues().get("dandelionCriterias");
        final InContextBuilderLink contextLink = (InContextBuilderLink) dataRequest.getValues().get("contextLink");

        LOGGER.debug(String.format("/buildData -> criteria=%s, context=%s", dc, contextLink));

        final Predicate predicate = this.getPredicate(Long.valueOf(contextLink.getContextKey().toString()));
        final long countInContext = this.service.count(predicate);

        final Page<SAppointmentTask> all = this.service.findAll(
                predicate,
                this.getPageable(countInContext, dc.getDisplaySize())
        );

        final List<Map<String, Object>> data = Lists.newArrayList();
        final SDomainInfoPage domain = this.pageMappings.getInfoPageForDomain(SAppointmentTask.class);

        for (final SAppointmentTask task : all) {
            final Map<String, Object> map = Maps.newHashMap();
            for (ColumnDef columnDef : dc.getColumnDefs()) {
                final String path = columnDef.getName();
                Object value = null;
                if (!path.equals("infopage")) {
                    try {
                        final Method method = ClassUtils.getMethod(SAppointmentTask.class, String.format("get%s", StringUtils.capitalize(path)));
                        value = method.invoke(task);
                    } catch (IllegalAccessException | InvocationTargetException ignore) {
                    }
                } else if (domain != null) {
                    final Link link = helper.getInfoPageLink(
                            domain.getPath(),
                            Long.parseLong(String.valueOf(task.getId()))
                    );
                    value = new DelegatedLink(link).withLabel(ClassUtils.getShortName(SAppointmentTask.class));
                }
                map.put(path, value != null ? value : "???");
            }
            if (!map.isEmpty()) {
                data.add(map);
            }
        }

        ComponentDataResponse<DataSet<Map<String, Object>>> response = new ComponentDataResponse<>();
        response.setValue(new DataSet<>(data, (long) data.size(), countInContext));
        return response;

    }

    private Predicate getPredicate(final Long appointmentId) {
        return QSAppointmentTask.sAppointmentTask.appointment.id.eq(appointmentId);
    }

    private PageRequest getPageable(final long totalObjects, final int pageSize) {
        return new PageRequest(this.getPageNumber(totalObjects, pageSize), pageSize);
    }

    private int getPageNumber(final long totalObjects, final int pageSize) {
        if (pageSize > totalObjects) {
            return 0;
        } else {
            final int pageNumber = (int) Math.floor(totalObjects / pageSize);
            if (pageNumber * pageSize < totalObjects) {
                return 0;
            }
            return pageNumber;
        }
    }
}
