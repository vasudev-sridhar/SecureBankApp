'use strict';
 
angular.module('Otp')
 
.factory('OtpTestService',
    ['$http', '$rootScope', '$timeout','$cookieStore',
    function ($http, $rootScope, $timeout, $cookieStore) {
    	console.log("The Otp has sent 1");
        var service = {};      
        service.getotp = function (callback) {
            $http.get('/api/generateOtp')
                .success(function (response) {
                    callback(response);
                });
        }
        service.verifyOtp = function (oTp,callback) {
        	console.log("The Otp has been received");
            $http.get('/api/verifyOtp'+'?'+'otp='+oTp)
                .success(function (response) {
                	console.log(response);
                    callback(response);
                });
        }
        return service
    }]);
