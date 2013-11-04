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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:message code="form.new-event.title" var="formTitle"/>
<s:message code="button.next.short" var="nextButton"/>
<s:message code="button.previous.short" var="previousButton"/>
<s:message code="button.ok" var="finishButton"/>

<div id="<%=request.getAttribute(org.agatom.springatom.web.wizard.util.SVWizardModelVariables.WIZARD_ID)%>"
     class="swMain">
    <ul>
        <li>
            <a href="#step-1">
                <label class="stepNumber">1</label>
                        <span class="stepDesc">
                        Step 1<br/>
                        <small>Step 1 description</small>
                        </span>
            </a>
        </li>
        <li><a href="#step-2">
            <label class="stepNumber">2</label>
                    <span class="stepDesc">
                    Step 2<br/>
                    <small>Step 2 description</small>
                    </span>
        </a>
        </li>
    </ul>
    <div id="step-1">
        <h2 class="stepTitle">Step 1 Content</h2>

        <form action="#"
              title="${formTitle}"
              class="x-form">
            <fieldset>
                <legend>Time frame</legend>
                <label title="Begins">
                    Begins:
                    <input type="datetime-local" name="begin" autofocus required>
                </label>
                <label title="Ends">
                    Ends:
                    <input type="datetime-local" name="end" autofocus required>
                </label>
            </fieldset>
            <fieldset>
                <legend>Target</legend>
                <label title="Reporter">
                    Reporter:
                    <security:authorize access="isFullyAuthenticated()" var="isAuthenticated"/>
                    <security:authorize access="hasRole('ROLE_BOSS')" var="isBoss"/>
                    <c:if test="${isAuthenticated}">
                        <c:choose>
                            <c:when test="${isBoss}">
                                <input name="reporter" class="x-input-select" placeholder="Reported by..." required/>
                            </c:when>
                            <c:otherwise>
                                <security:authentication property="principal.person.information" var="ppi"/>
                                <s:bind path="${ppi}" htmlEscape="true">
                                    <input name="reporter"
                                           class="x-input-readonly"
                                           value="<s:transform value="${status.value}"/>" readonly>
                                </s:bind>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </label>
                <label title="Assignee">
                    Assignee:
                    <input name="assignee" class="x-input-select" placeholder="Assigned to..." required/>
                </label>
                <label title="Car">
                    Car
                    <input name="car" class="x-input-select" placeholder="Appointment for car..." required/>
                </label>
            </fieldset>
        </form>
    </div>
    <div id="step-2">
        <h2 class="stepTitle">Step 2 Content</h2>

        <form action="#"
              title="Tasks"
              class="x-form">
            <div class="x-multiple-input">
                <div class="x-inputs">
                    <ul>
                        <li id="1" data-role="task">
                            <input name="type"
                                   type="text"
                                   placeholder="Type"
                                   list="tasks"
                                   class="x-input x-input-select"
                                   required>
                            <input name="task"
                                   type="text"
                                   placeholder="Description"
                                   class="x-input x-input-text">
                            <a href="#"
                               data-role="mv-add"
                               class="x-button x-button-add">
                                <i class="icon-plus icon-color-black"></i>
                            </a>
                            <a href="#"
                               data-ajax="mv-remove"
                               class="x-button x-button-remove">
                                <i class="icon-minus icon-color-black"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <datalist id="tasks"></datalist>
        </form>
    </div>
</div>

<script id="wizard-loader">
    $(function () {
        $.ajax({
            url        : '/data/appointment/task/types',
            method     : 'get',
            contentType: "application/json",
            dataType   : 'json',
            success    : function (data, success) {
                var pattern = '<option id="{0}" value="{1}" title="{2}">';
                if (success !== 'success') {
                    SA.core.showError('Internal error. Failed to load task types');
                }
                $.each(data, function () {
                    var newEl = pattern
                            .replace('{0}', this['typeIdentifier'])
                            .replace('{1}', this['typeIdentifier'])
                            .replace('{2}', this['description']);
                    $('#tasks').append(newEl);
                });
            },
            failure    : function () {
                SA.core.showError('Server is not responding')
            }
        });
        var $task = $('li[data-role=task]');
        $task.find('a[data-role=mv-add]').click(function () {
            var tasksCount = $('li[data-role=task]').length;
            var id = tasksCount + 1;
            var liEl = $(this).parent('li').clone(true, true);

            liEl.find('input[name=type]').val('');
            liEl.find('input[name=description]').val('');

            liEl.attr('id', tasksCount + 1);
            $('.x-inputs').find('ul').append(liEl);
        });
        $("#<%=request.getAttribute(org.agatom.springatom.web.wizard.util.SVWizardModelVariables.WIZARD_ID)%>").smartWizard({
            enableAllSteps    : true,
            enableFinishButton: true,
            labelNext         : '${nextButton}',
            labelPrevious     : '${previousButton}',
            labelFinish       : '${finishButton}',
            onFinish          : function (steps) {
                var data = [];
                var stepPrefix = '<%=org.agatom.springatom.web.wizard.util.SVWizardModelVariables.WIZARD_STEP_PREFIX%>';

                function createInputDate(value) {
                    console.log(value);
                    return value;
                }

                $.each(steps, function (index, step) {
                    var array = $('div' + $(step).attr('href')).find('form').serializeArray();
                    var stepData = [];

                    $.each(array, function (index2, val) {
                        stepData.push(createInputDate(val));
                    });

                    data.push({
                        name: stepPrefix + index,
                        data: stepData
                    });
                });
                $.ajax({
                    url        : '<%=request.getAttribute(org.agatom.springatom.web.wizard.util.SVWizardModelVariables.WIZARD_SUBMIT_URL)%>',
                    type       : 'POST',
                    contentType: "application/json",
                    data       : JSON.stringify({
                        data: data
                    }),
                    dataType   : 'json'
                })
            }
        });
    });
</script>