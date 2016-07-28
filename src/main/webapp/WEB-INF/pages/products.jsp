<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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
<body>
<a href="../../users.jsp">Back to Users table</a>

<br/>
<br/>

<h1>Products List</h1>

<c:if test="${!empty listProducts}">
  <table class="tg">
    <tr>
      <th width="80">ID</th>
      <th width="120">Name</th>
      <th width="120">Price</th>
      <th width="60">Edit</th>
      <th width="60">Delete</th>
    </tr>
    <c:forEach items="${listProducts}" var="product">
      <tr>
        <td>${product.id}</td>
        <td><a href="/productdata/${product.id}" target="_blank">${product.productName}</a></td>
        <td>${product.productPrice/100}${product.productPrice%100}</td>
        <td><a href="<c:url value='/edit/${product.id}'/>">Edit</a></td>
        <td><a href="<c:url value='/remove/${product.id}'/>">Delete</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>


<h1>Add a Product</h1>

<c:url var="addAction" value="/products/add"/>

<form:form action="${addAction}" commandName="product">
  <table>
    <c:if test="${!empty product.productName}">
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
    <tr>
      <td>
        <form:label path="productName">
          <spring:message text="Name"/>
        </form:label>
      </td>
      <td>
        <form:input path="productName"/>
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="productPrice">
          <spring:message text="Price"/>
        </form:label>
      </td>
      <td>
        <form:input path="productPrice"/>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <c:if test="${!empty product.productName}">
          <input type="submit"
                 value="<spring:message text="Edit Product"/>"/>
        </c:if>
        <c:if test="${empty product.productName}">
          <input type="submit"
                 value="<spring:message text="Add Product"/>"/>
        </c:if>
      </td>
    </tr>
  </table>
</form:form>
</body>
</html>
