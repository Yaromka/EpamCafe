<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

    <!-- Custom styles for this template -->
    <link href="../../css/jsp/pagepart/header.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark">
    <nav class="navbar navbar-light bg-faded">
        <h1 class="navbar-brand mb-0">EPAM CAFE</h1>
    </nav>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse" >
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/controller?command=get_page&jsp_page=LOGIN_JSP"><fmt:message key="label.login.login_button" bundle="${rb}" /></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"  href="/controller?command=get_page&jsp_page=REGISTRATION_JSP"><fmt:message key="label.login.registration" bundle="${rb}"/></a>
            </li>
        </ul>
        <ul class="navbar-nav right">
            <li class="nav-item">
                <form action="/controller">
                    <input type="hidden" name="command" value="locale">
                    <button type="submit" class="btn btn-default navbar-btn" name="locale" value="RU" ><fmt:message key="label.language.ru"  bundle="${rb}"/></button>
                </form>
            </li>
            <li class="nav-item">
                <form action="/controller">
                    <input type="hidden" name="command" value="locale">
                    <button type="submit" class="btn btn-default navbar-btn" name="locale" value="EN" ><fmt:message key="label.language.en"  bundle="${rb}"/></button>
                </form>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
