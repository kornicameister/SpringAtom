<%@page autoFlush="true" trimDirectiveWhitespaces="true" language="java" pageEncoding="UTF-8" %>

<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<jsp:useBean id="report" scope="request" type="org.agatom.springatom.server.model.beans.report.SReport"/>
<jsp:useBean id="links" scope="request" type="java.util.Map"/>
<jsp:useBean id="title" scope="request" type="java.lang.String"/>

<s:eval expression="T(org.agatom.springatom.web.rbuilder.ReportRepresentation$Representation).CSV"
        var="csv"
        scope="page"/>
<s:eval expression="T(org.agatom.springatom.web.rbuilder.ReportRepresentation$Representation).EXCEL"
        var="excel"
        scope="page"/>
<s:eval expression="T(org.agatom.springatom.web.rbuilder.ReportRepresentation$Representation).HTML"
        var="html"
        scope="page"/>
<s:eval expression="T(org.agatom.springatom.web.rbuilder.ReportRepresentation$Representation).PDF"
        var="pdf"
        scope="page"/>

<div id="download-dialog-${report.id}" style="text-align: center" class="x-form" title="${title}">
	<ul>
		<c:forEach items="${links}" var="linkEntry" varStatus="loop">
			<li id="${loop.index}">
				<a href="${linkEntry.value.href}" target="_blank" media="all">
					<i class="fa fa-color-black fa-download fa-lg fa-spin"></i>
					<c:if test="${linkEntry.key == csv.id}">
						[${fn:toUpperCase(csv.id)}]
					</c:if>
					<c:if test="${linkEntry.key == pdf.id}">
						[${fn:toUpperCase(pdf.id)}]
					</c:if>
					<c:if test="${linkEntry.key == html.id}">
						[${fn:toUpperCase(html.id)}]
					</c:if>
					<c:if test="${linkEntry.key == excel.id}">
						[${fn:toUpperCase(excel.id)}]
					</c:if>
				</a>
			</li>
		</c:forEach>
	</ul>
</div>
<script>
	$(function () {
		var parent = $('#' + 'download-dialog-${report.id}').parents('.dijitDialog');
		if (parent.length >= 1) {
			parent = parent.find('.dijitDialogTitle');
		}
		if (parent.length >= 1) {
			parent.html('${title}');
		}
	})
</script>
