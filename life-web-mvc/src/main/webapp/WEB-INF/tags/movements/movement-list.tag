

<!-- List By Movement -->
<table ng-show="movements" id="box-table-a" summary="Bank Account Movements">
    <thead>
    <tr>
        <th colspan="4">
            <div class="row">
                <div class="btn-toolbar hidden-xs hidden-sm col-md-3 col-lg-2 text-center">
                    <div class="btn-group col_header col_action_header">
                        <span>Actions</span>
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-4 col-lg-4">
                    <div class="col-xs-12 col_account_header" style="padding-left: 0px;padding-right: 0px">
                        <span class="col_header col_account_header">Account</span>
                    </div>
                    <div class="col-xs-12 col_header col_description" style="padding-left: 0px;padding-right: 0px">
                        <span class="col_header col_description_header">Description</span>
                    </div>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-5 col-lg-6">
                    <div class="col-xs-12 col-sm-6" style="padding-left: 0px;padding-right: 0px">
                        <div class="col-xs-12 col_header col_date_header" style="padding-left: 0px;padding-right: 0px">
                            <span>Date</span>
                        </div>
                        <div class="col-xs-12 col_header col_amount_header" style="padding-left: 0px;padding-right: 0px">
                            <span>Amount</span>
                        </div>
                    </div>
                    <div class="hidden-xs col-sm-6" style="padding-left: 0px;padding-right: 0px">
                        <div class="col-xs-12 col_header col_balance_after_header" style="padding-left: 0px;padding-right: 0px">
                            <span>Balance</span>
                        </div>
                        <div class="col-sm-12 hidden-xs col_header col_overall_balance_after_header" style="padding-left: 0px;padding-right: 0px">
                            <span>Overall Balance</span>
                        </div>
                    </div>
                </div>
            </div>
        </th>
    </tr>
    </thead>
    <tbody>
    <!-- MOVEMENTS -->
    <%--<tr ng-hide="movementHideBankTransfers && movement.description.indexOf('Transfer') == 0"--%>
        <%--ng-repeat="movement in movements | orderBy : 'date' : true | filter:movementFilter">--%>
    <tr ng-hide="(movementHideBankTransfers && movement.description.indexOf('Transfer') == 0) || (movementHideAlreadyCategorised && movement.category != null)"
        ng-repeat="movement in movements | filter:movementFilter">
        <td colspan="4" ng-class="{'disabled': searchValid == false}">
            <div class="row mov_row taphover" ng-class="{'uncategorised': movementHighlightUncategorised && movement.category==null}">
                <div id="movementButtonGroup" class="btn-toolbar hidden-xs hidden-sm text-center col-md-3 col-lg-2">
                    <div class="btn-group col-md-12" style="padding-left: 0px; padding-right: 0px;">
                        <div class="mov-btn-item life-checkbox col-md-4" style="padding-left: 5px">
                            <label class="checkbox-inline">
                                <input type="checkbox" ng-model="movement.checked"
                                       style="margin-top: 9px; margin-left: -15px"
                                ></input>
                            </label>
                        </div>
                        <div class="mov-btn-item col-md-4" style="padding-left: 5px">
                            <%--<img class="icon" src="${resourcePath}/images/edit.jpg"--%>
                                 <%--style="margin-top: 20px"--%>
                                 <%--ng-click='toggleEditMode(movement, $event)'></img>--%>
                            <span class="glyphicon glyphicon-edit chevron"
                                  style="margin-top: 22px; margin-left: 12px"
                                  ng-click='toggleEditMode(movement, $event)'
                            ></span>
                        </div>
                        <div class="mov-btn-item col-md-4" style="padding-left: 5px">
                            <%--<img class="icon" src="${resourcePath}/images/delete.jpg"--%>
                                 <%--style="margin-top: 20px"--%>
                                 <%--ng-click="deleteMovement(movement)"></img>--%>
                            <span class="glyphicon glyphicon-remove chevron"
                                  style="margin-top: 22px; margin-left: 12px"
                                  ng-click='deleteMovement(movement)'
                            ></span>
                        </div>
                    </div>
                </div>
                <div class="col-xs-8 col-sm-6 col-md-4 col-lg-4">
                    <div class="col-xs-12 col_mov col_account" style="padding-left: 0px;padding-right: 0px">
                        <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0, 'disabled': searchValid == false }">
                            {{movement.bankAccount.displayName}}</span>
                        </div>
                    <div class="col-xs-12 col_mov col_description taphover" style="padding-left: 0px;padding-right: 0px" ng-click="shouldToggleEditMode(movement, $event)">
                        <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0, 'disabled': searchValid == false }">
                            {{movement.description}}
                        </span>
                    </div>
                </div>
                <div class="col-xs-4 col-sm-6 col-md-5 col-lg-6">
                    <div class="col-xs-12 col-sm-6" style="padding-left: 0px;padding-right: 0px">
                        <div class="col-xs-12 col_mov col_date" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0, 'disabled': searchValid == false }">
                                {{movement.date | date:'dd MMM yyyy'}}
                            </span>
                        </div>
                        <div class="col-xs-12 col_mov col_amount" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0 , 'negative-amount' : movement.eu == 'U' , 'positive-amount' : movement.eu == 'E', 'disabled': searchValid == false }">
                                {{movement.formattedAmount}}
                            </span>
                        </div>
                    </div>
                    <div class="col-sm-12 col-sm-6" style="padding-left: 0px;padding-right: 0px; table-layout: fixed">
                        <div class="col-xs-12 col_mov col_balance_after" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0, 'disabled': searchValid == false }">
                            {{movement.formattedBalanceAfter}}
                            </span>
                        </div>
                        <div class="col-sm-12 col_mov col_overall_balance_after" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{ 'transfer-movement-class' : movement.description.indexOf('Transfer') == 0, 'disabled': searchValid == false }">
                                {{movement.formattedOverallBalanceAfter}}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
    <tr ng-repeat="accountTotal in accountTotals | orderBy : 'date' : true">
        <th colspan="4">
            <div class="row summary">
                <div class="btn-toolbar hidden-xs hidden-sm col-md-2 text-center">
                </div>
                <div class="col-xs-4 col-sm-4 col-md-3">
                    <div class="col-xs-12 col_mov col_account" style="padding-left: 0px;padding-right: 0px; height: 100%;display: table-cell;vertical-align: middle">
                        <span ng-class="{'disabled': searchValid == false}">{{accountTotal.account.displayName}}</span>
                    </div>
                </div>
                <div class="col-xs-8 col-sm-8 col-md-7">
                    <div class="col-xs-6" style="padding-left: 0px;padding-right: 0px">
                        <div class="col-xs-12 col_mov col_amount" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{'disabled': searchValid == false}">IN</span>
                        </div>
                        <div class="col-xs-12 col_mov col_amount" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{'disabled': searchValid == false}">OUT</span>
                        </div>
                    </div>
                    <div class="col-xs-6" style="padding-left: 0px;padding-right: 0px">
                        <div class="col-xs-12 col_mov col_amount positive-amount" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{'disabled': searchValid == false}">{{accountTotal.formattedAmountIn}}</span>
                        </div>
                        <div class="col-xs-12 col_mov col_amount negative-amount" style="padding-left: 0px;padding-right: 0px">
                            <span ng-class="{'disabled': searchValid == false}">{{accountTotal.formattedAmountOut}}</span>
                        </div>
                    </div>
                </div>
            </div>
        </th>
    </tr>
    </tbody>
</table>
