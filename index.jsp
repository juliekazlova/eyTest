<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="${param.lang}">
<head>
    <title>Main Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>


<h2>Data:</h2>
<table class="table table-striped">
    <tr>
        <th>bank and class</th>
        <th>active opening balance</th>
        <th>passive opening balance</th>
        <th>debit</th>
        <th>credit</th>
        <th>active outgoing balance</th>
        <th>passive outgoing balance</th>
    </tr>
    <c:forEach items="${requestScope.data}" var="excelData">
        <tr>
            <td>${excelData.bankClassNumber}</td>
            <td>${excelData.opGBActive}</td>
            <td>${excelData.opGBPassive}</td>
            <td>${excelData.debit}</td>
            <td>${excelData.credit}</td>
            <td>${excelData.outBGActive}</td>
            <td>${excelData.outGBPassive}</td>

        </tr>
    </c:forEach>
</table>
</body>
</html>