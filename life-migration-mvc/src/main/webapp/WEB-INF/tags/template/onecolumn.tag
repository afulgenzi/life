<%@ tag body-content="scriptless"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/footer"%>
<%@ taglib prefix="side" tagdir="/WEB-INF/tags/side"%>
<%@ taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<template:master pageTitle="${pageTitle}">

    <jsp:attribute name="pageCss">
		<link rel="stylesheet/less" type="text/css" href="${resourcePath}/css/life-base.less" />
		<link rel="stylesheet/less" type="text/css" href="${resourcePath}/css/life-menu.less" />
		<jsp:invoke fragment="pageCss" />
	</jsp:attribute>

    <jsp:attribute name="pageScripts">
		<jsp:invoke fragment="pageScripts" />
	</jsp:attribute>

    <jsp:body>
		<header:header />
		
		<jsp:doBody />
		
		<footer class="footer-wrapper container">
			<footer:footer />
		</footer>

	</jsp:body>

</template:master>
