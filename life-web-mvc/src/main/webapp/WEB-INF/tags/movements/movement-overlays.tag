<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="movements" tagdir="/WEB-INF/tags/movements" %>

<div id="overlay-background" class="col-sm-12">
</div>
<div id="loading-background" class="col-sm-12" ng-show="loading">
    <img width="52" src="${resourcePath}/images/loader.gif" class="ajax-loader"/>
</div>

<div id="movement-overlays">

    <%--<!-- Add Movement -->--%>
    <div id="add-movement-overlay-box" class="hidden-xs hidden-sm col-md-12" style="font-size: 16px">
        <div class="form-row col-xs-12" style="padding-top: 15px">
            <div>Bank account</div>
            <div><select ng-model="newMovement.movement.bankAccount"
                         ng-options="ba.displayName + ' | ' + ba.accountNumber + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts"
                         ng-change="setCurrencyOnBankAccountSelection(newMovement.movement, currencies)"
                         class="form-control input-lg"></select></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Currency</div>
            <div><select ng-model="newMovement.movement.currency"
                         ng-options="cu.description + ' | ' + cu.code + ' | ' + cu.abbreviation for cu in currencies"
                         class="form-control input-lg"></select></div>
        </div>
        <div class="form-row col-xs-12">
            <div>In/Out</div>
            <div>
                <select ng-model="newMovement.movement.eu" class="form-control input-lg">
                    <option value="E">In</option>
                    <option value="U">Out</option>
                </select>
            </div>
        </div>
        <div class="form-row col-xs-12">
            <div>Date</div>
            <div>
                <input type="text"
                       class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                       datepicker-popup="dd-MMMM-yyyy" ng-model="newMovement.movement.date" is-open="opened1"
                       min="2000-01-01" max="'2020-12-31'"
                       datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                       close-text="Close"
                       required="required" ng-click="open($event,'opened1')"
                       ng-change="setDefaultTime(newMovement.movement.date);">
            </div>
        </div>
        <div class="form-row col-xs-12">
            <div>Description</div>
            <div><input type="text" size="80" ng-model="newMovement.movement.description" class="form-control input-lg"/>
            </div>
        </div>
        <div class="form-row col-xs-12">
            <div>Amount</div>
            <div><input type="text" ng-model="newMovement.movement.amount" class="form-control input-lg"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Category</div>
            <div class="input-group" style="z-index: 0">
                <input class="form-control input-lg search-box" ng-model="newMovement.movement.category.title" ng-click="selectCategoryForNewMovement()" />
                <span class="input-group-btn">
                    <button ng-show="newMovement.movement.category != null" class="btn btn-default"
                            type="button" ng-click="newMovement.movement.category = null">
                        <span class="glyphicon glyphicon-remove" style="font-size: 2.2em"></span>
                    </button>
                    <button class="btn btn-default" type="button" ng-click="selectCategoryForNewMovement()">
                        <span class=" glyphicon glyphicon-search" style="font-size: 2.2em"></span>
                    </button>
                </span>
            </div>
        </div>
        <%--<div class="form-row col-xs-12">--%>
            <%--<div>Repeat</div>--%>
        <%--</div>--%>
        <div class="form-row col-xs-12">
            <div><hr/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>
                <div class="col-xs-6" style="padding-left: 0; padding-right: 0">Frequency Interval</div>
                <div class="col-xs-6" style="padding-left: 0; padding-right: 0">Frequency Type</div>
                <div class="col-xs-6" style="padding-left: 0; padding-right: 0">
                    <input class="form-control input-lg life-input"
                           ng-model="newMovement.frequency.frequencyInterval"/>
                </div>
                <div class="col-xs-6" style="padding-left: 0; padding-right: 0">
                    <select style="font-size:1.2em;color:#339;background-color:lightyellow"
                            class="form-control input-lg clickable"
                            ng-model="newMovement.frequency.frequencyType"
                            ng-options="ft.intervalUnit for ft in frequencyTypes | orderBy:'displayName'">
                    </select>
                </div>
            </div>
        </div>
        <div class="form-row col-xs-12">
            <div>Repeat Until</div>
            <div>
                <input type="text"
                       class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                       datepicker-popup="dd-MMMM-yyyy" ng-model="newMovement.untilDate"
                       is-open="opened2"
                       min="2000-01-01" max="'2099-12-31'"
                       datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                       close-text="Close"
                       required="required" ng-click="open($event,'opened2')"
                       ng-change="setDefaultTime(newMovement.untilDate);">
            </div>
        </div>
        <div class="form-row col-xs-12" style="text-align: right">
            <button class="btn btn-primary" ng-click="addMovementAndContinue()">Save and continue</button>
            <button class="btn btn-primary" ng-click="addMovement()">Save and close</button>
            <button class="btn btn-warning" ng-click="toggleAddMode()">Cancel</button>
        </div>
    </div>

    <%--<!-- Edit Movement -->--%>
    <div id="edit-movement-overlay-box" class="">
        <div class="form-row col-xs-12">
            <div>Bank account</div>
            <div><select ng-model="editMovement.bankAccount"
                         ng-options="ba.displayName + ' | ' + ba.accountNumber + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
                         class="form-control input-lg"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Currency</div>
            <div><select ng-model="editMovement.currency"
                         ng-options="cu.description + ' | ' + cu.code + ' | ' + cu.abbreviation for cu in currencies"
                         class="form-control input-lg"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>In/Out</div>
            <div>
                <select ng-model="editMovement.eu" class="form-control input-lg">
                    <option value="E">In</option>
                    <option value="U">Out</option>
                </select>
            </div>
        </div>
        <div class="form-row col-xs-12">
            <div>Date</div>
            <div><input name="editMovementDate" type="text"
                        class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                        datepicker-popup="dd-MMMM-yyyy" ng-model="editMovement.date" is-open="opened3"
                        min="2000-01-01" max="'2020-12-31'"
                        datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                        close-text="Close"
                        required="required" ng-click="open($event,'opened3')"
                        ng-change="setDefaultTime(editMovement.date);"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Description</div>
            <div><input size="80" ng-model="editMovement.description" class="form-control input-lg"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Amount</div>
            <div><input ng-model="editMovement.amount" class="form-control input-lg"/></div>
        </div>
        <div class="form-row col-xs-12">
            <div>Category</div>
            <div class="input-group" style="z-index: 0">
                <input class="form-control input-lg search-box" ng-model="editMovement.category.title" ng-click="selectCategoryForEditMovement()" />
                <span class="input-group-btn">
                    <button ng-show="editMovement.category != null" class="btn btn-default"
                            type="button" ng-click="editMovement.category = null">
                        <span class="glyphicon glyphicon-remove" style="font-size: 2.2em"></span>
                    </button>
                    <button class="btn btn-default" type="button" ng-click="selectCategoryForEditMovement()">
                        <span class=" glyphicon glyphicon-search" style="font-size: 2.2em"></span>
                    </button>
                </span>
            </div>
        </div>
        <div class="form-row col-xs-12" style="text-align: right">
            <button class="btn btn-primary" ng-click="confirmSaveMovement(editMovement)">OK</button>
            <button class="btn btn-warning" ng-click="toggleEditMode(editMovement, this)">Cancel</button>
        </div>
    </div>

    <%--<!-- Edit Transfer -->--%>
    <div id="edit-transfer-overlay-box" class="hidden-xs hidden-sm">
        <div class="col-xs-6">
            <div class="form-row">
                <div></div>
                <div><span style="font-size: 14px;">FROM</span></div>
            </div>
            <div class="form-row">
                <div>Bank Account</div>
                <div>
                    <select ng-model="editTransfer.fromBankAccount"
                            disabled
                            ng-options="ba.displayName + ' | ' + ba.accountNumber + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
                            class="form-control input-lg"></select>
                </div>
            </div>
            <div class="form-row">
                <div>Date</div>
                <div><input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                            datepicker-popup="dd-MMMM-yyyy" ng-model="editTransfer.fromMovement.date" is-open="opened4"
                            min="2000-01-01" max="'2020-12-31'"
                            datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                            close-text="Close"
                            required="required" ng-click="open($event,'opened4');onChangeTransferDate(editTransfer)"
                            ng-change="setDefaultTime(editTransfer.fromMovement.date);"/>
                </div>
            </div>
            <div class="form-row">
                <div>Amount</div>
                <div><input ng-model="editTransfer.fromMovement.amount" class="form-control input-lg"/></div>
            </div>
        </div>
        <%--<div class="form-row col-xs-12">--%>
        <%--<div colspan="2">--%>
        <%--<hr/>--%>
        <%--</div>--%>
        <%--</div>--%>
        <div class="col-xs-6">
            <div class="form-row">
                <div></div>
                <div><span style="font-size: 14px;">TO</span></div>
            </div>
            <div class="form-row">
                <div><span class="aside-filter-item">Bank Account</span></div>
                <div>
                    <select ng-model="editTransfer.toBankAccount"
                            disabled
                            ng-options="ba.displayName + ' | ' + ba.accountNumber + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
                            class="form-control input-lg"></select>
                </div>
            </div>
            <div class="form-row">
                <div>Date</div>
                <div><input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                            datepicker-popup="dd-MMMM-yyyy" ng-model="editTransfer.toMovement.date" is-open="opened5"
                            min="2000-01-01" max="'2020-12-31'"
                            datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true"
                            close-text="Close"
                            required="required" ng-click="open($event,'opened5')"
                            ng-change="setDefaultTime(editTransfer.toMovement.date);"/></div>
            </div>
            <div class="form-row">
                <div>Amount</div>
                <div><input ng-model="editTransfer.toMovement.amount" class="form-control input-lg"
                            ng-change="changeEditTransferAmount()"/></div>
            </div>
        </div>
        <%--<div class="form-row col-xs-12">--%>
        <%--<div>Conversion Rate</div>--%>
        <%--<div><input ng-model="editTransfer.conversionRate" class="form-control"--%>
        <%--ng-change="changeEditTransferConversionRate()"/></div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="modal-footer">--%>
        <div class="form-row col-xs-12" style="text-align: right">
            <button class="btn btn-primary" ng-click="updateTransfer(editTransfer)">OK</button>
            <button class="btn btn-warning" ng-click="toggleEditTransferMode(editTransfer, this)">Cancel</button>
        </div>
        <%--</div>--%>
    </div>

    <%--<!-- Select Category -->--%>
    <div id="select-category-overlay-box" class="col-md-12">
        <div class="modal-body">
            <div class="form-row">
                <ul class="nav nav-pills" style="margin-bottom: 20px">
                    <li id="createNewCategoryPill" style="width: 49%; text-align: center">
                        <a data-toggle="pill" href="#createNewCategoryPane" ng-click="loadExistingCategories('selectParentCategoryTree', false)">
                            Create New
                        </a>
                    </li>
                    <li id="selectExistingCategoryPill" style="width: 49%; text-align: center; float: right">
                        <a data-toggle="pill" href="#selectExistingCategoryPane" ng-click="loadExistingCategories('selectExistingCategoryTree')">
                            Select Existing
                        </a>
                    </li>
                </ul>
            </div>
            <div class="tab-content">

                <!-- Create New Category -->
                <div id="createNewCategoryPane" class="tab-pane fade">
                    <div class="row">
                        <div class="col-sm-12 text-left">
                            <span class="aside-filter-item">Code</span>
                        </div>
                        <div class="col-sm-12 text-left">
                            <input class="form-control input-lg" ng-model="newCategoryForm.code"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 text-left">
                            <span class="aside-filter-item">Title</span>
                        </div>
                        <div class="col-sm-12 text-left">
                            <input class="form-control input-lg" ng-model="newCategoryForm.title"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 text-left" style="padding-bottom: 10px">
                            <span class="aside-filter-item">Parent Category</span>
                        </div>
                        <div class="col-sm-12 text-left" style="padding-bottom: 20px">
                            <div id="selectParentCategoryTree" style="height: 400px; overflow-y: scroll"></div>
                        </div>
                    </div>
                </div>

                <!-- Search Existing Category -->
                <div id="selectExistingCategoryPane" class="tab-pane fade">
                    <div class="row">
                        <div class="col-sm-12 text-left" style="padding-bottom: 10px">
                            <span class="aside-filter-item">Select Category</span>
                        </div>
                        <div class="col-sm-12 text-left" style="padding-bottom: 20px">
                            <div id="selectExistingCategoryTree" class="selectable-category-tree"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-row" style="text-align: right">
                <button class="btn btn-primary" ng-click="confirmCategorySelection()">Confirm</button>
                <button class="btn btn-warning" ng-click="toggleCategorySelectionMode()">Cancel</button>
            </div>
        </div>
    </div>

</div>
