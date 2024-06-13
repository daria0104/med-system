<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 02.10.2023
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<fmt:setLocale value="en_US"/>  <%-- "uk_UA"/> --%>

<h2>Visits</h2>
<table border="2" cellpadding="7">
    <tr>
        <th>Patient</th>
        <th>Doctor code</th>
        <th>Service</th>
        <th>Visit time</th>
        <th>Price</th>
        <th align="left">Paid</th>
    </tr>
    <c:forEach var="v" items="${visits}">
        <tr>
            <td>
                <input type="hidden" name="patient_id" value="${v.patient.patient_id}"/>
                    ${v.patient.surname} ${v.patient.name}
            </td>
            <td>${v.doctorCode}</td>
            <td>${v.service}</td>
            <td>
                <fmt:parseDate value="${v.timeVisit}" pattern="yyyy-MM-dd'T'HH:mm" var="timeVisit" type="date"/>
                <fmt:formatDate value="${timeVisit}" pattern="MMM dd, yyyy HH:mm"/>
            </td>
            <td>
                <fmt:formatNumber value="${v.cost}" type="currency"/>
            </td>
            <td>
                <fmt:formatNumber value="${v.payment}" type="currency"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
