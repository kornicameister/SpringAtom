<%@ tag import="org.apache.log4j.Logger" %>
<%@ tag import="org.springframework.webflow.engine.Flow" %>
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

<%@ attribute name="flow" type="org.springframework.webflow.definition.FlowDefinition" %>
<%@ attribute name="index" type="java.lang.Short" description="Index of the state" %>
<%@ attribute name="var" type="java.lang.String" description="target variable in the pageContext" %>

<%
    final Flow flowDef = (Flow) flow;
    final String[] stateIds = flowDef.getStateIds();
    try {
        if (index >= stateIds.length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        final String stateId = stateIds[index];
        request.setAttribute(var, stateId);
    } catch (Exception e) {
        Logger.getLogger("org.agatom.springatom.tags.swf.stateIdAt").error(e);
    }
%>