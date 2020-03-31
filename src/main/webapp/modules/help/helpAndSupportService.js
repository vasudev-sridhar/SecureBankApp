// JavaScript source code
'use strict';

angular.module('HelpCenter')

    .factory('helpAndSupportService',
        ['Base64', '$http', '$rootScope', '$timeout',
        	function (Base64, $http, $rootScope, $timeout) {
            // Initialize the service.
            var service = {};

            // Define functions on the service.

            // Update customer's contact info
            //      customerId = the ID of the customer to update
            //      contact = the value of the contact to update
            //      emailId = the customer's email ID to update
            //      callback = the function to run when the updating is complete
           /* service.UpdateContact = function (customerId, contact, emailId, callback) {

                // Make a call to the contact API
                //      TODO - make sure this is the correct URI for the API
                $http.post('/api/contact', { customerId: customerId, contact: contact, emailId: emailId })
                    .success(function (response) {
                        console.log(response);

                        // Handle the case of an unsucessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating customer contact info.';
                        }
                    });
            };*/

            // Schedule an appointment.
            //      date = the date to schedule
            //      time = the time to schedule
            //      callback = the function to run when the scheduling is complete
            service.ScheduleAppointment = function (date, time, callback) {

                // Make a call to the appointment API
                //      TODO - make sure this is the correct URI for the API
            	var body = {
                		"userid": $rootScope.userId,
                		"date": date,
                		"time" : time
                	}
                $http.post('/api/appointment/create', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsucessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error scheduling appointment.';
                        }
                    });
            };

            // Finish by returning the service.
            return service;
        }]);