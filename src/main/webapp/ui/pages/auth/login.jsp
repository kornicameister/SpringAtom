<%@page session="true" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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

<tiles:importAttribute name="mode" scope="page" toName="formEmbedded"/>
<c:choose>
    <c:when test="formEmbedded == ''">
        <c:set var="formCssClass" value="login-form"/>
    </c:when>
    <c:otherwise>
        <c:set var="formCssClass" value="login-embedded-form"/>
    </c:otherwise>
</c:choose>

<section id="loginForm" class="auth login ${formCssClass}">
    <s:url value="/app/auth/login.do" var="securityCheckLogin"/>
    <form:form name="auth"
               action="${securityCheckLogin}"
               htmlEscape="true"
               autocomplete="true"
               method="post"
               acceptCharset="UTF-8"
               cssClass="login">
        <p>
            <label for="username"><s:message code="label.auth.login"/></label>
            <input type="text"
                   name="ares"
                   id="username"
                   placeholder="<s:message code="sa.msg.login.prompt"/>"
                   autofocus
                   required>
        </p>

        <p class="login-submit">
            <button type="submit" class="login-button" title="<s:message code="button.login"/>">Login</button>
        </p>
        <p>
            <label for="password"><s:message code="label.auth.password"/></label>
            <input type="password"
                   name="athena"
                   id="password"
                   placeholder="<s:message code="sa.msg.password.prompt"/>"
                   required>
        </p>

        <p class="forgot-password">
            <a href="<s:url value="/app/auth/forgotten/password"/>"><s:message code="sa.msg.forgotPassword"/></a>
        </p>

        <p class="register">
            <a href="<s:url value="/app/auth/register"/>"><s:message code="sa.msg.register"/></a>
        </p>
    </form:form>
    <%-- SpringAtom-#38 Ajax backend authorization service --%>
    <%--
    <script type="text/javascript">
        $('.login-embedded-form form').submit(function () {
            $.ajax({
                'url'        : '<s:url value="/app/auth/login.ajax.do"/>',
                'type'       : '<%out.print(RequestMethod.POST.toString());%>',
                'data'       : JSON.stringify($(this).serializeObject()),
                'dataType'   : 'json',
                'contentType': 'application/json',
                'resetForm'  : true,
                'success'    : function (responseText, responseStatus) {
                    $(this).clearForm();
                    if (responseStatus !== undefined && responseStatus === 'success') {
                        if (responseText['success']) {
                            noty({
                                text    : responseText['message'],
                                type    : 'info',
                                layout  : 'center',
                                callback: {
                                    afterClose: function () {
                                        location.reload(true);
                                    }
                                }
                            });
                        } else {
                            noty({
                                text     : responseText['message'],
                                type     : 'error',
                                closeWith: ['hover']
                            });
                        }
                    }
                },
                'failure'    : function () {
                    noty({
                        text: 'Error in server communication',
                        type: 'error'
                    });
                }
            });
            return false;
        });
    </script>
    --%>
</section>