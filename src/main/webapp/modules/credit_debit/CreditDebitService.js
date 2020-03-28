'use strict';
 
angular.module('TransactionRequest')
 
.factory('CreditDebitService',
    ['Base64', '$http', '$rootScope', '$timeout',
    function (Base64, $http, $rootScope, $timeout) {
        var service = {};
		
	 service.updateBalance = function (amount, accountNo, callback) {
		 var body = {
			 "accountNo": accountNo,
			 "amount": amount
		 }
		 console.log("updateBalance...");
	     $http.post('/api/transaction/balance', body)
		 .success(function (response) {
            console.log(response);
            callback(response);
		 }).error(function (response) {
            console.log(response);
            callback(response);
		 });          
 
       };   
                   
       service.getAccounts = function (userid, callback) {
       	console.log("getAccounts...");
           $http.get('/api/account/get/' + userid)
               .success(function (response) {
               	console.log(response);
                   callback(response);
           });

       };
	service.approveAccount = function (id,callback) {
		console.log("Approve Account...");

          $http.post('/request/approve/' + id)
              .success(function (response) {
                console.log(response);

                    callback(response);
                });

        };
 
     service.declineAccount = function (id,callback) {
		console.log("Decline Accounts...");
            $http.post('/request/decline/' + id)
                .success(function (response) {
                console.log(response);

                    callback(response);
                });
        };	
		
       
        return service;
    }]);
