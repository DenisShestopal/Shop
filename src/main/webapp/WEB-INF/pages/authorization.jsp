<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <title>Authorization page</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
<%--<a href="../../products">Back to Products table</a>--%>

<br/>
<br/>

<h1>Authorization</h1>

<br>Please enter your login and password
<table class="tg">
        <%! public boolean tf = false;%>
    <tr>
        <td><a href="<c:url value='/users/signin/${product.id}'/>">Sign In</a></td>
        <td><a href="<c:url value='/users/singup/${product.id}'/>" onclick="<%tf=true;%>">I have no account yet</a></td>
    </tr>


    <c:url var="signupAction" value="/users/signup"/>
    <c:url var="signinAction" value="/users/singin"/>

    <c:set var="actionType" value="${signinAction}"/>

    <c:if test="${!empty product.name}">
        <c:set var="actionType" value="${signupAction}"/>
    </c:if>

    <form:form action="${actionType}" modelAttribute="user">
    <table>
        <h2>Please enter your login and password</h2>
        <tr>
            <td>
                <form:label path="login">
                    <spring:message text="Enter your login"/>
                </form:label>
            </td>
            <td>
                <form:input path="login" required = "true"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Enter your password"/>
                </form:label>
            </td>
            <td>
                <form:input path="password"/>
            </td>
        </tr>
        <c:if test="<%=tf%>">
            <h2>Please confirm your password</h2>
            <tr>
                <td>
                    <form:label path="passwordCheck">
                        <spring:message text="Reenter your password"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="passwordCheck"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <c:if test="${empty user.passwordCheck}">
                    <input type="submit"
                           value="<spring:message text="Sign In"/>"/>
                </c:if>

                <c:if test="${!empty user.passwordCheck}">
                    <input type="submit"
                           value="<spring:message text="Sign Up"/>"/>
                </c:if>

            </td>
        </tr>
    </table>
    </form:form>


</body>
</html>
