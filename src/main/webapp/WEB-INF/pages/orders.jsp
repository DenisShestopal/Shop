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

<%! public int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public Long totalPrice = 0L;%>

<c:if test="${!empty userOrder}">
    <br>Order status
    <br>${userOrder.status}
    <a href="<c:url value='orders/confirm/${userOrder.id}'/>">Confirm</a>
    <a href="<c:url value='orders/pay/${userOrder.id}'/>">Pay</a>
    <a href="<c:url value='orders/remove/${userOrder.id}'/>">Delete</a>
    <table class="tg">
        <tr>
            <th width="120">Product</th>
            <th width="120">Price</th>
            <th width="60">Quantity</th>
            <th width="60">Delete</th>
        </tr>

        <c:forEach items="${userOrder.productList}" var="product">
            <% totalPrice += Long.valueOf("${product.price}"); %>
            <tr>
                <td>${product.name}</td>
                <td>${product.price/100}${product.price%100}</td>
                <td>${product.quantity}</td>
                <c:if test="${userOrder.status == 'UNORDERED'}">
                    <form:form action="${'/orders/changeQuantity'}" commandName="user">
                        <input name="productId" type="hidden" value="${product.id}">
                        <input name="quantity" type="number" required min="0"
                               max="1000"/> <%--onselect="&quantity${}"--%>
                        <input type="submit"
                               value="<spring:message text="accept"/>"/>
                    </form:form>
                </c:if>


                <td>
                    <a href="<c:url value='orders/removeProduct/${product.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td>Total price : <%=totalPrice%>
            </td>
            <td></td>
            <td></td>
            <td><a href="<c:url value='orders/confirm/${userOrder.id}'/>">Confirm</a></td>
        </tr>
    </table>
</c:if>


</body>
</html>
