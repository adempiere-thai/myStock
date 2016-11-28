<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking-input" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backPage" href="${pageContext.request.contextPath}/stocktaking/detail?locator=${locator.locatorId}" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse"><spring:message code="btn.back" text="btn.back" /></a>
			<h2><spring:message code="stocktaking.countItem" text="stocktaking.countItem" /></h2>
		</div>
		
		<div role="main" class="ui-content">
			<form id="countItemFrm" novalidate="novalidate" data-ajax="false" method="post" action="${pageContext.request.contextPath}/stocktaking/detail/add">
        	<div class="ui-field-contain">
				<label for="pdCode"><spring:message code="label.itemCode" text="label.itemCode" /></label>
				<input type="text" name="pdCode" id="pdCode" class="required" value="${stkLine.product.productSrhKey}" placeholder="Please Scan Item Code" data-mini="true" />
			</div>
			
			<p id="productName">${stkLine.product.productName}</p>
            
            <div class="ui-field-contain">
				<label for="asiId"><spring:message code="label.asiId" text="label.asiId" /></label>
				<input type="text" name="asiId" id="asiId" class="required" value="${stkLine.asi.asiId}" placeholder="Please Scan ASI Id" data-mini="true" />
			</div>
			
			<p id="asiDesc">${stkLine.asi.description}</p>
			
			<div class="ui-field-contain">
				<label for="countQty"><spring:message code="label.count.qty" text="label.count.qty" /></label>
				<input type="number" name="countQty" id="countQty" class="required" value="${stkLine.countQty}" placeholder="Count Qty" data-mini="true" />
			</div>
			<input id="productId" name="productId" type="hidden" value="" />
			<input id="locatorId" name="locatorId" type="hidden" value="${locator.locatorId}" />
			<button type="submit" id="submitBtn" class="ui-shadow ui-btn ui-corner-all ui-mini"><spring:message code="btn.submit" text="btn.submit" /></button>
			</form>
		</div>

	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking-input.js"></script>
</body>
</html>