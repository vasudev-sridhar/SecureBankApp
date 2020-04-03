'use strict';
 
angular.module('TransferFunds')
 
.factory('TransferFundsService',
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
          
      service.transferFunds = function (frmAcc,toAcc, transferAmt, callback) {
        	console.log("transferFunds...");
        	var body = {
            		"fromAccNo":frmAcc,
            		"toAccNo": toAcc,
            		"transferAmount" : transferAmt
            	}
            $http.post('/api/transaction/transfer', body)
                .success(function (response) {
                	console.log(response);
                    callback(response);
            }).error(function (response) {
              	console.log(response);
                  callback(response);
                 
             });
        };
		
		
        return service;
    }]);
