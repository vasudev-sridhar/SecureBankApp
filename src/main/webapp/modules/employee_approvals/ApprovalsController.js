// JavaScript source code
'use strict';

angular.module('Approvals')

    .controller('ApprovalsController',
        ['$scope', '$rootScope', '$state','$location', 'ApprovalsService',
            function ($scope, $rootScope, $state, $location, ApprovalsService) {
                // Do stuff
        		if(!$rootScope.isEmployee) {
        			alert("Invalid Access! Only for Employees")
        			$state.go('Dashboard')
        		}
				// Get pending transactions for approval.
				$scope.GetPendingTransactions = function () {

					$scope.dataLoading = true;
					ApprovalsService.GetTransactionsPendingApproval(function (response) {

						console.log(response)

						if (response) {
							$scope.transactionList = response;
						}

						$scope.dataLoading = false;
					})
				}

				// Get pending account actions for approval.
				$scope.GetPendingTransactions = function () {

					$scope.dataLoading = true;
					ApprovalsService.GetAccountPendingApproval(function (response) {

						console.log(response)

						if (response) {
							$scope.accountList = response;
						}

						$scope.dataLoading = false;
					})
				}

				// Respond to pending transaction by approving or denying.
				$scope.RespondToPendingTransactions = function () {

					$scope.dataLoading = true;
					ApprovalsService.RepondToTransactionApproval(id, approve, function (response) {

						console.log(response)

						if (response) {
							$scope.accountList = response;
						}

						$scope.dataLoading = false;
					})
				}

				// Respond to pending account actions by approving or denying.
				$scope.RespondToPendingAccounts = function () {

					$scope.dataLoading = true;
					ApprovalsService.RepondToAccountApproval(id, approve, function (response) {

						console.log(response)

						if (response) {
							$scope.accountList = response;
						}

						$scope.dataLoading = false;
					})
				}
				
				$scope.GetPendingTransactions();
            }]);
