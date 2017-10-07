angular.module('LifeApp.controllers', [])
.controller('importMovementsController',
		function($scope, lifeAPIservice) {
			$scope.bankAccount = null;
			$scope.payload = null;
			$scope.month = null;
			$scope.year = null;
});
