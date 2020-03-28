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
						  $scope.transactionList[i].transactionTimestamp = $scope.formatDate(t);
					  }
				  }
				  $scope.dataLoading = false;
			  })
	    }
	    
	    $scope.formatDate = function(timestamp) {

	    	 // Months array
	    	 var months_arr = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

	    	 // Convert timestamp to milliseconds
	    	 var date = new Date(timestamp);

	    	 // Year
	    	 var year = date.getFullYear();

	    	 // Month
	    	 var month = months_arr[date.getMonth()];

	    	 // Day
	    	 var day = date.getDate();

	    	 // Hours
	    	 var hours = date.getHours();

	    	 // Minutes
	    	 var minutes = "0" + date.getMinutes();

	    	 // Seconds
	    	 var seconds = "0" + date.getSeconds();

	    	 // Display date time in MM-dd-yyyy h:m:s format
	    	 var convdataTime = month+'-'+day+'-'+year+' '+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
	    	 
	    	 return convdataTime;
	    	}
	    
	    //$scope.getAccounts();
	    //$scope.createTransferRequest()
	    $scope.getTransactions();
    
    }]);
