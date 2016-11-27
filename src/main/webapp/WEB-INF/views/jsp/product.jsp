<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="product" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<a id="backMenu" href="${pageContext.request.contextPath}/home" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary"><spring:message code="btn.menu"  text="btn.menu" /></a>
			<h2><spring:message code="menu.checkStock"  text="menu.checkStock" /></h2>
		</div>
		
		<div role="main" class="ui-content">
			<form>
        	<input id="products-search" data-type="search" placeholder="Search Here ..." />
			</form>
			 <ul id="productList" data-role="listview" data-autodividers="true" data-inset="false" data-filter="true" data-input="#products-search">
				
			</ul>
		</div>
	</div>
	<!-- Page Javascript -->
	<script src="${pageContext.request.contextPath}/resources/js/numeral.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/product.js"></script>
</body>
</html>