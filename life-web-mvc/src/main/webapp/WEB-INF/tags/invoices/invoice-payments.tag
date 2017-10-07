<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="loading-background" class="col-xs-12" ng-show="loading" ng-cloak>
    <img width="52" src="${resourcePath}/images/loader.gif" class="ajax-loader"/>
</div>

<div id="msg-divs" class="col-xs-12">
    <div id="infoMsgDiv" class="col-xs-12 panel-success" style="padding-bottom:20px"
         ng-repeat="infoMsg in infoMsgs">
        <span class="panel-heading" ng-init="infoMsg = 'infoMsg'">{{infoMsg}}</span>
    </div>
    <div id="warnMsgDiv" class="col-xs-12 panel-danger" style="padding-bottom:20px"
         ng-repeat="warnMsg in warnMsgs">
        <span class="panel-heading" ng-init="warnMsg = 'warnMsg'">{{warnMsg}}</span>
    </div>
</div>
<div class="col-xs-12">
    <div ng-show="1==2" class="col-xs-12 text-left" style="margin-top:20px; margin-bottom:20px">
        <div class="col-xs-12 text-center">
            <div class="col-xs-1 text-center">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </div>
            <div class="col-xs-2 text-center btn-group" data-toggle="buttons"
                 style="padding-bottom:20px">
                <%--<button type="button" class="btn btn-info btn-block" ng-model="selectAllYears" ng-click="selectYear(year)">--%>
                    <%--ALL--%>
                <%--</button>--%>
                <label class="btn btn-info btn-block">
                    <input type="checkbox" name="yearSelection" value="all" style="display: none" />
                    ALL
                </label>
            </div>
            <div class="col-xs-2 clickable text-center btn-group" data-toggle="buttons"
                 style="padding-bottom:20px"
                 ng-repeat="year in years"  ng-click="checkYearSelection(this)">
                <%--<button type="button" class="btn btn-primary btn-block" ng-click="selectYear(year)">--%>
                    <%--{{year}}--%>
                <%--</button>--%>
                    <label class="btn btn-info btn-block" ng-class="{'btn-warning': isSelected(year)}">
                        <div class="itemcontent">
                            <input type="checkbox" name="yearSelection" value="{{year}}" style="display: none" />
                            <span>{{year}}</span>
                        </div>
                    </label>
            </div>
            <div class="col-xs-1 text-center">
                <span class="glyphicon glyphicon-chevron-right"></span>
            </div>
        </div>
        <div class="col-xs-12 text-center">
            YEAR SELECTION: {{getYearSelection()}}
        </div>
    </div>

    <!-- Month -->
    <%--<div class="col-xs-12 col-sm-12 text-left" style="padding-bottom:10px">--%>
    <%--<span class="aside-filter-item">Month:</span>--%>
    <%--</div>--%>
    <%--<div class="col-xs-3 col-sm-2 col-md-1 clickable text-left" style="padding-bottom:20px"--%>
    <%--ng-repeat="availableImport in availableImports">--%>
    <%--<button class="btn  btn-primary btn-month clickable"--%>
    <%--ng-disabled="!availableImport.available"--%>
    <%--ng-click="loadMovementsForBankAccount(availableImport.month)">--%>
    <%--{{availableImport.month.displayName.substr(0,3)}}--%>
    <%--</button>--%>
    <%--</div>--%>
    <%--</div>--%>

    <!-- DESKTOP -->
    <div class="col-lg-12 hidden-xs hidden-sm hidden-md">
        <table border="0" width="100%">
            <thead>
            <tr>
                <th class="panel-heading text-center">
                    <span>Year</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Month</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Work Days</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Worked Days</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Transaction Date</span>
                </th>
                <th class="panel-heading text-right">
                    <span>Transaction Amount</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Daily Rate</span>
                </th>
                <th class="panel-heading text-center">
                    <span>VAT</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Invoice Number</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Invoice Date</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Actions</span>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="invoicePayment in invoicePayments | orderBy : 'movement.date' : true"
                ng-class="{
        'panel-danger' : missingInfo(invoicePayment),
        'panel-warning': differentAmount(invoicePayment) || (differentDays(invoicePayment) && !invoiceIssued(invoicePayment)),
        'panel-info'   : (allTheSame(invoicePayment) && !invoiceIssued(invoicePayment)) || hasChanged(invoicePayment.invoices[0], invoicePayment.form),
        'panel-success': invoiceIssued(invoicePayment) && !hasChanged(invoicePayment.invoices[0], invoicePayment.form)
        }" >
                <td class="panel-heading text-center">
                    <input class="form-control input-lg" type="text" ng-model="invoicePayment.form.year" ng-change="calculateWorkDays(invoicePayment);" size="4" maxlength="4"/>
                </td>
                <td class="panel-heading text-center">
                    <select class="form-control input-lg text-center" ng-model="invoicePayment.form.month" ng-change="calculateWorkDays(invoicePayment);updateMonthYearOnPreviousMovements(invoicePayment);" ng-options="m.displayName.substring(0,3) for m in months"></select>
                </td>
                <td class="panel-heading text-center invoice-item">
                    <span>{{invoicePayment.form.workDays}}</span>
                </td>
                <td class="panel-heading text-center">
                    <input class="form-control input-lg" type="text" ng-model="invoicePayment.form.days" size="4" ng-change="calculateAmount(invoicePayment.form, invoicePayment.form.vatRate);"/>
                </td>
                <td class="panel-heading text-center invoice-item">
                    <span>{{invoicePayment.movement.date | date:'dd MMM yyyy'}}</span>
                </td>
                <td class="panel-heading text-right invoice-item">
                    <span ng-hide="differentAmount(invoicePayment)" >{{invoicePayment.movement.formattedAmount}}</span>
                    <span ng-show="differentAmount(invoicePayment)" style="text-decoration: line-through; color: red">{{invoicePayment.movement.formattedAmount}}</span>
                    <span ng-show="differentAmount(invoicePayment)">{{invoicePayment.form.newTotal}}</span>
                </td>
                <td class="panel-heading text-center">
                    <input class="form-control input-lg" type="text" ng-model="invoicePayment.form.dailyRate" size="3" ng-change="calculateAmount(invoicePayment.form);updateDailyRate(invoicePayment);touch(invoicePayment)"/>
                </td>
                <td class="panel-heading text-center">
                    <input class="form-control input-lg" type="text" ng-model="invoicePayment.form.vatRate" size="2" ng-change="calculateAmount(invoicePayment.form)"/>
                </td>
                <td class="panel-heading text-center">
                    <%--<input class="form-control input-lg" type="text" ng-model="invoicePayment.form.vatRate" size="2" ng-change="calculateAmount(invoicePayment.form)"/>--%>
                    <input class="form-control input-lg" type="text" ng-model="invoicePayment.form.number" size="7"/>
                </td>
                <td class="panel-heading text-center invoice-item">
                    <input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                           datepicker-popup="dd-MMMM-yyyy" ng-model="invoicePayment.form.date" is-open="opened16" min="2000-01-01" max="'2050-12-31'"
                           datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
                           required="required" ng-click="open($event,'opened16')"
                           ng-change="setDefaultTime(inputSearchForm.endDate);"/>
                </td>
                <td class="panel-heading text-center">
                    <button ng-class="{'disabled': !invoicePayment.form.dailyRate}" class="btn btn-default" ng-click="calculateDays(invoicePayment)">Calc. Days</button>
                    <button ng-class="{'disabled': !hasChanged(invoicePayment.invoices[0], invoicePayment.form) && !differentAmount(invoicePayment)}" class="btn btn-primary" ng-click="saveInvoicePayment(invoicePayment)">Save</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- MOBILE -->
    <div class="col-xs-12 hidden-lg" style="padding-left: 0; padding-right: 0">
        <table border="0" width="100%">
            <thead>
            <tr>
                <th class="panel-heading text-center">
                    <span>Month / Year</span>
                </th>
                <th class="panel-heading text-center">
                    <span>Worked Days / Total</span>
                </th>
                <th class="panel-heading text-center hidden-xs">
                    <span>Transaction Date</span>
                </th>
                <th class="panel-heading text-right">
                    <span>Transaction Amount</span>
                </th>
                <th class="panel-heading text-right">
                    <span>Daily Rate</span>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="invoicePayment in invoicePayments | orderBy : 'movement.date' : true"
                ng-class="{
        'panel-danger' : missingInfo(invoicePayment),
        'panel-warning': differentAmount(invoicePayment) || (differentDays(invoicePayment) && !invoiceIssued(invoicePayment)),
        'panel-info'   : (allTheSame(invoicePayment) && !invoiceIssued(invoicePayment)) || hasChanged(invoicePayment.invoices[0], invoicePayment.form),
        'panel-success': invoiceIssued(invoicePayment) && !hasChanged(invoicePayment.invoices[0], invoicePayment.form)
        }" >
                <td class="panel-heading text-center invoice-item">
                    <span ng-show="invoicePayment.form.month != null">{{invoicePayment.form.month.displayName.substring(0,3)}} {{invoicePayment.form.year}}</span>
                    <span ng-hide="invoicePayment.form.month != null">-</span>
                </td>
                <td class="panel-heading text-center invoice-item">
                    <div ng-show="invoicePayment.form.days || invoicePayment.form.workDays">
                        <span ng-class="{'different-days': differentDays(invoicePayment)}">{{invoicePayment.form.days}}</span>
                        <span>/</span>
                        <span>{{invoicePayment.form.workDays}}</span>
                    </div>
                    <div ng-hide="invoicePayment.form.days || invoicePayment.form.workDays">
                        <span>-</span>
                    </div>
                </td>
                <td class="panel-heading text-center invoice-item hidden-xs">
                    <span>{{invoicePayment.movement.date | date:'dd MMM yyyy'}}</span>
                </td>
                <td class="panel-heading text-right invoice-item">
                    <span>{{invoicePayment.movement.formattedAmount}}</span>
                </td>
                <td class="panel-heading text-right invoice-item">
                    <span ng-show="invoicePayment.form.dailyRate">{{invoicePayment.form.dailyRate}}</span>
                    <span ng-hide="invoicePayment.form.dailyRate">-</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

</div>
