<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 19.08.2017
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../fonts/favicon.ico">

    <title>Password Recovery</title>
</head>
    <body>
        <jsp:include page="/jsp/pagepart/header.jsp"/>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <c:if test="${not empty sessionScope.statusInfo}">
                    <div class="alert alert-warning" role="alert">${sessionScope.statusInfo}</div>
                    <c:remove var="statusInfo" scope="session" />
                </c:if>
                <form class="form-horizontal" action="/controller" method="POST">
                    <input type="hidden" name="command" value="sendpass" />
                    <fieldset>
                        <p><span class="required">*</span> - Required</p>
                        <div class="control-group">
                            <!-- E-mail -->
                            <label class="control-label" for="email">Email*</label>
                            <div class="controls">
                                <input type="email" id="email" name="email" required="" class="form-control" value="${user.mail}" />
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Phone -->
                            <label class="control-label"  for="phone">Phone<span class="required">*</span></label>
                            <div class="controls">
                                <input type="text" class="form-control bfh-phone" id="phone" name="phone"  required="" value="${user.phone}" data-format="+375 (dd) ddd-dd-dd" pattern="(^[+]{1}[\d]{3}[\ ][(]{1}[\d]{2}[)]{1}[\ ][\d]{3}[-]{1}[\d]{2}[-]{1}[\d]{2}$)" />
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Button -->
                            <div class="controls">
                                <button class="btn btn-success" type="submit">Submit</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="col-md-4"></div>
        </div>
        <jsp:include page="/jsp/pagepart/footer.jsp"/>
    </body>
</html>
