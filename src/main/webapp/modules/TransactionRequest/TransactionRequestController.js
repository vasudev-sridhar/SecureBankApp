'use strict';
 
angular.module('TransactionRequest')
 
.controller('TransactionRequestController',
    ['$scope', '$rootScope','$state','TransactionRequestService',
    function ($scope, $rootScope, $state, TransactionRequestService) {
    	console.log("TransactionRequestService")
    	console.log($state.current)
		$scope.accountList = [];
    	$scope.transactionList = [];

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
