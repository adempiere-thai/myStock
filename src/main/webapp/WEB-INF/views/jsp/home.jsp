<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="../../head2.jsp"></jsp:include>
<body>
	<div data-role="page">
		<div data-role="header" data-theme="a">
			<h1>myStock </h1>
		</div>
		
		<div role="main" class="ui-content">
			<div id="logo" class="text-center">
				<img src="img/wip-logo.png" />
			</div><!-- #logo -->
			<div id="warehouse" class="text-center">
				<span class="default"><i class="fa fa-home"></i><small>&nbsp;Sm-จรัญฯ</span>
			</div><!-- #logo -->
			
        	<a href="checkQty.html" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false">ตรวจสอบจำนวนสินค้า</a>
			<a href="stocktaking.html" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false">ตรวจนับสินค้า</a>
			<a href="internaluse.html" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false">เบิกใช้สินค้า</a>
			<a href="login.html" class="ui-btn ui-corner-all btn-info" data-direction="reverse" data-ajax="false">ออกจากระบบ</a>
            
		</div>
		
		<div data-role="footer"">
			<div id="version" class="text-center">
				<h4><i class="fa fa-cube"></i>myStock V.1.0.0 by <a href="http://adempiere-thai.net">COS</a></h4>
			</div>
		</div>
	</div>
</body>
</html>