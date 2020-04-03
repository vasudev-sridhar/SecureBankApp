'use strict';
 
angular.module('Otp')
 
.controller('OtpTestController',
		['$scope','$rootScope', '$state','OtpTestService',
    function ($scope, $rootScope, $state, OtpTestService) {
      console.log("OtpTestService")
      $scope.flg='true';
      $scope.getotp1 = function() {
    	  $scope.dataLoading = true;
//      console.log($rootScope.uname);
    	  OtpTestService.getotp(function(response) {
    		  console.log(response);
    		  $scope.otp1=response.message;
    		  })
    	  };
      $scope.verifyOtp = function() {
    	  $scope.dataLoading = false;
//    	  	console.log($rootScope.uname);
			 OtpTestService.verifyOtp($scope.otp,function(response){
			 if(response.isSuccess) {
                 $state.go('Dashboard');
                  
			  }else {
				  $state.go('OtpTest');
			  }
			  })
     };
      $scope.getotp1();
 
    }]);
