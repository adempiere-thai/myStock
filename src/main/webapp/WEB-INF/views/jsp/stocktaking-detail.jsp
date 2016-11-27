<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking-detail" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<h2><spring:message code="label.itemList" text="label.itemList"/></h2>
			<a id="countItem" href="${pageContext.request.contextPath}/stocktaking/detail/new?locator=${locator.locatorId}" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-plus ui-btn-right btn-success" data-transition="slide">&nbsp;</a>
		</div>
		
		<div role="main" class="ui-content">
			<ul id="items-in-order-list" data-role="listview" data-inset="false">
				<c:forEach items="${detailL}" var="line">
					<li class="items-in-order-item">
						<h3>{line.product.productSrhKey} {line.product.productName}</h3>
						<div class="ui-grid-a">
							<div class="ui-block-a" style="width:70%">
								<p>{line.asi.description}</p>
							</div><!-- .ui-block-a -->
							<div class="ui-block-b" style="width:30%">
								<p class="text-right">{line.asi.countQty}</p>
							</div><!-- .ui-block-b -->
						</div><!-- .ui-grid-a -->
						<div class="action-block">
							<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-success edit-price-btn" data-rel="popup">Edit</button>
							<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-danger delete-btn" data-rel="popup">Delete</button>
						</div>
					</li>
				</c:forEach>	
			</ul>
		</div>
		<div id="step-block" data-role="footer" data-position="fixed">
			<a id="prevStep" href="${pageContext.request.contextPath}/stocktaking" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse"><spring:message code="btn.prev" text="btn.prev" /></a>
			<h4><spring:message code="msg.step" arguments="2,2" htmlEscape="false" argumentSeparator=","/></h4>
			<a  id="save" href="${pageContext.request.contextPath}/home" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-check ui-btn-right btn-success" data-rel="popup">&nbsp;</a>
		</div>
		
		<div data-role="popup" id="editQty" data-theme="a" data-overlay-theme="b" class="ui-corner-all" data-dismissible="false" style="width:260px;">
			<form action="#" method="get">
				<div style="padding:10px 20px;">
					<label for="price" class="ui-hidden-accessible"><spring:message code="label.count.qty" text="label.count.qty" /></label>
					<input type="text" name="price" id="price" value="1" autocomplete="off" data-mini="true" />
					<a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-mini" data-rel="back"><spring:message code="btn.cancel" text="btn.cancel" /></a>
					<button type="submit" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-primary"><spring:message code="btn.edit" text="btn.edit" /></button>
				</div>
			</form>
		</div>
		
		<div data-role="popup" id="deleteDialog" data-theme="none" data-overlay-theme="b" data-transition="slideup" data-position-to="#step-block" data-dismissible="false" style="width:260px;">
			<div data-role="controlgroup">
				<a href="#" class="ui-btn ui-corner-all ui-mini cancel-txt" data-rel="back" data-transition="flow"><spring:message code="btn.removeItem" text="btn.removeItem" /></a>
				<a href="#" class="ui-btn ui-corner-all ui-mini" data-rel="back"><spring:message code="btn.cancel" text="btn.cancel" /></a>
			</div>
		</div>
	</div>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking2.js"></script>
</body>
</html>