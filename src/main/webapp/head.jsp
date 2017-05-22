<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    
	<title><spring:message code="app.name" /> : <spring:message code="client.name" /></title>
	<!-- Stylesheet -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.mobile-1.4.0.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css" />

	<!-- Font Awesome -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/font-awesome-4.6.3/css/font-awesome.min.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customize.css" />
	<!-- End Attach Stylesheet -->
	
	<!-- Javascript -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mobile-1.4.5.min.js"></script>
	
	<script>var contextPath = "${pageContext.request.contextPath}"</script>
	<!-- End Javascript -->
</head>