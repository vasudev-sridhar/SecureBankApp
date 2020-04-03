'use strict';

angular.module('AccountRequest')

.controller('AccountRequestController',
    ['$scope', '$window', '$rootScope','$state','AccountRequestService', 'DownloadStatementService',
    function ($scope, $window, $rootScope, $state, AccountRequestService, DownloadStatementService) {
    	$scope.approveErrorMsg = "";
    	$scope.isApproveSuccess = false;
    	$scope.declineErrorMsg = "";
    	$scope.isDeclineSuccess = false;
        $scope.accountList =  [ {name:'Savings Account', id:0},
                                 {name:'Checkings Account', id:1},
                                 {name:'Credit Card Account', id:2}
                               ];
    	$scope.account;

    	$scope.flg='true';
		$scope.isOtpValidated = false;
		

		$scope.getotp1 = function() {
    	  $scope.dataLoading = true;
//      console.log($rootScope.uname);
    	  DownloadStatementService.getotp(function(response) {
    		  console.log(response);
    		  $scope.otp1=response.message;
    		  })
		};
		  
		$scope.verifyOtp = function() {
    	  $scope.dataLoading = false;
//    	  	console.log($rootScope.uname);
			 DownloadStatementService.verifyOtp($scope.otp,function(response){
			 if(response.isSuccess) {
				console.log("VERIFIED");
				$scope.isOtpValidated = true;
			  }else {
				  $state.go('OtpTest');
			  }
			  })
     	};
    	

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

       $scope.getotp1();
       
    }]);

