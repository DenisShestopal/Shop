<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <title>Orders Page</title>

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
<a href="../../products">Back to Products table</a>

<br/>
<br/>

<h1>Orders List</h1>

<c:if test="${!empty listOrders}">
    <table class="tg">
        <tr>
            <th width="80">Order ID</th>
            <th width="120">Product</th>
            <th width="120">Price</th>
            <%--<th width="60">Quantity</th>--%>
            <th width="120">Order status</th>
            <th width="60">Confirm</th>
            <th width="60">Pay</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listOrders}" var="order">
            <tr>
                <td>${order.id}</td>
                <%--<td><a href="/productdata/${product.id}" target="_blank">${product.name}</a></td>--%>
                <td>${order.product.name}</td>
                <td>${order.product.price/100}${product.price%100}</td>
                <%--<td>${product.quantity}</td>--%>
                <td>${order.status}</td>
                <td><a href="<c:url value='/confirm/${order.id}'/>">Confirm</a></td>
                <td><a href="<c:url value='/pay/${order.id}'/>">Pay</a></td>
                <td><a href="<c:url value='/remove/${order.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
