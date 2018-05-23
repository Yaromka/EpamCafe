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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="../css/jsp/login.css" rel="stylesheet">
    <title>Login Page</title>

</head>

<body>
    <jsp:include page="../jsp/pagepart/header.jsp"/>
    <div class="container-fluid bg" role="main" id="loginForm">
        <div class="row">
            <div class="col-md-4 col-ms-4 col-xs-12"></div>
            <div class="col-md-4 col-ms-4 col-xs-12">
                <!-- form start-->
                <form class="form-container" name="loginForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="login" />
                    <h2 class="form-signin-heading"><fmt:message key="label.login.header" bundle="${rb}"/></h2>
                    <div class="form-group">
                        <label for="inputEmail" class="sr-only"><fmt:message key="label.registration.email" bundle="${rb}"/></label>
                        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="sr-only"><fmt:message key="label.registration.password" bundle="${rb}"/></label>
                        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    </div>
                    <button type="submit" class="btn btn-success btn-block"><fmt:message key="label.login.login_button" bundle="${rb}" /></button>
                    <br>
                    <c:if test="${not empty sessionScope.operationStatusNegative}">
                        <p id="errorMessage"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}" /></p>
                        <c:remove var="operationStatusNegative" scope="session" />
                    </c:if>
                </form>
                <!-- form end-->

            </div>
            <div class="col-md-4 col-ms-4 col-xs-12"></div>
        </div>
    </div>
    <div class="col-md-4 col-ms-4 col-xs-12"></div>
    <jsp:include page="/jsp/pagepart/footer.jsp"/>
</body>
</html>

