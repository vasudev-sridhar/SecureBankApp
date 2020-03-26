'use strict';
 
angular.module('TransactionRequest')
 
.factory('TransactionRequestService',
    ['Base64', '$http', '$rootScope', '$timeout',
    function (Base64, $http, $rootScope, $timeout) {
        var service = {};
		
	 service.createTransferRequest = function (fromAccNo,toAccNo, transferAmount) {
	 var transaction_details = [
        {
            "fromAccNo": fromAccNo,
            "toAccNo": toAccNo,
            "transferAmount":transferAmount
        }
	 var transactions = JSON.stringify(transaction_details);

	 console.log("Transaction Details...");
     $http.post('/request/transfer/' + transactions )
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
    }])

 
