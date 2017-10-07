<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="loading-background" class="col-sm-12" ng-show="loading" ng-cloak>
    <img width="52" src="${resourcePath}/images/loader.gif" class="ajax-loader"/>
</div>

<div id="msg-divs" class="col-xs-12">
    <div id="infoMsgDiv" class="col-xs-12 col-sm-12 panel-success" style="padding-bottom:20px" ng-show="infoMsg != ''"
         ng-repeat="infoMsg in infoMsgs">
        <span class="panel-heading" ng-init="infoMsg = ''">{{infoMsg}}</span>
    </div>
    <div id="warnMsgDiv" class="col-xs-12 col-sm-12 panel-danger" style="padding-bottom:20px" ng-show="warnMsg != ''"
         ng-repeat="warnMsg in warnMsgs">
        <span class="panel-heading" ng-init="warnMsg = ''">{{warnMsg}}</span>
    </div>
</div>
<div class="col-xs-12 col-sm-12">
    <div class="bank-account-import">
        <!-- Bank Account -->
        <div class="col-xs-12 text-left" style="padding-bottom:10px">
            <span>Bank Account:</span>
            <span>
                <select style="font-size:1.2em;color:#339;background-color:lightyellow" class="form-control input-sm"
                        ng-model="importForm.bankAccount"
                        ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"></select>
            </span>
        </div>

        <!-- Import Options -->
        <div class="col-xs-12 text-left">
            <div class="col-xs-12 text-left" style="padding-bottom:10px" ng-init="importType='text'">
                <label class="radio-inline"> <input class="form-inline" type="radio" ng-model="importType" value="text">Import from Text</input><br/> </label>
                <label class="radio-inline"> <input class="form-inline" type="radio" ng-model="importType" value="db">Import from DB</input> </label>
            </div>
        </div>

        <!-- Import Option: Import Text-->
        <div class="col-xs-12 text-left" ng-show="importType=='text'">
            <div style="padding-bottom:20px">
                <span class="aside-filter-item">Paste movements here:</span>
				<textarea style="width:100%;font-size:1.2em;color:#339;background-color:lightyellow" rows="8"
                          ng-model="importForm.payload"
                          name="payload"></textarea>
            </div>
            <div class="text-right" style="padding-bottom:20px">
                <button id="btnParseMovements" class="btn btn-primary btn-sm"
                        ng-click="parseMovementsForBankAccount()">
                    Parse Text
                </button>
            </div>
        </div>

        <!-- Import Option: Import from DB -->
        <div ng-show="importType=='db'">
            <!-- Year -->
            <div class="col-xs-12 col-sm-12 text-left" style="padding-bottom:10px">
                <span class="aside-filter-item">Year:</span>
            </div>
            <div class="col-xs-12 col-sm-12 text-left" style="padding-bottom:10px">
                <div class="col-xs-3 col-sm-2 col-md-1 clickable text-left" style="padding-bottom:20px;padding-left: 0px;padding-right: 0px"
                     ng-repeat="year in years" >
                    <button class="btn  btn-primary btn-year clickable"
                            ng-click="searchAvailableImports(year)">
                        {{year}}
                    </button>
                </div>
            </div>

            <!-- Month -->
            <div class="col-xs-12 col-sm-12 text-left" style="padding-bottom:10px">
                <span class="aside-filter-item">Month:</span>
            </div>
            <div class="col-xs-3 col-sm-2 col-md-1 clickable text-left" style="padding-bottom:20px" ng-repeat="availableImport in availableImports" >
                <button class="btn  btn-primary btn-month clickable"
                        ng-disabled="!availableImport.available"
                        ng-click="loadMovementsForBankAccount(availableImport.month)">
                    {{availableImport.month.displayName.substr(0,3)}}
                </button>
            </div>
        </div>
    </div>

    <div class="bank-account-import-movements" style="padding-bottom:20px">

        <!-- Main Loop on Movements to import -->
        <%--
        <div ng-show="importMovements" ng-repeat="importMov in importMovements | orderBy:['date','pk'] | filter:movementFilter">

            <!-- Movement to import -->
            <div class="col-xs-12">
                <div class="col-xs-2" style="padding-bottom:20px">
                    <img class="icon" src="${resourcePath}/images/expand.png"
                         ng-click="toggleExpandMovement(importMov)" width="20"
                         ng-hide="importMov.expanded"></img>
                    <img class="icon" src="${resourcePath}/images/collapse.png"
                         ng-click="toggleExpandMovement(importMov)" width="20"
                         ng-show="importMov.expanded"></img>
                    <span ng-show="(!importMov.bankTransfer) && importMov.selectedExistingMov==null || importMov.selectedExistingMov==0">NEW</span>
                    <span ng-show="(!importMov.bankTransfer) && importMov.selectedExistingMov!=null && importMov.selectedExistingMov!=0">UPD</span>
                    <span ng-show="importMov.bankTransfer">TRA</span>
                </div>

                <div class="col-xs-6" ng-class="{'disabled': importMov.skip}">
                    <span ng-hide="importMov.editMode">{{importMov.importedBankAccountMovement.description}}</span>
                    <img ng-hide="importMov.editMode" class="icon" src="${resourcePath}/images/edit-2-trans.png"
                         ng-click='importMov.newDescription=importMov.importedBankAccountMovement.description;toggleEditMode(importMov)'></img>
                    <input ng-show="importMov.editMode" type="text" ng-model="importMov.newDescription" size="70"/>
                    <img ng-show="importMov.editMode" class="icon" src="${resourcePath}/images/confirm.jpg"
                         ng-click='confirmNewDescription(importMov.importedBankAccountMovement, importMov.newDescription);toggleEditMode(importMov)'></img>
                    <img ng-show="importMov.editMode" class="icon" src="${resourcePath}/images/cancel.jpg"
                         ng-click='toggleEditMode(importMov)'></img>
                </div>

                <div class="col-xs-4" ng-class="{'disabled': importMov.skip}">
                    <span class="col_amount" ng-class="{'disabled': importMov.skip}"> {{importMov.importedBankAccountMovement.date | date : "dd MMM yy"}}</span>
                    <br/>
                <span ng-class="{ 'disabled': importMov.skip, 'transfer-movement-class' : importMov.importedBankAccountMovement.description.indexOf('Transfer') == 0 , 'negative-amount' : importMov.importedBankAccountMovement.eu == 'U' , 'positive-amount' : importMov.importedBankAccountMovement.eu == 'E' }">
                    {{importMov.importedBankAccountMovement.formattedAmount}}
                </span>
                </div>
            </div>
        </div>
        --%>

        <table id="box-table-a">
        <%--<table class="table table-striped">--%>
            <!-- Table header -->
            <thead ng-show="importMovements">
                <tr>
                    <th colspan="3">
                        <span>MOVEMENTS TO IMPORT</span>
                    </th>
                </tr>
            </thead>
            <tbody ng-repeat="importMov in importMovements | orderBy:['date','pk'] | filter:movementFilter">

                <!-- Movement to be imported -->
                <tr ng-init="importMov.expanded=true">
                    <td colspan="3">
                        <div class="col-xs-2">
                            <img class="icon" src="${resourcePath}/images/expand.png"
                                 ng-click="toggleExpandMovement(importMov)" width="20"
                                 ng-hide="importMov.expanded"></img>
                            <img class="icon" src="${resourcePath}/images/collapse.png"
                                 ng-click="toggleExpandMovement(importMov)" width="20"
                                 ng-show="importMov.expanded"></img>
                            <span ng-show="isNew(importMov)">NEW</span>
                            <span ng-show="(!importMov.bankTransfer) && importMov.selectedExistingMov!=null && importMov.selectedExistingMov!=0">UPD</span>
                            <span ng-show="importMov.bankTransfer">TRA</span>
                        </div>
                        <div class="col-xs-8">
                            <%--<div class="col-xs-4">--%>
                            <div ng-class="{'disabled': importMov.skip}">
                                <span ng-hide="importMov.editMode">{{importMov.importedBankAccountMovement.description}}</span>
                                <img ng-hide="importMov.editMode" class="icon" src="${resourcePath}/images/edit-2-trans.png"
                                     ng-click='importMov.newDescription=importMov.importedBankAccountMovement.description;toggleEditMode(importMov, this)'></img>
                                <input ng-show="importMov.editMode" type="text" ng-model="importMov.newDescription" size="70"/>
                                <img ng-show="importMov.editMode" class="icon" src="${resourcePath}/images/confirm.jpg"
                                     ng-click='confirmNewDescription(importMov.importedBankAccountMovement, importMov.newDescription);toggleEditMode(importMov, this)'></img>
                                <img ng-show="importMov.editMode" class="icon" src="${resourcePath}/images/cancel.jpg"
                                     ng-click='toggleEditMode(importMov, this)'></img>
                            </div>
                        </div>
                        <div class="col-xs-2">
                            <div class="col-xs-12">
                                <span class="col_amount" ng-class="{'disabled': importMov.skip}"> {{importMov.importedBankAccountMovement.date | date : "dd MMM yy"}}</span>
                            </div>
                            <div class="col-xs-12">
                                <span ng-class="{ 'disabled': importMov.skip, 'transfer-movement-class' : importMov.importedBankAccountMovement.description.indexOf('Transfer') == 0 , 'negative-amount' : importMov.importedBankAccountMovement.eu == 'U' , 'positive-amount' : importMov.importedBankAccountMovement.eu == 'E' }">
                                    {{importMov.importedBankAccountMovement.formattedAmount}}
                                </span>
                            </div>
                        </div>
                    </td>
                </tr>

                <!-- skip / bank transfer / create new -->
                <tr ng-hide="importMov.expanded == false" ng-init="checkIfMovementToBeSkipped(importMov)">
                    <td colspan="3" style="background: #ffffff">
                        <div class="clickable life-checkbox col-xs-4 col-sm-3 col-md-2" style="display: inline-block">
                            <label class="checkbox-inline" style="overflow: visible;white-space: nowrap">
                                <input class="checkbox-inline" type="checkbox"
                                       ng-model="importMov.skip"/>
                                Skip
                            </label>
                        </div>
                        <div class="clickable life-checkbox col-xs-4 col-sm-3 col-md-2" style="display: inline-block">
                            <label class="checkbox-inline" style="overflow: visible;white-space: nowrap">
                                <input class="checkbox-inline" type="checkbox"
                                       ng-disabled="importMov.skip"
                                       ng-model="importMov.bankTransfer"/>
                                Bank Transfer
                            </label>
                        </div>
                        <div class="clickable life-checkbox col-xs-4 col-sm-3 col-md-2" style="display: inline-block">
                            <label class="checkbox-inline" style="overflow: visible;white-space: nowrap">
                                <input class="checkbox-inline" type="checkbox"
                                       ng-disabled="importMov.skip"
                                       ng-click="toggleCreateNewTransfer(importMov)"
                                       ng-model="importMov.createNew"/>
                                Create New
                            </label>
                        </div>
                    </td>
                </tr>

                <!-- bank transfer details -->
                <tr ng-show="importMov.bankTransfer">
                    <td colspan="3" style="background: #ffffff">
                        <div class="life-checkbox col-xs-2" style="display: inline-block">
                            <label style="text-overflow: clip;white-space: normal">
                                Bank Transfer Details (other bank account)
                            </label>
                        </div>
                        <div class="life-checkbox col-xs-5" style="display: inline-block">
                            <span>
                                <select style="font-size:0.8em;color:#339;background-color:lightyellow" class="form-control input-sm"
                                        ng-model="importMov.otherBankAccount"
                                        ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
                                >
                                </select>
                            </span>
                        </div>
                        <div class="life-checkbox col-xs-5" style="display: inline-block">
                            <div class="col-xs-12">
                                <input type="text" class="form-control ng-pristine ng-valid ng-valid-required"
                                       datepicker-popup="dd-MMMM-yyyy" ng-model="importMov.otherDate" is-open="opened12" min="2000-01-01" max="'2020-12-31'"
                                       datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
                                       required="required" ng-click="open($event,'opened12')"
                                       ng-change="setDefaultTime(importMov.otherDate)"  placeholder="date on other account"/>
                            </div>
                            <div class="col-xs-12">
                                <input type="text" class="form-control"
                                       ng-model="importMov.otherAmount" placeholder="amount on other account"/>
                            </div>
                        </div>
                    </td>
                </tr>

                <!-- matching movements -->
                <tr ng-repeat="matchingMov in importMov.matchingBankAccountMovements | orderBy:['date','pk']"
                    ng-hide="shouldHidePotentialMatchingMovement(importMov,matchingMov)"
                    class="matching-movement">
                    <td colspan="3" class="import-candidate">
                        <div class="col-xs-2" style="background: #f8fdff">
                            <div class="life-radio">
                                <label class="checkbox-inline">
                                    <input type="radio"
                                           ng-click="selectExistingMovement(importMov, matchingMov.pk)"
                                           ng-checked="isChecked(importMov, matchingMov.pk)"/>
                                    &lt;upd&gt;
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-8" style="background: #f8fdff; height:100%">
                            <span>{{matchingMov.description}} {{matchingMov.pk}}</span>
                            <img ng-show="isChecked(importMov, matchingMov.pk) && matchingMov.description != importMov.importedBankAccountMovement.description"
                                 class="icon" src="${resourcePath}/images/copy-paste-1.png"
                                 ng-click='copyPasteDescription(matchingMov,importMov.importedBankAccountMovement)'></img>
                        </div>
                        <div class="col-xs-2" style="background: #f8fdff">
                            <div class="col-xs-12">
                                <span class="col_amount">
                                    {{matchingMov.date | date : "dd MMM yy"}}
                                </span>
                            </div>
                            <div class="col-xs-12">
                                <span class="col_amount"
                                  ng-class="{ 'negative-amount' : matchingMov.eu == 'U', 'positive-amount' : matchingMov.eu == 'E' }">
                                    {{matchingMov.formattedAmount}}
                                </span>
                            </div>
                        </div>
                    </td>
                </tr>

                <!-- other movements -->
                <tr ng-repeat="matchingMov in importMov.otherBankAccountMovements | orderBy:['date','pk']"
                    ng-hide="shouldHidePotentialMatchingMovement(importMov,matchingMov)">
                    <td colspan="3" class="import-candidate col-xs-12">
                        <div class="col-xs-2" style="background: #f8fdff">
                            <div class="life-radio">
                                <label class="checkbox-inline">
                                    <input type="radio"
                                           ng-click="selectExistingMovement(importMov, matchingMov.pk)"
                                           ng-checked="isChecked(importMov, matchingMov.pk)"/>
                                    &lt;upd&gt;
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-8" style="background: #f8fdff; height:100%">
                            <span>{{matchingMov.description}} {{matchingMov.pk}}</span>
                            <img ng-show="isChecked(importMov, matchingMov.pk)" class="icon"
                                 src="${resourcePath}/images/copy-paste-1.png"
                                 ng-click='copyPasteDescription(matchingMov,importMov.importedBankAccountMovement)'></img>
                        </div>
                        <div class="col-xs-2" style="background: #f8fdff">
                            <div class="col-xs-12">
                                <span class="col_amount"> {{matchingMov.date | date : "dd MMM yy"}}</span>
                            </div>
                            <div class="col-xs-12">
                                <span class="col_amount"
                                      ng-class="{ 'negative-amount' : matchingMov.eu == 'U', 'positive-amount' : matchingMov.eu == 'E' }">
                                    {{matchingMov.formattedAmount}}
                                </span>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
            <thead ng-show="importMovements">
                <tr>
                    <th colspan="3">
                        <span>EXISTING UNMATCHED MOVEMENTS</span>
                    </th>
                </tr>
            </thead>
            <tbody ng-repeat="existingMov in existingMovements">
                <tr ng-hide="isSelectedAndUsed(existingMov)">
                    <td colspan="3" class="import-candidate">
                        <div class="col-xs-2">
                            <input type="checkbox"
                                   ng-model="existingMov.deleteOnSubmit"
                                   ng-disabled="deleteUnmatchedMovements || replaceAllMovements"></input>
                            {{existingMovementsMap[existingMov.pk].selectedFor}}
                            <span>&lt;del&gt;</span>
                        </div>
                        <div class="col-xs-8">
                            <div>
                                <span>{{existingMov.description}}</span>
                            </div>
                        </div>
                        <div class="col-xs-2">
                            <span class="col_amount"> {{existingMov.date | date : "dd MMM yy"}}</span>
                            <br/>
                                <span ng-class="{ 'negative-amount' : existingMov.eu == 'U' , 'positive-amount' : existingMov.eu == 'E' }">
                                    {{existingMov.formattedAmount}}
                                    </span>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <div ng-show="importMovements != null && importMovements.length > 0" style="float:right">
        <div class="clickable life-checkbox" style="display: inline-block">
            <a ng-click="markNew()">
                mark all unmatched as new
            </a>
        </div>

        <div class="clickable life-checkbox" style="display: inline-block">
            <label class="checkbox-inline" style="overflow: visible;white-space: nowrap">
                <input class="checkbox-inline" type="checkbox"
                       ng-model="replaceAllMovements"/>
                replace all
            </label>
        </div>

        <div class="clickable life-checkbox" style="display: inline-block">
            <label class="checkbox-inline" style="overflow: visible;white-space: nowrap">
                <input class="checkbox-inline" type="checkbox"
                       ng-model="deleteUnmatchedMovements"/>
                delete all unmatched movements
            </label>
        </div>

        <%--<span> <input type="checkbox" ng-model="replaceAllMovements"/> </span>--%>
        <%--<span> replace all </span>--%>

        <%--<span> <input type="checkbox" ng-model="deleteUnmatchedMovements"/> </span>--%>
        <%--<span> delete all unmatched movements </span>--%>

        <button id="confirmImport" class="btn btn-success btn-sm"
                ng-click="confirmImport()">Submit
        </button>
    </div>
</div>
