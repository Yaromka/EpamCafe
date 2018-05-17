<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="../../css/jsp/client/basket.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />
    <title>Basket</title>

</head>
<body>
<jsp:include page="/jsp/client/pagepart/header.jsp"/>
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
        </div>
        <div class="col-md-4"></div>
    </div>
    <br>
    <br>
    <div class="row">
        <div class="col-md-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-condensed" >
                    <thead>
                    <tr>
                        <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.category" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.description" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                        <th><fmt:message key="label.basket.amount" bundle="${rb}"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.user.shopBasket.basket}" var="dish">
                        <tr>
                            <td><img src="#" alt="<fmt:message key="label.dish.photo" bundle="${rb}"/>"></td>
                            <td>${dish.key.name}</td>
                            <td>${dish.key.category.name}</td>
                            <td>${dish.key.description}</td>
                            <td>
                                <c:if test="${sessionScope.locale == 'en_US'}">
                                    <span>${dish.key.price/200}$</span>
                                </c:if>
                                <c:if test="${sessionScope.locale == 'ru_RU'}">
                                    <span>${dish.key.price/100}р</span>
                                </c:if>
                            </td>
                            <td>${dish.value}</td>
                            <td>
                                <form method="get" action="/controller">
                                    <input type="hidden" name="command" value="remove_from_basket"/>
                                    <input type="hidden" name="dishId" value="${dish.key.id}"/>
                                    <button type="submit" class="btn btn-danger"><fmt:message key="label.foodcort.removefrombasket" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>
                            <strong>
                                <fmt:message key="label.basket.totalprice" bundle="${rb}"/> :
                                <c:if test="${sessionScope.locale == 'en_US'}">
                                    <span>${sessionScope.user.shopBasket.basketPrice/200}$</span>
                                </c:if>
                                <c:if test="${sessionScope.locale == 'ru_RU'}">
                                    <span>${sessionScope.user.shopBasket.basketPrice/100}р</span>
                                </c:if>
                            </strong>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
    <hr/>
    <div class="row">
        <div class="col-md-4">
            <form method="get" action="/controller">
                <label for="date" ><fmt:message key="label.basket.selectdate" bundle="${rb}"/></label>
                <select name="pay_method" class="form-control">
                    <option selected value="CASH"><fmt:message key="label.pay.cash" bundle="${rb}"/></option>
                    <option value="CLIENTBILL"><fmt:message key="label.pay.clientbill" bundle="${rb}"/></option>
                </select>
                <input type="hidden" name="command" value="make_order"/>
                <input type="date" id="date" name="oder_date" value="">
                <label for="time"><fmt:message key="label.basket.time" bundle="${rb}"/></label>
                <input type="time" id="time" name="oder_time" value="">
                <br>
                <div class="controls">
                    <button type="submit"  class="btn btn-success" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><fmt:message key="label.basket.makeorder" bundle="${rb}"/></button>
                </div>
                </form>
        </div>
        <div class="col-md-6"></div>
    </div>
</div>
<jsp:include page="/jsp/client/pagepart/footer.jsp"/>
</body>
</html>