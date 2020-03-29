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
    			  var a = response.authRole.roleType;
    			  if(a == "ADMIN" || "TIER1" || "TIER2")
    				  $rootScope.isEmployee = true;
    		  }
    		  $scope.dataLoading = true;
    	  })
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