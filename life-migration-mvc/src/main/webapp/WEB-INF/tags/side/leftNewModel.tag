<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="leftcolumn">
    Tables:<br>
    <c:forEach items="${NewModelTables}" var="table">
        <a href="${contextPath}/explore/newModel/showTable?tableName=${table.name}"><span class="dbTableName">${table.name} (${table.size})</span></a><br>
    </c:forEach>
</div>
