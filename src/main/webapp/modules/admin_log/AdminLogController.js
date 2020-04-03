'use strict';

angular.module('AdminLog')

    .controller('AdminLogController',
        ['$scope', '$rootScope', '$state', 'AdminLogService',
            function ($scope, $rootScope, $state, AdminLogService) {
                // reset login status
                console.log("AdminLogController")

                $scope.GetLog = function () {
                    $scope.dataLoading = true;

                    AdminLogService.GetLog(function (response) {

                        console.log(response)

                        if (response.isSuccess) {

                            // Set the log on the scope to the returned log.
                            $scope.log = response.log;
                        } else {
                            $scope.error = response.message;
                        }

                        $scope.dataLoading = false;
                    });
                };
            }]);