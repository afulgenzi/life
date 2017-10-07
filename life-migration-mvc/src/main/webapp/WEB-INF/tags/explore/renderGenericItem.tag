<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <c:if test="${not empty selectedTable}">
        <table>
            <tr>
                <td>Table:</td>
                <td>${selectedTable.name}</td>
            </tr>
            <tr>
                <td>Rows:</td>
                <td>${selectedTable.size}</td>
            </tr>
            <tr>
                <td>Last migrated:</td>
                <c:if test="${not empty selectedTable.lastMigrated}">
                    <td>${selectedTable.lastMigrated}</td>
                </c:if>
                <c:if test="${empty selectedTable.lastMigrated}">
                    <td>&lt;never migrated&gt;</td>
                </c:if>
            </tr>
        </table>

        <!-- ROWS -->
        <table border="1">
            <c:forEach items="${selectedTable.rows}" var="row" varStatus="rowNum">
                <c:if test="${rowNum.index == 0}">
                    <tr>
                        <c:forEach items="${row}" var="field">
                            <th>
                                ${field.key}
                            </th>
                        </c:forEach>
                    </tr>
                </c:if>
                <tr>
                    <c:forEach items="${row}" var="field">
                        <td>
                            ${field.value}
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>