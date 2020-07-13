<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.10.2019
  Time: 7:21
приприпри  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color:green;}
        .excess {color:red;}
    </style>
</head>
<body>

    <table border="1" cellpadding="2" cellspacing="0">

        <tr>
            <th>Description</th>
            <th>Date&Time</th>
            <th>Calories</th>
            <th colspan=2>Action</th>
        </tr>

        <c:forEach items="${meals}" var="meal">

         <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ?'excess':'normal'}">
            <td><c:out value="${meal.description}"/></td>

                <td><c:out value="${TimeUtil.toString(meal.getDateTime())}"/></td>
            </td>
            <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=update&mealId=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
            </tr>
            <br />
    </c:forEach>

    </table>
<br />

  <%--  <p><a href="meals?action=create">Add Meal</a></p>  --%>

    <form method="POST" action='meals'>


        Meal ID : <input type="hidden" readonly="readonly" name="id" value="${meal.id}" /> <br />
        Date Time : <input type="datetime-local" name="dateTime" value="${meal.dateTime}" /> > <br />
        Description : <input
            type="text" name="description"
            value="${meal.description}"  /> <br />

        Calories : <input type="text" name="calories"
                       value="${meal.calories}"  /> <br />
        <input type="submit" value="Save" />

    </form>
</body>
</html>
