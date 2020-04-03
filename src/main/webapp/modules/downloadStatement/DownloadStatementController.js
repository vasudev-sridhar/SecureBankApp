'use strict';
 
angular.module('DownloadStatement')
 
.controller('DownloadStatementController',
    ['$scope', '$rootScope','$state', 'TransferFundsService', 'DownloadStatementService',
    function ($scope, $rootScope, $state, TransferFundsService, DownloadStatementService) {
		
		$scope.fromAccountList = [];
    	$scope.fromAccount;
    	$scope.isDownloadSuccess = false;
		$scope.downloadErrorMsg = "";
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


    	$scope.downloadStatement = function() {
			$scope.dataLoading = true;
			var fileName = "Accounts_Statement.pdf";
            var a = document.createElement("a");
            document.body.appendChild(a);
			console.log("Downloading Statement")
			console.log("Redirect OTP")
      	  DownloadStatementService.downloadStatement($scope.fromAccount,
      			  function(response) {
				console.log("controller response")
      		//   if(response && response.isSuccess) {
				var file = new Blob([response], {type: 'application/pdf'});
				var fileURL = window.URL.createObjectURL(file);
				console.log(fileURL);
                a.href = fileURL;
                a.download = fileName;
				a.click()

    			$scope.downloadErrorMsg = response.msg;
    			$scope.isDownloadSuccess = true;
    		//   } else {
    		// 	  $scope.downloadErrorMsg = (response.msg)?response.msg : "Something went wrong";
    		// 	  $scope.isDownloadSuccess = false;
    		//   }
      		  $scope.dataLoading = true;
      	  })
		}


		$scope.getAccounts = function() {
      	  $scope.dataLoading = true;
      	  console.log("userid: " + $rootScope.userId)
      	  var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;
      	  DownloadStatementService.getAccounts(userId, function(response) {
      		  console.log("controller response")
      		  if(response && response.accounts) {
      			  $scope.fromAccountList = response.accounts;
      		  } else {
      			  console.log("Something went wrong")
      		  }
      		  $scope.dataLoading = true;
      	  })
        }
    	
    	$scope.getAllAccounts = function() {
        	  $scope.dataLoading = true;
        	  DownloadStatementService.getAllAccounts(function(response) {
        		  console.log("controller response")
        		  if(response && response.accounts) {
	      			  $scope.toAccountList = response.accounts;
	      		  } else {
	      			  console.log("Something went wrong")
	      		  }
        		  $scope.dataLoading = true;
        	  })
          }
		
		$scope.getotp1();
	    $scope.getAccounts();
	    $scope.getAllAccounts();
    
    }]);
