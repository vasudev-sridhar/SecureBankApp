'use strict';
 
angular.module('TransactionRequest')
 
.controller('TransactionRequestController',
    ['$scope', '$rootScope','$state','TransactionRequestService',
    function ($scope, $rootScope, $state, TransactionRequestService) {
    	console.log("TransactionRequestService")
      
		$scope.accountList = [];
    	$scope.transactionList = [];
	    $scope.getAccounts = function() {
			  $scope.dataLoading = true;
			  TransactionRequestService.getAccounts(1, function(response) {
				  console.log("controller response")
				  console.log(response)
				  if(response) {
					  $scope.accountList = response.accounts;
				  }
				  $scope.dataLoading = true;
			  })
	    }
	    $scope.CreateTransferRequest = function(){
	    	TransactionRequestService.createTransferRequest(1,2,300)
	    }
	  
	    $scope.getTransactions = function() {
			  $scope.dataLoading = true;
			  TransactionRequestService.getTransactions(function(response) {
				  console.log("controller response")
				  console.log(response)
				  if(response) {
					  $scope.transactionList = response;
					  for(var i=0; i < $scope.transactionList.length; i++) {
						  var t = $scope.transactionList[i].transactionTimestamp;
						  $scope.transactionList[i].transactionTimestamp = $rootScope.formatDate(t);
					  }
				  }
				  $scope.dataLoading = false;
			  })
	    }
	    	    
	    //$scope.getAccounts();
	    //$scope.createTransferRequest()
	    $scope.getTransactions();
    
    }]);
