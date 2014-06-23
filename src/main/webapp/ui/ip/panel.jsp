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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:eval expression="requestScope[T(org.agatom.springatom.web.component.infopages.InfoPageConstants).INFOPAGE_REQUEST]"
        var="ipRequest"/>
<s:eval expression="requestScope[T(org.agatom.springatom.web.component.infopages.InfoPageConstants).INFOPAGE_PAGE]"
        var="ipInfoPage"/>
<s:eval expression="requestScope[T(org.agatom.springatom.web.component.infopages.InfoPageConstants).INFOPAGE_DS]"
        var="ipDsLink"
        htmlEscape="false"
        javaScriptEscape="false"/>

<section id="infopage" class="x-info-page">
    <script type="text/javascript" id="ip-descriptor-script">
        Ext.onReady(function () {
            SA.infopage.loadInfoPage({
                el    : Ext.fly('infopage'),
                config: {
                    url    : <s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(ipDsLink.href)" htmlEscape="false" javaScriptEscape="false"/>,
                    request: <s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(ipRequest.asMap())" htmlEscape="false" javaScriptEscape="false"/>,
                    page   : <s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(ipInfoPage)" htmlEscape="false" javaScriptEscape="false"/>
                }
            })
        });
    </script>
</section>