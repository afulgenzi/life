<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="movements" tagdir="/WEB-INF/tags/movements"%>

<movements:movement-overlays/>

<div class="hidden-xs hidden-sm">
    <div class="tabbable">

        <div class="hidden-xs hidden-sm col-md-9" ng-show="movements.length > 0" style="padding-left: 0px">
            <div>
                <ul class="nav nav-pills" style="margin-bottom: 20px">
                    <li id="byListView" style="float: left; text-align: center" class="active">
                        <a id="byListViewLink" data-toggle="pill" href="#byListPane">List</a>
                    </li>
                    <li id="byAccountView" style="float: left; text-align: center">
                        <a data-toggle="pill" href="#byAccountPane">By Account</a>
                    </li>
                    <li id="byCategoryView" style="float: left; text-align: center" >
                        <a data-toggle="pill" href="#byCategoryPane" ng-click="loadMovementsAsCategoryTree('movementsByCategoryTree')">By Category</a>
                    </li>
                </ul>
            </div>
        </div>

        <div id="create-new-movement-button" class="hidden-xs hidden-sm" ng-class="{'col-md-3': movements.length > 0, 'col-md-12': movements.length==0}" style="padding-right: 0px">
            <ul class="nav nav-pills" style="margin-bottom: 20px">
                <li style="float: right; text-align: right" class="active"><a data-toggle="pill" ng-click="toggleAddMode()">Create New</a></li>
            </ul>
        </div>

        <div ng-show="movements" class="tab-content col-md-12" style="padding-left: 0px;padding-right: 0px">
            <div id="byListPane" class="tab-pane active">
                <movements:movement-list/>
            </div>
            <div id="byAccountPane" class="tab-pane">
                <!-- List By BankAccount -->
                <table border="1" id="table-by-account" ng-show="splitView && getCheckedBankAccounts().length > 1"
                       summary="Bank Account Movements" class="table table-hover table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th class="col_date" scope="col">Date</th>
                        <th class="col_account" scope="col" ng-repeat="acc in getCheckedBankAccounts()">
                            {{acc.displayName}}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- MOVEMENTS -->
                    <tr ng-repeat="movement in getValidList(movements) | orderBy: 'date' : true | filter:movementFilter"
                        ng-class="{ 'danger' : movement.balanceAfter < 0, 'warning' : movement.eu == 'U', 'success' : movement.eu == 'E' }">
                        <td class="col_date">
                            <span class="center">{{movement.date | date:'dd MMM yyyy'}}</span>
                        </td>
                        <td ng-repeat="acc in getCheckedBankAccounts()">

                            <img class="disabled" src="${resourcePath}/images/transfer-from.jpg" width="20" height="20"
                                 ng-show="isTransferSourceMovement(movement) && isMovementOfAccount(movement, acc)">
                            <img class="disabled" src="${resourcePath}/images/transfer-to.jpg" width="20" height="20"
                                 ng-show="isMovementTransferRelatedToAccount(movement, acc)">

                            <!-- Movement of current account -->
                            <span class="center" title="{{movement.description}}"
                                  ng-show="isMovementOfAccount(movement, acc) && isNotTransferTargetMovement(movement)">{{movement.formattedAmount}}<br>{{movement.formattedBalanceAfter}}<br><snap
                                    ng-hide="isTransferMovement(movement)"><b>{{movement.formattedOverallBalanceAfter}}</b></snap></span>

                            <!-- Transfer Target Movement of current account -->
                            <span class="center"
                                  title="{{getMovementFromList(movements, movement.transferTargetMovement.pk).description}}"
                                  ng-show="isMovementTransferRelatedToAccount(movement, acc)">{{getMovementFromList(movements, movement.transferTargetMovement.pk).formattedAmount}}<br>{{getMovementFromList(movements, movement.transferTargetMovement.pk).formattedBalanceAfter}}</span>

                            <!-- Unrelated to current account -->
                            <span class="center" class="light-view-class"
                                  ng-show="isMovementUnrelatedToAccount(movement, acc)">{{getBankAccountBalance(movement, acc)}}</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- Search Existing Category -->
            <div id="byCategoryPane" class="tab-pane fade">
                <div class="row">
                    <div class="col-sm-12 text-left" style="padding-bottom: 20px">
                        <div id="movementsByCategoryTree" style="height: 520px; overflow-y: scroll"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div ng-show="movements" class="visible-xs visible-sm">
    <movements:movement-list/>
</div>