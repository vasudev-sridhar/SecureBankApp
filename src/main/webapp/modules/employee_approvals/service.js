// JavaScript source code
'use strict';

angular.module('Approvals')

    .factory('ApprovalsService',
        [function () {
            // Initialize the service.
            var service = {};

            // Define functions on the service.

            // Create a new request.
            //      requestId = the unique ID for the request to create
            //      type = the type of request
            //      customerId = the ID of the customer the request is for
            //      callback = the function to run when the scheduling is complete
            service.CreateRequest = function (requestId, type, customerId, callback) {

                // Create the time stamp for the request creation (and update).
                var timeStamp = new Date();

                // Make a call to the approval API
                //      TODO - make sure this is the correct URI for the API
                $http.post('/api/approvals', { requestId: requestId, type: type, createdTimeStamp: timeStamp, updatedTimeStamp: timeStamp, customerId: customerId })
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error creating approval request.';
                        }
                    });
            };

            // Update Request with an approve or deny action
            //      requestId = the ID of the request to update
            //      requestState = the value of the request state to update
            //      callback = the function to run when the updating is complete
            service.UpdateRequest = function (requestId, requestState, callback) {

                // Get the existing request record.
                var requestRecord = {};
                $http.get('api/approvals', { requestId: requestId })
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating approval request.';
                        }
                        else {
                            // HTTP get was successful, so grab the value.
                            requestRecord = response.value;
                        }
                    });

                // Update the request record with the new status value, and update the updated time stamp.
                requestRecord.requestState = requestState;
                requestRecord.updatedTimeStamp = new Date();

                // Make a call to the approval API to post the updated request record.
                //      TODO - make sure this is the correct URI for the API
                $http.post('/api/approvals', requestRecord)
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating approval request.';
                        }
                    });
            };

            // Check if request is approved.
            //      requestId = the ID of the request to update
            //      callback = the function to run when the updating is complete
            service.RequestApproved = function (requestId, callback) {

                // Get the existing request record.
                var requestRecord = {};
                $http.get('api/approvals', { requestId: requestId })
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error locating request.';
                        }
                        else {
                            // HTTP get was successful, so grab the value.
                            requestRecord = response.value;
                        }
                    });

                // Return whether the request is approved or not.
                return requestRecord.isApproved;
            };

            // Get requests for customer.
            //      customerId = the ID of the customer to get requests for
            //      callback = the function to run when the updating is complete
            service.GetRequestsForCustomer = function (customerId, callback) {

                // Get existing request records for the customer.
                var requestRecords = {};
                $http.get('api/approvals', { customerId: customerId })
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP get response.
                        if (!response.isSuccess) {
                            response.message = 'Error locating requests for customer.';
                        }
                        else {
                            // HTTP get was successful, so grab the value.
                            requestRecords = response.value;
                        }
                    });

                // Return the object containing the request records.
                return requestRecords;
            };


            // Finish by returning the service.
            return service;
        }]);