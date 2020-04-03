'use strict';

angular.module('AdminLog')

    .factory('AdminLogService',
         ['$http', function ($http) {
            // Initialize the service.
            var service = {};

            service.GetTransactionsPendingApproval = function (callback) {

                $http.get('/api/cheque/listIssueApprovals')
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error locating Cheques from customer.';
                        }

                        // Call the callback function.
                        callback(response);
                    });
            };

                return service;
            }])