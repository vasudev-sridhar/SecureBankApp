'use strict';
 
angular.module('Authentication')
 
.controller('LoginController',
    ['$scope', '$rootScope', '$state', 'AuthenticationService',
    function ($scope, $rootScope, $state, AuthenticationService) {
        // reset login status
    	console.log("LoginController")
        AuthenticationService.ClearCredentials();
 
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
            	console.log("controller response")
            	console.log(response)
                if(response.isSuccess) {
                	console.log("UserId " + response.userId);
                	$rootScope.userId = response.userId;
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    //$state.go('/');
                    $state.go('Dashboard')
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);