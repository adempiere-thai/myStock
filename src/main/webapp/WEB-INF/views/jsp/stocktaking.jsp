<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking1" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backMenu" href="${pageContext.request.contextPath}/home" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary"><spring:message code="btn.menu"  text="btn.menu" /></a>
			<h2><spring:message code="stocktaking.selectLocator"  text="stocktaking.selectLocator" /></h2>
		</div>

		<div role="main" class="ui-content">
			<form id="locatorFrm" novalidate="novalidate" data-ajax="false" method="post" action="${pageContext.request.contextPath}/stocktaking/detail">
			<div class="ui-field-contain">
				<label for="locator" class="caption"><spring:message code="label.locator"  text="label.locator" /></label> 
				<select name="locator" id="locator">
					<c:forEach items="${locatorL}" var="locator">
						<c:set var="locatorSelected" value=""/>
						<c:if test="${locator.defaultLocator}" >
							<c:set var="locatorSelected" value="selected"/>
						</c:if>
						<option value="${locator.locatorId}" ${locatorSelected}>${locator.locatorKey}</option>					
					</c:forEach>
				</select>
			</div>
			</form>
		</div>
		<div id="step-block" data-role="footer" data-position="fixed">
			<h4>Step 1 of 2</h4>
			<button id="nextStep" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-icon-carat-r ui-btn-right btn-primary"
				data-transition="slide">Next</button>
		</div>
	</div>
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking.js"></script>
</body>
</html>