<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <title>Users Page</title>

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
<a href="../../index.jsp">Back to main menu</a>

<br/>
<br/>

<h1>Users List</h1>

<c:if test="${!empty listUsers}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Login</th>
            <th width="120">Password</th>
            <th width="120">Admin</th>
            <th width="120">Blocked</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
            <th width="60">Add user to black list</th>
            <th width="60">Form order</th>

        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><a href="/userdata/${user.id}" target="_blank">${user.login}</a></td>
                <td>${user.password}</td>
                <td>${user.admin}</td>
                <td>${user.blocked}</td>
                <td><a href="<c:url value='users/edit/${user.id}'/>">Edit</a></td>
                <td><a href="<c:url value='users/remove/${user.id}'/>">Delete</a></td>
                <td><a href="<c:url value='users/addtoblacklist/${user.id}'/>">Add user to black list</a></td>
                <td><a href="<c:url value="/products"/>" target="_blank">Products list</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>


<h1>Add User</h1>

<c:url var="addAction" value="/users/add"/>

<form:form action="${addAction}" commandName="user">
    <table>
        <c:if test="${!empty user.login}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="User ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="login">
                    <spring:message text="Login"/>
                </form:label>
            </td>
            <td>
                <form:input path="login"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password"/>
                </form:label>
            </td>
            <td>
                <form:input path="password"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="admin">
                    <spring:message text="Admin"/>
                </form:label>
            </td>
            <td>
                <form:input path="admin"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="blocked">
                    <spring:message text="Blocked"/>
                </form:label>
            </td>
            <td>
                <form:input path="blocked"/>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <c:if test="${!empty user.login}">
                    <input type="submit"
                           value="<spring:message text="Edit User"/>"/>
                </c:if>
                <c:if test="${empty user.login}">
                    <input type="submit"
                           value="<spring:message text="Add User"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
