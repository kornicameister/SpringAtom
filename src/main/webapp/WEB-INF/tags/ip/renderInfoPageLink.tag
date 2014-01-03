<%@ tag import="org.agatom.springatom.web.component.elements.value.DelegatedLink" %>
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

<%@ attribute name="link" rtexprvalue="true" required="true" type="java.lang.Object" %>

<%
    if (link instanceof DelegatedLink) {
        final DelegatedLink htmlLink = (DelegatedLink) link;
        final String href = htmlLink.getHref();
        final String rel = htmlLink.getRel();
        final String label = htmlLink.getLabel();
%>
<a id="<%=rel%>" href="<%=href%>" class="x-infopage-link" rel="<%=rel%>"><i
        class="fa fa-external-link fa-color-black fa-fw"></i><%=label%>
</a>
<%
    } else {
        out.write(link.toString());
    }
%>
