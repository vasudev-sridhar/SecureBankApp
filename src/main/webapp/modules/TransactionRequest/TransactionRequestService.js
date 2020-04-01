'use strict';
 
angular.module('TransactionRequest')
 
.factory('TransactionRequestService',
    ['Base64', '$http', '$rootScope', '$timeout',
    function (Base64, $http, $rootScope, $timeout) {
        var service = {};
		
	 service.createTransferRequest = function (fromAccNo,toAccNo, transferAmount, callback) {
		 var transaction_details = 
	        {
	            "fromAccNo": fromAccNo,
	            "toAccNo": toAccNo,
	            "transferAmount":transferAmount
	        }
		 var transactions = JSON.stringify(transaction_details);
	
		 console.log("Transaction Details...");
	     $http.post('/api/transaction/transfer', transaction_details)
		 .success(function (response) {
            console.log(response);
            callback(response);
		 });          
 
       };
 
       service.getTransactions = function (callback) {
    		 //var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;

    		 console.log("Transaction Details...");
    		 var query = ($rootScope.isTAC)? "?userName=" + $rootScope.tacUser.username : "";
    	     $http.get('/api/transaction/get' + query)
    		 .success(function (response) {
	            console.log(response);
                callback(response);
    		 });          
    	 
       };   
                   
   service.getAccounts = function (page,id,callback) {
		console.log("getAccounts...");
		
         $http.get('/request/list/' + page)
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
