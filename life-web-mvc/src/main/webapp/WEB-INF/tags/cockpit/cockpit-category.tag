<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="category-overlays">
    <%--<!-- Extend -->--%>
    <div id="extend-category-overlay-box" class="">
        <div class="form-row col-xs-12 text-left" style="padding-left: 0; padding-right: 0;">
            <div>Bank account</div>
            <div><select ng-model="categoryExtension.bankAccount"
                         ng-options="ba.displayName + ' | ' + ba.accountNumber + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
                         class="form-control"></select></div>
        </div>
        <div class="form-row col-xs-12 text-left" style="padding-left: 0; padding-right: 0;">
            <div>Description</div>
            <div><input size="80" ng-model="categoryExtension.description" class="form-control"/></div>
        </div>
        <div class="form-row col-xs-12 text-left" style="padding-left: 0; padding-right: 0;">
            <div>Amount</div>
            <div><input ng-model="categoryExtension.amount" class="form-control"/></div>
        </div>
        <div class="form-row col-xs-12 text-left" style="padding-left: 0; padding-right: 0;">
            <div>Extend Until</div>
            <div>
                <input type="text"
                       class="form-control ng-pristine ng-valid ng-valid-required"
                       datepicker-popup="dd-MMMM-yyyy" ng-model="categoryExtension.untilDate" is-open="opened13"
                       min="2000-01-01" max="'2099-12-31'"
                       datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                       close-text="Close"
                       required="required" ng-click="open($event,'opened13')"
                       ng-change="setDefaultTime(categoryExtension.untilDate);">
            </div>
        </div>
        <%--<div class="modal-footer">--%>
        <div class="form-row col-xs-12 text-right" style="padding-left: 0; padding-right: 0;">
            <button class="btn btn-primary" ng-click="confirmExtension(categoryExtension)">Extend</button>
            <button class="btn btn-warning" ng-click="toggleExtendMode(categoryExtension.categoryAlert, this)">Cancel</button>
        </div>
    </div>

</div>

<div>
    <!-- SEARCH -->
    <%--<div class="col-xs-12" style="padding-bottom:10px">--%>
        <%--<button class="btn btn-primary btn-year clickable"--%>
                <%--ng-click="searchCategoryAlerts()">--%>
            <%--Search--%>
        <%--</button>--%>
    <%--</div>--%>

    <!-- LEGEND -->
    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-bottom: 20px">
        <div class="text-center">
            <div class="panel-war col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>less than 1 month</span>
                </div>
            </div>
            <div class="panel-danger col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>1 - 3 months</span>
                </div>
            </div>
            <div class="panel-warning col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>3 - 6 months</span>
                </div>
            </div>
            <div class="panel-info col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>6 - 12 months</span>
                </div>
            </div>
            <div class="panel-success col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>more than 12 months</span>
                </div>
            </div>
            <div class="panel-default col-xs-12 col-sm-6 col-md-4 col-lg-2" style="padding-top: 20px;">
                <div class="panel-heading">
                    <span>Complete</span>
                </div>
            </div>
        </div>
    </div>

    <!-- ALERTS -->
    <div class="col-xs-12 cockpit-item-group" style="padding-left: 0; padding-right: 0">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 cockpit-item" ng-repeat="catAlert in categoryAlerts | orderBy:'alertLevel'"
             style="padding-bottom: 20px;">
            <div class="text-center" ng-class="{
                'panel-war'     : catAlert.alertLevel == 0,
                'panel-danger'  : catAlert.alertLevel == 1,
                'panel-warning' : catAlert.alertLevel == 2,
                'panel-info'    : catAlert.alertLevel == 3,
                'panel-success' : catAlert.alertLevel == 4,
                'panel-default' : catAlert.alertLevel == 5
                }" >
                <%--<div class="panel-heading">--%>
                    <%--<span>({{$index + 1}})</span>--%>
                <%--</div>--%>
                <div class="panel-heading cockpit-description">
                    <span>{{catAlert.category.path}}</span>
                </div>
                <%--<div class="panel-heading">--%>
                    <%--<span>Alert Level: {{catAlert.alertLevel}}</span>--%>
                <%--</div>--%>
                <div class="panel-heading">
                    <span>Every {{catAlert.category.frequency.frequencyInterval}} {{catAlert.category.frequency.frequencyType.intervalUnit}}</span>
                </div>
                <div class="panel-heading" style="height: 75px;">
                    <div>
                        <span>Latest transaction:</span>
                    </div>
                    <div ng-show="catAlert.latestMovement == null">
                        <span>&lt;No Transactions&gt;</span>
                    </div>
                    <div ng-show="catAlert.latestMovement != null" class="cockpit-description">
                        <span>{{catAlert.latestMovement.description}}</span>
                    </div>
                    <div ng-show="catAlert.latestMovement != null">
                        <span>{{catAlert.latestMovement.date | date:'dd MMM yyyy'}}</span>
                        -
                        <span>{{catAlert.latestMovement.formattedAmount}}</span>
                        <span style="position: absolute;right: 23px"><a ng-click="toggleExtendMode(catAlert, $event)">extend</a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
