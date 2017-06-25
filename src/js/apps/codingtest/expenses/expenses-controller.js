"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", ['ui-notification']);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "config", "restalchemy","Notification", 
function ExpensesCtrl($rootScope, $scope, $config, $restalchemy, Notification) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";

	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);

	var restExpenses = $restalchemy.init({ root: $config.apiroot }).at("expenses");

	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		restExpenses.get().then(function(expenses) {
			$scope.expenses = expenses;
		});
	}

	var isPositiveNumber = function(n) {
        return ($.isNumeric(n)) && (new Number(n) > 0);
	}

	$scope.calculateVatOnChange = function() {
		const amountRegex = /-?\d+(\.\d+)?/;
		const amountString = amountRegex.exec($scope.newExpense.amount);
		if(amountString) {
			const amount = amountString[0]
			if (isPositiveNumber(amount)) {
				const amountValue = new Big(new Number(amount));
				$scope.newExpense.vat = amountValue.minus(amountValue.div(1.20)).toFixed(2);				
			} else {
				$scope.newExpense.vat = 0;
				Notification.error({message: $scope.newExpense.amount + 
				" is not a correct amount. Please use 00.00 EUR format.", replaceMessage: true});
			}
		} else {
			$scope.newExpense.vat = 0;
		}
	}

	$scope.validateDate = function() {
		const date = $scope.newExpense.date;
		const information = 'It has been changed to current day.';

		if(!moment(date, "DD/MM/YYYY", true).isValid()) {
			$scope.newExpense.date = moment().format("DD/MM/YYYY");
			Notification.error('Invalid date.' + information);
		} else if (moment(date, "DD/MM/YYYY").isAfter(moment())) {
			$scope.newExpense.date = moment().format("DD/MM/YYYY");
			Notification.warning('Date cannot be set in the future.' + information);
		}
	}

	$scope.saveExpense = function() {
		if ($scope.expensesform.$valid) {
			// Post the expense via REST
			restExpenses.post($scope.newExpense).then(function() {
				// Reload new expenses list
				$scope.clearExpense();
				loadExpenses();
			}).error(function(reason) {
				Notification.error(reason.error);
			});
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
	};

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
