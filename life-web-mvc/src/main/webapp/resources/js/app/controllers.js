angular.module('LifeApp.controllers', [])
.controller('movementsController',
		function($scope, lifeAPIservice) {
			$scope.nameFilter = null;
			$scope.bankAccounts = [];
			$scope.currencies = [];
			$scope.movements = [];
			$scope.months = [];
			$scope.categories = [];
			$scope.currentMonth = null;
			$scope.currentYear = null;
			$scope.newMovement = {};
			$scope.editMovement = null;
			$scope.changeBankAccount = null;
			$scope.currentBankAccount = null;
			$scope.newTransfer = null;
			$scope.editTransfer = null;
			$scope.normalView = false;
			$scope.splitView = true;
			$scope.repetitions = [{code:"",description:"None"}, {code:"daily",description:"Daily"}, {code:"monthly",description:"Monthly"}];
			$scope.inputSearchForm = {};
			$scope.inputSearchTransferForm = {};
			$scope.openedDate = {};
			$scope.loading = false;
			$scope.defaultMsgDelay = 5000;
			$scope.currentSearchType = {};
			$scope.searchTypePeriod = "Period";
			$scope.searchTypeText = "Text";
			$scope.searchTypeTransfer = "Transfer";
			$scope.accountTotals = [{account: {displayName: '5056'}, amountIn: '€ 1000.00', amountOut: '€ 2000.00'}];
			$scope.searchValid = false;
			$scope.categoryForm = {};
            $scope.testSaveMovement = {}
			$scope.selectAll = false;
			$scope.categoryTreeSelectionCriteria = {selectOnlyLeaves: false};

            /**
			 * DataPicker (START)
			 */
			$scope.today = function() {
				$scope.dt = new Date();
			};
			$scope.today();

			$scope.showWeeks = true;
			$scope.toggleWeeks = function() {
				$scope.showWeeks = !$scope.showWeeks;
			};

			$scope.clear = function() {
				$scope.dt = null;
			};

			// Disable weekend selection
			$scope.disabled = function(date, mode) {
				return false;
			};

			$scope.toggleMin = function() {
				$scope.minDate = ($scope.minDate) ? null : new Date();
			};
			$scope.toggleMin();

			$scope.openDate = function($event, ind) {
				console.log("executing $scope.open ...");
				$event.preventDefault();
				$event.stopPropagation();
			};

			$scope.open = function($event, opened) {
				console.log("executing $scope.open ...");
				$event.preventDefault();
				$event.stopPropagation();

				$scope[opened] = true;
			};

			$scope.dateOptions = {
				'year-format' : "'yy'",
				'starting-day' : 1
			};

			$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
				'shortDate' ];
			$scope.format = $scope.formats[0];
			/**
			 * DataPicker (END)
			 */

			$scope.loadExistingCategoryTreeForSelectingParent = function (treeName) {
				console.log("loadExistingCategoryTreeForSelectingParent('" + treeName + "')");
				$scope.categoryForm = {};
				$scope.loadExistingCategories(treeName);
			}

			$scope.loadExistingCategoryList = function () {
				console.log("loadExistingCategoryList");
				$scope.categoryForm = {};
				lifeAPIservice.getAllCategories().success(
					function(response) {
						// console.log("loadExistingCategoryList -> " + JSON.stringify(response));
						$scope.categories = response;
					});
			}

			$scope.loadExistingCategories = function (treeName, inputDefaultSelectedCategory) {
				console.log("loadExistingCategories -> defaultSelectedCategory " + JSON.stringify($scope.categoryTreeSelectionCriteria.defaultSelectedCategory));
				var defaultSelectedCategory = $scope.categoryTreeSelectionCriteria.defaultSelectedCategory;
				if (inputDefaultSelectedCategory != null) {
					defaultSelectedCategory = inputDefaultSelectedCategory;
				}
				categoryForm = {};
				lifeAPIservice.getCategoryTree($scope.categoryTreeSelectionCriteria.selectOnlyLeaves, defaultSelectedCategory).success(
					function (response) {
						$scope.treeName = treeName;
						console.log("treeName [" + treeName + "]")
						var tree = $('#' + treeName);
						tree.treeview({
							color: "#428bca",
							backColor: "#444",
							data: response,
							onNodeSelected: function (event, data) {
								console.log("Selected [" + JSON.stringify(data) + "]");
								expandParents(data, tree, 0);
							}
						});
					});
			}

			expandParents = function (node, tree, level) {
				console.log("Expanded node [" + node.text + "]")
				tree.treeview("expandNode", node);
				console.log("current: " + node.text);
				var parent = tree.treeview("getParent", node);
				if (parent.context == undefined) {
					console.log("parent: " + parent.text);
					expandParents(parent, tree, level+1);
				}
			}

			$scope.toggleNoSupercategoryCheckbox = function (noSupercategoryValue, treeName) {
				if (typeof noSupercategoryValue != 'undefined' && treeName == $scope.treeName) {
					console.log("toggleNoSupercategoryCheckbox(" + noSupercategoryValue + ", '" + treeName + "')");
					if (noSupercategoryValue) {
						if ($('#' + treeName).treeview('getDisabled').length == 0) {
							$('#' + treeName).treeview('disableAll');
						}
					} else {
						if ($('#' + treeName).treeview('getEnabled').length == 0) {
							$('#' + treeName).treeview('enableAll');
							if ($scope.categoryForm.category != null) {
								$scope.selectedCategory($scope.categoryForm.category);
							}
						}
					}
				}
			}
			
			$scope.loadMovementsAsCategoryTree = function (treeName) {
				// Search for movements
				if ($scope.currentSearchType === $scope.searchTypePeriod)
				{
					$scope.loadCategoryTreeByMonth(treeName);
				}
				else if ($scope.currentSearchType === $scope.searchTypeText)
				{
					$scope.loadCategoryTreeByText(treeName);
				}
				else if ($scope.currentSearchType === $scope.searchTypeTransfer)
				{
					$scope.setWarnMsg("Cannot load Category Tree for Search Type [" + $scope.currentSearchType + "]");
				}
			}

			$scope.loadCategoryTreeByMonth = function (treeName) {
				var selectedBankAccounts = [];
				$scope.loading = true;
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						selectedBankAccounts.push(ba.pk);
					}
				});
				lifeAPIservice.getCategoryTreeByMonth(
                    selectedBankAccounts,
					$scope.currentYear.code,
					$scope.currentMonth.code
				).success(
					function(response) {
                        console.log("loadCategoryTreeByMonth, response: " + JSON.stringify(response));
						$('#'+treeName).treeview({
                            color: "#428bca",
							backColor: "#e8edff",
                            data: response,
                            showTags: true
                        });
                        $scope.loading = false;
					}
				).error(
					function(data, status, headers, config) {
						$scope.setWarnMsg("searchMovementsByBankAccountSelection. Error: [" + status + "]", $scope.defaultMsgDelay);
						// $scope.reloadCurrentList();
                        $scope.loading = false;
					}
				);
			}

			$scope.loadCategoryTreeByText = function(treeName) {
				$scope.loading = true;
				var multiAccountSearchForm = jQuery.extend(true, {}, $scope.inputSearchForm);
				multiAccountSearchForm.bankAccounts = [];
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						multiAccountSearchForm.bankAccounts.push(ba.pk);
					}
				});
				lifeAPIservice.getCategoryTreeByText(multiAccountSearchForm
				).success(
					function(response) {
						// console.log("loadCategoryTreeByText, response: " + JSON.stringify(response));
						$('#'+treeName).treeview({
							color: "#428bca",
							backColor: "#e8edff",
							data: response,
							showTags: true
						});
						$scope.loading = false;
					}
				).error(
					function(data, status, headers, config) {
						$scope.setWarnMsg("searchMovementsByBankAccountSelection. Error: [" + status + "]", $scope.defaultMsgDelay);
						// $scope.reloadCurrentList();
						$scope.loading = false;
					}
				);
			};
			
			$scope.confirmCategorySelection = function() {
				var selectedNodes = $('#'+$scope.treeName).treeview('getSelected', '');
				if (selectedNodes.length > 0)
				{
					var node = selectedNodes[0];
					console.log("Selected Category [" + node.code + ", " + node.text + "]");
					lifeAPIservice.getCategoryByCode(node.code).success(
						function(response) {
							console.log("Assigning Category [" + response.title + "]");
							$scope.categorySelectionCallback(response);
						}
					);
				}
				$scope.toggleCategorySelectionMode();
			}
			
			$scope.saveCategory = function(treeName, categoryForm) {
				var selectedNodes = $('#'+treeName).treeview('getSelected', '');
				if (selectedNodes.length > 0 || categoryForm.noSupercategory) {
					var invokeSaveCategory = function(categoryForm){
						// Save CategoryForm
						lifeAPIservice.saveCategory(categoryForm).success(
							function(response) {
								if (response.success){
									console.log("Category [" + response.title + "] saved.");
									$scope.categoryForm = {};
									$scope.loadExistingCategoryList();
									$scope.loadExistingCategoryTreeForSelectingParent($scope.treeName);
								}else{
									if (response.messages != null){
										$scope.setWarnMsg(response.messages.messages[0]);
									}
								}
							}
						);
					}

					if (categoryForm.noSupercategory){
						// Save CategoryForm
						$scope.categoryForm.category.supercategory = null;
						invokeSaveCategory(categoryForm);
					}else{
						var node = selectedNodes[0];
						console.log("Selected Category [" + node.code + ", " + node.text + "]");
						lifeAPIservice.getCategoryByCode(node.code).success(
							function(response) {
								console.log("Assigning Category [" + response.title + "]");
								// Set Supercategory to CategoryForm.category
								$scope.categoryForm.category.supercategory = response;

								// Save CategoryForm
								invokeSaveCategory(categoryForm);
							}
						);
					}
				}
				else {
					$scope.setWarnMsg('Select the parent Category');
				}
			}

			$scope.deleteCategory = function(treeName, categoryForm) {
				lifeAPIservice.deleteCategory(categoryForm).success(
					function(response) {
						console.log("Category [" + response.title + "] deleted.");
						$scope.categoryForm = {};
						$scope.loadExistingCategoryList();
						$scope.loadExistingCategoryTreeForSelectingParent($scope.treeName);
					}
				);
			}

			$scope.selectedCategory = function(category) {
				console.log("Selected Category from list [" + category.code + "]");
				$scope.categoryForm.category = jQuery.extend(true, {}, category);
				if (category.supercategory == null) {
					$scope.categoryForm.noSupercategory = true;
				} else {
					$scope.categoryForm.noSupercategory = false;
				}
				if (category.frequency != null && category.frequency.frequencyType != null) {
					angular.forEach($scope.frequencyTypes, function (ft, index) {
						if (category.frequency.frequencyType.code == ft.code) {
							$scope.categoryForm.category.frequency.frequencyType = ft;
						}
					});
				}

				if (category.supercategory != null) {
					var supercategoryCode = category.supercategory.code;
					$('#' + $scope.treeName).treeview('clearSearch');
					var searchResult = searchNode(supercategoryCode, $('#' + $scope.treeName));

					if (searchResult.length > 0) {
						$('#' + $scope.treeName).treeview('search', searchResult[0].nodeId, {
							ignoreCase: true,     // case insensitive
							exactMatch: true,    // like or equals
							revealResults: true  // reveal matching nodes
						});
						$('#' + $scope.treeName).treeview('selectNode', searchResult[0].nodeId);
						$('#' + $scope.treeName).treeview('clearSearch');
					}
					else {
						$scope.setWarnMsg("Supercategory not found [" + supercategoryCode + "]");
					}
				}
			}

			searchNode = function (categoryCode, tree) {
				var nodes = tree.treeview("getEnabled");
				// console.log("getEnabled: " + JSON.stringify(nodes));
				var ind = 0;
				var result = [];
				while (i < nodes.length && result.length == 0) {
					if (nodes[ind].code == categoryCode) {
						var node = nodes[ind];
						console.log("Found node [" + node.code + "],  [" + node.text + "]")
						result.push(node);
					}
					ind++;
				}
				return result;
			}
			
			// GET BANK ACCOUNTS
			$scope.searchBankAccounts = function() {
				console.log('getting bankAccounts ...');
				// Search for current month
				lifeAPIservice.getBankAccounts().success(
						function(response) {
							$scope.bankAccounts = response;
							angular.forEach($scope.bankAccounts,function(ba,index){
								if (ba != null){
									console.log("Bank account[" + index + "]: [" + ba.displayName + "]");
								}
							});
						});
			};

			// GET CURRENCIES
			$scope.searchCurrencies = function() {
				// Search for current month
				lifeAPIservice.getCurrencies().success(
						function(response) {
							$scope.currencies = response;
						});
			};

			// GET FREQUENCY TYPES
			$scope.searchFrequencyTypes = function() {
				console.log("searchFrequencyTypes ...");
				lifeAPIservice.getFrequencyTypes().success(
						function(response) {
							$scope.frequencyTypes = response;
						});
			};

			$scope.loadMovementsPerSearchType = function(searchType) {
				$scope.currentSearchType = searchType;
				$scope.reloadCurrentList();
			}

			// REPEAT SEARCH
			$scope.reloadCurrentList = function() {
				// Search for movements
				if ($scope.currentSearchType === $scope.searchTypePeriod)
				{
					$scope.searchMovementsByBankAccountSelection();
				}
				else if ($scope.currentSearchType === $scope.searchTypeText)
				{
					$scope.searchMovementsByBankAccountSelectionAndText();
				}
				else if ($scope.currentSearchType === $scope.searchTypeTransfer)
				{
					$scope.searchMovementsByBankAccountSelectionAndTransfer();
				}
			};

			// GET MOVEMENTS FOR CURRENT MONTH
			$scope.searchMovementsByBankAccountSelection = function() {
    	        $scope.movements = [];
    	        $scope.accountTotals = [];
				$scope.loading = true;
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						var newBa = jQuery.extend(true, {}, ba);
						delete newBa.checked;
						delete newBa.disabled;
						// Search for movements
						ba.disabled = true;
						lifeAPIservice.searchMovementsByMonth(
								newBa,
								$scope.currentYear.code,
								$scope.currentMonth.code, $scope, true)
						.success(function (response) {
							processMovementResponse(response, true);
							ba.disabled = false;
						}).error(function(data, status, headers, config) {
							$scope.setWarnMsg("searchMovementsByBankAccountSelection. Error: [" + status + "]", $scope.defaultMsgDelay);
							// $scope.reloadCurrentList();
							ba.disabled = false;
						});
					}
					$scope.loading = false;
	            });
				$scope.currentSearchType = $scope.searchTypePeriod;
				postSearch();
			};

			// GET MOVEMENTS FOR TEXT
			$scope.searchMovementsByBankAccountSelectionAndText = function() {
    	        $scope.movements = [];
				$scope.accountTotals = [];
				var atLeastOneCall = false;
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						atLeastOneCall = true;
						var newBa = jQuery.extend(true, {}, ba);
						delete newBa.checked;
						delete newBa.disabled;
						// Search for movements
						ba.disabled = true;
						var tempForm = jQuery.extend(true, {}, $scope.inputSearchForm);
						tempForm.bankAccount = newBa;
						lifeAPIservice.searchMovementsByText(
							tempForm
						).success(function (response) {
							processMovementResponse(response, true);
							ba.disabled = false;
						}).error(function(data, status, headers, config) {
							$scope.setWarnMsg("searchMovementsByBankAccountSelectionAndText. Error: [" + status + "]", $scope.defaultMsgDelay);
							// $scope.reloadCurrentList();
							ba.disabled = false;
						});
					}
	            });
				if (!atLeastOneCall)
				{
					$scope.setWarnMsg("Select at least one Bank Account in order to perform the search", $scope.defaultMsgDelay);
				}
				else
				{
					$scope.currentSearchType = $scope.searchTypeText;
				}
				postSearch();
			};

			// SEARCH TRANSFERS
			$scope.searchMovementsByBankAccountSelectionAndTransfer = function() {
				console.log("searchMovementsByBankAccountSelectionAndTransfer");
    	        $scope.movements = [];
				$scope.accountTotals = [];

				var atLeastOneCall = false;
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						atLeastOneCall = true;
						var newBa = jQuery.extend(true, {}, ba);
						delete newBa.checked;
						delete newBa.disabled;
						// Search for movements
						ba.disabled = true;
						$scope.inputSearchTransferForm.bankAccount = newBa;
						lifeAPIservice.searchMovementsByTransfer(
							$scope.inputSearchTransferForm
						).success(function (response) {
							processMovementResponse(response, true);
							ba.disabled = false;
						}).error(function(data, status, headers, config) {
							$scope.setWarnMsg("searchMovementsByBankAccountSelectionAndTransfer. Error: [" + status + "]", $scope.defaultMsgDelay);
							// $scope.reloadCurrentList();
							ba.disabled = false;
						});
					}
				});
				if (!atLeastOneCall)
				{
					$scope.setWarnMsg("Select at least one Bank Account in order to perform the search", $scope.defaultMsgDelay);
				}
				else
				{
					$scope.currentSearchType = $scope.searchTypeTransfer;
				}
				postSearch();
			};

			postSearch = function() {
				hideSidePanel();
				// activatePill("byListPane");
				// $('#byListViewList').click();
				$("#byListView").addClass("active");
				$("#byListPane").addClass("active");

				$("#byAccountView").removeClass("active");
				$("#byAccountPane").removeClass("active");

				$("#byCategoryView").removeClass("active");
				$("#byCategoryPane").removeClass("active");
			}

			hideSidePanel = function() {
				// $("#side-panel").removeClass("in");
			}

			processMovementResponse = function(response, appendMode) {
				if(typeof response == 'object'){
					console.log("Response, Movements [" + response.movements.length + "], AccountTotals [" + JSON.stringify(response.accountTotals) + "]");
					if (appendMode) {
						$scope.movements = $scope.movements.concat(response.movements);
						if (response.accountTotals.length > 0)
						{
							$scope.accountTotals = $scope.accountTotals.concat(response.accountTotals);
						}
					}else{
						$scope.movements = response.movements;
						$scope.accountTotals = response.accountTotals;
					}
					$scope.searchValid = true;
				}else{
					$scope.movements = [];
					$scope.accountTotals = [];
					$scope.setWarnMsg("You're not logged in anymore!", $scope.defaultMsgDelay);
				}
			}

			var processMovementResponse = function(response, appendMode) {
				if(typeof response == 'object'){
					console.log("Response, Movements [" + response.movements.length + "], AccountTotals [" + JSON.stringify(response.accountTotals) + "]");
					if (appendMode) {
						$scope.movements = $scope.movements.concat(response.movements);
						if (response.accountTotals.length > 0)
						{
							$scope.accountTotals = $scope.accountTotals.concat(response.accountTotals);
						}
					}else{
						$scope.movements = response.movements;
						$scope.accountTotals = response.accountTotals;
					}
					$scope.movements = $scope.movements.sort($scope.sortMovements);
					$scope.searchValid = true;
				}else{
					$scope.movements = [];
					$scope.accountTotals = [];
					$scope.setWarnMsg("You're not logged in anymore!", $scope.defaultMsgDelay);
				}
			}

			$scope.invalidateSearch = function() {
				$scope.searchValid = false;
			}

			// SELECT ALL BANK ACCOUNTS
			$scope.selectAllBankAccounts = function(state) {
				angular.forEach($scope.bankAccounts,function(ba,index){
					ba.checked = state;
	            });
				$scope.reloadCurrentList();
			};

			// GET MOVEMENTS FOR CURRENT MONTH
			$scope.searchMovementsByCurrentMonth = function() {
				// Search for current month
				lifeAPIservice.getCurrentMonth().success(
						function(response) {
							$scope.setCurrentMonthFromResponse(response);

							$scope.searchMovementsByBankAccountSelection();
							// Search for movements
						});
			};

			// GET ALL MONTHS
			$scope.searchAllMonths = function() {
				console.log("getting all months ...");
				// Search for current month
				lifeAPIservice.getAllMonths().success(
						function(response) {
							console.log("Found months");
							$scope.months = response;
							angular.forEach($scope.months,function(month,index){
								if (month != null){
									console.log("Month[" + index + "]: [" + month.displayName + "]");
								}
							});

							console.log("Get current month");
							lifeAPIservice.getCurrentMonth().success(
									function(response) {
										console.log("Found current month");
										$scope.setCurrentMonthFromResponse(response);
									});
						});
			};

			// GET ALL MOVEMENTS
			$scope.searchAllMovements = function() {
				// Search for movements
    	        $scope.movements = [];
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
						var newBa = jQuery.extend(true, {}, ba);
						delete newBa.checked;
						// Search for movements
						lifeAPIservice.searchAllMovements(ba, $scope);
					}
	            });
			};

			// GET MOVEMENTS FOR PREVIOUS MONTH
			$scope.searchMovementsForPreviousMonth = function() {
				// Search for current month
				$scope.loading = true;
				lifeAPIservice.getPreviousMonth($scope.currentYear.code,
						$scope.currentMonth.code).success(
						function(response) {
							$scope.setCurrentMonthFromResponse(response);

							// Search for movements
							$scope.searchMovementsByBankAccountSelection();

							$scope.loading = false;
						});
			};

			// GET MOVEMENTS FOR NEXT MONTH
			$scope.searchMovementsForNextMonth = function() {
				// Search for current month
				$scope.loading = true;
				lifeAPIservice.getNextMonth($scope.currentYear.code,
						$scope.currentMonth.code).success(
						function(response) {
							$scope.setCurrentMonthFromResponse(response);

							// Search for movements
							$scope.searchMovementsByBankAccountSelection();

							$scope.loading = false;
						});
			};

			// GET MOVEMENTS FOR PREVIOUS YEAR
			$scope.searchMovementsForPreviousYear = function() {
				// Search for current month
				lifeAPIservice.getMonth($scope.currentYear.code - 1,
						$scope.currentMonth.code).success(
						function(response) {
							$scope.setCurrentMonthFromResponse(response);

							// Search for movements
							$scope.searchMovementsByBankAccountSelection();
						});
			};

			// GET MOVEMENTS FOR NEXT YEAR
			$scope.searchMovementsForNextYear = function() {
				// Search for current month
				lifeAPIservice.getMonth($scope.currentYear.code + 1,
						$scope.currentMonth.code).success(
						function(response) {
							$scope.setCurrentMonthFromResponse(response);

							// Search for movements
							$scope.searchMovementsByBankAccountSelection();
						});
			};

			$scope.setCurrencyOnBankAccountSelection = function(movement, currencies) {
				console.log("setCurrencyOnBankAccountSelection");
				if (movement.bankAccount != null)
				{
					angular.forEach(currencies,function(currency,index){
						if (currency.pk == movement.bankAccount.currency.pk){
							movement.currency = currency;
						}
					});
				}
			}

			// New / Save
			$scope.toggleAddMode = function() {
				$scope.addMode = !$scope.addMode;
				// toggleLifeOverlay('add-movement-overlay-box', true);
				toggleAddPanel();
			};

			$scope.shouldToggleEditMode = function (movement, $event) {
				if ($('#movementButtonGroup').css('display') == 'none') {
					if ($scope.touchedMovement == null || $scope.touchedMovement.pk != movement.pk) {
						$scope.touchedMovement = movement;
						$scope.editMode = false;
						toggleEditPanel($event, "edit-movement-overlay-box", $scope.editMode);
					} else {
						$scope.toggleEditMode(movement, $event);
					}
				}
			}

			$scope.toggleEditMode = function(movement, $event) {
				console.log("scope.toggleEditMode");
				if (movement.bankTransfer != null){
					$scope.toggleEditTransferMode(movement.bankTransfer, $event);
				} else {
					$scope.editTransferMode = false;
					if ($scope.editMode && $scope.editMovement.pk == movement.pk) {
						$scope.editMode = false;
					} else {
						$scope.editMode = true;
					}
					toggleEditPanel($event, "edit-movement-overlay-box",$scope.editMode);
					if ($scope.editMode){
						$scope.editMovement = jQuery.extend(true, {}, movement);
						// Set BankAccount from main list
						angular.forEach($scope.bankAccounts,function(ba,index){
							if (ba.accountNumber == $scope.editMovement.bankAccount.accountNumber){
								$scope.editMovement.bankAccount = ba;
							}
						});
						$scope.showObjectProperties($scope.editMovement);
						// Set Currency from main list
						angular.forEach($scope.currencies,function(item,index){
							if (item.code == $scope.editMovement.currency.code){
								$scope.editMovement.currency = item;
							}
						});
					}
				}
			};

			$scope.toggleEditTransferMode = function (transfer, $event) {
				$scope.editMode = false;
				if ($scope.editTransferMode && $scope.editTransfer.pk == transfer.pk)
				{
					$scope.editTransferMode = false;
				}
				else
				{
					$scope.editTransferMode = true;
				}
				// toggleLifeOverlay('edit-transfer-overlay-box', true);
				toggleEditPanel($event, "edit-transfer-overlay-box", $scope.editTransferMode);
				if ($scope.editTransferMode) {
					$scope.editTransfer = transfer;
					// Set BankAccount from main list
					angular.forEach($scope.bankAccounts, function (ba, index) {
						if (ba.accountNumber == $scope.editTransfer.fromBankAccount.accountNumber) {
							$scope.editTransfer.fromBankAccount = ba;
						}
					});
					angular.forEach($scope.bankAccounts, function (ba, index) {
						if (ba.accountNumber == $scope.editTransfer.toBankAccount.accountNumber) {
							$scope.editTransfer.toBankAccount = ba;
						}
					});
//					$scope.showObjectProperties($scope.editMovement);
				}
			};
			
			$scope.selectCategoryForSearch = function() {
				$scope.categorySelectionCallback = function (category)
				{
					$scope.inputSearchForm.category = category;
				}
				$scope.categoryTreeSelectionCriteria = {};
				$scope.categoryTreeSelectionCriteria.selectOnlyLeaves = false;
				$scope.categoryTreeSelectionCriteria.defaultSelectedCategory = $scope.inputSearchForm.category;
				$scope.toggleCategorySelectionMode();
			};

			$scope.selectCategoryForEditMovement = function() {
				$scope.categorySelectionCallback = function (category)
				{
					$scope.editMovement.category = category;
				}
				$scope.categoryTreeSelectionCriteria = {};
				$scope.categoryTreeSelectionCriteria.selectOnlyLeaves = true;
				$scope.categoryTreeSelectionCriteria.defaultSelectedCategory = $scope.editMovement.category;
				$scope.toggleCategorySelectionMode();
			};

			$scope.selectCategoryForNewMovement = function() {
				$scope.categorySelectionCallback = function (category)
				{
					$scope.newMovement.movement.category = category;
				}
				$scope.categoryTreeSelectionCriteria = {};
				$scope.categoryTreeSelectionCriteria.selectOnlyLeaves = true;
				$scope.categoryTreeSelectionCriteria.defaultSelectedCategory = $scope.newMovement.movement.category;
				$scope.toggleCategorySelectionMode();
			};

			$scope.changeCategoryForSelectedMovements = function() {
				$scope.categorySelectionCallback = function (category)
				{
					$scope.categoryForSelectedMovements = category;
				}
				$scope.categoryTreeSelectionCriteria = {};
				$scope.categoryTreeSelectionCriteria.selectOnlyLeaves = true;
				$scope.categoryTreeSelectionCriteria.defaultSelectedCategory = $scope.categoryForSelectedMovements;
				$scope.toggleCategorySelectionMode();
			};
			
			$scope.toggleCategorySelectionMode = function() {
				$scope.selectCategoryMode = !$scope.selectCategoryMode;

				$("#createNewCategoryPill").removeClass("active");
				$("#createNewCategoryPane").removeClass("active");

				$("#selectExistingCategoryPill").removeClass("active");
				$("#selectExistingCategoryPane").removeClass("active");

				toggleLifeOverlay('select-category-overlay-box', true);
			};
			
			$scope.showObjectProperties = function(obj){
				var keys = [];
				for(var key in obj){
					// console.log("Found key: " + key);
					keys.push(key);
				}
				// console.log("Found keys: " + keys);
				return keys;
			};

			// New
			$scope.addMovement = function() {
				$scope.addMovementCommon();
				
				// $scope.addMode = false;
				$scope.toggleAddMode();
				$scope.newMovement = {};
			};

			// New and Continue
			$scope.addMovementAndContinue = function() {
				$scope.addMovementCommon();
			};

			// Add movement (common code)
			$scope.addMovementCommon = function () {
				lifeAPIservice.addMovement($scope.newMovement).success(function (response) {
					$scope.reloadCurrentList();
				}).error(function (data, status, headers, config) {
					$scope.setWarnMsg("Error saving movement! ");
				});
			};

			$scope.confirmSaveMovement = function(movement) {
				$scope.saveMovement(movement);
				// $scope.editMode = false;
				$scope.toggleEditMode(movement);
				$scope.editMovement = null;
			}

			replaceMovementInList = function (movement) {
				var ind = 0;
				var found = false;
				while (ind < $scope.movements.length && !found) {
					if ($scope.movements[ind].pk == movement.pk) {
						$scope.movements.splice(ind, 1);
						$scope.movements.push(movement);
						found = true;
					}
					ind++;
				}
			}

			// Save
			$scope.saveMovement = function(movement) {
				if ($scope.editMovementReperition != null && $scope.editMovementReperition.repetition.code != ''){
					var baseDate = $scope.editMovementReperition.fromDate;
					var ind = 0;
					while (baseDate < $scope.editMovementReperition.untilDate){
						ind++;
						console.log("Inserting movement #" + ind);
						lifeAPIservice.addMovement(movement).success(function(response) {
							// $scope.reloadCurrentList();
						}).error(function(data, status, headers, config) {
							alert("Error saving movement! ");
							// $scope.reloadCurrentList();
						});
						if ($scope.newModementReperition.repetition.code == 'daily')
						{
							baseDate.setDate(baseDate.getDate() + 1);
						}
						else
						{
							baseDate.setMonth(baseDate.getMonth() + 1);
						}
					}
				}else{
					lifeAPIservice.saveMovement(movement).success(function(response) {
						$scope.reloadCurrentList();
					}).error(function(data, status, headers, config) {
						alert("Error saving movement: " + movement.pk);
						// $scope.reloadCurrentList();
					});
				}
			};

			// Save List
			$scope.saveMovements = function(movements) {
				lifeAPIservice.saveMovements(movements).success(function(response) {
					$scope.reloadCurrentList();
			    }).error(function(data, status, headers, config) {
					alert("Error saving movements: " + JSON.stringify(movements) + ", status: " + status);
					$scope.reloadCurrentList();
			    });
			};

			// Delete
			$scope.deleteMovement = function(movementToDelete) {
				lifeAPIservice.deleteMovement(movementToDelete).success(function(response) {
					$scope.reloadCurrentList();
			    }).error(function(data, status, headers, config) {
					alert("Error deleting movement " + movementToDelete.pk);
					$scope.reloadCurrentList();
			    });;
			};
			
			$scope.changeAllBankAccount = function() {
				var movements = [];
				var changedCurrencyToMovement = false;
				angular.forEach($scope.movements,function(movement,index){
					if (movement.checked){
						movement.bankAccount = $scope.changeBankAccount;
						if (movement.currency.code != movement.bankAccount.currency.code){
							movement.currency = movement.bankAccount.currency;
							changedCurrencyToMovement = true;
							console.log("Changed currency for movement: " + movement.description);
						}
						movements.push(movement);
						movement.checked = false;
					}
	            });
				$scope.saveMovements(movements);
				if (changedCurrencyToMovement){
					alert("Changed currency to at least one movement!");
				}
			};
			
			$scope.changeAllAmount = function() {
				var movements = [];
				angular.forEach($scope.movements,function(movement,index){
					if (movement.checked){
						movement.amount = $scope.changeAmount;
						movements.push(movement);
						movement.checked = false;
					}
	            });
				$scope.saveMovements(movements);
			};

			$scope.changeAllCategory = function() {
				var movements = [];
				angular.forEach($scope.movements,function(movement,index){
					if (movement.checked){
						movement.category = $scope.categoryForSelectedMovements;
						movements.push(movement);
						movement.checked = false;
					}
				});
				$scope.saveMovements(movements);
			};
			
			$scope.changeAllDescription = function() {
				var movements = [];
				angular.forEach($scope.movements,function(movement,index){
					if (movement.checked){
						movement.description = $scope.changeDescription;
						movements.push(movement);
						movement.checked = false;
					}
	            });
				$scope.saveMovements(movements);
			};

			$scope.selectAllVisibleMovements = function(state) {
				angular.forEach($scope.movements,function(mov,index){
					if (isVisible(mov)){
						mov.checked = state;
					}
				});
			};

			isSelectedAndVisible = function(movement) {
				return isSelected(movement) && isVisible(movement);
			}

			isSelected = function(movement) {
				var checked = false;
				if (movement.checked) {
					checked = true;
				}
				return checked;
			}

			isVisible = function(movement) {
				var visible = true;
				if ($scope.movementHideAlreadyCategorised && movement.category != null)
				{
					visible = false;
				}
				if ($scope.movementHideBankTransfers && movement.bankTransfer)
				{
					visible = false;
				}

				return visible;
			}

			$scope.addRemoveBankAccount = function(ba) {
				var newBa = jQuery.extend(true, {}, ba);
				delete newBa.checked;
			};

			$scope.sortMovements = function (mov1, mov2) {
				if (mov1.date == mov2.date) {
					return mov2.pk - mov1.pk;
				} else {
					return mov2.date - mov1.date;
				}
			}
			
			$scope.sortMovements = function (mov1, mov2) {
				if (mov1.date == mov2.date) {
					return mov2.pk - mov1.pk;
				} else {
					return mov2.date - mov1.date;
				}
			}

			$scope.executeBankTransfer = function() {
				lifeAPIservice.addTransfer($scope.newTransfer).success(function(response) {
					$scope.reloadCurrentList();
			    }).error(function(data, status, headers, config) {
					alert("Error creating bank transfer! ");
					$scope.reloadCurrentList();
			    });
				$scope.addTransfer = false;
				$scope.newTransfer = null;
			};
			
			// Update Transfer
			$scope.updateTransfer = function(transfer) {
				console.log("updateTransfed [" + JSON.stringify(transfer) + "]")
				lifeAPIservice.updateTransfer(transfer).success(function(response) {
					// $scope.reloadCurrentList();
			    }).error(function(data, status, headers, config) {
					alert("Error updating transfer: " + transfer.pk);
					// $scope.reloadCurrentList();
			    });
				//$scope.editTransferMode = false;
				$scope.toggleEditTransferMode(transfer);
				$scope.editTransfer = null;
			};

			$scope.getCheckedBankAccounts = function() {
				var retValue = [];
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.checked){
//						console.log("checked bank account: " + ba.accountNumber + " " + ba.checked);
						retValue = retValue.concat(ba);
					}
	            });
				return retValue;
			};
			
			$scope.getBankAccountBalance = function(movement, account){
//				console.log(movement.description + ", " + account.accountNumber);
				var retValue = 0;
				angular.forEach(movement.bankAccountBalances,function(balance,index){
					if (balance.bankAccount.pk == account.pk){
						retValue = balance.balance;
					}
				});
				return retValue;
			};
			
			$scope.isMovementOfAccount = function(movement, account) {
				if (movement != null && account != null) {
					var retValue = movement.bankAccount.pk == account.pk;
					return retValue;
				}
				return false;
			};
			
			$scope.isMovementTransferRelatedToAccount = function(movement, account) {
				if (movement.transferTargetMovement != null){
					var retValue = movement.transferTargetMovement.bankAccount.pk == account.pk;
					return retValue;
				}
				return false;
			};
			
			$scope.isMovementUnrelatedToAccount = function(movement, account) {
				if ($scope.isMovementOfAccount(movement, account) || $scope.isMovementTransferRelatedToAccount(movement, account)){
					return false;
				} else {
					return true;
				}
			};
			
			$scope.isTransferTargetMovement = function(movement){
				return (movement.description.indexOf('Transfer from') == 0);
			};
			
			$scope.isTransferSourceMovement = function(movement){
				return (movement.description.indexOf('Transfer to ') == 0);
			};
			
			$scope.isNotTransferTargetMovement = function(movement){
				return !($scope.isTransferTargetMovement(movement));
			};
			
			$scope.isNotTransferSourceMovement = function(movement){
				return !($scope.isTransferSourceMovement(movement));
			};
			
			$scope.isTransferMovement = function(movement){
				if (movement.description.indexOf('Transfer') == 0){
					return true;
				} else {
					return false;
				}
			};
			
			$scope.getMovementFromList = function(movements, movementPk){
				var retValue = 0;
				angular.forEach(movements,function(movement,index){
					if (movement.pk == movementPk){
						retValue = movement;
					}
				});
				return retValue;
			};
			
			$scope.getValidList = function(movements){
				var retList = [];
				angular.forEach(movements,function(movement,index){
					if ($scope.isNotTransferTargetMovement(movement)){
						retList.push(movement);
					}
				});
				return retList;
			};
			
			$scope.setCurrentMonthFromResponse = function(response){
				var retList = [];
				angular.forEach($scope.months,function(month,index){
					if (month.code == response.month.code){
						$scope.currentMonth = month;
					}
				});
				if ($scope.currentMonth == null){
					$scope.currentMonth = response.month;
				}
				$scope.currentYear = response.year;
				return retList;
			};

			$scope.changeEditTransferAmount = function(){
				var conversionRate = $scope.editTransfer.toMovement.amount / $scope.editTransfer.fromMovement.amount;
				$scope.editTransfer.conversionRate = conversionRate;
			};
			
			$scope.setConversionRate = function() {
				if ($scope.newTransfer.fromBankAccount != null && $scope.newTransfer.toBankAccount){
					var fromCurrency = $scope.newTransfer.fromBankAccount.currency;
					var toCurrency = $scope.newTransfer.toBankAccount.currency;
					lifeAPIservice.getConversionRate(fromCurrency, toCurrency).success(function(response) {
						$scope.newTransfer.conversionRate = response;
				    }).error(function(data, status, headers, config) {
						alert("Error getting conversion rate.");
				    });
				}
			};			

			$scope.bankTransferDateChanged = function(bankTransfer) {
				console.log("bankTransferDateChanged - bankTransfer.fromMovement.date: " + bankTransfer.fromMovement.date);
				if (bankTransfer.toMovement == null)
				{
					bankTransfer.toMovement = {};
				}
				if (bankTransfer.fromMovement.date != null && bankTransfer.toMovement.date == null){
					bankTransfer.toMovement.date = bankTransfer.fromMovement.date;
				}
				if (bankTransfer.fromMovement.date != null && bankTransfer.toMovement.date == null){
					var fromCurrency = $scope.newTransfer.fromBankAccount.currency;
					var toCurrency = $scope.newTransfer.toBankAccount.currency;
					lifeAPIservice.getConversionRate(fromCurrency, toCurrency).success(function(response) {
						$scope.newTransfer.conversionRate = response;
				    }).error(function(data, status, headers, config) {
						alert("Error getting conversion rate.");
				    });
				}
			};

			$scope.setDefaultTime = function(date) {
				console.log("PRE  date: " + date);
				// var newDate = new Date();
				// newDate.setUTCFullYear(date.getFullYear());
				// newDate.setUTCMonth(date.getMonth());
				// newDate.setUTCDate(date.getDate());
				// newDate.setUTCHours(12);
				// date = newDate;
				date.setHours(12);
				console.log("POST input.value: " + date);
			}
			
			$scope.bankTransferAmountChanged = function(bankTransfer) {
				console.log("bankTransferAmountChanged");
				if (bankTransfer.toMovement == null)
				{
					bankTransfer.toMovement = {};
				}
				if (bankTransfer.fromMovement != null && bankTransfer.fromMovement.amount != null){
					if (bankTransfer.fromBankAccount != null && bankTransfer.toBankAccount != null)
					{
						console.log("bankTransferAmountChanged - setting amount with conversion");
						var fromCurrency = bankTransfer.fromBankAccount.currency;
						var toCurrency = bankTransfer.toBankAccount.currency;
						lifeAPIservice.getConversionRate(fromCurrency, toCurrency).success(function(response) {
							bankTransfer.toMovement.amount = bankTransfer.fromMovement.amount * response;
						}).error(function(data, status, headers, config) {
							alert("Error getting conversion rate from [" + fromCurrency.code + "] to [" + toCurrency.code + "]");
						});
					}
					else 
					{
						console.log("bankTransferAmountChanged - setting amount without conversion");
						bankTransfer.toMovement.amount = bankTransfer.fromMovement.amount;
					}
				}
			};

			$scope.setWarnMsg = function(msg, delay){
				if (delay == null) {
					delay = $scope.defaultMsgDelay;
				}
				$scope.warnMsg = msg;
				$("#warnMsgDiv").show().delay(delay).fadeOut();
				document.getElementById('msg-divs').scrollIntoView();
			}

			$scope.setInfoMsg = function(msg, delay){
				$scope.infoMsg = msg;
				$("#infoMsgDiv").show().delay(delay).fadeOut();
				document.getElementById('msg-divs').scrollIntoView();
			}

			/******************************************
			 * DOCUMENT -> READY
			 ******************************************/
			angular.element("document").ready(function(){
				console.log("do something when document is ready ... moving 'add-movement-overlay-box' to body");
				$("#add-movement-overlay-box").prependTo($("body"));
				// Actions at startup
				$scope.searchAllMonths();
				$scope.searchBankAccounts();
				$scope.searchCurrencies();
				$scope.searchFrequencyTypes();
			});

}).controller('importMovementsController',
		function($scope, lifeAPIservice) {
			$scope.importForm = {};
			$scope.bankAccounts = [];
	        $scope.importMovements = [];
	        $scope.existingMovements = [];
	        $scope.existingMovementsMap = [];
	        $scope.deleteUnmatchedMovements = false;
	        $scope.toProcess = 0;
	        $scope.countProcessed = 0;
	        $scope.countProcessedWithError = 0;
			$scope.yearlyMatchesJson = [];
			$scope.years = ['2015','2016','2017'];
			$scope.tableMode = false;
			$scope.loading = false;

			/**
			 * ImportMov additional attributes
			 * - skip
			 * - bankTransfer
			 * - createNew
			 */

			/**
			 * ExistingMov additional attributes
			 * - deleteOnSubmit
			 */

			/**
			 * DataPicker (START)
			 */
			$scope.today = function() {
				$scope.dt = new Date();
			};
			$scope.today();

			$scope.showWeeks = true;
			$scope.toggleWeeks = function() {
				$scope.showWeeks = !$scope.showWeeks;
			};

			$scope.clear = function() {
				$scope.dt = null;
			};

			// Disable weekend selection
			$scope.disabled = function(date, mode) {
				return false;
			};

			$scope.toggleMin = function() {
				$scope.minDate = ($scope.minDate) ? null : new Date();
			};
			$scope.toggleMin();

			$scope.openDate = function($event, ind) {
				console.log("executing $scope.open ...");
				$event.preventDefault();
				$event.stopPropagation();
			};

			$scope.open = function($event, opened) {
				console.log("executing $scope.open ...");
				$event.preventDefault();
				$event.stopPropagation();

				$scope[opened] = true;
			};

			$scope.dateOptions = {
				'year-format' : "'yy'",
				'starting-day' : 1
			};

			$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
				'shortDate' ];
			$scope.format = $scope.formats[0];
			/**
			 * DataPicker (END)
			 */

			/******************************************
	         * SELECT EXISTING MATCHING MOVEMENT
	         ******************************************/
			$scope.selectExistingMovement = function(importMov, matchingMovPk){
                // console.log("selectExistingMovement for importMov [" + importMov.pk + "], matchingMovPk [" + matchingMovPk + "], selectedExistingMov [" + importMov.selectedExistingMov + "]")

                // IF SELECTED DIFFERENT VALUE
                if (importMov.selectedExistingMov != matchingMovPk)
                {
                    // IF REAL EXISTING MOVEMENT SELECTED (NOT CREATE_NEW)
                    if (matchingMovPk != null)
                    {
                        // SEARCH FOR EXISTING MOVEMENT ALREADY SELECTED IN OTHER IMPORTED MOV
                        var oldValue = $scope.getSelectedForObject(matchingMovPk);
						// console.log("oldValue: " + oldValue);
                        if (oldValue != null)
                        {
                            // console.log("already selected for [" + oldValue.pk + "]")
                            oldValue.selectedExistingMov = null;
                        }
                        $scope.setSelectedForValue(matchingMovPk, importMov.pk);
                    }

                    // CLEAR IMPORT MOVEMENT
                    $scope.setSelectedForValue(importMov.selectedExistingMov, null);
                    importMov.selectedExistingMov = matchingMovPk;
                }

				// POPULATE BANK TRANSFER DATA
				var existingMov = $scope.getExistingMovementByPk(matchingMovPk);
				if (existingMov != null && existingMov.bankTransfer != null)
				{
                    console.log("Setting bank transfer details ...")
					if (importMov.importedBankAccountMovement.eu == "E")
					{
                        importMov.otherDate = existingMov.bankTransfer.fromMovement.date;
                        importMov.otherAmount = existingMov.bankTransfer.fromMovement.amount;
                        angular.forEach($scope.bankAccounts,function(ba,index){
                            if (existingMov.bankTransfer.fromBankAccount.pk == ba.pk)
                            {
                                importMov.otherBankAccount = ba;
                            }
                        });
					}
					else
					{
                        importMov.otherDate = existingMov.bankTransfer.toMovement.date;
                        importMov.otherAmount = existingMov.bankTransfer.toMovement.amount;
                        angular.forEach($scope.bankAccounts,function(ba,index){
                            if (existingMov.bankTransfer.toBankAccount.pk == ba.pk)
                            {
                                importMov.otherBankAccount = ba;
                            }
                        });
					}
				}
			};

			/**
			 * GET CORRESPONDING IMPORT MOVEMENT PK FOR EXISTING SELECTED ONE
			 */
			$scope.getSelectedForValue = function(matchingMovPk){
				if ($scope.existingMovementsMap[matchingMovPk] == null)
				{
					$scope.existingMovementsMap[matchingMovPk] = {}
					$scope.existingMovementsMap[matchingMovPk].selectedFor = null;
				}
				return $scope.existingMovementsMap[matchingMovPk].selectedFor;
			};
			
			$scope.getSelectedForObject = function(matchingMovPk){
				if ($scope.existingMovementsMap[matchingMovPk] == null
						|| $scope.existingMovementsMap[matchingMovPk].selectedFor == null)
				{
					// console.log("getSelectedForObject, existingMovementsMap[" + matchingMovPk + "] = " + $scope.existingMovementsMap[matchingMovPk]);
					return null;
				}
				// console.log("getSelectedForObject, existingMovementsMap[" + matchingMovPk + "].selectedFor = " + $scope.existingMovementsMap[matchingMovPk].selectedFor);
				var ind = 0;
				while (ind < $scope.importMovements.length){
					var mov = $scope.importMovements[ind];
					if (mov.pk == $scope.existingMovementsMap[matchingMovPk].selectedFor)
					{
						return mov;
					}
					ind++;
				};
			};
			
			$scope.setSelectedForValue = function(matchingMovPk, value){
				if ($scope.existingMovementsMap[matchingMovPk] == null)
				{
					$scope.existingMovementsMap[matchingMovPk] = {}
				}
				$scope.existingMovementsMap[matchingMovPk].selectedFor = value;
			};
			
	        /******************************************
	         * RETURNS TRUE IN CASE THE MATCHING MOV IS 
	         * CURRENTLY CHECKED FOR THE IMPORT MOV
	         ******************************************/
			$scope.isChecked = function(mov, matchingMovPk){
				if (matchingMovPk == null)
				{
					if ($scope.replaceAllMovements || mov.selectedExistingMov == null || mov.selectedExistingMov == 0)
					{
						return true;
					}
				}
				else
				{
					if ($scope.getSelectedForValue(matchingMovPk) == mov.pk && !$scope.replaceAllMovements)
					{
						return true;
					}
				}
				return false;
			};
			
	        /******************************************
			 * PARSE MOVEMENTS FROM TEXT
			 ******************************************/ 
			$scope.parseMovementsForBankAccount = function() {
				console.log("parseMovementsForBankAccount for form [" + JSON.stringify($scope.importForm) + "] ...");
				$scope.loading = true;
				$("#btnParseMovements").attr('disabled', true);
				// Import movements
				lifeAPIservice.parseMovementsForBankAccount(
						$scope.importForm,
						$scope).success(
								function(response) {
									// console.log("parseMovementsForBankAccount: " + JSON.stringify(response));
                                    $scope.importMovements = response.importMovements;
                                    $scope.existingMovements = response.existingMovements;
									angular.forEach(response.importMovements,function(mov,index){
										mov.pk = index+1;
										if (mov.bankTransfer == true)
										{
											angular.forEach($scope.bankAccounts,function(bankAccount,index1){
                                                if (bankAccount.displayName == mov.otherBankAccount.displayName)
                                                {
                                                    mov.otherBankAccount = bankAccount;
                                                    if (mov.otherBankAccount.displayName == mov.otherBankAccount.displayName)
                                                    {
                                                        mov.otherAmount = mov.importedBankAccountMovement.amount;
                                                        mov.otherDate = mov.importedBankAccountMovement.date;
                                                    }
                                                }
											});
                                            if (mov.matchingBankAccountMovements != null && mov.matchingBankAccountMovements.length == 1){
                                                console.log("Selecting default movement for Bank Account [" + mov.importedBankAccountMovement.description + "]");
                                                var matchingMovPk = mov.matchingBankAccountMovements[0].pk;
												if (!alreadyMatchedWithHigherPriority(matchingMovPk, mov.matchingPriority, $scope.importMovements)) {
													$scope.selectExistingMovement(mov, matchingMovPk);
												}
                                            }
										}
										else if (mov.matchingBankAccountMovements != null && mov.matchingBankAccountMovements.length == 1){
											var matchingMovPk = mov.matchingBankAccountMovements[0].pk;
											if (!alreadyMatchedWithHigherPriority(matchingMovPk, mov.matchingPriority, $scope.importMovements)) {
												$scope.selectExistingMovement(mov, matchingMovPk);
												console.log("Set matching movement [" + matchingMovPk + "] for [" + mov.importedBankAccountMovement.description + "]");
											}
										}
										else if (mov.matchingBankAccountMovements == null || mov.matchingBankAccountMovements.length == 0){
											mov.createNew = true;
										}
									});
									$("#btnParseMovements").attr('disabled', false);
									$scope.loading = false;
								}).error(function(data, status, headers, config) {
									alert("Error importing movements: " + status + ", " + JSON.stringify(data));
									$("#btnParseMovements").attr('disabled', false);
									$scope.loading = false;
							    });;
			};

			function alreadyMatchedWithHigherPriority(matchingMovPk, currentPriority, importMovements){
				console.log("alreadyMatchedWithHigherPriority(" + matchingMovPk + ", " + currentPriority + ", " + importMovements.length + ")");
				var highestPriority = 999;
				angular.forEach(importMovements, function(mov, index){
					if (containsMovementPk(matchingMovPk, mov.matchingBankAccountMovements)){
						if (mov.matchingPriority < highestPriority){
							highestPriority = mov.matchingPriority;
						}
					}
				});
				var retValue = highestPriority < currentPriority;
				console.log("retValue: [" + retValue + "]");
				return retValue;
			}

			function containsMovementPk(pk, movements){
				var ind = 0;
				while (ind < movements.length){
					if (movements[ind].pk == pk){
						return true;
					}
					ind++
				}
				return false;
			}
			
	        /******************************************
			 * LOAD MOVEMENTS FROM DB
			 ******************************************/ 
			$scope.loadMovementsForBankAccount = function(month) {
				$scope.importForm.month = month.code;
				$scope.loading = true;
				console.log("loadMovementsForBankAccount for form [" + JSON.stringify($scope.importForm) + "] ...");
				// Load movements
				lifeAPIservice.loadMovementsForBankAccount(
						$scope.importForm,
						$scope
                    ).success(
                        function(response){
                            return $scope.processParseResponse(response);
                        }
                    ).error(
                        function(data, status, headers, config) {
							alert("Error importing movements: " + status + ", " + JSON.stringify(data));
							$scope.loading = false;
						}
                    );
			};

            $scope.processParseResponse = function(response) {
                $scope.importMovements = response.importMovements;
                $scope.existingMovements = response.existingMovements;
                angular.forEach(response.importMovements,function(mov,index){
                    mov.pk = index+1;
					if (mov.bankTransfer == true)
                    {
                        angular.forEach($scope.bankAccounts,function(bankAccount,index1){
                            if (bankAccount.displayName==mov.otherBankAccount.displayName)
                            {
                                mov.otherBankAccount = bankAccount;
                            }
                        });
                        if (mov.matchingBankAccountMovements != null && mov.matchingBankAccountMovements.length == 1){
                            console.log("Only 1 matching movement for [" + mov.importedBankAccountMovement.description + "]");
                            var matchingMovPk = mov.matchingBankAccountMovements[0].pk;
							if (!alreadyMatchedWithHigherPriority(matchingMovPk, mov.matchingPriority, $scope.importMovements)) {
								$scope.selectExistingMovement(mov, matchingMovPk);
								console.log("Set matching movement [" + matchingMovPk + "] for [" + mov.importedBankAccountMovement.description + "]");
							}
	                    }
                    }
                    else if (mov.matchingBankAccountMovements != null && mov.matchingBankAccountMovements.length == 1){
                        var matchingMovPk = mov.matchingBankAccountMovements[0].pk;
						if (!alreadyMatchedWithHigherPriority(matchingMovPk, mov.matchingPriority, $scope.importMovements)) {
							$scope.selectExistingMovement(mov, matchingMovPk);
						}
                    }
                });
				$scope.loading = false;
            };

	        /******************************************
			 * LOAD MOVEMENTS FROM TEXT
			 ******************************************/
			$scope.checkMatchingImport = function(year) {
				$scope.importForm.month = month.code;
				console.log("checkMatchingImport for form [" + JSON.stringify($scope.importForm) + "] ...");
				// Load movements
				lifeAPIservice.checkMatchingImport(
						$scope.importForm,
						$scope).success(
								function(response) {
									console.log("response.status: " + response.status);
								}).error(function(data, status, headers, config) {
									alert("Error importing movements: " + status + ", " + JSON.stringify(data));
							    });;
			};

			/******************************************
			 * GET BANK ACCOUNTS
			 ******************************************/ 
			$scope.searchBankAccounts = function() {
				console.log('getting bankAccounts ...');
				// Search for current month
				lifeAPIservice.getBankAccounts().success(
						function(response) {
							$scope.bankAccounts = response;
							// $scope.importForm.bankAccount = $scope.bankAccounts[3];
						});
			};
			
	        /******************************************
			 * CONFIRM IMPORT
			 ******************************************/ 
			$scope.confirmImport = function() {
				console.log("************ CONFIRM SUBMIT");

				$scope.countProcessed = 0;
				$scope.countProcessedWithError = 0;

				// verify imprt movement are either New or Update
				if ($scope.verifyAllMovementsAreImportable())
				{
					$("#confirmImport").attr('disabled', true);

					console.log("Import confirmed!");
					// delete existing movements
					$scope.deleteExistingMovements($scope.deleteUnmatchedMovements, $scope.replaceAllMovements);

					// insert/update movements
					$scope.insertUpdateMovements();
				}
				else
				{
					console.log("Import aborted!");
				}

			};

			/**********************************************************
			 * VERIFY IMPORT MOVEMENTS ARE EITHER SKIP / NEW / UPDATE
			 **********************************************************/
			$scope.verifyAllMovementsAreImportable = function()
			{
				var allGood = true;
				var errorMsg = [];
				angular.forEach($scope.importMovements,function(importMov,index){
					if (importMov.skip)
					{
						console.log("verifyAllMovementsAreImportable - [" + importMov.importedBankAccountMovement.description + "] -> OK, Skip");
					}
					else if (importMov.createNew)
					{
						if (!importMov.bankTransfer)
						{
							console.log("verifyAllMovementsAreImportable - [" + importMov.importedBankAccountMovement.description + "] -> OK, Create New");
						}
						else if (importMov.otherBankAccount!=null && importMov.otherDate!=null && importMov.otherAmount!=null)
						{
							console.log("verifyAllMovementsAreImportable - [" + importMov.importedBankAccountMovement.description + "] -> OK, Create New Transfer");
						}
						else
						{
							console.log("importMov.otherBankAccount [" + importMov.otherBankAccount + "]");
							console.log("importMov.otherDate [" + importMov.otherDate + "]");
							console.log("importMov.otherAmount [" + importMov.otherAmount + "]");
							var msg = "Don't know how to Create New Trasfer for [" + importMov.importedBankAccountMovement.description.substring(0,20) + "] ...]";
							console.log(msg);
							errorMsg.push(msg);
							allGood = false;
						}
					}
					else if ((importMov.selectedExistingMov != null && importMov.selectedExistingMov != 0))
					{
						if (!importMov.bankTransfer)
						{
							console.log("verifyAllMovementsAreImportable - [" + importMov.importedBankAccountMovement.description + "] -> OK, Updating [" + importMov.selectedExistingMov + "]");
						}
						// else if (importMov.otherBankAccount != null && importMov.otherDate!=null && importMov.otherAmount!=null)
						// {
						// 	var msg = "Cannot update a bank Transfer ... [" + importMov.importedBankAccountMovement.description.substring(0,20) + "] ...]";
						// 	console.log(msg);
						// 	errorMsg.push(msg);
						// 	allGood = false;
						// }
						// else
						// {
						// 	console.log("importMov.otherBankAccount [" + importMov.otherBankAccount + "]");
						// 	console.log("importMov.otherDate [" + importMov.otherDate + "]");
						// 	console.log("importMov.otherAmount [" + importMov.otherAmount + "]");
						// 	var msg = "Don't know how to Update Transfer for [" + importMov.importedBankAccountMovement.description.substring(0,20) + "] ...]";
						// 	console.log(msg);
						// 	errorMsg.push(msg);
						// 	allGood = false;
						// }
					}
					else if (importMov.bankTransfer && importMov.otherBankAccount != null)
					{
						console.log("verifyAllMovementsAreImportable - [" + importMov.importedBankAccountMovement.description + "] -> OK, Creating new Bank Transfer. OtherBankAccount [" + importMov.otherBankAccount + "]");
					}
					else
					{
						var msg = "Don't know what to do with [" + importMov.importedBankAccountMovement.description.substring(0,20) + "] ...]";
						console.log(msg);
						errorMsg.push(msg);
						allGood = false;
					}
				});
				$scope.setWarnMsgs(errorMsg);
				return allGood;
			}

			/*************************************
			 * DELETE EXISTING MOVEMENTS
			 *************************************/
			$scope.deleteExistingMovements = function(deleteAllUnmatched, replaceAll){
		        var movementsToDelete = [];
				console.log("$scope.deleteExistingMovements(" + deleteAllUnmatched + ")");
				if (deleteAllUnmatched || replaceAll)
				{
					angular.forEach($scope.existingMovements,function(existingMov,index){
						if (replaceAll || $scope.existingMovementsMap[existingMov.pk].selectedFor == null){
							movementsToDelete.push(existingMov);
					        $scope.addToProcess();
						}
					});
				}
				else
				{
					angular.forEach($scope.existingMovements,function(existingMov,index){
						if (existingMov.deleteOnSubmit){
							movementsToDelete.push(existingMov);
					        $scope.addToProcess();
						}
					});
				}
				// ACTUAL DELETE
				angular.forEach(movementsToDelete,function(existingMov,index){
					$scope.deleteExistingMovement(existingMov);
				});
			}

			/*************************************
			 * INSERT / UPDATE MOVEMENTS
			 *************************************/
			$scope.insertUpdateMovements = function(deleteAll){
				var movementsToInsert = [];
				var movementsToUpdate = [];
				var transfersToInsert = [];
				var transfersToUpdate = [];
				angular.forEach($scope.importMovements,function(mov,index){
					if (!mov.skip)
					{
						if (mov.bankTransfer)
						{
							if ($scope.replaceAllMovements || mov.selectedExistingMov == null || mov.selectedExistingMov == 0)
							{
								console.log("Index " + mov.pk + " (" + $scope.getMovementDescription(mov.pk) + "), creating new bank transfer");
								transfersToInsert.push(mov);
								$scope.addToProcess();
							}
							else
							{
								console.log("Index " + mov.pk + " (" + $scope.getMovementDescription(mov.pk) + "), updating existing bank transfer [" + mov.selectedExistingMov + ", " + $scope.getExistingMovementByPk(mov.selectedExistingMov) + "]")
								var transferToUpdate = $scope.mergeExistingTransfer(mov, $scope.getExistingMovementByPk(mov.selectedExistingMov));
								transfersToUpdate.push(transferToUpdate);
								$scope.addToProcess();
							}
						}
						else
						{
							if ($scope.replaceAllMovements || mov.createNew)
							{
								console.log("Index " + mov.pk + " (" + $scope.getMovementDescription(mov.pk) + "), insert new")
								movementsToInsert.push(mov.importedBankAccountMovement);
								$scope.addToProcess();
							}
							else
							{
								console.log("Index " + mov.pk + " (" + $scope.getMovementDescription(mov.pk) + "), updating existing mov [" + mov.selectedExistingMov + ", " + $scope.getExistingMovementByPk(mov.selectedExistingMov).description + "]")
								var movementToUpdate = $scope.mergeExistingMovement(mov.importedBankAccountMovement, $scope.getExistingMovementByPk(mov.selectedExistingMov));
								movementsToUpdate.push(movementToUpdate);
								$scope.addToProcess();
							}
						}
					}
					else
					{
						console.log("Skipping movement [" + mov.importedBankAccountMovement.description + "]");
					}
				});
				// INSERT MOVEMENTS
				angular.forEach(movementsToInsert,function(mov,index){
					$scope.insertMovement(mov);
				});
				// UPDATE MOVEMENTS
				angular.forEach(movementsToUpdate,function(mov,index){
					$scope.updateExistingMovement(mov);
				});
				// INSERT TRANSFERS
				angular.forEach(transfersToInsert,function(mov,index){
					$scope.insertTransfer(mov);
				});
				// INSERT TRANSFERS
				angular.forEach(transfersToUpdate,function(transfer,index){
					$scope.updateTransfer(transfer);
				});
			}
			
			$scope.insertBankTransfer = function(){
				
			}
			
			$scope.copyPasteDescription = function(existingMov,importMov){
				importMov.description = existingMov.description;
			}
			
			$scope.getMovementDescription = function(pk){
				var retValue = "";
				angular.forEach($scope.importMovements, function(mov,index){
					if (mov.pk == pk)
					{
						retValue = mov.importedBankAccountMovement.description;
					}
				});
				return retValue;
			}
			
			$scope.getImportMovementByPk = function(pk){
				var retValue = null;
				angular.forEach($scope.importMovements, function(mov,index){
					if (mov.pk == pk)
					{
						retValue = mov;;
					}
				});
				return retValue;
			}

			$scope.isSelectedAndUsed = function (existingMov) {
				if ($scope.existingMovementsMap[existingMov.pk].selectedFor != null) {
					if (!$scope.getImportMovementByPk($scope.existingMovementsMap[existingMov.pk].selectedFor).createNew) {
						return true;
					}
				}
				return false;
			}
			
			$scope.getExistingMovementByPk = function(pk){
				var retValue = null;
				angular.forEach($scope.existingMovements, function(mov,index){
					if (mov.pk == pk)
					{
						retValue = mov;
					}
				});
				if (retValue == null){
					console.log("couldn't find existing movement by PK [" + pk + "]")
				}
				return retValue;
			}
			
			$scope.addToProcess = function(){
				$scope.toProcess = $scope.toProcess + 1;
			}
			
			$scope.addProcessed = function(withError){
				$scope.countProcessed = $scope.countProcessed + 1;
				if (withError)
				{
					$scope.countProcessedWithError = $scope.countProcessedWithError + 1;
				}
		        if ($scope.countProcessed == $scope.toProcess)
	        	{
					if ($scope.countProcessedWithError > 0)
					{
						$scope.setWarnMsg("Total Processed [" + $scope.countProcessed + "]. With Errors [" + $scope.countProcessedWithError + "]");
					}
					else
					{
						$scope.setInfoMsg("Total Processed [" + $scope.countProcessed + "]. With Errors [" + $scope.countProcessedWithError + "]");
					}
			        $scope.importMovements = [];
			        $scope.existingMovements = [];
			        $scope.existingMovementsMap = [];
					$("#confirmImport").attr('disabled', false);
	        	}
			}

			$scope.setWarnMsg = function(msg){
				var array = [];
				array.push(msg);
				$scope.setWarnMsgs(array);
			}

			$scope.setWarnMsgs = function(msgs){
				$scope.warnMsgs = msgs;
				$("#msg-divs").show().delay(30000).fadeOut();
				document.getElementById('container').scrollIntoView();
			}

			$scope.setInfoMsg = function(msg){
				var array = [];
				array.push(msg);
				$scope.setInfoMsgs(array);
			}

			$scope.setInfoMsgs = function(msgs){
				$scope.infoMsgs = msgs;
				$("#msg-divs").show().delay(30000).fadeOut();
				document.getElementById('container').scrollIntoView();
			}

			/******************
			 * DELETE MOVEMENT
			 ******************/
			$scope.deleteExistingMovement = function(existingMov){
				console.log("deleting existing mov [" + existingMov.pk + ", " + existingMov.description + "]")
				console.log("JSON: " + JSON.stringify(existingMov));
				delete existingMov.deleteOnSubmit;
				lifeAPIservice.deleteMovement(existingMov).success(function(response) {
					console.log("Movement deleted [" + existingMov.pk + ", " + existingMov.description + "]");
					$scope.addProcessed(false);
			    }).error(function(data, status, headers, config) {
					console.warn("Error deleting movement [" + existingMov.pk + ", " + existingMov.description + "]: " + status);
					$scope.addProcessed(true);
			    });;
			}
			
			/******************
			 * MERGE MOVEMENTS
			 ******************/
			$scope.mergeExistingMovement = function(importMov, existingMov){
				console.log("mergeExistingMovement [" + existingMov.description +"] with [" + importMov.description +"]");
				existingMov.date = importMov.date;
				existingMov.description = importMov.description;
				existingMov.amount = importMov.amount;
				return existingMov;
			}
			
			/******************
			 * MERGE MOVEMENTS
			 ******************/
			$scope.mergeExistingTransfer = function(importMov, existingMov){
				console.log("mergeExistingTransfer \nIMPORT_MOV\n" + JSON.stringify(importMov) +"\nEXISTING_MOV\n" + JSON.stringify(existingMov));
                if (importMov.importedBankAccountMovement.eu == "E")
                {
                    existingMov.bankTransfer.fromMovement.bankAccount = importMov.otherBankAccount;
					if (importMov.otherDate != null){
						existingMov.bankTransfer.fromMovement.date = importMov.otherDate;
					}else {
						existingMov.bankTransfer.fromMovement.date = importMov.importedBankAccountMovement.date;
					}
					if (importMov.otherAmount){
						existingMov.bankTransfer.fromMovement.amount = importMov.otherAmount;
					}else{
						existingMov.bankTransfer.fromMovement.amount = importMov.importedBankAccountMovement.amount;
					}
                    existingMov.bankTransfer.toMovement.bankAccount = importMov.importedBankAccountMovement.bankAccount;
                    existingMov.bankTransfer.toMovement.date = importMov.importedBankAccountMovement.date;
                    existingMov.bankTransfer.toMovement.amount = importMov.importedBankAccountMovement.amount;
                }
                else
                {
                    existingMov.bankTransfer.fromMovement.bankAccount = importMov.importedBankAccountMovement.bankAccount;
                    existingMov.bankTransfer.fromMovement.date = importMov.importedBankAccountMovement.date;
                    existingMov.bankTransfer.fromMovement.amount = importMov.importedBankAccountMovement.amount;

                    existingMov.bankTransfer.toMovement.bankAccount = importMov.otherBankAccount;
                    existingMov.bankTransfer.toMovement.date = importMov.otherDate;
                    existingMov.bankTransfer.toMovement.amount = importMov.otherAmount;
					if (importMov.otherDate != null){
						existingMov.bankTransfer.toMovement.date = importMov.otherDate;
					}else {
						existingMov.bankTransfer.toMovement.date = importMov.importedBankAccountMovement.date;
					}
					if (importMov.otherAmount){
						console.log("1) existingMov.bankTransfer.toMovement.amount = " + importMov.otherAmount);
						existingMov.bankTransfer.toMovement.amount = importMov.otherAmount;
					}else{
						console.log("1) existingMov.bankTransfer.toMovement.amount = " + importMov.importedBankAccountMovement.amount);
						existingMov.bankTransfer.toMovement.amount = importMov.importedBankAccountMovement.amount;
					}
                }
				return existingMov.bankTransfer;
			}

			/******************
			 * UPDATE MOVEMENT
			 ******************/
			$scope.updateExistingMovement = function(existingMov){
				delete existingMov.deleteOnSubmit;
				lifeAPIservice.saveMovement(existingMov).success(function(response) {
					console.log("Movement updated [" + existingMov.description + "], " + existingMov.description + "]");
					$scope.addProcessed(false);
			    }).error(function(data, status, headers, config) {
					console.warn("Error updating movement [" + existingMov.description + "], status [" + status + "]");
					$scope.addProcessed(true);
			    });
			}
			
			/******************
			 * INSERT MOVEMENT
			 ******************/
			$scope.insertMovement = function(importMov){
				console.log("Inserting [" + importMov.pk +"], [" + importMov.description +"]");
				console.log("JSON: " + JSON.stringify(importMov));
				delete importMov.skip;
				var newMovement = {};
				newMovement.movement = importMov;
				lifeAPIservice.addMovement(newMovement).success(function(response) {
					console.log("Movement created [" + importMov.pk + ", " + importMov.description + "]");
					$scope.addProcessed(false);
			    }).error(function(data, status, headers, config) {
					console.warn("Error creating movement [" + importMov.description + "] ");
					$scope.addProcessed(true);
			    });
			}
			
			/******************
			 * INSERT TRANSFER
			 ******************/
			$scope.insertTransfer = function(importMov){
				var newTransfer = {};
				if (importMov.importedBankAccountMovement.eu == "E")
				{
					newTransfer.fromBankAccount = importMov.otherBankAccount;
					newTransfer.fromMovement = {};
					newTransfer.fromMovement.date = importMov.importedBankAccountMovement.date;
					newTransfer.fromMovement.amount = importMov.importedBankAccountMovement.amount;

					newTransfer.toBankAccount = importMov.importedBankAccountMovement.bankAccount;
					newTransfer.toMovement = importMov.importedBankAccountMovement;
				}
				else
				{
					newTransfer.fromBankAccount = importMov.importedBankAccountMovement.bankAccount;
					newTransfer.fromMovement = importMov.importedBankAccountMovement;

					newTransfer.toBankAccount = importMov.otherBankAccount;
					newTransfer.toMovement = {};
					newTransfer.toMovement.date = importMov.importedBankAccountMovement.date;
					newTransfer.toMovement.amount = importMov.importedBankAccountMovement.amount;
				}

				console.log("insertTransfer -> newTransfer: " + JSON.stringify(newTransfer));
				lifeAPIservice.addTransfer(newTransfer).success(function(response) {
					console.log("Transfer created [" + importMov.pk + ", " + importMov.description + "]");
					$scope.addProcessed(false);
			    }).error(function(data, status, headers, config) {
					console.warn("Error creating bank transfer! Status: " + status);
					$scope.addProcessed(true);
			    });
			}

			/******************
			 * INSERT TRANSFER
			 ******************/
			$scope.updateTransfer = function(transfer){
				console.log("updateTransfer -> transfer: " + JSON.stringify(transfer));
				lifeAPIservice.updateTransfer(transfer).success(function(response) {
					console.log("Transfer updated [" + transfer.pk + ", " + transfer.description + "]");
					$scope.addProcessed(false);
			    }).error(function(data, status, headers, config) {
					console.warn("Error creating bank transfer! Status: " + status);
					$scope.addProcessed(true);
			    });
			}

			/******************
			 * TOGGLE EDIT MODE
			 ******************/
			$scope.toggleEditMode = function(movement) {
				if (movement.editMode == null){
					movement.editMode = true;
				} else {
					movement.editMode = !movement.editMode;
				}
			};
			
			/***********************
			 * TOGGLE EXPANDED MODE
			 ***********************/
			$scope.toggleExpandMovement = function(movement) {
				movement.expanded = !movement.expanded;
			};
			
			/**************************
			 * CONFIRM NEW DESCRIPTION
			 **************************/
			$scope.confirmNewDescription = function(movement, newDescription) {
				movement.description = newDescription;
			};
			
			/**************************
			 * GET CONVERSION RATE
			 **************************/
			$scope.setConversionRate = function(importMov) {
				if (importMov.otherBankAccount != null)
				{
					var fromCurrency = importMov.importedBankAccountMovement.bankAccount.currency;
					var toCurrency = importMov.otherBankAccount.currency;
					lifeAPIservice.getConversionRate(fromCurrency, toCurrency).success(function(response) {
						importMov.conversionRate = response;
				    }).error(function(data, status, headers, config) {
						console.warn("Error getting conversion rate.");
				    });
				}
			};

			/**************************
			 * GET AVAILABLE IMPORTS
			 **************************/
			$scope.searchAvailableImports = function(year) {
				console.log("searchAvailableImports: " + JSON.stringify(year));
				if ($scope.importForm.bankAccount == null){
					$scope.setWarnMsg("select a Bank Account");
					return;
				}
				console.log("searchAvailableImports(" + $scope.importForm.bankaccount + ", " + year + ")");
				$scope.importForm.year = year;
				lifeAPIservice.searchAvailableImports($scope.importForm.bankAccount, year, $scope)
					.success(function(response) {
						console.log('availableImports: ' + JSON.stringify(response));
					});
			};
			
			/**********************************
			 * CHECK IF MOVEMENT TO BE SKIPPED
			 **********************************/
			$scope.checkIfMovementToBeSkipped = function(importMov) {
				if (importMov.selectedExistingMov != null){
					var existingMov = $scope.getExistingMovementByPk(importMov.selectedExistingMov);
					if (importMov.importedBankAccountMovement.description == existingMov.description)
					{
						console.log("Marking movement as 'to be skipped' as it's already imported correctly [" + importMov.importedBankAccountMovement.description + "]")
						importMov.skip = true;
						importMov.expanded = false;
					}
				}
			};

			/******************************************
			 * SHOULD SHOW DAILY MATCH IN COCKPIT
			 ******************************************/
			$scope.shouldHidePotentialMatchingMovement = function(importMov, existingMov)
			{
				var retValue = false;
				// console.log("shouldHidePotentialMatchingMovement - [" + importMov.importedBankAccountMovement.description.substring(0,10) + "], [" + existingMov.description.substring(0,10) + "], Bank Transfer [" + importMov.bankTransfer + "]");
				if (importMov.bankTransfer && existingMov.bankTransfer == null)
				{
					// console.log("imp [true], existing [false]");
					retValue = true;
				}
				if ((!importMov.bankTransfer) && existingMov.bankTransfer != null)
				{
					// console.log("imp [false], existing [true]");
					retValue = true;
				}
				if (importMov.expanded == false)
				{
					// console.log("not expanded");
					retValue = true;
				}
				if (importMov.skip)
				{
					// console.log("skip");
					retValue = true;
				}
				if (importMov.createNew)
				{
					// console.log("create new");
					retValue = true;
				}
				// console.log("shouldHidePotentialMatchingMovement - retValue [" + retValue + "]");
				return retValue;
			}

			$scope.toggleCreateNewTransfer = function(importMov) {
				if (importMov.createNew)
				{
					importMov.otherBankAccount = null;
					importMov.otherDate = importMov.importedBankAccountMovement.date;
					importMov.otherAmount = importMov.importedBankAccountMovement.amount;
				}
				else
				{
					if (importMov.selectedExistingMov == null)
					{
						importMov.otherBankAccount = null;
						importMov.otherDate = null;
						importMov.otherAmount = null;
					}
					else
					{
						$scope.selectExistingMovement(importMov, importMov.selectedExistingMov);
					}
				}
			}

			$scope.isNew = function(importMov) {
				return (!importMov.bankTransfer) && importMov.selectedExistingMov == null || importMov.selectedExistingMov == 0;
			}

			$scope.markNew = function() {
				console.log("markNew");
				angular.forEach($scope.importMovements,function(mov,index){
					if ($scope.isNew(mov)){
						mov.createNew = true;
					}
				});
			}

			/******************************************
			 * TRIGGER SEARCH MOVEMENTS
			 ******************************************/ 
			$scope.searchBankAccounts();
			
	        /******************************************
			 * DOCUMENT -> READY
			 ******************************************/ 
			angular.element("document").ready(function(){
				console.log("do something when document is ready...");
			});
}).controller('cockpitController',
	function($scope, lifeAPIservice) {
		$scope.importForm = {};
		$scope.yearlyMatchesJson = {};
		$scope.years = ['2015','2016','2017'];
		$scope.dailyMatchesForSelectedMonth = {};
		$scope.skipMovement = {};
		$scope.months = [];
		$scope.loadedMonths = 0;
		$scope.categoryExtension = {};

		/**
		 * DataPicker (START)
		 */
		$scope.today = function() {
			$scope.dt = new Date();
		};
		$scope.today();

		$scope.showWeeks = true;
		$scope.toggleWeeks = function() {
			$scope.showWeeks = !$scope.showWeeks;
		};

		$scope.clear = function() {
			$scope.dt = null;
		};

		// Disable weekend selection
		$scope.disabled = function(date, mode) {
			return false;
		};

		$scope.toggleMin = function() {
			$scope.minDate = ($scope.minDate) ? null : new Date();
		};
		$scope.toggleMin();

		$scope.openDate = function($event, ind) {
			console.log("executing $scope.open ...");
			$event.preventDefault();
			$event.stopPropagation();
		};

		$scope.open = function($event, opened) {
			console.log("executing $scope.open ...");
			$event.preventDefault();
			$event.stopPropagation();

			$scope[opened] = true;
		};

		$scope.dateOptions = {
			'year-format' : "'yy'",
			'starting-day' : 1
		};

		$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
			'shortDate' ];
		$scope.format = $scope.formats[0];
		/**
		 * DataPicker (END)
		 */

		/**
		 * Movement additional attributes
		 * - movementKey
		 */

		$scope.selectedBankAccount = function(inputBA) {
			// angular.forEach($scope.bankAccounts,function(ba,index){
			// 	if (ba.pk == inputBA.pk){
			$scope.importForm.bankAccount = inputBA;
			// 		selectedBankAccounts.push(ba.pk);
			// 	}
			// });
		}

		/******************************************
		 * CHECK MATCHING IMPORT (BY YEAR)
		 ******************************************/
		$scope.checkMatchingImportByYear = function() {
			console.log("checkMatchingImport for form [" + JSON.stringify($scope.importForm) + "] ...");
			if ($scope.importForm.bankAccount == null){
				$scope.setWarnMsg("select a Bank Account");
				return;
			}
			if ($scope.importForm.year == null){
				$scope.setWarnMsg("select a Year");
				return;
			}
			// Load movements
			lifeAPIservice.checkMatchingImportByYear(
				$scope.importForm,
				$scope).success(
				function(response) {
					console.log("response.status: " + response.status);
				}).error(function(data, status, headers, config) {
				alert("Error importing movements: " + status + ", " + JSON.stringify(data));
			});;
		};

		/******************************************
		 * CHECK MATCHING IMPORT (BY MONTH)
		 ******************************************/
		$scope.checkMatchingImportByMonth = function(year) {
			console.log("checkMatchingImport by month for form [" + JSON.stringify($scope.importForm) + "] ...");
			if ($scope.importForm.bankAccount == null){
				$scope.setWarnMsg("select a Bank Account");
				return;
			}

			$scope.importForm.year = year;

			// initialize matchingMovementsPerMonth
			$scope.yearlyMatchesJson.monthlyMatches = [];

			// Load movements by month
			$scope.loadedMonths = 0;
			var month;
			for (month = 1; month <= 12; month++) {
				$scope.yearlyMatchesJson.monthlyMatches[month-1] = {'month': $scope.months[month-1]};
				lifeAPIservice.checkMatchingImportByMonth(
					$scope.importForm.bankAccount, $scope.importForm.year, month,
					$scope).success(
					function(response) {
						console.log("Month [" + month + "], Status [" + response.status + "]");
					}).error(function(data, status, headers, config) {
						alert("Error importing movements: " + status + ", " + JSON.stringify(data));
				});
			}
		};

		/******************************************
		 * CHECK MATCHING IMPORT
		 ******************************************/
		$scope.showMatchingImportPerMonth = function(monthlyMatches) {
			console.log("showing matching movements per month [" + monthlyMatches.month.code + "], status [" + monthlyMatches.status +"]");
			$scope.dailyMatchesForSelectedMonth = monthlyMatches.dailyMatches;
		}


		/******************************************
		 * SHOULD SHOW DAILY MATCH IN COCKPIT
		 ******************************************/
		$scope.shouldShowDailyMatch = function(status)
		{
			if ($scope.showAll)
			{
				return true;
			}
			else if (status == 'MATCHED' && $scope.showMatched)
			{
				return true;
			}
			else if (status == 'PARTIALLY_MATCHED' && $scope.showPartiallyMatched)
			{
				return true;
			}
			else if (status == 'UNMATCHED' && $scope.showUnmatched)
			{
				return true;
			}
			return false;
		}
		
		/******************************************
		 * SHOULD SHOW DAILY MATCH IN COCKPIT
		 ******************************************/
		$scope.onChangeTransferDate = function(transfer)
		{
			console.log("onChangeTransferDate:");
			console.log("\ttransfer.fromMovement: " + transfer.fromMovement);
			console.log("\ttransfer.fromMovement.date: " + transfer.fromMovement.date);
			console.log("\ttransfer.toMovement: " + transfer.toMovement);
			console.log("\ttransfer.toMovement.date: " + transfer.toMovement.date);
			if (transfer.fromMovement!=null && transfer.fromMovement.date!=null)
			{
				if (transfer.toMovement!=null && transfer.toMovement.date==null)
				{
					console.log("changing date");
					transfer.toMovement.date = transfer.fromModement.date;
				}
			}
		}

		/******************************************
		 * WARNING MESSAGE
         ******************************************/
		$scope.setWarnMsg = function(msg){
			$scope.warnMsg = msg;
			$("#warnMsgDiv").show().delay(5000).fadeOut();
			document.getElementById('container').scrollIntoView();
		}

		/******************************************
		 * INFO MESSAGE
		 ******************************************/
		$scope.setInfoMsg = function(msg){
			$scope.infoMsg = "Total Processed [" + $scope.countProcessed + "]. With Errors [" + $scope.countProcessedWithError + "]";
			$("#infoMsgDiv").show().delay(5000).fadeOut();
			document.getElementById('container').scrollIntoView();
		}

		/******************************************
		 * GET ALL BANK ACCOUNTS
		 ******************************************/
		$scope.searchBankAccounts = function() {
			console.log('getting all bankAccounts ...');
			// Search for current month
			lifeAPIservice.getBankAccounts().success(
				function(response) {
					$scope.bankAccounts = response;
					angular.forEach($scope.bankAccounts,function(ba,index){
						if (ba != null){
							console.log("Bank account[" + index + "]: [" + ba.displayName + "]");
						}
					});
				});
		};

		/******************************************
		 * GET ALL MONTHS
		 ******************************************/
		$scope.searchAllMonths = function() {
			console.log("getting all months ...");
			// Search for current month
			lifeAPIservice.getAllMonths().success(
				function(response) {
					$scope.months = response;
					angular.forEach($scope.months,function(month,index){
						if (month != null){
							console.log("Month[" + index + "]: [" + month.displayName + "]");
						}
					});
				});
		};

		/******************************************
		 * GET MOVEMENT KEY
		 ******************************************/
		$scope.getMovementKey = function(mov) {
			console.log("getMovementKey for [" + JSON.stringify(mov) + "] ...");

			if (mov.movementKey)
			{
				delete mov.movementKey;
			}
			else
			{
				lifeAPIservice.getMovementKey(mov,
					$scope).success(
					function(response) {
						mov.movementKey = response;
					}).error(function(data, status, headers, config) {
					alert("Error getting key for [" + JSON.stringify(data) + "]. Status: " + status);
				});;
			}
		};

		$scope.searchCategoryAlerts = function () {
			console.log("loadCategoryAlerts");
			$scope.categoryAlerts = [];
			lifeAPIservice.getCategoryAlerts().success(
				function(response) {
					console.log("loadCategoryAlerts -> " + JSON.stringify(response));
					$scope.categoryAlerts = response;
				});
		}

		$scope.toggleExtendMode = function(categoryAlert, $event) {
			console.log("scope.toggleExtendMode");
			console.log("scope.categoryExtension.categoryAlert " + JSON.stringify($scope.categoryExtension.categoryAlert));
			console.log("categoryAlert " + JSON.stringify(categoryAlert));
			if ($scope.extendMode && $scope.categoryExtension.categoryAlert.category.pk == categoryAlert.category.pk) {
				$scope.extendMode = false;
			} else {
				$scope.extendMode = true;
			}

			toggleExtendPanel($event, "extend-category-overlay-box",$scope.extendMode);
			if ($scope.extendMode){
				$scope.categoryExtension.categoryAlert = categoryAlert;
				$scope.categoryExtension.bankAccount = categoryAlert.latestMovement.bankAccount;
				// Set BankAccount from main list
				angular.forEach($scope.bankAccounts,function(ba,index){
					if (ba.accountNumber == $scope.categoryExtension.bankAccount.accountNumber){
						$scope.categoryExtension.bankAccount = ba;
					}
				});
				$scope.categoryExtension.description = categoryAlert.latestMovement.description;
				$scope.categoryExtension.amount = categoryAlert.latestMovement.amount;
			}
		};

		$scope.confirmExtension = function(categoryExtension) {
			$scope.extendMovement(categoryExtension);
			// $scope.editMode = false;
			$scope.toggleExtendMode(categoryExtension.categoryAlert);
			$scope.editMovement = null;
		}
		
		$scope.extendMovement = function(categoryExtension) {
			var latestMov = categoryExtension.categoryAlert.latestMovement;
			var category = categoryExtension.categoryAlert.category;
			var newMov = jQuery.extend(true, {}, latestMov);
			newMov.bankAccount = categoryExtension.bankAccount;
			newMov.description = categoryExtension.description;
			newMov.amount = categoryExtension.amount;
			console.log("About to extend " + JSON.stringify(newMov) + "");
			
			lifeAPIservice.extendMovement(newMov, category.frequency, newMov.date, categoryExtension.untilDate, true).success(
				function(response) {
					if (response.success){
						console.log("extendMovement -> SUCCESS " + JSON.stringify(response));
						$scope.searchCategoryAlerts();
					}else{
						console.log("extendMovement -> ERROR " + JSON.stringify(response));
						$scope.setWarnMsg(response.messages.messages[0]);
					}
				});
		}

		/******************************************
		 * DOCUMENT -> READY
		 ******************************************/
		angular.element("document").ready(function(){
			console.log("do something when document is ready...");
			$scope.searchBankAccounts();
			$scope.searchAllMonths();
			$scope.searchCategoryAlerts();
		});
	}).controller('invoicesController',
	function($scope, lifeAPIservice) {
		$scope.years = ['2015','2016','2017'];
		$scope.selectedYear = {};
		$scope.years = [];

		/**
		 * DataPicker (START)
		 */
		$scope.today = function() {
			$scope.dt = new Date();
		};
		$scope.today();

		$scope.showWeeks = true;
		$scope.toggleWeeks = function() {
			$scope.showWeeks = !$scope.showWeeks;
		};

		$scope.clear = function() {
			$scope.dt = null;
		};

		// Disable weekend selection
		$scope.disabled = function(date, mode) {
			return false;
		};

		$scope.toggleMin = function() {
			$scope.minDate = ($scope.minDate) ? null : new Date();
		};
		$scope.toggleMin();

		$scope.openDate = function($event, ind) {
			console.log("executing $scope.open ...");
			$event.preventDefault();
			$event.stopPropagation();
		};

		$scope.open = function($event, opened) {
			console.log("executing $scope.open ...");
			$event.preventDefault();
			$event.stopPropagation();

			$scope[opened] = true;
		};

		$scope.dateOptions = {
			'year-format' : "'yy'",
			'starting-day' : 1
		};

		$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
			'shortDate' ];
		$scope.format = $scope.formats[0];
		/**
		 * DataPicker (END)
		 */

		$scope.selectedYear = function(year) {
			console.log("selected year " + year + "");;
			$scope.selectedYear = year;
		}

		$scope.loadAllInvoicePayments = function () {
			console.log("loadAllInvoices");
			$scope.invoicePayments = [];
			lifeAPIservice.getAllInvoices().success(
				function(response) {
					// console.log("loadAllInvoices -> " + JSON.stringify(response));
					if (response.success){
						$scope.invoicePayments = response.data;
						angular.forEach($scope.invoicePayments, function(invoicePayment, index){
							initForm(invoicePayment);
						});
					}else{
						console.log("loadAllInvoices -> ERROR " + JSON.stringify(response));
						$scope.setWarnMsg(response.messages.messages[0]);
					}
				});
		}

		$scope.calculateAmount = function (form) {
			if (form.days && form.dailyRate && form.vatRate != null) {
				var month = "";
				if (form.month!=null){
					month = form.month.code;
				}
				// console.log("calculateAmount, year [" + form.year + "], month [" + month + "], days [" + form.days + "], dailyRate [" + form.dailyRate + "], vatRate [" + form.vatRate + "]");
				form.newTotal = (form.days * form.dailyRate) * (1 + (form.vatRate / 100));
			} else {
				// console.log("calculateAmount .. skip");
				form.newTotal = "";
			}
		}

		$scope.updateDailyRate = function (invoicePayment) {
			var currentDailyRate = invoicePayment.form.dailyRate;
			var currentDate = invoicePayment.movement.date;
			angular.forEach($scope.invoicePayments, function (result, index) {
				if (result.movement.date > currentDate) {
					if (!result.form){
						result.form = {};
					}
					result.form.dailyRate = currentDailyRate;
				}
			});
		}

		$scope.hasChanged = function(invoice, form) {
			var year = form.year;
			var month = form.month != null ? form.month.code : "unknown";
			if (invoice && form){
				if ((invoice.dailyRate || form.dailyRate) && invoice.dailyRate != form.dailyRate){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (dailyRate) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.days || form.days) && invoice.days != form.days){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (days) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.year || form.year) && invoice.year != form.year){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (year) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.month || form.month) && invoice.month != form.month.code){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (month) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.vatRate || form.vatRate) && invoice.vatRate != form.vatRate){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (vatRate) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.number || form.number) && invoice.number != form.number){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (number) - Year [" + year + "], Month [" + month + "]");
					// }
					return true;
				} else if ((invoice.date || form.date) && invoice.date != form.date){
					// if (year == 2016 && month == 6){
					// 	console.log("hasChanged -> true (date) - Year [" + year + "], Month [" + month + "]" + ", [" + form.date + "], [" + invoice.date + "]");
					// }
					return true;
				}
			}
			// if (year == 2016 && month == 6){
			// 	console.log("hasChanged -> false - Year [" + year + "], Month [" + month + "]");
			// }
			return false;
		}

		$scope.calculateDays = function(invoicePayment) {
			if (invoicePayment.form.dailyRate){
				invoicePayment.form.days = invoicePayment.movement.amount / (1+(invoicePayment.form.vatRate/100)) / invoicePayment.form.dailyRate;
				$scope.calculateAmount(invoicePayment.form);
			}
		}

		$scope.calculateWorkDays = function(invoicePayment) {
			if (invoicePayment.form.year && invoicePayment.form.month){
				var year = invoicePayment.form.year;
				var month = invoicePayment.form.month.code;
				lifeAPIservice.getMonth(year,month).success(
					function(response) {
						invoicePayment.form.workDays = response.workDays;
					});
			}
		};

		$scope.updateMonthYearOnPreviousMovements = function(invoicePayment) {
			if (invoicePayment.form.year && invoicePayment.form.month){
				var year = invoicePayment.form.year;
				var month = invoicePayment.form.month.code;
				var nextInvoicePayment = getNextInvoicePayment(invoicePayment);
				var ind = 0;
				while (nextInvoicePayment != null){
					ind++;
					if (month == 12) {
						month = 1;
						year = Number(year) + 1;
					} else {
						month = month + 1;
					}
					lifeAPIservice.getMonth(year,month
					).success(
						(function (nextInvoicePayment) {
							return function (response) {
								nextInvoicePayment.form.year = response.year.code;
								angular.forEach($scope.months, function (monthData, index) {
									if (monthData.code == response.month.code) {
										nextInvoicePayment.form.month = monthData;
										$scope.calculateWorkDays(nextInvoicePayment);
									}
								});
							}
						})(nextInvoicePayment));
					nextInvoicePayment = getNextInvoicePayment(nextInvoicePayment);
				}
			}
		}
		
		$scope.saveInvoicePayment = function(invoicePayment) {
			invoicePayment.invoices[0].year = invoicePayment.form.year;
			invoicePayment.invoices[0].month = invoicePayment.form.month.code;
			invoicePayment.invoices[0].days = invoicePayment.form.days;
			invoicePayment.invoices[0].dailyRate = invoicePayment.form.dailyRate;
			invoicePayment.invoices[0].vatRate = invoicePayment.form.vatRate;
			invoicePayment.invoices[0].number = invoicePayment.form.number;
			invoicePayment.invoices[0].date = invoicePayment.form.date;
			invoicePayment.movement.amount = invoicePayment.form.newTotal;
			var temp = jQuery.extend(true, {}, invoicePayment);
			delete temp.form;
			lifeAPIservice.saveInvoicePayment(temp)
			.success(
				(function (invoicePayment) {
					return function (response) {
						if (response.success){
							console.log("saveInvoicePayment -> SUCCESS " + JSON.stringify(response));
							invoicePayment.invoices[0] = response.data.invoices[0];
							invoicePayment.movement = response.data.movement;
							initForm(invoicePayment);
							$scope.calculateAmount(invoicePayment.form);
						}else{
							console.log("loadAllInvoices -> ERROR " + JSON.stringify(response));
							$scope.setWarnMsg(response.messages.messages[0]);
						}
					}
				})(invoicePayment));
		}

		$scope.missingInfo = function(invoicePayment) {
			return !invoicePayment.form.dailyRate || !invoicePayment.form.days || !invoicePayment.form.year || !invoicePayment.form.month;
		}

		$scope.differentAmount = function(invoicePayment) {
			var retValue = invoicePayment.form.newTotal && invoicePayment.movement.amount != invoicePayment.form.newTotal;
			if (retValue){
				var month = invoicePayment.form.month != null ? invoicePayment.form.month.code : "unknown";
				console.log("differentAmount on " + invoicePayment.invoices[0].year + "/" + month);
			}
			return retValue;
		}

		$scope.differentDays = function(invoicePayment) {
			var retValue = invoicePayment.form.workDays && invoicePayment.form.days && invoicePayment.form.workDays != invoicePayment.form.days;
			if (retValue){
				// var month = invoicePayment.form.month != null ? invoicePayment.form.month.code : "unknown";
				// console.log("differentDays on " + invoicePayment.invoices[0].year + "/" + month + ", workDays [" + invoicePayment.form.WorkDays + "], form [" + invoicePayment.form.days + "]");
			} else {
				// var month = invoicePayment.form.month != null ? invoicePayment.form.month.code : "unknown";
				// console.log("NOT differentDays on " + invoicePayment.invoices[0].year + "/" + month + ", workDays [" + invoicePayment.form.WorkDays + "], form [" + invoicePayment.form.days + "]");
			}
			return retValue;
		}

		$scope.allTheSame = function(invoicePayment) {
			return !$scope.differentAmount(invoicePayment) && !$scope.differentDays(invoicePayment);
		}

		$scope.invoiceIssued = function(invoicePayment) {
			var retValue = invoicePayment.invoices[0].number != null && invoicePayment.invoices[0].date != null;
			// var year = invoicePayment.invoices[0].year;
			// var month = invoicePayment.form.month != null ? invoicePayment.form.month.code : "unknown";
			// if (year == 2016 && month == 6){
			// 	console.log("invoiceIssued - Year [" + year + "], Month [" + month + "],  [" + retValue + "], number [" + invoicePayment.invoices[0].number + "], date [" + invoicePayment.invoices[0].date + "]");
			// }
			return retValue;
		}

		/******************************************
		 * WARNING MESSAGE(S)
		 ******************************************/
		$scope.setWarnMsg = function(msg){
			var array = [];
			array.push(msg);
			$scope.setWarnMsgs(array);
		}

		$scope.setWarnMsgs = function(msgs){
			$scope.warnMsgs = msgs;
			$("#msg-divs").show().delay(30000).fadeOut();
			document.getElementById('container').scrollIntoView();
		}

		/******************************************
		 * INFO MESSAGE(S)
		 ******************************************/
		$scope.setInfoMsg = function(msg){
			var array = [];
			array.push(msg);
			$scope.setInfoMsgs(array);
		}

		$scope.setInfoMsgs = function(msgs){
			$scope.infoMsgs = msgs;
			$("#msg-divs").show().delay(30000).fadeOut();
			document.getElementById('container').scrollIntoView();
		}

		function initForm(invoicePayment) {
			invoicePayment.form = jQuery.extend(true, {}, invoicePayment.invoices[0]);
			// console.log("initForm, date [" + invoicePayment.form.date + "]")
			angular.forEach($scope.months, function (monthData, index) {
				if (monthData.code == invoicePayment.invoices[0].month) {
					invoicePayment.form.month = monthData;
				}
			});
			if (invoicePayment.invoices[0].year && invoicePayment.invoices[0].month){
				$scope.calculateWorkDays(invoicePayment);
			}
			$scope.calculateAmount(invoicePayment.form);
		}

		function getNextInvoicePayment(invoicePayment) {
			var nextInvoicePayment = null;
			angular.forEach($scope.invoicePayments, function (payment, index) {
				if (payment.movement.date > invoicePayment.movement.date) {
					if (nextInvoicePayment == null) {
						nextInvoicePayment = payment;
					} else if (payment.movement.date < nextInvoicePayment.movement.date) {
						nextInvoicePayment = payment;
					}
				}
			});
			return nextInvoicePayment;
		}

		function searchAllMonths() {
			// Search for current month
			lifeAPIservice.getAllMonths().success(
				function(response) {
					$scope.months = response;
				});
		};

		function loadYears (currentYear) {
			$scope.years = [];
			$scope.years.push(currentYear-1);
			$scope.years.push(currentYear);
			$scope.years.push(currentYear+1);
			$scope.years.push(currentYear+2);
		}

		function selectYear(year) {
			$scope.selectedYear = year;
			loadYears(year);
		}

		function loadCurrentYear () {
			selectYear(new Date().getFullYear());
		}

		/******************************************
		 * DOCUMENT -> READY
		 ******************************************/
		angular.element("document").ready(function(){
			console.log("do something when document is ready...");
			$scope.loadAllInvoicePayments();
			loadCurrentYear();
			searchAllMonths();
		});
	});
