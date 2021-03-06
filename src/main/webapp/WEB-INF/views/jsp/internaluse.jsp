<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="internaluse" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backMenu" href="${pageContext.request.contextPath}/home" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary"><spring:message code="btn.menu"  text="btn.menu" /></a>
			<h2><spring:message code="internaluse.selectReason"  text="internaluse.selectReason" /></h2>
		</div>

		<div role="main" class="ui-content">
			<form id="reasonFrm" novalidate="novalidate" data-ajax="false" method="post" action="${pageContext.request.contextPath}/internaluse">
			<div class="ui-field-contain">
				<label for="reason" class="caption"><spring:message code="label.reason"  text="label.reason" /></label> 
				<textarea id="reason" name="reason"><c:if test="${sessionScope.INTERNAL_USE != null}" >${sessionScope.INTERNAL_USE.reason}</c:if></textarea>
			</div>
			</form>
		</div>
		<div id="step-block" data-role="footer" data-position="fixed">
			<h4><spring:message code="msg.step" arguments="1,2" htmlEscape="false" argumentSeparator=","/></h4>
			<button id="internaluseDetail" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-icon-carat-r ui-btn-right btn-primary"
				data-transition="slide"><spring:message code="btn.next" text="btn.next" /></button>
		</div>
	</div>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/internaluse.js"></script>
</body>
</html>