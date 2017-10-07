<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="movements" tagdir="/WEB-INF/tags/movements"%>
<%@taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>
<%@taglib prefix="side" tagdir="/WEB-INF/tags/side"%>

<c:set var="pageTitle" value="Bank Account Movements" />
<template:page pageTitle="${pageTitle}">
	<jsp:body>
		<div ng-controller="importMovementsController" class="row">
			<movements:movements-import-main />
		</div>
	</jsp:body>
</template:page>