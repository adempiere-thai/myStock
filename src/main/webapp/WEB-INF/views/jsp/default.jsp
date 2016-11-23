<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<h1><spring:message code="app.name" /> </h1>
		</div>
		<div data-role="content" data-theme="a">
			<div id="logo" class="logo-menu-block text-center">
				<img src="${pageContext.request.contextPath}/resources/img/wip-logo.png" />
			</div><!-- #logo -->
			
			<form id="defaultFrm" novalidate="novalidate" data-ajax="false" method="post" action="${pageContext.request.contextPath}/default">
        	<div class="ui-field-contain">
				<label for="wh" class="select"><spring:message code="label.warehouse" text="Warehouse" /></label>
				<select name="wh" id="wh">
					<c:forEach items="${warehouseL}" var="warehouse"> 
  						<option value="${warehouse.warehouseId}">${warehouse.warehouseName}</option>
					</c:forEach>
				</select>
			</div>
            
			<button type="submit" id="loginBtn" class="ui-shadow ui-btn ui-corner-all ui-mini">Login</button>
			
			</form>
            <jsp:include page="../../../version.jsp"></jsp:include>
		</div>
	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
</body>
</html>