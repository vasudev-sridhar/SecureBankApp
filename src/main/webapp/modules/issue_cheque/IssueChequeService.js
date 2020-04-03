'use strict';
 
angular.module('IssueCheque')
 
.factory('IssueChequeService',
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

       service.getAllCheques = function (accNumber, callback) {
       	console.log("getCheques...");
           $http.get('/api/cheque/listAvailableCheques/' + accNumber)
               .success(function (response) {
               	// console.log(response);
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
          
      service.issueCheque = function (frmAcc,toAcc, transferAmt, callback) {
        	console.log("issue Cheque...");
        	var body = {
            		"fromAccNo":frmAcc,
            		"toAccNo": toAcc,
            		"transferAmount" : transferAmt
                }
            // var transactions = JSON.stringify(transaction_details);
            console.log(body);
            $http.post('api/cheque/issue', body)
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
