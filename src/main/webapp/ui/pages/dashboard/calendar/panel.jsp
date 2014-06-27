<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                 ~
  ~                                                                                              ~
  ~ [SpringAtom] is free software: you can redistribute it and/or modify                         ~
  ~ it under the terms of the GNU General Public License as published by                         ~
  ~ the Free Software Foundation, either version 3 of the License, or                            ~
  ~ (at your option) any later version.                                                          ~
  ~                                                                                              ~
  ~ [SpringAtom] is distributed in the hope that it will be useful,                              ~
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of                               ~
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                ~
  ~ GNU General Public License for more details.                                                 ~
  ~                                                                                              ~
  ~ You should have received a copy of the GNU General Public License                            ~
  ~ along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--%>

<%@ page language="java" session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sc" uri="/WEB-INF/tags/sa/calendar.tld" %>
<%@ taglib prefix="cmp" tagdir="/WEB-INF/tags/sa/component" %>
<%@ taglib prefix="scmp" uri="http://www.example.org/sa/scmp" %>

<div id="calendarWrapper">
    <s:eval expression="@defaultComponentBuilderRepository.getBuilderId(
    T(org.agatom.springatom.server.model.beans.appointment.SAppointment),
    T(org.agatom.springatom.web.component.core.builders.ComponentProduces).TABLE_COMPONENT)" var="builderId"
            scope="page"/>
    <script type="text/javascript">
        Ext.require(['Ext.tab.Panel', 'SA.view.cmp.GridComponent']);
        Ext.onReady(function () {
            Ext.create('Ext.tab.Panel', {
                activeTab: 1,
                renderTo : Ext.get('calendarWrapper'),
                items    : [
                    {
                        title    : 'Organizer',
                        tabConfig: {
                            title  : 'Organizer',
                            tooltip: 'Organizer'
                        },
                        autoEl   : {
                            tag: 'div',
                            id : 'calendar'
                        },
                        listeners: {
                            'render': function () {
                                <%--SA.calendar.createCalendar($('#calendar'), <sc:calendarConfiguration/>);--%>
                            }
                        }
                    },
                    {
                        title    : 'Appointments',
                        tabConfig: {
                            title  : 'Appointments',
                            tooltip: 'Appointments'
                        },
                        items    : {
                            xtype: 'infoPageGrid',
                            data :<scmp:getComponentConfiguration componentId="${builderId}"/>
                        }
                    }
                ]
            });
        })
    </script>
</div>

