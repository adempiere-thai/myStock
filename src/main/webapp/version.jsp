<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="version" class="logo-menu-block text-center">
	<span class="version"><i class="fa fa-cube"></i><small><spring:message code="app.name" /> V.<spring:message code="myStock.version" /> by <a href="<spring:message code="support.url" />">COS</a></small></span>
</div>