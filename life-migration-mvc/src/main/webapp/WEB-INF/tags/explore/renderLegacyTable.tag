<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <c:if test="${not empty selectedTable}">
        <table>
            <tr>
                <td>Content of table:</td>
                <td>${selectedTable.name}</td>
            </tr>
            <tr>
                <td>Rows:</td>
                <td>${selectedTable.size}</td>
            </tr>
            <tr>
                <td>Last migrated:</td>
                <td>${selectedTable.lastMigrated}</td>
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
                            <c:if test="${empty field.value}">&nbsp;</c:if>
                            <c:if test="${not empty field.value}">${field.value}</c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>