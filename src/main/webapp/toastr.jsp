<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
boolean isError = false;
String error = "";
String errParams = ""; 
if(request.getParameter("error") != null || request.getAttribute("error") != null){
	isError = true;
	error = (String)(request.getParameter("error") !=null ? request.getParameter("error") : request.getAttribute("error"));
	
	if(request.getParameter("errParams") != null || request.getAttribute("errParams") != null){ 
		errParams =  (String)(request.getParameter("errParams") !=null ? request.getParameter("errParams") : request.getAttribute("errParams"));
	}
}

pageContext.setAttribute("isError", isError);
pageContext.setAttribute("error",error);
pageContext.setAttribute("errParams",errParams);
%>
<c:if test="${isError}" >
	<!-- toastr -->
	<link href="${pageContext.request.contextPath}/resources/toastr/toastr.min.css" rel="stylesheet" />
	
	<!-- Toastr -->
	<script src="${pageContext.request.contextPath}/resources/toastr/toastr.min.js"></script>
	
	<script>
	 	toastr.options = {
				"closeButton": false,
				"debug": false,
				"newestOnTop": false,
				"progressBar": false,
				"positionClass": "toast-top-full-width",
				"preventDuplicates": false,
				"onclick": null,
				"showDuration": "300",
				"hideDuration": "1000",
				"timeOut": "5000",
				"extendedTimeOut": "1000",
				"showEasing": "swing",
				"hideEasing": "linear",
				"showMethod": "fadeIn",
				"hideMethod": "fadeOut"
			};
			toastr.error('<spring:message code="${error}" arguments="${errParams}" htmlEscape="false" argumentSeparator="," />', 'Error');
	</script>
</c:if>
