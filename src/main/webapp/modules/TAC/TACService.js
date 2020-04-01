'use strict';
 
angular.module('TAC')
 
.factory('TACService',
    ['Base64', '$http', '$rootScope', '$timeout',
    function (Base64, $http, $rootScope, $timeout) {
        var service = {}; 
                   
        service.getAllUsers = function (callback) {
			console.log("getAllUsers...");
			
	         $http.get('/api/user/get/')
	             .success(function (response) {
	                console.log(response);
	
	                    callback(response);
	                });
	
	    };
       
        return service;
    }]);
