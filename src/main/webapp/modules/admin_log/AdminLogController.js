'use strict';

angular.module('AdminLog')

    .controller('AdminLogController',
            ['$scope', '$rootScope', '$state','$location', 'AdminLogService',
            function ($scope, $rootScope, $state, $location, AdminLogService) {
        		$scope.transactionResponseError = "";
                // Do stuff
        		if(!$rootScope.isEmployee) {
        			alert("Invalid Access! Only for Employees")
        			$state.go('Dashboard')
        		}
				// Get pending transactions for approval.
				$scope.GetPendingIssueTransactions = function () {

					$scope.dataLoading = true;
					AdminLogService.GetTransactionsPendingApproval(function (response) {

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
                $scope.GetPendingIssueTransactions();
            }]);