'use strict';
 
angular.module('IssueCheque')
 
.controller('IssueChequeController',
    ['$scope', '$rootScope','$state','IssueChequeService',
    function ($scope, $rootScope, $state, IssueChequeService) {
    	
    	$scope.fromAccountList = [];
		$scope.toAccountList = [];
		$scope.chequeList = [];
    	$scope.fromAccount;
    	$scope.toAccount;
		$scope.transferAmount;
		$scope.toAccountNew;
    	$scope.isIssueSuccess = false;
		$scope.issueErrorMsg = "";
		$scope.fromAccountDeposit;
		$scope.chequeNumber;
		$scope.depositChequeErrorMsg = "";
		$scope.isDepositChequeSuccess = false;
	    	    
    	$scope.getAccounts = function() {
      	  $scope.dataLoading = true;
      	  console.log("userid: " + $rootScope.userId)
      	  var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;
      	  IssueChequeService.getAccounts(userId, function(response) {
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
        	  IssueChequeService.getAllAccounts(function(response) {
        		  console.log("controller response")
        		  if(response && response.accounts) {
	      			  $scope.toAccountList = response.accounts;
	      		  } else {
	      			  console.log("Something went wrong")
	      		  }
        		  $scope.dataLoading = true;
        	  })
		  }

		  $scope.getCheques = function() {
        	  $scope.dataLoading = true;
        	  IssueChequeService.getAllCheques($scope.fromAccountDeposit.id, function(response) {
				  console.log("controller response")
				//   console.log(response)
        		  if(response) {
	      			  $scope.chequeList = response;
	      		  } else {
	      			  console.log("Something went wrong")
	      		  }
        		  $scope.dataLoading = true;
        	  })
		  }

		  $scope.depositCheque = function() {
        	  $scope.dataLoading = true;
        	  IssueChequeService.depositCheque($scope.chequeNumber.checkId, function(response) {
				  console.log("controller response --- >")
				//   console.log($scope.chequeNumber.checkId)
				//   console.log(response)
        		  if(response) {
	      			  $scope.isDepositChequeSuccess = true;
	      		  } else {
						console.log("Something went wrong");
						$scope.isDepositChequeSuccess = false;
	      		  }
        		  $scope.dataLoading = true;
        	  })
		  }
    	
    	$scope.issueCheque = function() {
    		if(!$scope.transferAmount || $scope.transferAmount <= 0) {
    			$scope.isIssueSuccess = false;
    			$scope.issueErrorMsg = "Amount must be greater than zero";
    			return;
    		}
    		if($scope.fromAccount.id == $scope.toAccountNew) {
    			$scope.isIssueSuccess = false;
    			$scope.issueErrorMsg = "Both accounts must be different";
    			return;
    		}
      	  $scope.dataLoading = true;
			console.log("FromAccount:" + $scope.fromAccount.id)

      	  IssueChequeService.issueCheque($scope.fromAccount.id, $scope.toAccountNew, $scope.transferAmount,
      			  function(response) {
				console.log("controller response")
				console.log(response);
      		  if(response && response.isSuccess) {
    			  $scope.issueErrorMsg = response.msg;
    			  $scope.isIssueSuccess = true;
    			  $scope.transferAmount = 0;
    		  } else {
    			  $scope.issueErrorMsg = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isIssueSuccess = false;
    		  }
      		  $scope.dataLoading = true;
      	  })
		}
		
	    
	    $scope.getAccounts();
	    $scope.getAllAccounts();
	    //$scope.createTransferRequest()
	    //$scope.getTransactions();
    
    }]);
