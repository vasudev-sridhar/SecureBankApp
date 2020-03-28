'use strict';
 
angular.module('CreditDebit')
 
.controller('CreditDebitController',
    ['$scope', '$rootScope','$state','CreditDebitService',
    function ($scope, $rootScope, $state, CreditDebitService) {
    	$scope.credit = 0;
    	$scope.debit = 0;
    	$scope.creditErrorMsg = "";
    	$scope.isCreditSuccess = false;
    	$scope.debitErrorMsg = "";
    	$scope.isDebitSuccess = false;
    	
    	$scope.accountList = [];
    	$scope.accountNo;
	    $scope.creditAccount = function() {
	    	console.log($scope.credit);
	    	console.log($scope.accountNo);
    		if(!$scope.credit || $scope.credit < 0) {
    			$scope.isCreditSuccess = false;
    			$scope.creditErrorMsg = "Amount must be greater than zero";
    			return;
    		}
			  $scope.dataLoading = true;
			  CreditDebitService.updateBalance($scope.credit,$scope.accountNo, function(response) {
				  console.log("controller response")
				  if(response && response.isSuccess) {
					  $scope.isCreditSuccess = true;
					  $scope.creditErrorMsg = "";
					  $scope.credit = 0;
					  $scope.debit = 0;
				  } else {
					  $scope.isCreditSuccess = false;
					  $scope.creditErrorMsg = (response && response.msg)? response.msg : "Operation Failed";
					  console.log($scope.creditErrorMsg)
				  }
				  $scope.dataLoading = false;
			  })
	    }
	    
	    $scope.debitAccount = function() {
	    	console.log($scope.debit);
	    	console.log($scope.accountNo);
	    	if(!$scope.debit || $scope.debit < 0) {
    			$scope.isDebitSuccess = false;
    			$scope.debitErrorMsg = "Amount must be greater than zero";
    			return;
    		}
			  $scope.dataLoading = true;
			  CreditDebitService.updateBalance(-$scope.debit,$scope.accountNo, function(response) {
				  console.log("controller response")
				  if(response && response.isSuccess) {
					  $scope.isDebitSuccess = true;
					  $scope.debitErrorMsg = "";
					  $scope.credit = 0;
					  $scope.debit = 0;
				  } else {
					  $scope.isDebitSuccess = false;
					  $scope.debitErrorMsg = (response && response.msg)? response.msg : "Operation Failed";
					  console.log($scope.debitErrorMsg)
				  }
				  $scope.dataLoading = false;
			  })
	    }
	    
    	$scope.getAccounts = function() {
      	  $scope.dataLoading = true;
      	  CreditDebitService.getAccounts($rootScope.userId, function(response) {
      		  console.log("controller response")
      		  if(response) {
      			  $scope.accountList = response.accounts;
      		  }
      		  $scope.dataLoading = true;
      	  })
        }
	    
	    $scope.getAccounts();
	    //$scope.createTransferRequest()
	    //$scope.getTransactions();
    
    }]);
