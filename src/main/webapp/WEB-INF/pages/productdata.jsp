<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ taglib uri="/WEB-INF/helloTag.tld" prefix="tag"%>


<html>
<head>
    <title>ProductData</title>

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
<h1>Product Details</h1>

<p><h3><tag:hello /></h3></p>

<table class="tg">
    <tr>
        <th width="80">Product ID</th>
        <th width="120">Product Name</th>
        <th width="120">Product Price</th>
    </tr>
    <tr>
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.price}</td>
    </tr>
</table>

<a href="../../products">Back to Products list</a>
</body>
</html>
