'use strict';
 
angular.module('TAC')
 
.controller('TACController',
    ['$scope', '$rootScope','$state','TACService',
    function ($scope, $rootScope, $state, TACService) {
    	console.log("TACService")
    	console.log($state.current)
		$scope.userList = [];
    	$scope.transactionList = [];
    	$scope.tacUser;
	    $scope.getAllUsers = function() {
			  $scope.dataLoading = true;
			  TACService.getAllUsers(function(response) {
				  console.log("controller response")
				  console.log(response)
				  if(response) {
					  $scope.userList = response;
				  }
				  $scope.dataLoading = true;
			  })
	    }
	    
	    $scope.startTAC = function() {
	    	$rootScope.isTAC = true;
	    	$rootScope.tacUser = $scope.tacUser;
	    	$state.go('Dashboard');
	    }
	    
	    $scope.getAllUsers();
    
    }]);
