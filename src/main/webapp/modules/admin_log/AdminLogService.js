'use strict';

angular.module('AdminLog')

    .factory('AdminLogService',
         ['$http', function ($http) {
            // Initialize the service.
            var service = {};

            service.GetTransactionsPendingApproval = function (callback) {

                $http.get('/api/log/list')
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response) {
                            response.message = 'Error locating logs.';
                        }

                        // Call the callback function.
                        callback(response);
                    });
            };

                return service;
            }])