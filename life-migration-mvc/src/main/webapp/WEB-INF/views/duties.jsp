<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="explore" tagdir="/WEB-INF/tags/explore"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Duties" />
<template:twocolumns pageTitle="${pageTitle}">
	<jsp:body>
		<c:if test="${not empty duties}">
			<c:forEach var="duty" items="duties">
			</c:forEach>
		</c:if>
	</jsp:body>
</template:twocolumns>