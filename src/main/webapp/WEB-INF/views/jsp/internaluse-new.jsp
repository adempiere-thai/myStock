<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="internaluse-input" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backPage" href="${pageContext.request.contextPath}/internaluse/detail" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse"><spring:message code="btn.back" text="btn.back" /></a>
			<h2><spring:message code="internaluse.useItem" text="internaluse.useItem" /></h2>
		</div>
		
		<div role="main" class="ui-content">
			<form id="useItemFrm" novalidate="novalidate" data-ajax="false" method="post" action="${pageContext.request.contextPath}/internaluse/detail/new">
        	<div class="ui-field-contain">
				<label for="locator" class="caption"><spring:message code="label.locator"  text="label.locator" /></label> 
				<select name="locator" id="locator">
					<c:forEach items="${locatorL}" var="locator">
						<c:set var="locatorSelected" value=""/>
						<c:if test="${locator.defaultLocator}" >
							<c:set var="locatorSelected" value="selected"/>
						</c:if>
						<c:if test="${locator.locatorId == line.locator.locatorId}" >
							<c:set var="locatorSelected" value="selected"/>
						</c:if>
						<option value="${locator.locatorId}" ${locatorSelected}>${locator.locatorKey}</option>					
					</c:forEach>
				</select>
			</div>
        	<div class="ui-field-contain">
				<label for="pdCode"><spring:message code="label.itemCode" text="label.itemCode" /></label>
				<input type="text" name="pdCode" id="pdCode" class="required" value="${line.product.productSrhKey}" placeholder="Please Scan Item Code" data-mini="true" />
			</div>
			
			<p id="productName">${line.product.productName}</p>
            
            <div class="ui-field-contain">
				<label for="asiId"><spring:message code="label.asiId" text="label.asiId" /></label>
				<input type="text" name="asiId" id="asiId" class="required" value="${line.asi.asiId}" placeholder="Please Scan ASI Id" data-mini="true" />
			</div>
			
			<p id="asiDesc">${line.asi.description}</p>
			
			<div class="ui-field-contain">
				<label for="countQty"><spring:message code="label.count.qty" text="label.count.qty" /></label>
				<input type="number" name="usedQty" id="usedQty" class="required" value="${line.usedQty}" placeholder="Used Qty" data-mini="true" />
			</div>
			<input id="productId" name="productId" type="hidden" value="${line.product.productId}" />
			<button type="submit" id="submitBtn" class="ui-shadow ui-btn ui-corner-all ui-mini"><spring:message code="btn.submit" text="btn.submit" /></button>
			</form>
		</div>
	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/internaluse-input.js"></script>
</body>
</html>