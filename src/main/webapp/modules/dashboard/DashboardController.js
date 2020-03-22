'use strict';
 
angular.module('Dashboard')
 
.controller('DashboardController',
    ['$scope', '$state','DashboardService',
    function ($scope, $state, DashboardService) {
      console.log("DashboardService")
      $scope.accountList = [];
      $scope.getAccounts = function() {
    	  $scope.dataLoading = true;
    	  DashboardService.getAccounts(1, function(response) {
    		  console.log("controller response")
    		  console.log(response)
    		  if(response) {
    			  $scope.accountList = response.accounts;
    		  }
    		  $scope.dataLoading = true;
    	  })
      }
      
      $scope.logout = function() {
    	  //call Logout
    	  $state.go('Login')
      }
      
      $scope.getAccounts();
    }]);