// JavaScript source code
'use strict';

angular.module('ChequeApprovals')

    .controller('ChequeApprovalsController',
        ['$scope', '$rootScope', '$state','$location', 'ChequeApprovalsService',
            function ($scope, $rootScope, $state, $location, ChequeApprovalsService) {
        		$scope.transactionResponseError = "";
                // Do stuff
        		if(!$rootScope.isEmployee) {
        			alert("Invalid Access! Only for Employees")
        			$state.go('Dashboard')
        		}
				// Get pending transactions for approval.
				$scope.GetPendingIssueTransactions = function () {

					$scope.dataLoading = true;
					ChequeApprovalsService.GetTransactionsPendingApproval(function (response) {

						console.log(response)

						if (response) {
							$scope.transactionList = response;
							for(var i=0; i < $scope.transactionList.length; i++) {
							  var t = $scope.transactionList[i].transactionTimestamp;
							  $scope.transactionList[i].transactionTimestamp = $rootScope.formatDate(t);
							}
						}

						$scope.dataLoading = false;
					})
				}

				// Get pending account actions for approval.
				$scope.GetPendingAccounts = function () {

					$scope.dataLoading = true;
					ChequeApprovalsService.GetAccountPendingApproval(function (response) {

						console.log(response)

						if (response) {
							$scope.accountList = response;
						}

						$scope.dataLoading = false;
					})
				}

				// Respond to pending transaction by approving or denying.
				$scope.RespondToPendingTransactions = function (id, approve) {

					$scope.dataLoading = true;
					ChequeApprovalsService.RepondToTransactionApproval(id, approve, function (response) {

						console.log(response)

						if (response && response.isSuccess) {
							$scope.transactionResponseError = "";
							for(var i=0; i < $scope.transactionList.length; i++) {
								if(id==$scope.transactionList[i].checkId) {
									$scope.transactionList[i].status = (approve)? 1 : 4;
									break;
								}
							}
						} else {
							$scope.transactionResponseError = (response && response.message)? response.message : "Something went wrong";
						}

						$scope.dataLoading = false;
					})
				}

				// Respond to pending account actions by approving or denying.
				$scope.RespondToPendingAccounts = function () {

					$scope.dataLoading = true;
					ChequeApprovalsService.RepondToAccountApproval(id, approve, function (response) {

						console.log(response)

						if (response) {
							$scope.accountList = response;
						}

						$scope.dataLoading = false;
					})
				}
				
				$scope.GetPendingIssueTransactions();
            }]);
