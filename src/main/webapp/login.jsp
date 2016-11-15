<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="head.jsp"></jsp:include>
<body>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<h1>myStock </h1>
		</div>
		<div data-role="content" data-theme="a">
			<div id="logo" class="text-center">
				<img src="${pageContext.request.contextPath}/resources/img/wip-logo.png" />
			</div><!-- #logo -->
			
			<form id="loginFrm" novalidate="novalidate" data-ajax="false" metohd="post" action="#">
        	<div class="ui-field-contain">
				<label for="userId">User ID</label>
				<input type="text" name="userId" id="userId" class="required" value="" placeholder="User ID" data-mini="true" />
				<span />
			</div>
            
            <div class="ui-field-contain">
				<label for="pwd">Password</label>
				<input type="password" name="pwd" id="pwd" class="required" value="" placeholder="Password" data-mini="true" />
				<span />
			</div>
			
			<span />
			
			<button type="button" id="loginBtn" class="ui-shadow ui-btn ui-corner-all ui-mini">Login</button>

			</form>
            <div id="version" class="logo-menu-block text-center">
				<span class="version"><i class="fa fa-cube"></i><small>myStock V.1.0.0 by <a href="http://adempiere-thai.net">COS</a></small></span>
			</div>
		</div>
	</div>
	<!-- Page Javascript -->
	<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</body>
</html>