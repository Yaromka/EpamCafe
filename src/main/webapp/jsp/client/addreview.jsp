<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Account</title>

</head>
<body>
<jsp:include page="/jsp/client/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4">

        </div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.addStatusPositive}">
                <div class="alert alert-success" role="alert"><fmt:message key="${sessionScope.addStatusPositive}" bundle="${rb}"/></div>
                <c:remove var="addStatusPositive" scope="session" />
            </c:if>
            <br>
            <div>
                <h1><fmt:message key="label.add.reveiw" bundle="${rb}"/></h1>
            </div>

            <form class="form-horizontal" action='/controller' method="POST">
                <input type="hidden" name="orderId" value=${sessionScope.reviewedOrderId} />
                <input type="hidden" name="command" value="add_review" />

                <div class="control-group">
                    <!-- MARK -->
                    <label class="control-label"  for="review_mark"><fmt:message key="label.review.mark" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <select name="review_mark" id="review_mark" required class="form-control">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Comment -->
                    <label class="control-label"  for="review_comment"><fmt:message key="label.review.comment" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <textarea required class="form-control" rows="5" id="review_comment" name="review_comment"></textarea>
                    </div>
                </div>
                <br>
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