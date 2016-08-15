<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/helloTag.tld" prefix="tag"%>

<head>
    <title>Products Page</title>

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
<p>

    <br/>
    <br/>

<h1>Products List</h1>

<%--<p align="center">--%>
<%--<h3>Welcome, ${loggedUser}!</h3></p>--%>

<p><h3><tag:hello /></h3></p>

<c:if test="${!loggedUser == 'Unsigned User'}">
<c:if test="${!empty exception}">
    <p style="color:red;">${exception}</p>
</c:if>
</c:if>


<c:if test="${!empty listProducts}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Name</th>
            <th width="60">Code</th>
            <th width="120">Price</th>
            <th width="120">Currency</th>
            <c:if test="${user.admin==true}">
                <th width="60">Edit</th>
                <th width="60">Delete</th>
            </c:if>
            <c:if test="${!empty user.login}">
                <th width="120">Order product</th>
                <c:if test="${!empty result}">
                    <th width="60">Product status</th>
                </c:if>
            </c:if>
        </tr>
        <c:forEach items="${listProducts}" var="product">
            <tr>
                <td>${product.id}</td>
                <td><a href="products/${product.id}">${product.name}</a></td>
                <td>${product.code}</td>
                <td>${product.price/100}</td>
                <td>${product.currency}</td>
                <c:if test="${user.admin==true}">
                    <td>
                        <a href="<c:url value='/products/edit/${product.id}'/>">Edit</a></td>
                    <td>
                        <a href="<c:url value='/products/remove/${product.id}'/>">Delete</a></td>
                </c:if>
                <c:if test="${!empty user.login}">
                    <td><a href="<c:url value='/products/addtoorder/${product.id}'/>" onclick="">Add to Basket</a></td>
                    <c:if test="${!empty result}">
                        <td>${result}</td>
                    </c:if>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>


<c:url var="addAction" value="/products/add"/>
<c:url var="editAction" value="/products/edit"/>

<c:set var="actionType" value="${addAction}"/>

<c:if test="${!empty product.name}">
    <c:set var="actionType" value="${editAction}"/>
</c:if>

<c:if test="${user.admin==true}">
    <form:form action="${actionType}" commandName="product">
        <table>
            <c:if test="${!empty product.name}">
                <tr>
                    <td>
                        <form:label path="id">
                            <spring:message text="Product ID"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="id" readonly="true" size="8" disabled="true"/>
                        <form:hidden path="id"/>
                    </td>
                </tr>
            </c:if>
            <h2>Product data</h2>
            <tr>
                <td>
                    <form:label path="name">
                        <spring:message text="Name"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="name" maxlength="16" required="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="price">
                        <spring:message text="Price"/>
                    </form:label>
                </td>
                <td>
                    <form:input type="number" min="0" max="10000" step="1" path="price" required="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${!empty product.name}">
                        <input type="submit"
                               value="<spring:message text="Edit Product"/>"/>
                    </c:if>
                    <c:if test="${empty product.name}">
                        <input type="submit"
                               value="<spring:message text="Add Product"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</c:if>

<br><br><a href="../../unordered">My orders</a>
<c:if test="${user.admin==true}">
    <br><br><a href="../../users">Manage users</a>
</c:if>
<br><br><a href="../../users/authorization">Authorization page</a>
<%--<form action="/orders" method="get">--%>
<%--<input type="submit" value="My orders">--%>
<%--</form>--%>
</body>
</html>
