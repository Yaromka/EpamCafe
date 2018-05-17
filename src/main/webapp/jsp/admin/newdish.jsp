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
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />
    <link href="../../css/jsp/admin/newdish.css" rel="stylesheet">

    <title>New Dish</title>
</head>
<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.operationStatusPositive}">
                <div class="alert alert-success" role="alert"><fmt:message key="${sessionScope.operationStatusPositive}" bundle="${rb}"/></div>
                <c:remove var="operationStatusPositive" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.operationStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="operationStatusNegative" scope="session" />
            </c:if>
            <br>
            <h3><fmt:message key="label.newdish.header" bundle="${rb}"/></h3>
            <form method="post" action="/controller">
                <input type="hidden" name="command" value="add_dish" />

                <div class="control-group">
                    <!-- Name -->
                    <label class="control-label"  for="dish_name"><fmt:message key="label.foodcort.dishname" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" class="form-control" id="dish_name" name="dish_name"  required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{2,70}" />
                        <p class="help-block"><fmt:message key="label.add.dishname.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Category -->
                    <label class="control-label"  for="category_id"><fmt:message key="label.foodcort.category" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <select name="category_id" id="category_id" required class="form-control">
                            <c:forEach items="${sessionScope.categoryList}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Description -->
                    <label class="control-label"  for="dish_description"><fmt:message key="label.foodcort.description" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <textarea class="form-control" rows="5" id="dish_description" name="dish_description"></textarea>
                    </div>
                    <p class="help-block"><fmt:message key="label.add.description.help" bundle="${rb}"/></p>
                </div>

                <div class="control-group">
                    <!-- Amount -->
                    <label class="control-label"  for="dish_amount"><fmt:message key="label.foodcort.amount" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" class="form-control" id="dish_amount" name="dish_amount"  required="" pattern="\d{1,4}" />
                        <p class="help-block"><fmt:message key="label.add.amount.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Price -->
                    <label class="control-label"  for="dish_price"><fmt:message key="label.foodcort.price" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" class="form-control" id="dish_price" name="dish_price"  required="" pattern="[\d]{0,3}\.[\d]{0,2}" />
                        <p class="help-block"><fmt:message key="label.add.price.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Button -->
                    <div class="controls">
                        <button class="btn btn-success" type="submit"><fmt:message key="label.add.add" bundle="${rb}"/></button>
                    </div>
                </div>
            </form>
            <br>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>