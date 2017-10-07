<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="invoices" tagdir="/WEB-INF/tags/invoices"%>
<%@taglib prefix="javascript" tagdir="/WEB-INF/tags/javascript"%>
<%@taglib prefix="side" tagdir="/WEB-INF/tags/side"%>

<c:set var="pageTitle" value="Invoices" />
<template:page pageTitle="${pageTitle}">
    <jsp:body>
        <div ng-controller="invoicesController" class="row">
            <invoices:invoice-payments />
        </div>
    </jsp:body>
</template:page>