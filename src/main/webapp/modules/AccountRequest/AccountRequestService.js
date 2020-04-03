'use strict';

angular.module('AccountRequest')

.factory('AccountRequestService',
    ['Base64', '$http', '$window', '$rootScope', '$timeout',
    function (Base64, $http, $window, $rootScope, $timeout) {
        var service = {};


     service.submitAccountType = function (id,callback) {
                 console.log("Submitting Account Type...");
        	 var account_id_details = {'accountType': id};
                 var accountType2 = JSON.stringify(account_id_details);
                 console.log(account_id_details);
                 $http.post('/api/account/newAccount/' , account_id_details)
                     .success(function (response) {
                        console.log(response);
                        var msg = "Account Request submitted successfully. Pending Approval. "
                        $window.alert(msg);
                        callback(response);
                        }).error(function (response) {
                                         	console.log(response);
                                         	var msg2 = "Account Request submission failed."
                                            $window.alert(msg2);
                                             callback(response);

                                        });

                };


        return service;
    }]);