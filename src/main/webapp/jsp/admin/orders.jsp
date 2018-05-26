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
    <link href="../../css/jsp/admin/orders.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />
    <title>Orders</title>

</head>

<body>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.operationStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="operationStatusNegative" scope="session" />
            </c:if>
            <br>
            <h5><fmt:message key="label.order.sort" bundle="${rb}"/></h5>

            <form action="/controller">
                <input type="hidden" name="command" value="get_orders_by_parameters"/>
                <select class="form-control" name="pay_status" >
                    <option selected value="x"><fmt:message key="label.sort.any" bundle="${rb}"/></option>
                    <option value="PAID"><fmt:message key="label.sort.paid" bundle="${rb}"/></option>
                    <option value="VIOLATED"><fmt:message key="label.sort.violated" bundle="${rb}"/></option>
                    <option value="CANCELED"><fmt:message key="label.sort.canceled" bundle="${rb}"/></option>
                    <option value="EXPECTED"><fmt:message key="label.sort.expected" bundle="${rb}"/></option>
                </select>

                <br>
                <h5><fmt:message key="label.order.findbydate" bundle="${rb}"/></h5>

                <label class="control-label" for="from"><fmt:message key="label.order.from" bundle="${rb}"/></label>
                <div class="controls">
                    <input type="date" id="from" name="startDate" value="" />
                    <input type="date" id="to" name="endDate" value="" />
                </div>
                <input type="hidden" name="current_page" value="1"/>
                <br>
                <div class="controls">
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.foodcort.show_button" bundle="${rb}"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <br>
            <div class="table-responsive-sm">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.order.client" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.dishes" bundle="${rb}"/></th>
                        <th colspan = "4"><fmt:message key="label.order.paystatus" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.orderdate" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.comment" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.mark" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.price" bundle="${rb}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.orderList}" var="order">
                        <tr class="first">
                            <td>
                                <div class="dropdown show">
                                    <a class="btn btn-secondary dropdown-toggle" id="${order.user.name}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <strong>${order.user.name}</strong>
                                    </a>

                                    <div class="dropdown-menu" aria-labelledby="${order.user.name}">
                                        <table class="table table-striped table-bordered table-condensed">
                                            <thead>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <th><fmt:message key="label.registration.surname" bundle="${rb}"/></th>
                                                <th>${order.user.surname}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registratiion.loyaltypoints" bundle="${rb}"/></th>
                                                <th>
                                                        ${order.user.loyaltyPoints}
                                                </th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.email" bundle="${rb}"/></th>
                                                <th>${order.user.mail}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.phone" bundle="${rb}"/></th>
                                                <th>${order.user.phone}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.passport" bundle="${rb}"/></th>
                                                <th>${order.user.passport}</th>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </td>

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
                                                    <th><img src="${dish.key.picture}" alt=<fmt:message key="label.dish.photo" bundle="${rb}"/>></th>
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
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="change_pay_status" />
                                    <input type="hidden" name="orderId" value=${order.id} />
                                    <input type="hidden" name="orderStatus" value="CANCELED" />
                                    <button type="submit" class="btn btn-warning" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><i class="fa fa-trash-o"></i><fmt:message key="label.order.refuse" bundle="${rb}"/></button>
                                </form>
                            </td>
                            <td>
                                <c:if test="${order.orderStatus == 'VIOLATED'}">
                                    <fmt:message key="label.sort.violated" bundle="${rb}"/>
                                </c:if>
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="change_pay_status" />
                                    <input type="hidden" name="orderId" value=${order.id} />
                                    <input type="hidden" name="orderStatus" value="VIOLATED" />
                                    <button type="submit" class="btn btn-danger" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><i class="fa fa-ban"></i><fmt:message key="label.order.violated" bundle="${rb}"/></button>
                                </form>
                            </td>
                            <td>
                                <c:if test="${order.orderStatus == 'PAID'}">
                                    <fmt:message key="label.sort.paid" bundle="${rb}"/>
                                </c:if>
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="change_pay_status" />
                                    <input type="hidden" name="orderId" value=${order.id} />
                                    <input type="hidden" name="orderStatus" value="PAID" />
                                    <button type="submit" class="btn btn-success" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><i class="fa fa-check-circle-o"></i><fmt:message key="label.order.pay" bundle="${rb}"/></button>
                                </form>
                            </td>
                            <td>
                                <c:if test="${order.orderStatus == 'EXPECTED'}">
                                    <fmt:message key="label.sort.expected" bundle="${rb}"/>
                                </c:if>
                            </td>

                            <td>${order.date}</td>
                            <td>
                                <c:if test="${order.review == null}">
                                    <fmt:message key="label.order.no.comment" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.review != null}">
                                    ${order.review}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${order.rating == null}">
                                    <fmt:message key="label.order.no.rating" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.rating != null}">
                                    ${order.rating.getValue()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${sessionScope.locale == 'en_US'}">
                                    <span>${order.getTotalPrice()/200}$</span>
                                </c:if>
                                <c:if test="${sessionScope.locale == 'ru_RU'}">
                                    <span>${order.getTotalPrice()/100}р</span>
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
</div>
<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>