<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
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

<%@ attribute name="type" rtexprvalue="true" required="true" type="java.lang.String" %>

<s:eval expression="@ComponentBuildersModuleConfiguration.componentBuilders.getBuilderId(
                    T(java.lang.Class).forName(type),
                    T(org.agatom.springatom.web.component.builders.annotation.ComponentBuilds$Produces).TABLE_COMPONENT
                )"
        var="builderId"
        scope="page"/>
<div id="${builderId}">

</div>
<script type="text/javascript" defer="defer" async="async">
    $(function () {
        $('#' + '${builderId}').loadBuilderView({url: '/app/tableBuilder/${builderId}'});
    });
</script>