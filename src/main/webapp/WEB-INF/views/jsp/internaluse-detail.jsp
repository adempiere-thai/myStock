<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="internaluse-detail" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<h2><spring:message code="label.itemList" text="label.itemList"/></h2>
			<a id="addItem" href="${pageContext.request.contextPath}/internaluse/detail/new" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-plus ui-btn-right btn-success" data-transition="slide">&nbsp;</a>
		</div>
		
		<div role="main" class="ui-content">
			<ul id="items-list" data-role="listview" data-inset="false">
				<c:forEach items="${detailL}" var="line">				
					<li class="items-in-order-item">
						<h3>${line.product.productSrhKey} ${line.product.productName}</h3>
						<div class="ui-grid-a">
							<div class="ui-block-a" style="width:70%">
								<p>${line.asi.description}</p>
							</div><!-- .ui-block-a -->
							<div class="ui-block-b" style="width:30%">
								<p class="text-right"><span class="ui-li-count"> <fmt:formatNumber type="number" maxFractionDigits="0" value="${line.usedQty}" /> pcs</span> </p>
							</div><!-- .ui-block-b -->
						</div><!-- .ui-grid-a -->
						<p>${line.locator.locatorKey}</p>
						<div class="action-block">
							<button edit_no="${line.lineNo}" edit_qty="${line.usedQty}" type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-success edit-qty-btn" data-rel="popup"><spring:message code="btn.edit" text="btn.edit"/></button>
							<button del_no="${line.lineNo}" type="button" class="show-page-loading-msg ui-btn ui-corner-all ui-btn-inline ui-mini btn-danger delete-btn" data-rel="popup"><spring:message code="btn.delete" text="btn.delete"/></button>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div id="step-block" data-role="footer" data-position="fixed">
			<a id="prevStep" href="${pageContext.request.contextPath}/internaluse" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse"><spring:message code="btn.prev" text="btn.prev" /></a>
			<h4><spring:message code="msg.step" arguments="2,2" htmlEscape="false" argumentSeparator=","/></h4>
			<c:if test="${fn:length(detailL) gt 0}">
			<a  id="selectDocActionBtn" href="#pagetwo" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-check ui-btn-right btn-success">&nbsp;</a>
			</c:if>
		</div>
		
		<div data-role="popup" id="editQtyDialog" data-theme="a" data-overlay-theme="b" class="ui-corner-all" data-dismissible="false" style="width:260px;">
			<form id="editQtyFrm" novalidate="novalidate" data-ajax="false" action="${pageContext.request.contextPath}/internaluse/detail/edit" method="POST">
				<div style="padding:10px 20px;">
					<label for="editQty" class="ui-hidden-accessible"><spring:message code="label.count.qty" text="label.count.qty" /></label>
					<input type="number" name="editQty" id="editQty" value="1" autocomplete="off" data-mini="true" />
					<input type="hidden" name="editLineNo" id="editLineNo"  /> 
					<a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-mini" data-rel="back"><spring:message code="btn.cancel" text="btn.cancel" /></a>
					<button type="submit" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-primary"><spring:message code="btn.edit" text="btn.edit" /></button>
				</div>
			</form>
		</div>
		
		<div data-role="popup" id="deleteDialog" data-theme="none" data-overlay-theme="b" data-transition="slideup" data-position-to="#step-block" data-dismissible="false" style="width:260px;">
			<form  id="deleteFrm" novalidate="novalidate" data-ajax="false" action="${pageContext.request.contextPath}/internaluse/detail/delete" method="POST">
			<input type="hidden" name="delLineNo" id="delLineNo"  />
			<div data-role="controlgroup">
				<button type="submit" class="ui-btn ui-corner-all ui-mini"><span class="cancel-txt"><spring:message code="btn.removeItem" text="btn.removeItem" /></span></button>
				<!-- a id="submitDel" href="#" class="ui-btn ui-corner-all ui-mini cancel-txt" data-rel="back" data-transition="flow"><spring:message code="btn.removeItem" text="btn.removeItem" /></a-->
				<a href="javascript:void(0);" class="ui-btn ui-corner-all ui-mini cancel-txt" data-rel="back"><spring:message code="btn.cancel" text="btn.cancel" /></a>
			</div>
			</form>
		</div>
		
		<div data-role="popup" id="completedDocumentDialog" data-theme="a" data-overlay-theme="b" class="ui-corner-all" data-dismissible="false" style="width:260px;">
			<div style="padding:10px 20px;">
				
			</div>
		</div>
	</div>
	
	<div data-role="dialog" id="selectDocActionDialog" data-theme="a">
		<div data-role="header">
    		<h1>Document Action</h1>
  		</div>
  		<div data-role="main" class="ui-content">
			<div data-role="controlgroup">
				<button id="saveDraft" class="ui-btn ui-corner-all ui-mini" type="submit"><span><spring:message code="btn.draft" text="btn.draft" /></span></button>
				<button id="saveComplete" class="ui-btn ui-corner-all ui-mini" type="submit"><span><spring:message code="btn.complete" text="btn.complete" /></span></button>
				<!-- a id="submitDel" href="#" class="ui-btn ui-corner-all ui-mini cancel-txt" data-rel="back" data-transition="flow"><spring:message code="btn.removeItem" text="btn.removeItem" /></a-->
				<a href="javascript:void(0);" class="ui-btn ui-corner-all ui-mini" data-rel="back"><spring:message code="btn.cancel" text="btn.cancel" /></a>
			</div>
		</div>
		
		<div data-role="footer">
    		<h1>&nbsp;</h1>
  		</div>
	</div>
	
	<div data-role="dialog" id="processing" >
		<div data-role="header">
    		<h1>Document Processed</h1>
  		</div>
  		<div data-role="main" class="ui-content">
		<img id="processingImg" src="${pageContext.request.contextPath}/resources/img/loader.gif" />
			
		<div id="completedMsg">
			<p><spring:message code="msg.internal.use" htmlEscape="false" argumentSeparator="," /></p>
			<a href="${pageContext.request.contextPath}/home" class="ui-btn ui-corner-all ui-btn-inline ui-mini" ><spring:message code="btn.ok" text="btn.ok" /></a>
		</div>
		</div>
		<div data-role="footer">
    		<h1>&nbsp;</h1>
  		</div>
	</div>
	
	<jsp:include page="../../../toastr.jsp"></jsp:include>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/internaluse-detail.js"></script>
</body>
</html>