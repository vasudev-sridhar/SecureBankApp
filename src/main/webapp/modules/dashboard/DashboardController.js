'use strict';
 
angular.module('Dashboard')
 
.controller('DashboardController',
    ['$scope', '$rootScope','$state','DashboardService',
    function ($scope, $rootScope, $state, DashboardService) {
      console.log("DashboardService")
      $scope.accountList = [];
      $rootScope.isEmployee = false;
      $scope.getAccounts = function() {
    	  $scope.dataLoading = true;
    	  DashboardService.getAccounts($rootScope.userId, function(response) {
    		  console.log("controller response")
    		  console.log(response)
    		  if(response) {
    			  $scope.accountList = response.accounts;
    		  }
    		  $scope.dataLoading = true;
    	  })
      }
      
      $scope.getUser = function(userId) {
    	  $scope.dataLoading = true;
    	  DashboardService.getUser(userId, function(response) {
    		  if(response) {
    			  $rootScope.user = response;
    			  console.log(response)
    			  var a = response.authRole.roleType;
    			  if(a == "ADMIN" || a == "TIER1" || a == "TIER2") {
    				  console.log("employee!")
    				  $rootScope.isEmployee = true;
    				  console.log($rootScope.isEmployee)
    			  }
    		  }
    		  $scope.dataLoading = true;
    	  })
      }
      
      $rootScope.formatDate = function(timestamp) {

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
      
      $rootScope.goLogout = function() {
    	  //call Logout
    	  $state.go('Login')
      }
      
      $rootScope.goTransaction = function() {
    	  $state.go('Transaction')
      }
      
      $rootScope.goDashboard = function() {
    	  $state.go('Dashboard')
      }
      
      $rootScope.goCreditDebit = function() {
    	  $state.go('CreditDebit')
      }
      
      $rootScope.goTransferFunds = function() {
    	  $state.go('TransferFunds')
      }
      
      $rootScope.goHelpandSupport = function() {
    	  $state.go('HelpCenter')
      }
      
      $rootScope.goApprovals = function() {
    	  $state.go('Approvals')
      }
      
      $scope.getUser($rootScope.userId)
      $scope.getAccounts();
    }]);