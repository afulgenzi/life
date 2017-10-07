<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="side-panel" class="sidebar-collapse collapse in">
	<c:set var="icon_size" value="40"></c:set>
	
	<!-- MAIN SEARCH PANEL -->
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="panel-title">Search for movements</span>
		</div>
		<div class="panel-body">
			<div style="margin-bottom: 20px">
				<div class="row" ng-repeat="ba in bankAccounts | orderBy:['displayName']">
					<div class="col-xs-12 clickable life-checkbox" style="margin-top: -15px">
						<label class="checkbox-inline">
							<input class="checkbox-inline" type="checkbox"
								   ng-change="invalidateSearch()"
								   ng-model="ba.checked"
								   ng-disabled="ba.disabled"
								   style="margin-top: 7px; margin-left: -15px"
							/>
							<span class="account-checkbox-label">{{ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code}}</span>
						</label>
					</div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-sm-12 text-center">
					<span class="aside-filter-item">
					<a ng-click="selectAllBankAccounts(true)">Select ALL</a>
					<span> | </></span>
						<a ng-click="selectAllBankAccounts(false)">Select None</a></span>
					</div>
				</div>
			</div>

			<!-- Tab Pills -->
			<ul class="nav nav-pills" style="margin-bottom: 20px">
				<li style="width: 33%; text-align: center" class="active"><a data-toggle="pill" href="#searchByPeriodPane">Period</a></li>
				<li style="width: 32%; text-align: center"><a data-toggle="pill" href="#searchByTextPane">Text</a></li>
				<li style="width: 33%; text-align: center"><a data-toggle="pill" href="#searchByTransferPane">Transfer</a></li>
			</ul>

			<div class="tab-content">
				<!-- Search By Period -->
				<div id="searchByPeriodPane" class="tab-pane fade in active">
					<c:if test="${currentMonth != null}">
						<!-- Month -->
						<div class="row">
							<div class="col-xs-2 col-sm-3 text-center" style="vertical-align: middle">
								<a ng-click="searchMovementsForPreviousMonth()">
									<%--<span class="glyphicon glyphicon-chevron-left chevron"></span>--%>
									<img class="disabled" alt=""
										 src="${resourcePath}/images/go_left.png" width="${icon_size}"
										 height="${icon_size}">
								</a>
							</div>
							<div class="col-xs-8 col-sm-6 text-center">
								<!-- <span class="panel-title">{{currentMonth.displayName}}</span> -->
								<select class="form-control input-lg text-center" ng-change="invalidateSearch()" ng-model="currentMonth" ng-options="m.code + ' - ' + m.displayName for m in months"></select>
							</div>
							<div class="col-xs-2 col-sm-3 text-center">
								<a ng-click="searchMovementsForNextMonth()">
									<%--<span class="glyphicon glyphicon-chevron-right chevron"></span>--%>
									<img class="disabled" alt=""
										 src="${resourcePath}/images/go_right.png" width="${icon_size}"
										 height="${icon_size}">
								</a>
							</div>
						</div>
						<!-- Year -->
						<div class="row">
							<div class="col-xs-2 col-sm-3 text-center">
								<a ng-click="searchMovementsForPreviousYear()">
									<img class="disabled" alt=""
										 src="${resourcePath}/images/go_left.png" width="${icon_size}"
										 height="${icon_size}">
								</a>
							</div>
							<div class="col-xs-8 col-sm-6 text-center">
								<span class="panel-title form-control input-lg">{{currentYear.code}}</span>
							</div>
							<div class="col-xs-2 col-sm-3 text-center">
								<a ng-click="searchMovementsForNextYear()">
									<img class="disabled" alt=""
										 src="${resourcePath}/images/go_right.png" width="${icon_size}"
										 height="${icon_size}">
								</a>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 text-right">
								<button class="btn btn-primary btn-sm" ng-click="searchMovementsByBankAccountSelection()">Search</button>
							</div>
							<%--<div class="col-sm-12 text-right">--%>
								<%--<button class="btn btn-primary btn-sm" ng-click="saveMovement(testSaveMovement)">Test Save</button>--%>
							<%--</div>--%>
						</div>
					</c:if>
				</div>

				<!-- Search By Text -->
				<div id="searchByTextPane" class="tab-pane fade">
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">Description</span>
						</div>
						<div class="col-sm-12 text-left">
							<input class="form-control input-lg" ng-model="inputSearchForm.description"/>
						</div>
					</div>
					<!-- Search Start Date -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">Start Date</span>
						</div>
						<div class="col-sm-12 text-left">
							<input name="searchStartDate" type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
								   datepicker-popup="dd-MMMM-yyyy" ng-model="inputSearchForm.startDate" is-open="opened8" min="2000-01-01" max="'2020-12-31'"
								   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
								   required="required" ng-click="open($event,'opened8')"
								   ng-change="setDefaultTime(inputSearchForm.startDate);"/>
						</div>
					</div>
					<!-- Search End Date -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">End Date</span>
						</div>
						<div class="col-sm-12 text-left">
							<input name="searchEndDate" type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
								   datepicker-popup="dd-MMMM-yyyy" ng-model="inputSearchForm.endDate" is-open="opened9" min="2000-01-01" max="'2020-12-31'"
								   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
								   required="required" ng-click="open($event,'opened9')"
								   ng-change="setDefaultTime(inputSearchForm.endDate);"/>
						</div>
					</div>
					<!-- Search Category -->
					<div class="row">
						<div class="col-xs-12 text-left">
							<span class="aside-filter-item">Category</span>
						</div>
						<div class="col-xs-12 text-left">
							<div class="input-group" style="z-index: 0">
								<input class="form-control input-lg search-box" ng-model="inputSearchForm.category.title" ng-click="selectCategoryForSearch()" />
							<span class="input-group-btn">
								<button ng-show="inputSearchForm.category != null" class="btn btn-default"
										type="button" ng-click="inputSearchForm.category = null">
									<span class="glyphicon glyphicon-remove" style="font-size: 2.2em"></span>
								</button>
								<button class="btn btn-default" type="button"
										ng-click="selectCategoryForSearch()">
									<span class="glyphicon glyphicon-search" style="font-size: 2.2em"></span>
								</button>
							</span>
							</div>
						</div>
					</div>
					<!-- Search In/Out -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">In Out</span>
						</div>
						<div class="col-sm-12 text-left">
							<select ng-model="inputSearchForm.inOut" class="form-control input-lg">
								<option value="">Any</option>
								<option value="E">IN</option>
								<option value="U">OUT</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 clickable life-checkbox" style="margin-top: -5px">
							<label class="checkbox-inline">
								<input class="checkbox-inline" type="checkbox"
									   ng-model="inputSearchForm.uncategorised"
									   style="margin-top: 7px"
								/>
								<span class="account-checkbox-label">Uncategorised</span>
							</label>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 text-right">
							<button class="btn btn-primary btn-sm" ng-click="searchMovementsByBankAccountSelectionAndText()">Search</button>
						</div>
					</div>
				</div>

				<!-- Search By Transfer -->
				<div id="searchByTransferPane" class="tab-pane fade">
                    <!-- Search From Account -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">Account Direction</span>
						</div>
                        <div class="col-sm-12 text-left">
                            <select ng-model="inputSearchTransferForm.fromTo" class="form-control input-lg">
                                <option value="FROM">Transfer From</option>
                                <option value="TO">Transfer To</option>
                            </select>
                        </div>
					</div>
                    <!-- Search To Account -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">Account</span>
						</div>
                        <div class="col-sm-12 text-left">
                            <select class="form-control input-lg" ng-model="inputSearchTransferForm.otherBankAccount" ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"></select>
                        </div>
					</div>
					<!-- Search Start Date -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">Start Date</span>
						</div>
						<div class="col-sm-12 text-left">
							<input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
								   datepicker-popup="dd-MMMM-yyyy" ng-model="inputSearchTransferForm.startDate" is-open="opened10" min="2000-01-01" max="'2020-12-31'"
								   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
								   required="required" ng-click="open($event,'opened10')"
								   ng-change="setDefaultTime(inputSearchTransferForm.startDate);"/>
						</div>
					</div>
					<!-- Search End Date -->
					<div class="row">
						<div class="col-sm-12 text-left">
							<span class="aside-filter-item">End Date</span>
						</div>
						<div class="col-sm-12 text-left">
							<input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
								   datepicker-popup="dd-MMMM-yyyy" ng-model="inputSearchTransferForm.endDate" is-open="opened11" min="2000-01-01" max="'2020-12-31'"
								   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
								   required="required" ng-click="open($event,'opened11')"
								   ng-change="setDefaultTime(inputSearchTransferForm.endDate);"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 text-right">
							<button class="btn btn-primary btn-sm" ng-click="searchMovementsByBankAccountSelectionAndTransfer()">Search</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- FILTER RESULTS -->
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="panel-title">Filter Results</span>
		</div>
		<div class="panel-body" style="margin-bottom: -10px">
			<div class="row" style="height: 30px; margin-bottom: 20px">
				<div class="col-xs-12 clickable life-checkbox" style="height: 100%">
					<input class="form-control input-lg" ng-model="movementFilter"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 clickable life-checkbox" style="margin-top: -5px">
					<label class="checkbox-inline">
						<input class="checkbox-inline" type="checkbox"
							   ng-model="movementHideBankTransfers"
							   style="margin-top: 7px"
						/>
						<span class="account-checkbox-label">Hide Bank Transfers</span>
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 clickable life-checkbox" style="margin-top: -15px">
					<label class="checkbox-inline">
						<input class="checkbox-inline" type="checkbox"
							   ng-model="movementHideAlreadyCategorised"
							   style="margin-top: 7px"
						/>
						<span class="account-checkbox-label">Hide Already Categorised</span>
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 clickable life-checkbox" style="margin-top: -15px">
					<label class="checkbox-inline">
						<input class="checkbox-inline" type="checkbox"
							   ng-model="movementHighlightUncategorised"
							   style="margin-top: 7px"
						/>
						<span class="account-checkbox-label">Highlight Uncategorised</span>
					</label>
				</div>
			</div>
			<div class="row" style="margin-top: 10px">
				<div class="col-sm-12 text-center">
					<span class="aside-filter-item">
					<a ng-click="selectAllVisibleMovements(true)">Select ALL</a>
					<span> | </></span>
					<a ng-click="selectAllVisibleMovements(false)">Select None</a></span>
				</div>
			</div>
		</div>
	</div>
	
	<!-- APPLY TO SELECTED MOVEMENTS -->
	<div class="panel panel-primary hidden-xs hidden-sm">
		<div class="panel-heading">
			<span class="panel-title">Apply to selected movements</span>
		</div>
		<div class="panel-body" style="margin-bottom: -10px">
		
			<!-- Change Bank Account -->
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Bank account</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<select class="form-control input-lg" ng-model="changeBankAccount" ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"></select>
				</div>
				<div class="col-sm-12 text-right" style="margin-top: 10px; margin-bottom: 16px;">
					<button class="btn btn-primary btn-sm" ng-click="changeAllBankAccount()">Change</button>
				</div>
			</div>
			
			<!-- Change Description -->
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Description</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<input class="form-control input-lg" ng-model="changeDescription"></input>
				</div>
				<div class="col-sm-12 text-right" style="margin-top: 10px; margin-bottom: 16px;">
					<button class="btn btn-primary btn-sm" ng-click="changeAllDescription()">Change</button>
				</div>
			</div>

			<!-- Change Amount -->
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Amount</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<input class="form-control input-lg" ng-model="changeAmount"></input>
				</div>
				<div class="col-sm-12 text-right" style="margin-top: 10px; margin-bottom: 16px;">
					<button class="btn btn-primary btn-sm" ng-click="changeAllAmount()">Change</button>
				</div>
			</div>

			<!-- Change Category -->
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Category</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left" style="margin-bottom: 15px">
					<div class="input-group" style="z-index: 0">
						<input class="form-control input-lg search-box" ng-model="categoryForSelectedMovements.title" ng-click="changeCategoryForSelectedMovements()" />
							<span class="input-group-btn">
								<button ng-show="categoryForSelectedMovements!=null" class="btn btn-default"
										type="button" ng-click="categoryForSelectedMovements = null">
									<span class="glyphicon glyphicon-remove" style="font-size: 2.2em"></span>
								</button>
								<button class="btn btn-default" type="button"
										ng-click="changeCategoryForSelectedMovements()">
									<span class="glyphicon glyphicon-search" style="font-size: 2.2em"></span>
								</button>
							</span>
					</div>
				</div>
				<div class="col-sm-12 text-right">
					<button class="btn btn-primary btn-sm" ng-click="changeAllCategory()">Change</button>
				</div>
			</div>
			<div class="form-row col-xs-12">
			</div>
		</div>
	</div>
	
	<!-- BANK TRANSFERS -->
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="panel-title">Bank Transfer</span>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">From Account</span> 
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<select class="form-control input-lg" ng-model="newTransfer.fromBankAccount"
							ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
							ng-change="setConversionRate();bankTransferAmountChanged(newTransfer)"></select>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Date</span> 
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<input name="transferDate" type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
						   datepicker-popup="dd-MMMM-yyyy" ng-model="newTransfer.fromMovement.date" is-open="opened6" min="2000-01-01" max="'2020-12-31'"
						   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
						   required="required" ng-click="open($event,'opened6')"
						   ng-change="setDefaultTime(newTransfer.fromMovement.date);bankTransferDateChanged(newTransfer)"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Amount</span> 
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
	        		<input type="text" class="form-control input-lg"
						   ng-model="newTransfer.fromMovement.amount"
						   ng-change="bankTransferAmountChanged(newTransfer)"/>
				</div>
			</div>
			<div class="row">
				<hr/>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">To Account</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<select class="form-control input-lg" ng-model="newTransfer.toBankAccount"
							ng-options="ba.displayName + ' | ' + ba.bankName + ' | ' + ba.currency.code for ba in bankAccounts | orderBy:'displayName'"
							ng-change="setConversionRate();bankTransferAmountChanged(newTransfer)"></select>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Date</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<input name="transferDate" type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
						   datepicker-popup="dd-MMMM-yyyy" ng-model="newTransfer.toMovement.date" is-open="opened7" min="2000-01-01" max="'2020-12-31'"
						   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
						   required="required" ng-click="open($event,'opened7')"
						   ng-change="setDefaultTime(newTransfer.toMovement.date);"/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Amount</span>
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
					<input type="text" ng-model="newTransfer.toMovement.amount" class="form-control input-lg" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-3 text-left">
					<span class="aside-filter-item">Conversion Rate</span> 
				</div>
				<div class="col-xs-12 col-sm-9 text-left">
	        		<input type="text" ng-model="newTransfer.conversionRate" class="form-control input-lg" />
				</div>
			</div>
			<div class="row" style="margin-top: 10px; ">
				<%--<div class="col-sm-12">--%>
					<%--<input type="checkbox" ng-model="newTransfer.useTargetCurrency">Use target currency</input>--%>
				<%--</div>--%>
				<div class="col-sm-12 text-right">
					<button class="btn btn-primary btn-sm" ng-click="executeBankTransfer()">Execute Transfer</button>
				</div>
			</div>
		</div>
	</div>
</div>
