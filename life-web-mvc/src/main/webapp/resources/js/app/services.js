"use strict";

angular.module('LifeApp.services', []).
  factory('lifeAPIservice', function($http) {
	  
    var lifeAPI = {};
    
    var prepareMovementForBackend = function(movement){
		var newItem = jQuery.extend(true, {}, movement);
		console.log("prepareMovementForBackend - newItem: " + JSON.stringify(newItem));
		// delete newItem.$$hashKey;
		// delete newItem.bankAccount.$$hashKey;
		delete newItem.bankAccount.disabled;
		delete newItem.formattedAmount;
		delete newItem.formattedBalanceAfter;
		delete newItem.formattedOverallBalanceAfter;
		delete newItem.transferSourceMovement;
		delete newItem.transferTargetMovement;
		return angular.copy(newItem);
    };

    lifeAPI.getBankAccounts = function() {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/accounts/json/getBankAccounts'
        });
    };
    
    lifeAPI.getCurrencies = function() {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/currencies/json/getAll'
        });
    };
    
    lifeAPI.getFrequencyTypes = function() {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/categories/json/getAllFrequencyTypes'
        });
    };
    
    lifeAPI.searchAllMovements = function(bankAccount, scope) {
		console.log("service - search for ALL");
        return $http.get(LifeJs.Common.jsonPath + '/movements/json/searchAll',
          	  {params:{bankAccount:bankAccount.pk, now: (new Date())}}).success(function (response) {
			processMovementResponse(response, false, scope);
	    });
    };

	lifeAPI.searchMovementsByMonth = function (bankAccount, year, month, scope, appendMode) {
		if (bankAccount != null) {
			return $http.get(LifeJs.Common.jsonPath + '/movements/json/searchByMonth',
				{params: {bankAccount: bankAccount.pk, month: month, year: year, now: (new Date())}}
			).error(function (data, status, headers, config, statusText) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
				console.log("statusText: " + JSON.stringify(statusText));
			});
		} else {
			params:
				scope.movements = [];
		}
	};

	lifeAPI.searchMovementsByText = function(inputSearchForm) {
		console.log("searchMovementsByText: [" + JSON.stringify(inputSearchForm) + "]");
		// return $http.get(LifeJs.Common.jsonPath + '/movements/json/searchByText',inputSearchForm);
		return $http.post(LifeJs.Common.jsonPath + '/movements/json/searchByText', inputSearchForm)
			.error(function(data, status, headers, config, statusText) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
				console.log("statusText: " + JSON.stringify(statusText));
			});
    };

    lifeAPI.searchMovementsByTransfer = function(inputSearchTransferForm) {
		console.log("searchMovementsByTransfer: [" + JSON.stringify(inputSearchTransferForm) + "]");
		return $http.post(LifeJs.Common.jsonPath + '/movements/json/searchByTransfer', inputSearchTransferForm)
			.error(function(data, status, headers, config, statusText) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
				console.log("statusText: " + JSON.stringify(statusText));
			});
    };

    lifeAPI.getCurrentMonth = function(year, month) {
      return $http({
        method: 'GET', url : LifeJs.Common.jsonPath + '/datetime/json/getCurrentMonth'
      });
    };

    lifeAPI.getPreviousMonth = function(year, month) {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/datetime/json/getPreviousMonth'
        	  +'?month=' + month + '&year=' + year
        });
    };

    lifeAPI.getNextMonth = function(year, month) {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/datetime/json/getNextMonth'
        	  +'?month=' + month + '&year=' + year
        });
    };

    lifeAPI.getMonth = function(year, month) {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/datetime/json/getMonth'
        	  +'?month=' + month + '&year=' + year
        });
    };
    
    lifeAPI.getAllMonths = function() {
        return $http({
          method: 'GET', url : LifeJs.Common.jsonPath + '/datetime/json/getAllMonths'
        });
    };
    
    lifeAPI.addMovement = function(newMovement) {
		console.log('service - creating movement '+newMovement.description);
		newMovement.movement = prepareMovementForBackend(newMovement.movement);
		newMovement.fromDate = newMovement.movement.date;
		newMovement.skipFirstDate = false;
        return $http.post(LifeJs.Common.jsonPath + '/movements/json/addMovement', newMovement)
			.error(function(data, status, headers, config) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
			});
    };

	lifeAPI.saveMovement = function (movement) {
		console.log('service - saving movement ' + movement.pk + " = " + angular.copy(prepareMovementForBackend(movement)));
		return $http.post(LifeJs.Common.jsonPath + '/movements/json/saveMovement', angular.copy(prepareMovementForBackend(movement)))
			.error(function(data, status, headers, config) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
			});
	};
    
    lifeAPI.saveMovements = function(movements) {
		var movementsWrapper = {movements: []}
		angular.forEach(movements,function(mov,index){
			movementsWrapper.movements.push(mov);
		});
		console.log('service - saving [' + movements.size + '] movements ');
        return $http.post(LifeJs.Common.jsonPath + '/movements/json/saveMovements', movementsWrapper);
    };

	lifeAPI.deleteMovement = function(movement) {
		console.log('service - deleting movement '+movement.pk);
		delete movement.checked;
        return $http.post(LifeJs.Common.jsonPath + '/movements/json/deleteMovement', prepareMovementForBackend(movement));
    };
    
    lifeAPI.addTransfer = function(newTransfer) {
		console.log('service - creating transfer');
        return $http.post(LifeJs.Common.jsonPath + '/transfer/json/addTransfer', newTransfer);
    };
    
    lifeAPI.updateTransfer = function(transfer) {
		console.log('service - saving transfer');
        return $http.post(LifeJs.Common.jsonPath + '/transfer/json/updateTransfer', transfer);
    };
    
    lifeAPI.deleteTransfer = function(transfer) {
		console.log('service - deleting transfer');
        return $http.post(LifeJs.Common.jsonPath + '/transfer/json/deleteTransfer', transfer);
    };
    
    lifeAPI.getConversionRate = function(fromCurrency, toCurrency) {
        return $http.get(LifeJs.Common.jsonPath + '/currencies/json/getConversionRate',
            	  {params:{fromCurrency:fromCurrency.pk, toCurrency:toCurrency.pk, now: (new Date())}}).success(function (response) {
          	console.log(response);
  	    });
    };
    
    lifeAPI.parseMovementsForBankAccount = function(importForm, scope) {
        return $http.post(LifeJs.Common.jsonPath + '/movements/json/import', importForm
	        ).success(function (response) {
	        	if(typeof response == 'object'){
	    	        scope.movementsJson = response;
	        	}else{
	    	        scope.movementsJson = [];
	    	        alert("You're not logged in anymore!");
	        	}
		    });
    };

    lifeAPI.loadMovementsForBankAccount = function(importForm, scope) {
        return $http.post(LifeJs.Common.jsonPath + '/movements/json/loadImports', importForm
	        ).success(function (response) {
	        	if(typeof response == 'object'){
	    	        scope.movementsJson = response;
	        	}else{
	    	        scope.movementsJson = [];
	    	        alert("You're not logged in anymore!");
	        	}
		    });
    };

    lifeAPI.checkMatchingImportByYear = function(importForm, scope) {
        return $http.post(LifeJs.Common.jsonPath + '/cockpit/json/checkMatchingImport', importForm
	        ).success(function (response) {
	        	if(typeof response == 'object'){
	    	        scope.yearlyMatchesJson = response;
	        	}else{
	    	        scope.yearlyMatchesJson = [];
	    	        alert("You're not logged in anymore!");
	        	}
		    });
    };

    lifeAPI.checkMatchingImportByMonth = function(bankAccount, year, month, scope) {
        return $http.get(LifeJs.Common.jsonPath + '/cockpit/json/checkMatchingImportByMonth?bankAccount=' + bankAccount.pk + "&year=" + year + "&month=" + month
	        ).success(function (response) {
	        	if(typeof response == 'object'){
	    	        scope.yearlyMatchesJson.monthlyMatches[month-1] = response;
	        	}else{
	    	        scope.yearlyMatchesJson = [];
	    	        alert("You're not logged in anymore!");
	        	}
		    });
    };

	lifeAPI.searchAvailableImports = function(bankAccount, year, scope) {
		console.log("searchAvailableImports(" + bankAccount + ", " + year + ")");
		if (bankAccount!=null){
			return $http.get(LifeJs.Common.jsonPath + '/movements/json/availableImports',
				{params:{bankAccount:bankAccount.pk, year:year}}
			).success(function (response) {
				if(typeof response == 'object'){
					scope.availableImports = response.availableImportMovements;
				}else{
					scope.availableImports = [];
					alert("You're not logged in anymore!");
				}
			});
		}else{
			scope.availableImports = [];
		}
	};

	lifeAPI.getMovementKey = function(mov) {
		return $http.post(LifeJs.Common.jsonPath + '/cockpit/json/getMovementKey', mov);
	};

	lifeAPI.getCategoryByCode = function(code) {
		console.log("lifeAPI.getCategoryByCode(" + code + ")");
		return $http({
			method: 'GET',
			url : LifeJs.Common.jsonPath + '/categories/json/getCategoryByCode',
			params:{categoryCode:code}
		});
	};

	lifeAPI.getAllCategories = function() {
		return $http({
			method: 'GET',
			url : LifeJs.Common.jsonPath + '/categories/json/getAllCategories'
		});
	};

	lifeAPI.getCategoryTree = function(selectOnlyLeaves, defaultSelectedCategory) {
		var defaultSelectedCategoryCode = defaultSelectedCategory == null ? null : defaultSelectedCategory.code;
		console.log("getCategoryTree(" + selectOnlyLeaves + ", " + defaultSelectedCategoryCode + ")")
		return $http({
			method: 'GET', 
			url : LifeJs.Common.jsonPath + '/categories/json/getCategoryTree',
			params:{selectOnlyLeaves: selectOnlyLeaves, defaultSelectedCategoryCode: defaultSelectedCategoryCode}
		}).error(function(data, status, headers, config) {
			console.log("data: " + JSON.stringify(data));
			console.log("status: " + JSON.stringify(status));
			console.log("headers: " + JSON.stringify(headers));
			console.log("config: " + JSON.stringify(config));
		});
	};

	lifeAPI.getCategoryAlerts = function() {
		return $http({
			method: 'GET', 
			url : LifeJs.Common.jsonPath + '/categories/json/getCategoryAlerts'
		}).error(function(data, status, headers, config) {
			console.log("data: " + JSON.stringify(data));
			console.log("status: " + JSON.stringify(status));
			console.log("headers: " + JSON.stringify(headers));
			console.log("config: " + JSON.stringify(config));
		});
	};

	lifeAPI.getCategoryTreeByMonth = function(bankAccounts,year,month) {
		console.log("getCategoryTreeByMonth: [" + JSON.stringify(bankAccounts) + "]");
		var searchForm = {};
		searchForm.bankAccounts = bankAccounts;
		searchForm.year = year;
		searchForm.month = month;
		return $http.post(LifeJs.Common.jsonPath + '/categories/json/getCategoryTreeByMonth', searchForm
		).error(function(data, status, headers, config) {
			console.log("data: " + JSON.stringify(data));
			console.log("status: " + JSON.stringify(status));
			console.log("headers: " + JSON.stringify(headers));
			console.log("config: " + JSON.stringify(config));
		});
	};

	lifeAPI.getCategoryTreeByText = function(multiAccountSearchForm) {
		console.log("getCategoryTreeByTexth: [" + JSON.stringify(multiAccountSearchForm) + "]");
		return $http.post(LifeJs.Common.jsonPath + '/categories/json/getCategoryTreeByText', multiAccountSearchForm
		).error(function(data, status, headers, config) {
			console.log("data: " + JSON.stringify(data));
			console.log("status: " + JSON.stringify(status));
			console.log("headers: " + JSON.stringify(headers));
			console.log("config: " + JSON.stringify(config));
		});
	};

	lifeAPI.saveCategory = function (categoryForm) {
		console.log('service - saving category ' + categoryForm.code + " = " + JSON.stringify(categoryForm));
		return $http.post(LifeJs.Common.jsonPath + '/categories/json/insertUpdateCategory', angular.copy(categoryForm))
			.error(function(data, status, headers, config, statusText) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
				console.log("statusText: " + JSON.stringify(statusText));
			});
	};

	lifeAPI.deleteCategory = function (categoryForm) {
		console.log('service - Deleting category ' + categoryForm.code + " = " + JSON.stringify(categoryForm));
		return $http.post(LifeJs.Common.jsonPath + '/categories/json/deleteCategory', angular.copy(categoryForm))
			.error(function(data, status, headers, config) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
			});
	};

	lifeAPI.extendMovement = function (movement, frequency, fromDate, untilDate, skipFirstDate) {
		console.log("service - extending movement " + angular.copy(prepareMovementForBackend(movement)));

		var movementExtensionForm = {};
		movementExtensionForm.movement = prepareMovementForBackend(movement);
		movementExtensionForm.frequency = frequency;
		movementExtensionForm.fromDate = fromDate;
		movementExtensionForm.untilDate = untilDate;
		movementExtensionForm.skipFirstDate = skipFirstDate;

		return $http.post(LifeJs.Common.jsonPath + '/movements/json/addMovement', movementExtensionForm)
			.error(function(data, status, headers, config) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
			});
	};

	lifeAPI.getAllInvoices = function() {
		return $http({
			method: 'GET',
			url : LifeJs.Common.jsonPath + '/invoices/json/getAll'
		}).error(function(data, status, headers, config) {
			console.log("data: " + JSON.stringify(data));
			console.log("status: " + JSON.stringify(status));
			console.log("headers: " + JSON.stringify(headers));
			console.log("config: " + JSON.stringify(config));
		});
	};

	lifeAPI.saveInvoicePayment= function (invoicePayment) {
		console.log('service - saving invoicePayment ' +JSON.stringify(invoicePayment));
		return $http.post(LifeJs.Common.jsonPath + '/invoices/json/insertUpdateInvoicePayment', angular.copy(invoicePayment))
			.error(function(data, status, headers, config, statusText) {
				console.log("data: " + JSON.stringify(data));
				console.log("status: " + JSON.stringify(status));
				console.log("headers: " + JSON.stringify(headers));
				console.log("config: " + JSON.stringify(config));
				console.log("statusText: " + JSON.stringify(statusText));
			});
	};

	return lifeAPI;
  });