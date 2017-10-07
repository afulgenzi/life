<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <c:if test="${not empty selectedTable}">
        <table>
            <tr>
                <td>Source:</td>
                <td>${selectedTable.packageName}</td>
            </tr>
            <tr>
                <td>Last migrated:</td>
                <td>${selectedTable.lastMigration}</td>
            </tr>
        </table>
        
        <button class="migrateButton" name="migrateButton" value="${selectedTable.packageName}">Migrate</button>
        <div id="migrationResult"></div>

        <!-- ROWS -->
        <table border="1">
            <c:forEach items="${selectedTable.migrationItems}" var="row" varStatus="rowNum">
                <c:if test="${rowNum.index == 0}">
                    <tr>
                        <th>Source</th>
                        <th>Destination</th>
                    </tr>
                </c:if>
                <tr>
                    <td>${row.source}</td>
                    <td>${row.destination}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>