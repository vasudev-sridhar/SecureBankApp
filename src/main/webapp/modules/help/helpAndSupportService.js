// JavaScript source code
'use strict';

angular.module('HelpCenter')

    .factory('helpAndSupportService',
        ['Base64', '$http', '$rootScope', '$timeout',
        	function (Base64, $http, $rootScope, $timeout) {
            // Initialize the service.
            var service = {};

           
            
            service.UpdateEmail = function (newInfo, callback) {
            	console.log(newInfo)
            	var body = {
                		"userid": $rootScope.userId,
                		"newInfo": newInfo,
                		}
                $http.post('/api/user/updateEmail', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsuccessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating customer contact info.';
                        }
                    });
            };
            
            service.UpdateAddress = function (newInfo, callback) {
            	console.log(newInfo)
            	var body = {
                		"userid": $rootScope.userId,
                		"newInfo": newInfo,
                		}
                $http.post('/api/user/updateAddress', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsuccessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating customer contact info.';
                        }
                    });
            };
           
            service.UpdatePhone = function (newInfo, callback) {
            	console.log(newInfo)
            	var body = {
                		"userid": $rootScope.userId,
                		"newInfo": newInfo,
                		}
                $http.post('/api/user/updatePhone', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsuccessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating customer contact info.';
                        }
                    });
            };
            
            service.UpdateDOB = function (newInfo, callback) {
            	console.log(newInfo)
            	var body = {
                		"userid": $rootScope.userId,
                		"newInfo": newInfo,
                		}
                $http.post('/api/user/updateDOB', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsuccessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error updating customer contact info.';
                        }
                    });
            };
            
            service.ScheduleAppointment = function (date, time, callback) {               
            	var body = {
                		"userid": $rootScope.userId,
                		"date": date,
                		"time" : time
                	}
                $http.post('/api/appointment/create', body)
                    .success(function (response) {
                        console.log(response);
                        callback(response);
                        // Handle the case of an unsuccessful HTTP post response.
                        if (!response.isSuccess) {
                            response.message = 'Error scheduling appointment.';
                        }
                    });
            };

            // Finish by returning the service.
            return service;
        }]);