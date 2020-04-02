'use strict';
 
angular.module('IssueCheque')
 
.controller('IssueChequeController',
    ['$scope', '$rootScope','$state','TransferFundsService',
    function ($scope, $rootScope, $state, TransferFundsService) {
    	
    	$scope.fromAccountList = [];
    	$scope.toAccountList = [];
    	$scope.fromAccount;
    	$scope.toAccount;
    	$scope.transferAmount;
    	$scope.isTransferSuccess = false;
    	$scope.transferErrorMsg = "";
	    	    
    	$scope.getAccounts = function() {
      	  $scope.dataLoading = true;
      	  console.log("userid: " + $rootScope.userId)
      	  var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;
      	  TransferFundsService.getAccounts(userId, function(response) {
      		  console.log("controller response")
      		  if(response && response.accounts) {
      			  $scope.fromAccountList = response.accounts;
      		  } else {
      			  console.log("Something went wrong")
      		  }
      		  $scope.dataLoading = true;
      	  })
        }
    	
    	$scope.getAllAccounts = function() {
        	  $scope.dataLoading = true;
        	  TransferFundsService.getAllAccounts(function(response) {
        		  console.log("controller response")
        		  if(response && response.accounts) {
	      			  $scope.toAccountList = response.accounts;
	      		  } else {
	      			  console.log("Something went wrong")
	      		  }
        		  $scope.dataLoading = true;
        	  })
          }
    	
    	$scope.transferFunds = function() {
    		if(!$scope.transferAmount || $scope.transferAmount <= 0) {
    			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Amount must be greater than zero";
    			return;
    		}
    		if($scope.fromAccount.id == $scope.toAccount.id) {
    			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Both accounts must be different";
    			return;
    		}
      	  $scope.dataLoading = true;
      	  console.log("FromAccount:" + $scope.fromAccount.id)
      	  TransferFundsService.transferFunds($scope.fromAccount.id, $scope.toAccount.id, $scope.transferAmount,
      			  function(response) {
      		  console.log("controller response")
      		  if(response && response.isSuccess) {
    			  $scope.transferErrorMsg = response.msg;
    			  $scope.isTransferSuccess = true;
    			  $scope.transferAmount = 0;
    		  } else {
    			  $scope.transferErrorMsg = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isTransferSuccess = false;
    		  }
      		  $scope.dataLoading = true;
      	  })
        }
	    
	    $scope.getAccounts();
	    $scope.getAllAccounts();
	    //$scope.createTransferRequest()
	    //$scope.getTransactions();
    
    }]);
