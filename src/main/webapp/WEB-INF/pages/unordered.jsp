<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <title>Unordered orders Page</title>

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

<h1>Unordered order</h1>

<p align="center">
<h3>Welcome, ${loggedUser}!</h3></p>

<%! public int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public Double price = 0d;
    public Double totalPrice = 0d;
    public Integer totalQuantity = 0;
%>

<c:if test="${!empty exception}">
    <p style="color:red;">${exception}</p>
</c:if>

<c:if test="${empty userOrder.status}">
    <h3><br>
        <p style="color:dodgerblue;">Your basket is empty, please add products in.</p></h3>
</c:if>

<c:if test="${!empty ordersList}">
    <c:forEach items="${ordersList}" var="order">

        <c:if test="${!empty order.status}">
            <h3>Order status:
                <br>
                <th>${userOrder.status}</th>
            </h3>

            <br><a href="<c:url value='unordered/confirm/${order.id}'/>">Confirm</a>
            <a href="<c:url value='unordered/remove/${order.id}'/>">Delete all products</a><br><br>
            <table class="tg">
                <tr>
                    <th width="120">Product</th>
                    <th width="120">Price</th>
                    <th width="60">Quantity</th>
                    <th width="60">Total price</th>
                    <th width="60">Action</th>
                </tr>

                <c:set var="totalPrice" value="0"/>

                <c:forEach items="${order.productList}" var="entry">
                    <% totalPrice = 0d; %>
                    <% totalQuantity = 0; %>
                    <c:set var="price" value="${entry.key.price}"/>
                    <c:set var="quantity" value="${entry.value}"/>

                    <% price = Double.valueOf(pageContext.getAttribute("price").toString())
                            * Integer.valueOf(pageContext.getAttribute("quantity").toString()); %>

                    <c:set var="price" value="<%=price%>"/>
                    <c:set var="totalPrice" value="${totalPrice+price}"/>

                    <tr>
                        <td>${entry.key.name}</td>
                        <td>${entry.key.price}</td>
                        <td>
                            <c:if test="${order.status == 'UNORDERED'}">
                                <form:form action="${'/unordered/changeQuantity'}">
                                    <input name="productId" type="hidden" value="${entry.key.id}">
                                    <input name="quantity" type="number" value="${entry.value}" required min="0"
                                           max="1000"/>
                                    <input type="submit"
                                           value="<spring:message text="accept"/>"/>
                                </form:form>
                            </c:if>
                        </td>
                        <td><%=price%>
                        </td>
                        <td>
                            <a href="<c:url value='/unordered/removeProduct?orderId=${order.id}&productId=${entry.key.id}'/>">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><p><b>TOTAL:</b></p></td>
                    <td></td>
                    <td></td>
                    <td><p><b>${totalPrice}</b>
                        <p/></td>
                    <td></td>
                </tr>
            </table>
        </c:if>
    </c:forEach>
</c:if>

<c:set var="orderStatus" value="unordered"/>
<br><br><a href="<c:url value="/ordered"/>">Forward to confirmed orders</a>
<br><br><a href="<c:url value="/paid"/>">My paid orders</a>
<br><br><a href="<c:url value="/products"/>">Back to products list</a>
<br><br><a href="<c:url value="/users/authorization"/>">Authorization page</a><br><br>

<form action="/users/logout" method="get">
    <input type="submit" value="Logout"></td>
</form>
</body>
</html>
