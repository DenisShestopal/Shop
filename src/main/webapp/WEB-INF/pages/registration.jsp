<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <title>Registration page</title>

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


<h1>Registration</h1>

<h2>Please enter your login and password</h2>

<%! public boolean tf = false;%>

<form action="/users/signup" method="post">
    <table>
        <tr>
            <td>Login:<br></td>
            <td><input type="text" required="true" maxlength="16" name="login"></td>
        </tr>
        <tr>
            <td>Password:<br></td>
            <td><input type="password" required="true" maxlength="16" name="password"></td>
        </tr>
        <tr>
            <td>Password check:<br></td>
            <td><input type="password" required="true" maxlength="16" name="passwordCheck"></td>
        </tr>
        <tr>
            <td><br><input type="submit" value="Sign Up"></td>
        </tr>
        <c:if test="${!empty exception}">
            <p style="color:red;">${exception}</p>
        </c:if>
    </table>
</form>

<br><br><a href="../../">Back to Main</a>


</body>
</html>
