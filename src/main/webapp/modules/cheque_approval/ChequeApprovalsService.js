// JavaScript source code
'use strict';

angular.module('ChequeApprovals')

    .factory('ChequeApprovalsService',
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

            // Get pending account approvals for customer.
            //      callback = the function to run when the updating is complete
            service.GetAccountPendingApproval = function (callback) {

                // Get existing request records for the customer.
                //      Status = 3 refers to any requests that are pending approval.
                //var query = ($rootScope.isTAC) ? "?userName=" + $rootScope.userName + "&status=3" : "";
                $http.get('/api/account/get')
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error locating requests for customer.';
                        }

                        // Call the callback function.
                        callback(response);
                    });
            };

            // Approve or deny a pending account approval request.
            //      id = ID of request to approve
            //      approve = true for approve, false for decline
            //      callback = the function to run when the updating is complete
            service.RepondToAccountApproval = function (id, approve, callback) {

                // Approve the given account request.
                var action = (approve) ? "approve" : "decline";
                $http.post('/api/transaction/' + action + '/' + id)
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error approving request.';
                        }

                        // Call the callback function.
                        callback(response);
                    });
            };

            // Approve or deny a pending transaction approval request.
            //      id = ID of request to approve
            //      approve = true for approve, false for decline
            //      callback = the function to run when the updating is complete
            service.RepondToTransactionApproval = function (id, approve, callback) {

                // Approve the given account request.
                var action = (approve) ? "approve" : "reject";
                $http.post('/api/transaction/' + action + '/' + id)
                    .success(function (response) {
                        console.log(response);
                        if(!(response && response.isSuccess))
                        	response.message = response.msg

                        // Call the callback function.
                        callback(response);
                    }).error(function(response) {
                    	response.isSuccess = false;
                    	console.log(response);
                    	if(response && response.msg) {
                    		response.message = response.msg                    		
                    	} else if (response && response.error)
                    		response.message = response.error
                		else
                			response.message = 'Error ' + action + 'ing request.';
                    });
            };

            // Finish by returning the service.
            return service;
        }]);