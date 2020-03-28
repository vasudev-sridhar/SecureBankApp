'use strict';
 
angular.module('Dashboard')
 
.controller('DashboardController',
    ['$scope', '$rootScope','$state','DashboardService',
    function ($scope, $rootScope, $state, DashboardService) {
      console.log("DashboardService")
      $scope.accountList = [];
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
      
      $scope.getAccounts();
    }]);