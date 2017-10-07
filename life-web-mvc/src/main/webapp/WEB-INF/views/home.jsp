<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" -->
<!--    "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>

<c:set var="pageTitle" value="Home" />
<template:page pageTitle="${pageTitle}">
	
	<jsp:body>
		<div>
			<h1>Available functions:</h1>
			<ul>
				<li><a href="explore">Explore</a></li>
			</ul>
		</div>
	
		<div>ItemTypes: ${itemTypes}</div>
	
		<div>
			<c:forEach var="itemType" items="itemTypes">
				<c:set var="itemList" value="${itemType}"></c:set>
				<c:if test="${empty itemType}">
					No duties found!
				</c:if>
				<c:if test="${not empty duties}">
					Duties:
					<ul>
						<c:forEach var="duty" items="${duties}">
							<li>${duty.name}
						</c:forEach>
					</ul>
				</c:if>
			</c:forEach>
		</div>
	</jsp:body>
</template:page>