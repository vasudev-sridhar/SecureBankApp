'use strict';

angular.module('AccountRequest')

.controller('AccountRequestController',
    ['$scope', '$window', '$rootScope','$state','AccountRequestService',
    function ($scope, $window, $rootScope, $state, AccountRequestService) {
    	$scope.approveErrorMsg = "";
    	$scope.isApproveSuccess = false;
    	$scope.declineErrorMsg = "";
    	$scope.isDeclineSuccess = false;
        $scope.accountList =  [ {name:'Savings Account', id:0},
                                 {name:'Checkings Account', id:1},
                                 {name:'Credit Card Account', id:2}
                               ];
    	$scope.account;


       $scope.SubmitAccountType = function () {
               if(!$scope.SelectedAccountId){
            	   $scope.alertMsg = "Please select an account type";
                   $scope.isDisplayAlert = true;
                //var msg = "Please select an account type";
                //$window.alert(msg);
                }
                else{
                var accountId = $scope.SelectedAccountId;
                var accountName;
                for (var i = 0; i < $scope.accountList.length; i++) {
                    if ($scope.accountList[i].id == accountId) {
                        accountName = $scope.accountList[i].name;
                        break;
                    }
                }
                var message = "Value: " + accountId + " Text: " + accountName + "\n";
                console.log(message);
                AccountRequestService.submitAccountType(accountId, function(response) {
                      		  console.log("controller response");
                      		  if(response.message == "success"){
                        			$scope.alertMsg = "Account Request submitted successfully. Pending Approval. ";
                        			$scope.isDisplayAlert = true;
                        		  }
                        		  else{
                        			$scope.alertMsg = "Account Request submission failed.";
                        			$scope.isDisplayAlert = true;
                        		  }
                      		  
                      	  })
         }}


    }]);

