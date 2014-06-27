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

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="scmp" uri="http://www.example.org/sa/scmp" %>

<s:eval expression="@defaultComponentBuilderRepository.getBuilderId(T(java.lang.Class).forName(type),T(org.agatom.springatom.web.component.core.builders.ComponentProduces).TABLE_COMPONENT)"
        var="builderId" scope="page"/>
<c:if test="${builderId != null}">
    <div id="${builderId}">
        <script type="text/javascript" defer="defer" async="async">
            Ext.onReady(function () {
                SA.component.loadTable({
                    renderTo: '${builderId}',
                    config  :<scmp:getComponentConfiguration componentId="${builderId}"/>
                });
            })
        </script>
    </div>
</c:if>
<c:if test="${builderId == null}">
    Internal error: builder missing
</c:if>