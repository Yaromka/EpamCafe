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
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="../../css/jsp/admin/newdish.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />

    <title>New Category</title>
</head>
<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.addCategoryStatusPositive}">
                <div class="alert alert-success" role="alert"><fmt:message key="${sessionScope.addCategoryStatusPositive}" bundle="${rb}"/></div>
                <c:remove var="addCategoryStatusPositive" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.addCategoryStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.addCategoryStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="addCategoryStatusNegative" scope="session" />
            </c:if>
            <br>
            <h3><fmt:message key="label.newcategory.header" bundle="${rb}"/></h3>
            <form method="get" action="/controller" >
                <input type="hidden" name="command" value="add_category"/>
                <div class="control-group">
                    <!-- Name -->
                    <label class="control-label"  for="category_name"><fmt:message key="label.category.name" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" class="form-control" id="category_name" name="category_name"  required=""  pattern="[А-Яа-я\w\s.,?!-+#%_()]{2,70}"/>
                        <p class="help-block"><fmt:message key="label.add.category.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Button -->
                    <div class="controls">
                        <button class="btn btn-success" type="submit"><fmt:message key="label.add.add" bundle="${rb}"/></button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>