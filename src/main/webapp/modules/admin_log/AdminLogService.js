'use strict';

angular.module('Authentication')

    .factory('AuthenticationService',
        ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
            function (Base64, $http, $cookieStore, $rootScope, $timeout) {
                var service = {};

                service.GetLog = function (callback) {

                    // Get a list of the admin log records.
                    $http.get('/api/logs/list')
                        .success(function (response) {

                            console.log(response);

                            if (!response.isSuccess) {
                                response.message = 'Error getting log.';
                            }

                            callback(response);
                        });
                };

                return service;
            }])