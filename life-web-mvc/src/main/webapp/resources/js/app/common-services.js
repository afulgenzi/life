"use strict";

angular.module('LifeApp.services', []).factory('lifeCommonService', function () {

    var lifeCommon = {};

    lifeCommon.selectComboboxValue = function (target, availableValues, selectedValue) {
        angular.forEach(availableValue, function (value, index) {
            if (selectedValue == value.pk) {
                return value;
            }
        });
    }

    lifeCommon.setWarnMsg = function (msg) {
        $scope.warnMsg = msg;
        $("#warnMsgDiv").show().delay(5000).fadeOut();
    }

    lifeCommon.setInfoMsg = function (msg) {
        $scope.warnMsg = "Total Processed [" + $scope.countProcessed + "]. With Errors [" + $scope.countProcessedWithError + "]";
        $("#warnMsgDiv").show().delay(5000).fadeOut();
    }

    return lifeCommon;

});