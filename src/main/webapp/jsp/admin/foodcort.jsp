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
    <link href="../../css/jsp/admin/foodcort.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/icon.ico" />

    <title>Admin foodcort</title>
</head>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.operationStatusPositive}">
                <div class="alert alert-success" role="alert"><fmt:message key="${sessionScope.operationStatusPositive}" bundle="${rb}"/></div>
                <c:remove var="operationStatusPositive" scope="session" />
            </c:if>

            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="get_dishes"/>
                <br>
                <fmt:message key="label.foodcort.category" bundle="${rb}"/>
                <br/>
                <select name="category" class="form-control">
                    <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
                    <c:forEach items="${sessionScope.categoryList}" var="category">
                        <option  value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
                <br/>
                <br/>
                <fmt:message key="label.foodcort.enable" bundle="${rb}"/>
                <br/>
                <select name="enable_status" class="form-control" >
                    <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
                    <option value="false"><fmt:message key="label.foodcort.enable.no" bundle="${rb}"/></option>
                    <option value="true"><fmt:message key="label.foodcort.enable.yes" bundle="${rb}"/></option>
                </select>
                <input type="hidden" name="current_page" value="1"/>
                <br/>
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
                            <th><fmt:message key="label.foodcort.enable" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.operations" bundle="${rb}"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.dishList}" var="dish">
                            <tr>
                                <td><img src="${dish.picture}" alt="<fmt:message key="label.dish.photo" bundle="${rb}"/>"></td>
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
                                <td>
                                    <c:if test="${dish.getEnable() eq false}">
                                        <fmt:message key="label.foodcort.enable.no" bundle="${rb}"/>
                                    </c:if>
                                    <c:if test="${dish.getEnable() eq true}">
                                        <fmt:message key="label.foodcort.enable.yes" bundle="${rb}"/>
                                    </c:if>
                                </td>
                                <td style="vertical-align:middle; text-align: center;">
                                    <c:if test="${dish.getEnable() eq false}">
                                        <form method="get" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden"  name="command" value="update_enable_status" />
                                            <input type="hidden"  name="new_enable_status" value="true" />
                                            <input type="hidden"  name="dishId" value="${dish.id}" />
                                            <button class="btn btn-success" type="submit"><fmt:message key="label.foodcort.instock" bundle="${rb}"/></button>
                                        </form>
                                    </c:if>
                                    <c:if test="${dish.getEnable() eq true}">
                                        <form method="get" action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden"  name="command" value="update_enable_status" />
                                            <input type="hidden"  name="new_enable_status" value="false" />
                                            <input type="hidden"  name="dishId" value="${dish.id}" />
                                            <button class="btn btn-danger" type="submit"><fmt:message key="label.foodcort.notinstock" bundle="${rb}"/></button>
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
</div>



<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>