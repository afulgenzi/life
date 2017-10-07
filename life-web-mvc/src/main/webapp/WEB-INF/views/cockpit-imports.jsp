<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="cockpit" tagdir="/WEB-INF/tags/cockpit"%>
<%@taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>
<%@taglib prefix="side" tagdir="/WEB-INF/tags/side"%>

<c:set var="pageTitle" value="Bank Account Movements" />
<template:page pageTitle="${pageTitle}">
	<jsp:body>
		<div ng-controller="cockpitController">
			<div id="infoMsgDiv" class="col-sm-12 panel-success" style="padding-bottom:20px" ng-show="infoMsg != ''">
				<span class="panel-heading" ng-init="infoMsg = ''">{{infoMsg}}</span>
			</div>
			<div id="warnMsgDiv" class="col-sm-12 panel-danger" style="padding-bottom:20px" ng-show="warnMsg != ''">
				<span class="panel-heading" ng-init="warnMsg = ''">{{warnMsg}}</span>
			</div>

			<cockpit:cockpit-imports />
		</div>
	</jsp:body>
</template:page>