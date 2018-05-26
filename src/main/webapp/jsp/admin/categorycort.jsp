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
    <link href="../../css/jsp/admin/categorycort.css" rel="stylesheet">

    <title>Categorycort</title>
</head>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
        <br>
            <h3><fmt:message key="label.categorycort.header" bundle="${rb}"/></h3>

        <br>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.category.name" bundle="${rb}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.categoryList}" var="category">
                    <tr>
                        <td>${category.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <div class="controls ">
            <a class="btn btn-success" href="/controller?command=get_page&jsp_page=NEW_CATEGORY_JSP"><fmt:message key="label.add.category" bundle="${rb}"/></a>
            </div>
            <br>
            <br>
            <div class="col-md-4"></div>

        </div>
    </div>
</div>

<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>