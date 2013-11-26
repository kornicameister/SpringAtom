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

<%@ taglib prefix="swf" uri="http://www.example.org/sa/swf" %>
<%@ tag description="This tags simplifies reapplying state of the header. In other words it does recognize the current state and decide which status
is the most appropriate for the header" %>
<%@ attribute name="forState"
              required="true"
              rtexprvalue="true"
              type="org.springframework.webflow.definition.StateDefinition"
              description="Current state" %>
<script type="text/javascript">
    $(function () {
        var box = $('#error-box');
        if (box.has('span.error-entry').length > 0) {
            var stepHeader = $('#' + SA.wizard.genStepHeaderId('${forState.id}'));
            stepHeader.addClass('error');
            SA.core.showError(box.children());
        }
    })
</script>
