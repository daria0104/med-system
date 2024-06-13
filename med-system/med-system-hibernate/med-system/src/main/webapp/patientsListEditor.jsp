<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 11.10.2023
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit patients list</title>
    <style>
        form {
            display: table;
        }

        div.add-item {
            display: table-row;
        }
        label{
            vertical-align: middle;
        }
        div.add-item label, div.add-item input, div.add-item select, textarea{
            display: table-cell;
            margin: 4px 15px;
            padding: 2px;
        }
        div.add-item input, div.add-item select, textarea{
            width: 300px;
            max-width: 300px;
            max-height: 100px;
        }
        .add-item-gender {
            display: inline;
        }
        a{
            margin-top: 20px;
        }
        h2.error-message{
            margin-bottom: 35px;
            color: #a60000;
        }
    </style>
</head>
<body>
<h2 class="error-message"><c:out value="${errorMessage}"/></h2>

<form action="edit-patients-list" method="post">
    <input name="patient_id" value="${patient.patient_id}" required hidden="hidden"/>
    <div class="add-item"><label>Surname</label>
        <input required placeholder="Ivanov" type="text" name="surname" value="${patient.surname}"
               pattern="^[A-Z][a-z]+|[А-ЯЄІЇ][а-яієї]+$"/>
    </div>
    <div class="add-item">
        <label>Name</label>
        <input required placeholder="Ivan" type="text" name="name" value="${patient.name}"
               pattern="^[A-Z][a-z]+|[А-ЯЄІЇ][а-яієї]+$"/>
    </div>
    <div class="add-item"><label>Middle name</label>
        <input required placeholder="Ivanovich" type="text" name="midname" value="${patient.midName}"
               pattern="^[A-Z][a-z]+|[А-ЯЄІЇ][а-яієї]+$"/>
    </div>

    <div class="add-item"><label>Birthday</label>
        <input required type="date" name="birthday" value="${patient.birthday}"/>
    </div>

    <div class="add-item">
        <label for="gender">Gender</label>
        <div class="add-item-gender">
            <select name="gender" id="gender">
                <option value="male" ${patient.gender == true ? 'selected' : ''}>Male</option>
                <option value="female" ${patient.gender == false ? 'selected' : ''}>Female</option>
            </select>
        </div>
    </div>

    <div class="add-item"><label>State Comment</label>
        <textarea required type="text" name="state"><c:out value="${patient.stateComments}"/>
        </textarea>
    </div>
    <div>
        <input type="submit" value="Submit"/>
    </div>
</form>
<a href="patients_servlet">Return to the patient list</a>
</body>
</html>
