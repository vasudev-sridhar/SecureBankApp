'use strict';
 
angular.module('TransferFunds')
 
.controller('TransferFundsController',
    ['$scope', '$rootScope','$state','TransferFundsService',
    function ($scope, $rootScope, $state, TransferFundsService) {
    	
    	$scope.fromAccountList = [];
    	$scope.toAccountList = [];
    	$scope.toAccountList2 = [];
    	$scope.fromAccount;
    	$scope.fromAccount2;
    	$scope.toAccount;
    	$scope.toAccount2;
    	$scope.transferAmount;
    	$scope.isTransferSuccess = false;
    	$scope.transferErrorMsg = "";
    	$scope.isTransferSuccess2 = false;
    	$scope.transferErrorMsg2 = "";
    	$scope.phonemailoption;
    	$scope.isPhonemailValidate = true;
    	$scope.phoneMailList = ["Phone", "Email"];
    	$scope.phonemailText;
    	$scope.islookupsuccess = false;
    	
    	$scope.validatePhoneEmail = function() {
    		console.log("validating")
    		if($scope.phonemailoption == "Phone"){
    			$scope.isPhonemailValidate = /^\d+$/.test($scope.phonemailText);
    		}
    		else if ($scope.phonemailoption == "Email"){
    			 var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    			$scope.isPhonemailValidate = re.test($scope.phonemailText);    			
    		}
    		
    	}
    	
    	$scope.getToUserAccounts = function() {
    		$scope.validatePhoneEmail();
    		if($scope.isPhonemailValidate){
    			if($scope.phonemailoption == "Email"){
    				TransferFundsService.getAccountsbyEmail($scope.phonemailText, function(response) {
      		  console.log("controller response")
      		  if(response && response.accounts) {
      			$scope.toAccountList2 = response.accounts;
      			$scope.islookupsuccess = true;
      		  } else {
      			$scope.islookupsuccess = false;
      			  console.log("Something went wrong")
      		  }
      		  $scope.dataLoading = true;
      	  })
    			}
    			else if($scope.phonemailoption == "Phone"){
    				TransferFundsService.getAccountsbyPhone($scope.phonemailText, function(response) {
      		  console.log("controller response")
      		  if(response && response.accounts) {
      			$scope.toAccountList2 = response.accounts;
      			$scope.islookupsuccess = true;
      		  } else {
      			$scope.islookupsuccess = false;
      			  console.log("Something went wrong")
      		  }
      		  $scope.dataLoading = true;
      	  })
    			}
    			
    			
    		}    		
    		
    	}
	    	    
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
    			  $scope.fromAccount2 = null;
    			  $scope.toAccount2 = null;
    			  
    		  } else {
    			  $scope.transferErrorMsg = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isTransferSuccess = false;
    		  }
      		  $scope.dataLoading = true;
      	  })
        }
    	
    	
    	$scope.transferFundsbyphonemail = function() {
    		if(!$scope.transferAmount2 || $scope.transferAmount2 <= 0) {
    			$scope.isTransferSuccess2 = false;
    			$scope.transferErrorMsg2 = "Amount must be greater than zero";
    			return;
    		}
    		if($scope.fromAccount2.id == $scope.toAccount2.id) {
    			$scope.isTransferSuccess2 = false;
    			$scope.transferErrorMsg2 = "Both accounts must be different";
    			return;
    		}
      	  $scope.dataLoading = true;
      	  console.log("FromAccount:" + $scope.fromAccount2.id)
      	  TransferFundsService.transferFunds($scope.fromAccount2.id, $scope.toAccount2.id, $scope.transferAmount2,
      			  function(response) {
      		  console.log("controller response")
      		  if(response && response.isSuccess) {
    			  $scope.transferErrorMsg2 = response.msg;
    			  $scope.isTransferSuccess2 = true;
    			  $scope.transferAmount2 = 0;
    			  $scope.toAccountList2 = [];
    			  $scope.phonemailText;
    			  
    		  } else {
    			  $scope.transferErrorMsg2 = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isTransferSuccess2 = false;
    		  }
      		  $scope.dataLoading = true;
      	  })
        }
	    
	    $scope.getAccounts();
	    $scope.getAllAccounts();
	    //$scope.createTransferRequest()
	    //$scope.getTransactions();
    
    }]);
