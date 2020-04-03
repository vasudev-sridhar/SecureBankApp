'use strict';
 
angular.module('TransferFundsEP')
 
.factory('TransferFundsEPService',
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

      service.verifyEmailandTransfer = function (email, fromAcc, transferAmt, callback) {
                console.log("Checking if the email is tied to an account");
		var email = {
		     "Email" = e
 		}
		var body { 
            		"fromAccNo": fromAcc, 	
            		"transferAmount" : transferAmt
            	}
            $http.get('/api/accounts/email/{emailId}', { emailId : e})
	    if(email == e){
		console.log("transferFunds...");
        	var body = {
            		"fromAccNo":frmAcc,
            		"toAccNo": toAcc,
            		"transferAmount" : transferAmt
            	}
            $http.post('/api/transaction/transfer', body)
               
		.success(function (response) {
                 	onsole.log(response);
                    c allback(response);
          	}).error(function (response) {
              	console.log(response);
                  callback(response);
                 
             
		});

	    }
       };

      service.verifyPhoneandTransfer = function (PhoneNum, toAcc, transferAmt, callback) {
        	console.log("Checking if the phone# is tied to an account");
        	var phone = {
            		"Phone#": Phone
            	}

            $http.get('/api/accounts/contact/{phoneNo}', {phoneNo : Phone})
	    
            if(Phone == PhoneNum){

		console.log("transferFunds...");
        	var body = {
            		"fromAccNo":frmAcc,
            		"toAccNo": toAcc,
            		"transferAmount" : transferAmt
            	}
            $http.post('/api/transaction/transfer', body)
                
		.success(function (response) {
                 	onsole.log(response);
                    c allback(response);
          	}).error(function (response) {
              	console.log(response);
                  callback(response);
                 
             });
	    
	   }
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
