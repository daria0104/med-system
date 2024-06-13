<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 02.10.2023
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patients list</title>
    <style>
        .wrapper{
            margin: 20px 0 0 20px;
            width: 1030px;
        }
        body{

        }
        h2{
            margin: 10px 0 20px 0;
        }
        .search-bar {
            display: flex;
            align-items: end;
            flex-direction: column;

        }
        .block{
            margin: 30px 0px 0px 0;
            max-width: 1030px;
            display: flex;
            justify-content: space-between;

        }
        .block-item{
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        #patients-table{
            width: 1030px;
        }
        .search-bar input, .search-bar button{
            margin: 5px 2px;
            padding: 3px;
        }
        .search-bar button{
            width: 70px;
        }
        .search-bar form{
            display: inline-block;
        }
        .actions button{
            padding: 2px 5px;
            margin: 5px;
        }
        .sort-filter{
            width: 112px;
            padding: 2px 5px;
        }
        td {
            vertical-align: middle;
        }

        .actions-item form {
            margin: 0;
        }

        .actions-item input {
            width: 70px;
            margin: 2px;
            padding: 2px;
        }
    </style>
</head>
<body>

<c:if test="${dataSource}">
    <div role="alert">
        <h3 class="alert-heading">Data source: XML</h3>
        <hr>
        The database is down, you cannot edit the data
    </div>
</c:if>
<c:if test="${not dataSource}">
    <div role="alert">
        <h3>Data source: MySQL</h3>
        <hr>
        You can edit the data
    </div>
</c:if>

<div class="wrapper">
<div class="block">


    <div class="actions">
        <div>
            <label>Sort by birthday</label>
            <button onclick="sortByBirthday(true)" accesskey="q">&#8593</button>
            <button onclick="sortByBirthday(false)" accesskey="w">&#8595</button>
            <button class="sort-filter" onclick="sortBySurname()" accesskey="e">Sort by surname</button>
        </div>
        <div class="block-item">
            <label>Filter</label>
            <div>
                <button class="sort-filter" onclick="filterByAge(true)" accesskey="a">Adults</button>
                <button class="sort-filter" onclick="filterByAge(false)" accesskey="c">Children</button>
            </div>
        </div>
    </div>
    <div class="search-bar">
        <input type="text" placeholder="Find by surname..." id="search-text" onkeyup="tableSearch()">
        <div>
            <c:if test="${not dataSource}">
            <form action="edit-patients-list" method="get">
                <input type="hidden" name="patient_id" value="0"/>
                <button accesskey="a">Add</button>
            </form></c:if>
            <form action="patients_servlet" method="get">
                <button accesskey="r">Reset</button>
            </form></div>
    </div>
</div>

<h2>Patients</h2>
<table border="2" cellpadding="5" id="patients-table">
    <tr>
        <th>Surname</th>
        <th>Name</th>
        <th>Middle name</th>
        <th>Birthday</th>
        <th>Gender</th>
        <th align="left">State comments</th>
        <c:if test="${not dataSource}"><th colspan="2">Actions</th></c:if>
    </tr>
    <c:forEach var="p" items="${patients}">
        <tr>
            <td>${p.surname}</td>
            <td>${p.name}</td>
            <td>${p.midName}</td>
            <td id="birthday">
                <fmt:setLocale value="en_US"/> <%--"uk_UA"/>--%>
                <fmt:parseDate value="${p.birthday}" pattern="yyyy-MM-dd" var="birthday" type="date"/>
                <fmt:formatDate value="${birthday}"/>
            </td>
            <td>
                <c:out value="${p.gender ? 'male' : 'female'}"/>
            </td>
            <td width="400px">${p.stateComments}</td>
            <c:if test="${not  dataSource}">
            <td class="actions-item">
                <form action="edit-patients-list" method="get">
                    <input type="hidden" name="patient_id" value="${p.patient_id}">
                    <input type="submit" value="Edit">
                </form>
            </td>
            <td class="actions-item">
                <form action="delete-patient" method="get">
                    <input type="hidden" name="patient_id" value="${p.patient_id}">
                    <input type="submit" value="Delete" onclick="return confirmation()">
                </form>
            </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</div>

<script>
    function confirmation() {
        return confirm('Are you sure you want to delete?');
    }

    function tableSearch() {
        let phrase = document.getElementById('search-text');
        let table = document.getElementById('patients-table');
        let regPhrase = new RegExp(phrase.value, 'i');
        let flag = false;
        for (let i = 1; i < table.rows.length; i++) {
            flag = regPhrase.test(table.rows[i].cells[0].innerHTML);
            if (flag) {
                table.rows[i].style.display = "";
            } else {
                table.rows[i].style.display = "none";
            }
        }
    }

    function filterByAge(adults) {
        let table = document.getElementById('patients-table');
        let rows = table.rows;

        let child = "";
        let adult = "none";
        if (adults) {
            child = "none";
            adult = "";
        }

        for (let i = 1; i < rows.length; i++) {
            let birthday = new Date(rows[i].getElementsByTagName('td')[3].textContent);
            let diffInTime = new Date(Date.now() - birthday);
            console.log(diffInTime);
            let age = diffInTime.getFullYear() - 1970;

            if (age >= 18)
                table.rows[i].style.display = adult;
            else
                table.rows[i].style.display = child;
        }
    }

    function sortByBirthday(increased) {
        console.log("entered");
        let table = document.getElementById('patients-table');
        console.log(table);
        let swap = true;
        let condition = true;

        while (swap) {
            swap = false;
            let rows = table.rows;
            let i;
            for (i = 1; i < rows.length - 1; i++) {
                let item1 = new Date(rows[i].getElementsByTagName('td')[3].textContent);
                let item2 = new Date(rows[i + 1].getElementsByTagName('td')[3].textContent);

                condition = (item1 < item2);
                if (increased) {
                    condition = (item1 > item2);
                }
                if (condition) {
                    swap = true;
                    break;
                }
            }
            if (swap) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            }
        }
    }

    function sortBySurname() {
        console.log("entered");
        let table = document.getElementById('patients-table');
        console.log(table);
        let swap = true;

        while (swap) {
            swap = false;
            let rows = table.rows;
            let i;
            for (i = 1; i < rows.length - 1; i++) {
                let item1 = String(rows[i].getElementsByTagName('td')[0].textContent.toLowerCase());
                let item2 = String(rows[i + 1].getElementsByTagName('td')[0].textContent.toLowerCase());

                if (new Intl.Collator().compare(item1, item2) > 0) {
                    swap = true;
                    break;
                }
            }
            if (swap) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            }
        }
    }


</script>
</body>
</html>
