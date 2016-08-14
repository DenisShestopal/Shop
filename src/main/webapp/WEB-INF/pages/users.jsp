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

<h1>Users List</h1>

<p align = "center"><h3>Welcome, ${loggedUser}!</h3></p>

<c:if test="${!empty exception}">
    <p style="color:red;">${exception}</p>
</c:if>

<c:if test="${!empty listUsers}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Login</th>
            <th width="120">Password</th>
            <th width="60">Admin</th>
            <th width="60">Blocked</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
            <th width="180">Add user to black list</th>
            <%--<th width="60">Form order</th>--%>

        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><a href="users/${user.id}">${user.login}</a></td>
                <td>${user.password}</td>
                <td>${user.admin}</td>
                <td>${user.blocked}</td>
                <td><a href="<c:url value='/users/edit/${user.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/users/remove/${user.id}'/>">Delete</a>

                <td><a href="<c:if test="${!user.blocked}">
                <c:url value='/users/addtoblacklist/${user.id}'/>">Add</c:if>
                    <c:if test="${user.blocked}">
                        <c:url value='/users/addtoblacklist/${user.id}'/>">Remove</c:if></a></td>


                <%--<td><a href="<c:url value="/products"/>" target="_blank">Products list</a></td>--%>
            </tr>
        </c:forEach>
    </table>
</c:if>



<c:url var="addAction" value="/users/add"/>
<c:url var="editAction" value="/users/edit"/>

<%! public boolean[] tf = {false, true};%>

<c:set var="actionType" value="${addAction}"/>

<c:if test="${!empty user.login}">
    <c:set var="actionType" value="${editAction}"/>
</c:if>

<form:form action="${actionType}" commandName="user">

    <table>
        <c:if test="${!empty user.login}">
            <h1>Edit User</h1>
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
        <h2>User data</h2>
        <tr>
            <td>
                <form:label path="login">
                    <spring:message text="Login"/>
                </form:label>
            </td>
            <td>
                <form:input path="login" required = "true" maxlength="16"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password"/>
                </form:label>
            </td>
            <td>
                <form:input path="password" type="password" required = "true" maxlength="16"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="admin">
                    <spring:message text="Admin"/>
                </form:label>
            </td>
            <td>
                <form:select path="admin" items = "<%=tf%>"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="blocked">
                    <spring:message text="Blocked"/>
                </form:label>
            </td>
            <td>
                <form:select path="blocked" items = "<%=tf%>"/>
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

<br><a href="<c:url value="/products"/>" target="_blank">Products list</a>
<br><br><a href="../../users/authorization">Authorization page</a>
<br><br><a href="../../index.jsp">Back to main menu</a>
<br><br><a href="../../products.jsp">Products list</a>

</body>
</html>
