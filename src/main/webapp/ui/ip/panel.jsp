<%@ page import="org.agatom.springatom.ip.DomainInfoPage" %>
<%@ page import="org.agatom.springatom.ip.InfoPage" %>
<%@ page import="org.agatom.springatom.ip.InfoPageConstants" %>
<%@ page import="org.agatom.springatom.ip.resource.InfoPageResource" %>
<%@ page import="org.springframework.hateoas.Link" %>
<%@ page import="org.springframework.http.MediaType" %>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>
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
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%
    final InfoPageResource infoPageResource = (InfoPageResource) request.getAttribute(InfoPageConstants.INFOPAGE_RESOURCE_NAME);
    final InfoPage content = infoPageResource.getContent();

    if (infoPageResource.hasLink(InfoPageConstants.INFOPAGE_REST_CONTENT_LINK)) {
        final Link link = infoPageResource.getLink(InfoPageConstants.INFOPAGE_REST_CONTENT_LINK);
        final String href = link.getHref();
        pageContext.setAttribute(InfoPageConstants.INFOPAGE_REST_CONTENT_LINK, href, PageContext.PAGE_SCOPE);
    }

    if (content instanceof DomainInfoPage) {
        final DomainInfoPage domainInfoPage = (DomainInfoPage) content;
        pageContext.setAttribute("domainObjectClass", ClassUtils.getShortName(domainInfoPage.getDomainClass()), PageContext.PAGE_SCOPE);
    }
%>

<section id="domain-<%=pageContext.getAttribute("domainObjectClass")%>" class="x-info-page">
<div id="x-ip-basic-attr" class="x-attr-holder">
    </div>
    <div id="x-ip-system-attr" class="x-attr-holder">
    </div>
    <div id="x-ip-one-to-many-attr" class="x-attr-holder">
    </div>
    <div di="x-ip-many-to-one-attr" class="x-attr-holder">
    </div>
</section>
<script type="text/javascript" id="infopage-loader">
    $.ajax({
        url        : '<%=pageContext.getAttribute(InfoPageConstants.INFOPAGE_REST_CONTENT_LINK)%>',
        type       : '<%=RequestMethod.GET%>',
        contentType: '<%=MediaType.APPLICATION_JSON_VALUE%>',
        success    : function (data) {
            console.log('Retrieved content data from link => ', '<%=pageContext.getAttribute(InfoPageConstants.INFOPAGE_REST_CONTENT_LINK)%>');
        }
    })
</script>