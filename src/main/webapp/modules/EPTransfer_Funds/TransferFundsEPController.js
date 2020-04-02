'use strict';
 
angular.module('TransferFundsEP')
 
.controller('TransferFundsEPController',
    ['$scope', '$rootScope','$state','TransferFundsEmailService',
    function ($scope, $rootScope, $state, TransferFundsEmailService) {
    	
    	$scope.fromAccountList = [];
    	$scope.toAccountList = [];
    	$scope.fromAccount;
	$scope.toAccount;
    	$scope.transferAmount;
    	$scope.isTransferSuccess = false;
    	$scope.transferErrorMsg = "";
	$scope.Email;
	$scope.Phone;
    	    
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
    	
    	$scope.verifyEmailandTransfer = function() {
                
    		if(!$scope.transferAmount || $scope.transferAmount <= 0) {
    			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Amount must be greater than zero";
    			return;
    		}
.		if(!$scope.toAccount.Email){
			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Invalid email";
    			return;
		  $scope.dataLoading = true;
      	  console.log("FromAccount:" + $scope.fromAccount.id)
      	  TransferFundsEPSerivce.verifyEmailandTransfer($scope.Email, $scope.fromAccount, $scope.transferAmount),
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

	$scope.verifyPhoneandTransfer = function() {
		
    		if(!$scope.transferAmount || $scope.transferAmount <= 0) {
    			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Amount must be greater than zero";
    			return;
    		}
		if(!$scope.toAccount.Phone){
			$scope.isTransferSuccess = false;
    			$scope.transferErrorMsg = "Invalid phone number";
    			return;
		}

      	  $scope.dataLoading = true;
      	  console.log("FromAccount:" + $scope.fromAccount.id)
      	  TransferFundsEPSerivce.verifyPhoneandTransfer($scope.Phone, $scope.fromAccount, $scope.transferAmount,
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
