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

<%@ attribute name="stateId" type="java.lang.String" rtexprvalue="true" description="ID of the state" %>
<%@ attribute name="var" type="java.lang.String" description="target variable in the pageContext" %>
<%@ tag import="org.agatom.springatom.webmvc.flows.wizard.support.WizardRequiresStepsHolder" %>
<%@ tag import="org.springframework.webflow.execution.RequestContextHolder" %>
<%
    final WizardRequiresStepsHolder requiredSteps = RequestContextHolder.getRequestContext()
                                                                        .getFlowScope()
                                                                        .get("requiredSteps", WizardRequiresStepsHolder.class);
    request.setAttribute(var, requiredSteps.has(stateId));
%>