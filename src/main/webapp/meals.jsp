<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.10.2019
  Time: 7:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
        </tr>

        <c:forEach items="${meals}" var="meal">

         <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ?'excess':'normal'}">
            <td><c:out value="${meal.description}"/></td>

                <td><%=TimeUtil.toString(meal.getDateTime())%></td>
            </td>
            <td><c:out value="${meal.calories}"/></td>
            </tr>


            <br />
    </c:forEach>

    </table>


</body>
</html>
