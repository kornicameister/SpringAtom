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
    <aside>
        <s:url value="/app/wizard/NewReportWizard" htmlEscape="true" var="newReportActionUrl"/>
        <security:authorize url="${newReportActionUrl}">
            <s:message code="reports.actions.NewReportWizard" var="newReportActionLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${newReportActionLabel}" var="newReportActionLabelTooltip"
                       htmlEscape="true"/>
            <a id="${newReportActionLabel}" href="${newReportActionUrl}" title="${newReportActionLabelTooltip}">
                <i class="fa fa-calendar fa-color"></i>${newReportActionLabel}
            </a>
        </security:authorize>
    </aside>
    <div>
        <header>
            <s:message code="reports.savedReports"/>
        </header>
        <div id="reportsBuilder-savedReport"></div>
        <s:eval expression="@defaultComponentBuilderRepository.getBuilderId(
                    T(org.agatom.springatom.server.model.beans.report.SReport),
                    T(org.agatom.springatom.web.component.core.builders.ComponentProduces).TABLE_COMPONENT
                )"
                var="builderId"
                scope="page"/>
        <script type="text/javascript" defer="defer" async="async">
            $('#reportsBuilder-savedReport').loadBuilderView({url: '/app/tableBuilder/${builderId}'});
        </script>
    </div>
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