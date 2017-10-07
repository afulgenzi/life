<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<c:if test="${pageType == 'transactions'}">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
					<span class="glyphicon glyphicon-exsearch"></span>
				</button>
			</c:if>
			<a class="navbar-brand" href="#">Homepage ${pageType}</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<!-- 'Work' menu -->
				<li>
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Work<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="${pageContext.request.contextPath}/work/joboffers">Job Offers</a>
						</li>
					</ul>
				</li>
				<!-- 'Transactions' menu -->
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Transactions<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="${pageContext.request.contextPath}/movements/currentMonth">List</a>
							<a class="hidden-xs" href="${pageContext.request.contextPath}/movements/import">Import</a>
							<a href="${pageContext.request.contextPath}/categories/">Categories</a>
						</li>
					</ul>
				</li>
				<!-- 'Cockpit' menu -->
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Cockpit<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="${pageContext.request.contextPath}/cockpit/imports">Import Status</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/cockpit/categories">Category Alerts</a>
						</li>
					</ul>
				</li>
				<!-- 'Incoices' menu -->
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Invoices<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="${pageContext.request.contextPath}/invoices/">List</a>
						</li>
					</ul>
				</li>
			</ul>
			<ul>
				<c:if test="${pageContext.request.remoteUser != null}">
					<li class="userId">
						<span>User: ${pageContext.request.remoteUser}</span>
						<a href="${pageContext.request.contextPath}/j_spring_security_logout">(logout)</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</div>
