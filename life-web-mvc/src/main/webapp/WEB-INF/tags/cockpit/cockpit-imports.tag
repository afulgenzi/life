<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="infoMsgDiv" class="col-sm-12 panel-success" style="padding-bottom:20px" ng-show="infoMsg != ''">
    <span class="panel-heading" ng-init="infoMsg = ''">{{infoMsg}}</span>
</div>
<div id="warnMsgDiv" class="col-sm-12 panel-danger" style="padding-bottom:20px" ng-show="warnMsg != ''">
    <span class="panel-heading" ng-init="warnMsg = ''">{{warnMsg}}</span>
</div>

<div class="bank-account-import">

    <!-- Bank Account / Year selectors -->
    <div>
        <!-- Bank Account -->
        <div class="col-xs-12 col-sm-6 col-md-4">
            <div class="text-left" style="padding-bottom:10px">
                <span>Bank Account:</span>
            </div>
            <%--<div class="col-xs-12 col-sm-12 text-left" style="padding-bottom:10px">--%>
            <%--<select style="font-size:1.2em;color:#339;background-color:lightyellow"--%>
            <%--class="form-control input-sm clickable"--%>
            <%--ng-model="importForm.bankAccount"--%>
            <%--ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'">--%>
            <%--</select>--%>
            <%--</div>--%>
            <div class="text-left" style="margin-bottom: 20px">
                <div class="row" ng-repeat="ba in bankAccounts | orderBy:['displayName']">
                    <div class="col-xs-12 clickable life-radio" style="margin-top: -15px">
                        <label class="radio-inline">
                            <input class="radio-inline" name="bankAccount" type="radio"
                                   ng-model="inputForm.bankAccount"
                                   ng-value="ba"
                                   ng-change="selectedBankAccount(ba)"
                                   style="margin-top: 7px; margin-left: -15px"
                            />
                            <span class="account-radio-label">{{ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code + '| ' + (inputForm.bankAccount && inputForm.bankAccount.displayName || '')}}</span>
                        </label>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px">
                    <div class="col-sm-12 text-center">
					<span class="aside-filter-item">
					<a ng-click="selectAllBankAccounts(true)">Select ALL {{importForm.bankAccount.displayName}}</a>
					<span> | </></span>
                        <a ng-click="selectAllBankAccounts(false)">Select None</a></span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Year -->
        <div class="col-xs-12 col-sm-6 col-md-4">
            <%--<div class="col-xs-12 col-sm-6 text-left" style="padding-bottom:20px">--%>
            <%--<span class="aside-filter-item">Year:</span>--%>
            <%--<span>--%>
            <%--<select style="font-size:1.2em;color:#339;background-color:lightyellow"--%>
            <%--class="form-control input-sm clickable"--%>
            <%--ng-model="importForm.year"--%>
            <%--ng-change="checkMatchingImportByMonth()"--%>
            <%--ng-options="year for year in years">--%>
            <%--</select>--%>
            <%--</span>--%>
            <%--</div>--%>
            <!-- Year -->
            <div class="text-center" style="padding-bottom:10px">
                <span class="aside-filter-item">Year:</span>
            </div>
            <div class="text-center" style="padding-bottom:10px">
                <div class="col-xs-4 col-sm-12 clickable text-center" style="padding-bottom:20px;padding-left: 0px;padding-right: 0px"
                     ng-repeat="year in years" >
                    <button class="btn  btn-primary btn-year clickable"
                            ng-click="checkMatchingImportByMonth(year)">
                        {{year}}
                    </button>
                </div>
            </div>
        </div>

        <!-- Month -->
        <div class="col-xs-12 col-sm-12 col-md-4">
            <%--<div ng-show="yearlyMatchesJson">--%>
                <div class="text-center" style="padding-bottom:10px">
                    <span class="aside-filter-item">Month:</span>
                </div>
                <div class="text-left" style="padding-bottom:10px">
                    <div class="col-xs-3 col-sm-2 col-md-3 text-center clickable"
                         ng-repeat="monthlyMatches in yearlyMatchesJson.monthlyMatches"
                         style="padding-bottom:20px">
                        <button class="btn btn-month text-center"
                                ng-class="{'btn-success' : monthlyMatches.status == 'MATCHED', 'btn-info' : monthlyMatches.status == 'PARTIALLY_MATCHED', 'btn-warning' : monthlyMatches.status == 'PARTIALLY_MATCHED_SOME_MISSING', 'btn-danger' : monthlyMatches.status == 'UNMATCHED', 'btn-default' : monthlyMatches.status == 'UNAVAILABLE'}"
                                ng-disabled="monthlyMatches.status == 'UNAVAILABLE' || !monthlyMatches.status"
                                ng-click="showMatchingImportPerMonth(monthlyMatches)">
                            {{monthlyMatches.month.displayName.substr(0,3)}}
                        </button>
                    </div>
                </div>
            <%--</div>--%>
        </div>
    </div>

    <!-- MatchingMovements -->
    <div ng-show="dailyMatchesForSelectedMonth.length">
        <!-- SHOW OPTIONS -->
        <div class="col-xs-12 col-sm-12" ng-init="showUnmatched = true" style="padding-bottom:20px">
            <div class="col-xs-12 col-sm-6 col-md-3 clickable life-checkbox">
                <label class="checkbox-inline"> <input class="checkbox-inline" type="checkbox"
                                                       ng-model="showAll"/> Show All </label>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3 clickable life-checkbox">
                <label class="checkbox-inline"> <input class="checkbox-inline" type="checkbox"
                                                       ng-disabled="showAll"
                                                       ng-model="showMatched"/> Show Matched </label>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3 clickable life-checkbox">
                <label class="checkbox-inline"> <input class="checkbox-inline" type="checkbox"
                                                       ng-disabled="showAll"
                                                       ng-model="showPartiallyMatched"/> Show Partially
                    Matched </label>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3 clickable life-checkbox">
                <label class="checkbox-inline"> <input class="checkbox-inline" type="checkbox"
                                                       ng-disabled="showAll"
                                                       ng-model="showUnmatched"/> Show Unmatched </label>
            </div>
        </div>
        <!-- TABLE HEADER -->
        <div class="col-xs-12 col-sm-12" style="padding-bottom:20px">
            <div class="panel-default">
                <div class="col-xs-5 col-sm-5 col-md-5 text-left panel-heading">
                    &lt;Date&gt;
                </div>
                <div class="col-xs-7 col-sm-7 col-md-7 text-left panel-heading"
                     style="text-overflow: ellipsis;white-space: nowrap; overflow: hidden">
                    &lt;Real Bank Account&gt;
                </div>
                <div class="col-xs-5 col-sm-5 col-md-5 text-left panel-heading">
                    &lt;Amount&gt;
                </div>
                <div class="col-xs-7 col-sm-7 col-md-7 text-left panel-heading"
                     style="text-overflow: ellipsis;white-space: nowrap; overflow: hidden">
                    &lt;Life&gt;
                </div>
            </div>
        </div>
        <!-- TABLE CONTENT (LIST) -->
        <div class="col-xs-12 col-sm-12" style="padding-bottom:20px"
             ng-show="shouldShowDailyMatch(dailyMatch.status)"
             ng-repeat="dailyMatch in dailyMatchesForSelectedMonth">
            <div ng-class="
                {
                'panel-success' : dailyMatch.status == 'MATCHED',
                'panel-info'    : dailyMatch.status == 'PARTIALLY_MATCHED',
                'panel-warning' : dailyMatch.status == 'PARTIALLY_MATCHED_SOME_MISSING',
                'panel-danger'  : dailyMatch.status == 'UNMATCHED', 'panel-default' : dailyMatch.status == 'UNAVAILABLE'
                }" >
                <div class="col-xs-5 col-sm-5 col-md-5 text-left panel-heading">
                    <span ng-class="{'negative-amount' : dailyMatch.eu == 'U' , 'positive-amount' : dailyMatch.eu == 'E'}">
                        {{dailyMatch.date | date:'dd MMM yyyy'}}
                    </span>
                </div>
                <div class="col-xs-7 col-sm-7 col-md-7 text-left panel-heading taphover cockpit-description">
                    <a ng-click="getMovementKey(dailyMatch.importMov)">{{dailyMatch.importMov &&
                        dailyMatch.importMov.description || '&nbsp;'}}</a>
                    <span ng-show="dailyMatch.importMov.movementKey"> (key: {{dailyMatch.importMov.movementKey}})</span>
                </div>
                <div class="col-xs-5 col-sm-5 col-md-5 text-left panel-heading">
                    <span ng-class="{'negative-amount' : dailyMatch.eu == 'U' , 'positive-amount' : dailyMatch.eu == 'E'}">
                        {{dailyMatch.formattedAmount}}
                    </span>
                </div>
                <div class="col-xs-7 col-sm-7 col-md-7 text-left panel-heading taphover cockpit-description">
                    <a ng-click="getMovementKey(dailyMatch.existingMov)">{{dailyMatch.existingMov &&
                        dailyMatch.existingMov.description || '&nbsp;'}}</a>
                    <span ng-show="dailyMatch.existingMov.movementKey"> (key: {{dailyMatch.existingMov.movementKey}})</span>
                </div>
            </div>
        </div>
    </div>
</div>
