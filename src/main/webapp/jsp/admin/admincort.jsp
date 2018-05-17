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
    <title>Admincort</title>
    <link href="../../css/jsp/admin/admincort.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />

</head>

<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.userLoyaltyStatus}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.userLoyaltyStatus}" bundle="${rb}"/></div>
                <c:remove var="userLoyaltyStatus" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.operationStatusNegative}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.operationStatusNegative}" bundle="${rb}"/></div>
                <c:remove var="operationStatusNegative" scope="session" />
            </c:if>
            <br>
            <form class="form-inline" action="/controller">
                <input type="hidden" name="command" value="get_all_users" />
                <button type="submit" class="btn btn-primary"><fmt:message key="label.admimcort.showallusers" bundle="${rb}"/></button>
            </form>
            <br/>
            <form class="form-inline" action="/controller">
                <input type="hidden" name="command" value="get_user_by_surname" />
                <label class="sr-only" for="inlineFormInput"><fmt:message key="label.registration.surname" bundle="${rb}"/></label>
                <input type="text" name="surname" class="form-control" id="inlineFormInput" required placeholder="Ivanov" pattern="^[А-ЯA-Z][a-яa-z]{2,24}(-[А-ЯA-Z][a-яa-z]{2,12})?"/>
                <button type="submit" class="btn btn-primary"><fmt:message key="label.foodcort.show_button" bundle="${rb}"/></button>
            </form>
            <br>
        </div>
        <div class="col-md-4"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="table-responsive-sm">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.registration.name" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.surname" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registratiion.loyaltypoints" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.passport" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.email" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.phone" bundle="${rb}"/></th>
                        <th><fmt:message key="label.navbar.admin.orders" bundle="${rb}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <th>
                                <div class="btn-group">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            ${user.loyaltyPoints}
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <a class="dropdown-item">
                                            <form method="get" action="/controller">
                                                <input type="hidden" name="command" value="update_loyalty_points">
                                                <input type="hidden" name="userId" value=${user.id} />
                                                <input type="hidden" name="user_loyalty_points" value=${user.loyaltyPoints} />
                                                <button type="submit" class="btn btn-success dropdown-item" name="loyalty_operation" value="+" onclick="return confirm('Are you sure?')">+</button>
                                            </form>
                                        </a>

                                        <a class="dropdown-item">
                                            <form method="get" action="/controller">
                                                <input type="hidden" name="command" value="update_loyalty_points">
                                                <input type="hidden" name="userId" value=${user.id} />
                                                <input type="hidden" name="user_loyalty_points" value=${user.loyaltyPoints} />
                                                <button type="submit" class="btn btn-danger dropdown-item" name="loyalty_operation" value="-" onclick="return confirm('Are you sure?')">-</button>
                                            </form>
                                        </a>
                                    </div>
                                </div>
                            </th>
                            <td>${user.passport}</td>
                            <td>${user.mail}</td>
                            <td>${user.phone}</td>
                            <td>
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="get_orders_by_user" />
                                    <input type="hidden" name="userId" value=${user.id} />
                                    <button type="submit" class="btn btn-primary"><fmt:message key="label.navbar.admin.orders" bundle="${rb}"/></button>
                                </form>
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
<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>