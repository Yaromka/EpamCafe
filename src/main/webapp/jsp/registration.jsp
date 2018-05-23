<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="/css/jsp/registration.css" rel="stylesheet">

    <title>Registration</title>
</head>
    <body>
    <jsp:include page="/jsp/pagepart/header.jsp"/>
        <div class="container" role="main">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <br>
                    <c:if test="${not empty sessionScope.operationStatusNegative}">
                        <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                        <c:remove var="operationStatusNegative" scope="session" />
                    </c:if>
                    <br>
                    <p class="registrationMessage"><fmt:message key="label.registration.title" bundle="${rb}"/></p>
                    <form name="registerForm" method="POST" action="/controller">
                        <input type="hidden" name="command" value="sign_up"/>
                        <fieldset>

                            <div class="control-group">
                                <!-- E-mail -->
                                <label class="control-label" for="email"><fmt:message key="label.registration.email" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="email" id="email" name="email" required="" class="form-control" placeholder="email@email.com" value="${sessionScope.registrBin.email}" />
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Name -->
                                <label class="control-label" for="name"><fmt:message key="label.registration.name" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" id="name" name="name" required="" class="form-control" placeholder="Kate" value="${sessionScope.registrBin.name}" pattern="^[А-ЯA-Z][a-яa-z]{2,24}"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Surname -->
                                <label class="control-label" for="surname"><fmt:message key="label.registration.surname" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" id="surname" name="surname" required="" class="form-control" placeholder="Smith" value="${sessionScope.registrBin.surname}" pattern="^[А-ЯA-Z][a-яa-z]{2,24}"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Phone -->
                                <label class="control-label" for="phone"><fmt:message key="label.registration.phone" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" class="form-control bfh-phone" id="phone" name="phone"  required="" value="${sessionScope.registrBin.phone}" data-format="+375 (dd) ddd-dd-dd" placeholder="+375 (29) 229-29-29" pattern="(^[+]{1}[\d]{3}[\ ][(]{1}[\d]{2}[)]{1}[\ ][\d]{3}[-]{1}[\d]{2}[-]{1}[\d]{2}$)" />
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Passport -->
                                <label class="control-label" for="passport"><fmt:message key="label.registration.passport" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" id="passport" name="passport" required="" class="form-control" placeholder="MP1234567" value="${sessionScope.registrBin.passport}" pattern="[A-Z]{2}\d{7}"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Password -->
                                <label class="control-label" for="password"><fmt:message key="label.registration.password" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="password" id="password" name="password" required="" class="form-control"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[\W|_]).{8,20}"/>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Repeat password -->
                                <label class="control-label" for="repeatpassword"><fmt:message key="label.registration.repeatpassword" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="password" id="repeatpassword" name="repeatpassword" required="" class="form-control" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[\W|_]).{8,20}"/>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-success" ><fmt:message key="label.registration.submit" bundle="${rb}"/></button>
                        </fieldset>
                    </form>
                </div>
                <div class="col-md-4"></div>
            </div>
            <c:if test="${not empty sessionScope.registrBin}">
                <c:remove var="registrBin" scope="session" />
            </c:if>
    <jsp:include page="/jsp/pagepart/footer.jsp"/>
    </body>
</html>
