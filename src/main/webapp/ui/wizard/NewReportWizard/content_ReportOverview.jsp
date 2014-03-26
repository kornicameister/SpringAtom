<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                 ~
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
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>

<%@ page import="org.agatom.springatom.server.model.beans.report.SReport" %>
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<div id="sa-wizard-step-body" class="x-wizard-content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="<%=SReport.ENTITY_NAME%>"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
        </fieldset>
        <swf:notificationsBox context="${flowRequestContext}"/>
    </form:form>
</div>
<swf:getDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:getActions forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>