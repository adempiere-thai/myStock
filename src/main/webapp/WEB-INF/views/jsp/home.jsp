<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div data-role="page">
		<div data-role="header" data-theme="a">
			<h1>myStock </h1>
		</div>
		
		<div role="main" class="ui-content">
			<div id="logo" class="logo-menu-block text-center">
				<img src="${pageContext.request.contextPath}/resources/img/wip-logo.png" />
			</div><!-- #logo -->
			<div id="warehouse" class="text-center">
				<span class="default"><i class="fa fa-home"></i><small>&nbsp;${warehouse.warehouseName}</span>
			</div><!-- #logo -->
			
			<c:forEach items="${menuL}" var="menu">
				<%-- <c:if test="${menu.url eq 'stocktaking'}">
					<c:if test="${sessionScope.IS_OPEN_STOCKTAKING}">	
						<a href="${pageContext.request.contextPath}/${menu.url}" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false"> <spring:message code="${menu.menuName}" text="${menu.menuName}" /> </a>
					</c:if>
					<c:if test="${not sessionScope.IS_OPEN_STOCKTAKING}">	
						<a href="#" class="ui-btn ui-corner-all btn-disabled" data-direction="reverse" data-ajax="false"> <spring:message code="${menu.menuName}" text="${menu.menuName}" /> </a>
					</c:if>										
				</c:if>			
				<c:if test="${menu.url ne 'stocktaking'}">
					<a href="${pageContext.request.contextPath}/${menu.url}" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false"> <spring:message code="${menu.menuName}" text="${menu.menuName}" /> </a>					
				</c:if> --%>
				<a href="${pageContext.request.contextPath}/${menu.url}" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false"> <spring:message code="${menu.menuName}" text="${menu.menuName}" /> </a>
			</c:forEach>
			<a href="logout" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false"><spring:message code="menu.logout" text="menu.logout" /> </a>
		</div>
		
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>