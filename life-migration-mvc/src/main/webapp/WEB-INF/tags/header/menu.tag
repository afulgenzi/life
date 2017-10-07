<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="menudiv">
	<ul class="menu">
		<li class="menuitem"><a href="${contextPath}">Home</a></li>
		<li class="menuitem"><a href="${contextPath}/migrate/showAvailableMigrations">Migrate</a></li>
		<li class="menuitem"><a href="${contextPath}/explore/legacy/">Explore Legacy</a></li>
        <li class="menuitem"><a href="${contextPath}/explore/newModel/">Explore Target</a></li>
	</ul>
</div>