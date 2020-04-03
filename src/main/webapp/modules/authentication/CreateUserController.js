'use strict';

angular.module('CreateUser')

    .controller('CreateUserController', 
        ['$scope', '$rootScope', '$state', 'AuthenticationService',
            function ($scope, $rootScope, $state, AuthenticationService) {
                // reset login status
                console.log("CreateUserController")
                AuthenticationService.ClearCredentials();
                $scope.passwordMessage="password";
                $scope.goLogin = function () {$state.go('Login')}
                $scope.CreateUser = function () {
                    $scope.dataLoading = true;
                    console.log("registration");                 

                    var validUsername = AuthenticationService.CheckUserNameAvailability($scope.username);
                    // Set the message and return if the username is not valid (available).
                    if (validUsername === false) {
                        $scope.usernameMessage = "Username taken. Try another one.";
                        return;
                    }

                    // Username is valid, so clear the message.
                    $scope.usernameMessage = "";

                    var validPwd = AuthenticationService.ValidatePassword($scope.password);

                    // Set the message and return if the password is not valid.
                    if (validPwd.ok === false) {
                        $scope.passwordMessage = validPwd.message;
                        return;
                    }

                    AuthenticationService.CreateUser($scope.name, $scope.birthday, $scope.email, $scope.phone, $scope.address, $scope.username, $scope.password, function (response) {
                        console.log("controller response")
                        console.log(response)

                        if (response.isSuccess) {
                        	var encrypted = $rootScope.MD5($scope.password);
                            //console.log("UserId " + response.userId);
                            AuthenticationService.Login($scope.username, encrypted, function(response) {
                            	console.log("controller response")
                            	console.log(response)
                                if(response.isSuccess) {
                                	console.log("UserId " + response.userId);
                                	$rootScope.userId = response.userId;
                                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                                    //$state.go('/');
                                    $rootScope.stateName = 'Dashboard'
                                    $state.go('Dashboard')
                                } else {
                                    $scope.error = response.message;
                                    $scope.dataLoading = false;
                                }
                            });
                            /*$rootScope.userId = response.userId;
                            AuthenticationService.SetCredentials($scope.username, $scope.password);

                            $rootScope.stateName = 'Dashboard'
                            $state.go('Dashboard')*/
                        } else {
                            $scope.error = response.message;
                            $scope.dataLoading = false;
                        }
                    });
                };

                $scope.ValidatePassword = function () {
                	console.log("pwvalide");
                    $scope.dataLoading = true;
                    var validPwd;
                    
                    validPwd = AuthenticationService.ValidatePassword($scope.password);

                    // Set the message and valid password flag.
                    $scope.passwordMessage = validPwd.message;
                    $scope.passwordInvalid = !validPwd.ok;

                    $scope.dataLoading = false;
                };

                $scope.CheckUserNameAvailability = function () {
                    $scope.dataLoading = true;

                    // Set the username available flag and message.
                    $scope.usernameUnavailable = !AuthenticationService.CheckUserNameAvailability($scope.username);
                    $scope.usernameMessage = $scope.usernameAvailable ? "" : "Username taken. Try another one.";
                    $scope.dataLoading = false;
                }
            }]);