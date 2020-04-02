'use strict';
 
angular.module('DownloadStatement')
 
.factory('DownloadStatementService',
    ['Base64', '$http', '$rootScope', '$timeout',
    function (Base64, $http, $rootScope, $timeout) {
        var service = {};

        service.getAccounts = function (userid, callback) {
       	console.log("getAccounts...");
           $http.get('/api/account/get/' + userid)
               .success(function (response) {
               	console.log(response);
                   callback(response);
           }).error(function (response) {
              	console.log(response);
                callback(response);
               
           });
       };
       
       service.getAllAccounts = function (callback) {
          	console.log("getAllAccounts...");
              $http.get('/api/account/get')
                  .success(function (response) {
                  	console.log(response);
                      callback(response);
              }).error(function (response) {
                	console.log(response);
                    callback(response);
                   
               });
          };
          
      service.downloadStatement = function (frmAcc, callback) {
            console.log("Downloading Statement...");
    		var query = "?userName=" + frmAcc;
    	        $http.get('/api/transaction/statement',{responseType: 'arraybuffer'})
    		        .success(function (response) {
	                    console.log(response);
                        callback(response);
             });
        };
		
		
        return service;
    }]);
