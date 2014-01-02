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

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<section id="reportBuilder">
    <div>
        <header>
            <s:message code="reports.savedReports"/>
        </header>
    </div>
    <div>
        <header>
            <s:message code="reports.selectedReport.summary"/>
        </header>
    </div>
    <aside>
        <header>
            <s:message code="reports.actions"/>
        </header>
        <ul>
            <s:url value="/app/wizard/NewReportWizard" htmlEscape="true" var="newReportActionUrl"/>
            <security:authorize url="${newReportActionUrl}">
                <s:message code="reports.actions.NewReportWizard" var="newReportActionLabel" htmlEscape="true"/>
                <s:message code="tooltip.nav" arguments="${newReportActionLabel}" var="newReportActionLabelTooltip"
                           htmlEscape="true"/>
                <li>
                    <a id="${newReportActionLabel}" href="${newReportActionUrl}" title="${newReportActionLabelTooltip}">
                        <i class="fa fa-calendar fa-color"></i>${newReportActionLabel}
                    </a>
                </li>
            </security:authorize>
            <li>
                <s:message code="reports.actions.delete"/>
            </li>
            <li>
                <s:message code="reports.actions.generate"/>
            </li>
        </ul>
    </aside>
</section>
<script type="text/javascript">
    $(function () {
        Spring.addDecoration(new Spring.AjaxEventDecoration({
            elementId: '${newReportActionLabel}',
            event    : 'onclick',
            popup    : true,
            params   : {
                mode : "embedded",
                title: '${newReportActionLabel}'
            }
        }));
    });
</script>