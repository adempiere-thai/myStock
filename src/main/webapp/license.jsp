<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="head.jsp"></jsp:include>
<body>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="inline">
			<h1><spring:message code="app.name" /> </h1>
		</div>
		<div data-role="content" data-theme="a">
			<p>License is expired</p>
		</div>
	</div>
</body>
</html>