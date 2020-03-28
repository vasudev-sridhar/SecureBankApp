// JavaScript source code
'use strict';

angular.module('Approvals')

    .controller('ApprovalsController',
        ['$scope', '$rootScope', '$location', 'ApprovalsService',
            function ($scope, $rootScope, $location, ApprovalsService) {
                // Do stuff

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
            }]);
