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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="../../css/jsp/client/myorders.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />
    <title>My orders</title>

</head>

<body>
<jsp:include page="/jsp/client/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12 center">
            <c:if test="${not empty sessionScope.operationStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="operationStatusNegative" scope="session" />
            </c:if>
        </div>
    </div>
    <br>
    <br>
    <h3><fmt:message key="label.order.pagetitle" bundle="${rb}"/></h3>
    <h5><fmt:message key="label.order.leavefeedback" bundle="${rb}"/></h5>
    <br>
    <div class="row">
        <div class="col-md-12">
            <div class="table-responsive-sm">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.order.dishes" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.paystatus" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.orderdate" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.price" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.add.review" bundle="${rb}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.orderList}" var="order">
                        <tr class="first">
                            <td>

                                <div class="dropdown show">
                                    <a class="btn btn-secondary dropdown-toggle" id="${order.id}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span><fmt:message key="label.order.dishes" bundle="${rb}"/></span>
                                    </a>

                                    <div class="dropdown-menu" aria-labelledby="${order.id}">
                                        <table class="table table-striped table-bordered table-condensed">
                                            <thead>
                                            <tr>
                                                <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.order.amount" bundle="${rb}"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${order.dishes}" var="dish">
                                                <tr>
                                                    <th><img src="${dish.key.picture}" alt="<fmt:message key="label.dish.photo" bundle="${rb}"/>"></th>
                                                    <th>${dish.key.name}</th>
                                                    <th>
                                                        <c:if test="${sessionScope.locale == 'en_US'}">
                                                            <span>${dish.key.price/200}$</span>
                                                        </c:if>
                                                        <c:if test="${sessionScope.locale == 'ru_RU'}">
                                                            <span>${dish.key.price/100}р</span>
                                                        </c:if>
                                                    </th>
                                                    <th>${dish.value}</th>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </td>

                            <td>
                                <c:if test="${order.orderStatus == 'CANCELED'}">
                                    <fmt:message key="label.sort.canceled" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.orderStatus == 'VIOLATED'}">
                                    <fmt:message key="label.sort.violated" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.orderStatus == 'EXPECTED'}">
                                    <fmt:message key="label.sort.expected" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.orderStatus == 'PAID'}">
                                    <fmt:message key="label.sort.paid" bundle="${rb}"/>
                                </c:if>

                            </td>
                            <td>${order.date}</td>
                            <td>
                                <c:if test="${sessionScope.locale == 'en_US'}">
                                    <span>${order.getTotalPrice()/200}$</span>
                                </c:if>
                                <c:if test="${sessionScope.locale == 'ru_RU'}">
                                    <span>${order.getTotalPrice()/100}р</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${order.orderStatus == 'PAID'}">
                                    <form class="form-inline" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="add_review" />
                                        <input type="hidden" name="orderId" value=${order.id} />
                                        <button type="submit" class="btn btn-primary"><fmt:message key="label.order.add.review" bundle="${rb}"/></button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Search results">
                <ul class="pagination">
                    <c:if test="${sessionScope.current_page != 1}">
                        <li class="page-item">
                            <a class="page-link" href="${sessionScope.urlForPagination}current_page=${sessionScope.current_page - 1}"><fmt:message key="label.pagination.prev" bundle="${rb}"/></a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
                        <li class="page-item"><a class="page-link" href="${sessionScope.urlForPagination}current_page=${i}">${i}</a></li>
                    </c:forEach>

                    <c:if test="${sessionScope.current_page < sessionScope.noOfPages}">
                        <li class="page-item">
                            <a class="page-link" href="${sessionScope.urlForPagination}current_page=${sessionScope.current_page + 1}"><fmt:message key="label.pagination.next" bundle="${rb}"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>

        </div>
    </div>

</div> <!-- /container -->


<jsp:include page="/jsp/client/pagepart/footer.jsp"/>
</body>
</html>
