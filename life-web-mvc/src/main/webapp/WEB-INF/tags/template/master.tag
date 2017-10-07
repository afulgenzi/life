<%@ tag body-content="scriptless"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="metaDescription" required="false"%>
<%@ attribute name="metaKeywords" required="false"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>

<html lang="${currentLanguage.isocode}" class="no-js">

	<head>
		<meta name="description" content="${metaDescription}" />
		<meta name="keywords" content="${metaKeywords}" />
		<meta name="robots" content="${metaRobots}" />

		<meta name="viewport" content="width=device-width,initial-scale=1"/>

		<%-- Inject any additional CSS required by the page --%>
		<jsp:invoke fragment="pageCss" />
		<title>${pageTitle}</title>
	</head>
	
	<%-- Inject the page body here --%>
	<jsp:doBody />
	
	<%-- Inject any additional JavaScript required by the page --%>
    <javascript:defaultJavascript/>
	<jsp:invoke fragment="pageScripts" />
</html>
