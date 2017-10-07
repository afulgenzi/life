<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" -->
<!--    "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@ taglib prefix="explore" tagdir="/WEB-INF/tags/explore"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Explore Legacy" />
<template:newModelExploring pageTitle="${pageTitle}">
    <jsp:body>
		<explore:renderGenericItem />
	</jsp:body>
</template:newModelExploring>