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

<a id="dupa" href="<s:url value="/app/wizard/NewUserWizard"/>">AAAA</a>
<script type="text/javascript">
    $(function () {
        Spring.addDecoration(new Spring.AjaxEventDecoration({
            elementId: 'dupa',
            event    : 'onclick',
            popup    : true,
            params   : {
                mode: "embedded"
            }
        }));
    });
</script>

<div id="calendarWrapper">
    <div id="calendar" class="x-calendar">
        <script type="text/javascript" id="calendarLoader">
            $(function () {
                SA.calendar.createCalendar($('#calendar'), <sc:calendarConfiguration/>);
            })
        </script>
    </div>
    <div id="calendarTable">
        <cmp:table type="org.agatom.springatom.server.model.beans.appointment.SAppointment"/>
    </div>
    <script type="text/javascript">
        var a = new Spring.ElementDecoration({
                    elementId  : 'calendarWrapper',
                    widgetType : "dijit.layout.TabContainer",
                    widgetAttrs: {
                        style   : 'width:100%; height:100%',
                        doLayout: false
                    }
                }),
                b = new Spring.ElementDecoration({
                    elementId  : 'calendar',
                    widgetType : "dijit.layout.ContentPane",
                    widgetAttrs: {
                        title     : 'Organizer',
                        style     : 'width:95%; height:95%',
                        scrollable: true,
                        selected  : true,
                        closable  : false
                    }
                }),
                c = new Spring.ElementDecoration({
                    elementId  : 'calendarTable',
                    widgetType : "dijit.layout.ContentPane",
                    widgetAttrs: {
                        title   : 'Table',
                        selected: false,
                        closable: false
                    }
                });
        Spring.addDecoration(c);
        Spring.addDecoration(b);
        Spring.addDecoration(a);
    </script>
</div>

