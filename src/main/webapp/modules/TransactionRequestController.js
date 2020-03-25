'use strict';
 
angular.module('TransactionRequest')
 
.controller('TransactionRequestController',
    ['$scope', '$state','TransactionRequestService',
    function ($scope, $state, TransactionRequestService) {
      console.log("TransactionRequestService")
      
	$scope.accountList = [];
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
      
      $scope.getAccounts();
      $scope.createTransferRequest()
    }]);