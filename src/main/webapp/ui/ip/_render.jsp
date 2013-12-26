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

<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" isThreadSafe="false" %>
<%@ taglib prefix="ip" tagdir="/WEB-INF/tags/ip" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="ipBuilder" scope="request" type="org.agatom.springatom.ip.component.builder.EntityInfoPageComponentBuilder"/>

<s:eval expression="ipBuilder.definition" var="definition" scope="page"/>
<jsp:useBean id="definition" class="org.agatom.springatom.ip.component.elements.InfoPageComponent" scope="page"/>

<s:eval expression="ipBuilder.data" var="data" scope="page"/>
<jsp:useBean id="data" type="org.agatom.springatom.ip.data.EntityInfoPageResponse" scope="page"/>

<c:forEach items="${definition.content}" var="panel" varStatus="it">
    <ip:renderThumbnail thubmnail="${definition.thumbnail}"/>
    <div id="panels-${fn:toLowerCase(panel.holds)}" class="x-ip-panel" data-builtWith="${ipBuilder.id}">

        <div class="x-ip-panel-title">
            <c:if test="${panel.basicAttributesHolder}">
                <i class="fa fa-info-circle fa-color-black"></i>
            </c:if>
            <c:if test="${panel.oneToManyAttributesHolder}">
                <i class="fa fa-fast-backward fa-color-black"></i>
            </c:if>
            <c:if test="${panel.manyToOneAttributesHolder}">
                <i class="fa fa-fast-forward fa-color-black"></i>
            </c:if>
            <c:if test="${panel.systemAttributesHolder}">
                <i class="fa fa-lock fa-color-black"></i>
            </c:if>
            <s:message code="${panel.title}"/>
        </div>

        <div class="x-ip-panel-content x-layout-${fn:toLowerCase(panel.layout)} ">
            <c:forEach items="${panel.attributes}" var="attr" varStatus="itAttr">
                <p id="${attr.path}-holder" class="x-ip-attr" data-displayAs="${fn:toLowerCase(attr.displayAs)}">
                    <span class="x-ip-attr-label">${attr.title}</span>
                        <span class="x-ip-attr-value">
                            <c:if test="${attr.valueAttribute}">
                                <s:eval expression="data.getValueForPath(attr.path)"/>
                            </c:if>
                            <c:if test="${attr.tableAttribute}">
                                <s:eval expression="data.getValueForPath(attr.path)" var="builderContextLink" scope="page"/>
                                <script>
                                    $(function () {
                                        $('#' + '${attr.path}-holder').find('span.x-ip-attr-value').loadBuilderView({
                                            url : '${builderContextLink.link.href}',
                                            data: {
                                                contextKey  : '${builderContextLink.contextKey}',
                                                contextClass: '${builderContextLink.contextClassName}',
                                                builderId   : '${builderContextLink.builderId}'
                                            }
                                        });
                                    });
                                </script>
                            </c:if>
                            <c:if test="${attr.infoPageAttribute}">
                                <s:eval expression="data.getValueForPath(attr.path)" scope="page" var="_link"/>
                                <ip:renderInfoPageLink link="${_link}"/>
                            </c:if>
                        </span>
                </p>
            </c:forEach>
        </div>
    </div>
</c:forEach>
