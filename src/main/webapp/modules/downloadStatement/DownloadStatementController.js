'use strict';
 
angular.module('DownloadStatement')
 
.controller('DownloadStatementController',
    ['$scope', '$rootScope','$state', 'TransferFundsService', 'DownloadStatementService',
    function ($scope, $rootScope, $state, TransferFundsService, DownloadStatementService) {
		
		$scope.fromAccountList = [];
    	$scope.fromAccount;
    	$scope.isDownloadSuccess = false;
    	$scope.downloadErrorMsg = "";
    	
    	$scope.downloadStatement = function() {
      	  $scope.dataLoading = true;
      	  console.log("Downloading Statement")
      	  DownloadStatementService.downloadStatement($scope.fromAccount,
      			  function(response) {
      		  console.log("controller response")
      		  if(response && response.isSuccess) {
    			  $scope.downloadErrorMsg = response.msg;
    			  $scope.isDownloadSuccess = true;
    		  } else {
    			  $scope.downloadErrorMsg = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isDownloadSuccess = false;
    		  }
      		  $scope.dataLoading = true;
      	  })
		}


		$scope.getAccounts = function() {
      	  $scope.dataLoading = true;
      	  console.log("userid: " + $rootScope.userId)
      	  var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;
      	  DownloadStatementService.getAccounts(userId, function(response) {
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
        	  DownloadStatementService.getAllAccounts(function(response) {
        		  console.log("controller response")
        		  if(response && response.accounts) {
	      			  $scope.toAccountList = response.accounts;
	      		  } else {
	      			  console.log("Something went wrong")
	      		  }
        		  $scope.dataLoading = true;
        	  })
          }
	    
	    $scope.getAccounts();
	    $scope.getAllAccounts();
    
    }]);
