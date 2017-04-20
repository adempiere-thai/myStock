<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking-input" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backPage" href="${pageContext.request.contextPath}/stocktaking" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse"><spring:message code="btn.back" text="btn.back" /></a>
			<h2><spring:message code="stocktaking.countItem" text="stocktaking.countItem" /></h2>
		</div>
		
		<div role="main" class="ui-content">
			<form:form method="post" novalidate="novalidate" data-ajax="false" modelAttribute="piDocLineFrm" action="${pageContext.request.contextPath}/stocktaking/detail">
        	<div class="ui-field-contain">
				<label for="locator" class="caption"><spring:message code="label.locator"  text="label.locator" /></label> 
				<form:select path="locatorId" items="${locatorL}" />
			</div>
        	<div class="ui-field-contain">
				<label for="pdCode"><spring:message code="label.itemCode" text="label.itemCode" /></label>
				<form:input path="pdCode" class="required" type="text" placeholder="Please Scan Item Code" data-mini="true" />
			</div>
			
			<p id="productNameTxt">${piDocLineFrm.product.productName}</p>
            
            <div class="ui-field-contain">
				<label for="asiId"><spring:message code="label.asiId" text="label.asiId" /></label>
				<form:input type="text" id="asiId" path="asi.attributeSetInstanceId" class="required" placeholder="Please Scan ASI Id" data-mini="true" />
			</div>
			
			<p id="asiDescTxt">${piDocLineFrm.asi.description}</p>
			
			<div class="ui-field-contain">
				<label for="countQty"><spring:message code="label.count.qty" text="label.count.qty" /></label>
				<form:input type="number" path="countQty" class="required" placeholder="Count Qty" data-mini="true" />
			</div>
			<form:input id="productId" path="product.productId" type="hidden" />
			<form:input id="productName" path="product.productName" type="hidden" />
			<form:input id="asiDesc" path="asi.description" type="hidden" />
			<form:input path="piDocId" type="hidden" />
			
			<input id="action" name="action" type="hidden" value="" />
			<button type="button" id="saveNextBtn" class="ui-shadow ui-btn ui-corner-all ui-mini"><spring:message code="btn.save.and.next" text="btn.save.and.next" /></button>
			<button type="button" id="saveBtn" class="ui-shadow ui-btn ui-corner-all ui-mini"><spring:message code="btn.save" text="btn.save" /></button>
			</form:form>
		</div>

	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking-input.js"></script>
</body>
</html>