// JavaScript source code
'use strict';

angular.module('Approvals')

    .controller('ApprovalsController',
        ['$scope', '$rootScope', '$state','$location', 'ApprovalsService',
            function ($scope, $rootScope, $state, $location, ApprovalsService) {
        		$scope.transactionResponseError = "";
                // Do stuff
        		if(!$rootScope.isEmployee) {
        			alert("Invalid Access! Only for Employees")
        			$state.go('Dashboard')
        		}
				// Get pending transactions for approval.
				$scope.GetPendingTransactions = function () {

					$scope.dataLoading = true;
					var role = $rootScope.user.authRole.roleType;
					var isCritical = (role == 'TIER2' || role == 'ADMIN')
					ApprovalsService.GetTransactionsPendingApproval(isCritical, function (response) {
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
					ApprovalsService.GetAccountPendingApproval(function (response) {

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
					ApprovalsService.RepondToTransactionApproval(id, approve, function (response) {

						console.log(response)

						if (response && response.isSuccess) {
							$scope.transactionResponseError = "";
							for(var i=0; i < $scope.transactionList.length; i++) {
								if(id==$scope.transactionList[i].transactionId) {
									$scope.transactionList[i].status = (approve)? "Approved" : "Declined";
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
