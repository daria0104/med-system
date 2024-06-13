<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 12.10.2023
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
  <style>
    h2, a{
        margin: 150px 0 20px 150px;
        width: 60%;
    }
  </style>
</head>
<body>
  <h2><c:out value="${errorMessage}"/></h2>
  <a href="patients_servlet">Return to the patient list</a>
</body>
</html>
