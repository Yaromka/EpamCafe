<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link href="../../css/jsp/client/myaccount.css" rel="stylesheet">

    <title>Account</title>

</head>
<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4">

        </div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.operationStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="operationStatusNegative" scope="session" />
            </c:if>
            <br>
            <div>
                <h3><fmt:message key="label.account.edituser" bundle="${rb}"/></h3>
            </div>

            <form class="form-horizontal" action='/controller' method="POST">
                <input type="hidden" name="command" value="edit_user" />
                <fieldset>

                    <div class="control-group">
                        <!-- E-mail -->
                        <label class="control-label" for="email"><fmt:message key="label.registration.email" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="email" id="email" name="email" required="" class="form-control" placeholder="email@email.com" value="${user.mail}" />
                            <p class="help-block"><fmt:message key="label.registration.email.help" bundle="${rb}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Name -->
                        <label class="control-label" for="name"><fmt:message key="label.registration.name" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="text" id="name" name="name" required="" class="form-control" placeholder="Игорь" value="${user.name}" pattern="^[А-ЯA-Z][a-яa-z]{2,24}"/>
                            <p class="help-block"><fmt:message key="label.registration.name.help" bundle="${rb}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Surname -->
                        <label class="control-label" for="surname"><fmt:message key="label.registration.surname" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="text" id="surname" name="surname" required="" class="form-control" placeholder="Блинов" value="${user.surname}" pattern="^[А-ЯA-Z][a-яa-z]{2,24}"/>
                            <p class="help-block"><fmt:message key="label.registration.surname.help" bundle="${rb}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Phone -->
                        <label class="control-label" for="phone"><fmt:message key="label.registration.phone" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="text" class="form-control bfh-phone" id="phone" name="phone"  required="" value="${user.phone}" data-format="+375 (dd) ddd-dd-dd" placeholder="+375 (29) 229-29-29" pattern="(^[+]{1}[\d]{3}[\ ][(]{1}[\d]{2}[)]{1}[\ ][\d]{3}[-]{1}[\d]{2}[-]{1}[\d]{2}$)" />
                            <p class="help-block"><fmt:message key="label.registration.phone.help" bundle="${rb}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Passport -->
                        <label class="control-label" for="passport"><fmt:message key="label.registration.passport" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="text" id="passport" name="passport" required=""  value="${user.passport}" class="form-control"  data-format="[A-Z]{2}ddddddd" placeholder="MP2222222" value="${registrBin.passport}" pattern="[A-Z]{2}\d{7}"/>
                            <p class="help-block"><fmt:message key="label.registration.passport.help" bundle="${rb}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Password -->
                        <label class="control-label" for="password"><fmt:message key="label.registration.password" bundle="${rb}"/><span class="required">*</span></label>
                        <div class="controls">
                            <input type="password" id="password" name="password" required="" class="form-control"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[\W|_]).{8,20}"/>
                        </div>
                    </div>
                    <br>
                    <div class="control-group">
                        <!-- Button -->
                        <div class="controls ">
                            <button class="btn btn-success btn-space" type="submit"><fmt:message key="label.account.changepass.changebutton" bundle="${rb}"/></button>
                            <a class="btn btn-success" href="/controller?command=get_page&jsp_page=ADMIN_CHANGE_PASS_JSP"><fmt:message key="label.account.changepass.changepass" bundle="${rb}"/></a>
                        </div>
                    </div>
                    <br>
                </fieldset>
            </form>
        </div>
        <div class="col-md-4">

        </div>
    </div>
</div>


<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>