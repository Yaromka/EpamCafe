<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
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
    <link rel="icon" href="#">
    <link href="../../css/jsp/client/foodcort.css" rel="stylesheet">
    <title>Foodcort</title>
</head>
<body>
<jsp:include page="/jsp/client/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4" id="categoryPanel">
            <c:if test="${not empty sessionScope.operationStatusPositive}">
                <div class="alert alert-success" role="alert"><fmt:message key="${sessionScope.operationStatusPositive}" bundle="${rb}"/></div>
                <c:remove var="operationStatusPositive" scope="session" />
            </c:if>

            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="get_menu"/>
                <fmt:message key="label.foodcort.category" bundle="${rb}"/>
                <br>
                <br>
                <select name="category" class="form-control">
                    <option value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
                    <c:forEach items="${sessionScope.categoryList}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="current_page" value="1"/>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="label.foodcort.show_button" bundle="${rb}"/></button>
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
                        <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.category" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.description" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.amount" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.dishList}" var="dish">
                        <tr>
                            <td><img src="#" alt="<fmt:message key="label.dish.photo" bundle="${rb}"/>"></td>
                            <td>${dish.name}</td>
                            <td>${dish.category.name}</td>
                            <td>${dish.description}</td>
                            <td>${dish.weight}</td>
                            <td>
                                <c:if test="${sessionScope.locale == 'en_US'}">
                                    <span>${dish.price/200}$</span>
                                </c:if>
                                <c:if test="${sessionScope.locale == 'ru_RU'}">
                                    <span>${dish.price/100}р</span>
                                </c:if>
                            </td>
                            <td style="vertical-align:middle; text-align: center;">
                                <form method="get" action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden"  name="command" value="add_to_basket" />
                                    <input type="hidden"  name="dishId" value="${dish.id}" />
                                    <button type="submit" class="btn btn-success"><fmt:message key="label.foodcort.addtobasket" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <br>

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
<jsp:include page="/jsp/client/pagepart/footer.jsp"/>
</body>
</html>