<%@ page import="org.agatom.springatom.web.component.infopages.InfoPageConstants" %>
<%@ page import="java.util.Map" %>
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


<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"
         isThreadSafe="false" %>
<%@ taglib prefix="ip" tagdir="/WEB-INF/tags/ip" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="ipTableBuilder" scope="request"
             type="org.agatom.springatom.web.component.table.TableComponentBuilder"/>

<s:eval expression="ipTableBuilder.definition" var="def" scope="page"/>
<jsp:useBean id="def" class="org.agatom.springatom.web.component.table.elements.dandelion.DandelionTableComponent"
             scope="page"/>

<c:set var="extraParamsMethodName" value="_js_${fn:toLowerCase(def.tableId)}" scope="page"/>

<script type="text/javascript">
    function ${extraParamsMethodName}(aoData) {
        <%
            final Object attribute = request.getAttribute(InfoPageConstants.INFOPAGE_PARAMS);
            if(attribute != null && attribute instanceof Map){
                final Map<?,?> map = (Map<?, ?>) attribute;
                for(final Object key : map.keySet()){
        %>
        aoData.push({
            name : '<%=key.toString()%>',
            value: '<%=map.get(key)%>'
        });
        <%
                }
            }
        %>
    }
    function onTableInitAction() {
        $('#' + '${def.tableId}').componentActions();
    }
</script>

<s:escapeBody javaScriptEscape="false" htmlEscape="false">
    <dt:table id="${def.tableId}"
              url="${def.url.href}"
              serverSide="true"
              export="pdf"
              pipelining="true"
              pipeSize="10"
              sort="${def.sortable}"
              stateSave="${def.stateful}"
              deferRender="${def.deferRender}"
              processing="${def.processingIndicator}"
              serverParams="${extraParamsMethodName}"
              info="${def.infoVisible}">

        <c:forEach items="${def.content}" var="col" varStatus="it">
            <c:set var="cssClass" value=""/>
            <c:if test="${fn:contains(col.property, 'id')}">
                <c:set var="cssClass" value="x-table-idColumn"/>
            </c:if>
            <c:if test="${fn:contains(col.property, 'infopage')}">
                <c:set var="cssClass" value="x-table-infoPageColumn"/>
            </c:if>
            <c:if test="${fn:contains(col.property, 'action')}">
                <c:set var="cssClass" value="x-table-actionColumn"/>
            </c:if>
            <dt:column title="${col.title}"
                       property="${col.property}"
                       visible="${col.visible}"
                       renderFunction="${col.renderFunctionName}"
                       sortable="${col.sortable}"
                       filterable="${col.filterable}"
                       sortDirection="${fn:toLowerCase(col.sortDirection)}"
                       cssClass="${cssClass}"/>
        </c:forEach>

        <dt:callback type="init" function="onTableInitAction"/>

    </dt:table>
</s:escapeBody>