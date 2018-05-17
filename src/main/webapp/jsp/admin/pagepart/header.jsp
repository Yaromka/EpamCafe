<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Epam cafe. Food.">
    <meta name="author" content="Yaromka Dzmitry">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="../../../css/jsp/admin/pagepart/header.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../../img/icon.ico" />
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
                <a class="nav-link" href="/controller?command=get_all_users&current_page=1"><fmt:message key="label.navbar.admin.main"  bundle="${rb}"/></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/controller?command=get_orders_by_parameters&pay_status=EXPECTED&startDate=&endDate=&current_page=1"><fmt:message key="label.navbar.admin.orders"  bundle="${rb}"/></a>
            </li>

            <li class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.navbar.admin.add"  bundle="${rb}"/></a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="/controller?command=get_page&jsp_page=NEW_DISH_JSP"><fmt:message key="label.navbar.admin.newdish"  bundle="${rb}"/></a>
                    <a class="dropdown-item" href="/controller?command=get_page&jsp_page=NEW_CATEGORY_JSP"><fmt:message key="label.navbar.admin.newcategory"  bundle="${rb}"/></a>
                </div>
            </li>

            <li class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.navbar.admin.show"  bundle="${rb}"/></a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink1">
                    <a class="dropdown-item" href="/controller?command=get_dishes&category=x&enable_status=x&current_page=1"><fmt:message key="label.navbar.admin.dishes"  bundle="${rb}"/></a>
                    <a class="dropdown-item" href="/controller?command=get_categories"><fmt:message key="label.navbar.admin.categorys"  bundle="${rb}"/></a>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/controller?command=get_page&jsp_page=ADMIN_ACCOUNT_JSP"><fmt:message key="label.navbar.account"  bundle="${rb}"/></a>
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
            <li class="nav-item">
                <form method="get" action="/controller">
                    <input type="hidden" name="command" value="logout" />
                    <button class="btn btn-default navbar-btn" ><fmt:message key="label.main.logout_button"  bundle="${rb}"/></button>
                </form>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>