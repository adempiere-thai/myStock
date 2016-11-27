<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backMenu" href="${pageContext.request.contextPath}/home" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary"><spring:message code="btn.menu"  text="btn.menu" /></a>
			<h2><spring:message code="stocktaking.selectLocator"  text="stocktaking.selectLocator" /></h2>
		</div>

		<div role="main" class="ui-content">
			<form id="locatorFrm" novalidate="novalidate" data-ajax="false" method="get" action="${pageContext.request.contextPath}/stocktaking/detail">
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
			<h4><spring:message code="msg.step" arguments="1,2" htmlEscape="false" argumentSeparator=","/></h4>
			<button id="stocktakingDetail" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-icon-carat-r ui-btn-right btn-primary"
				data-transition="slide"><spring:message code="btn.next" text="btn.next" /></button>
		</div>
	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking.js"></script>
</body>
</html>