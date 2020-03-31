// JavaScript source code
'use strict';

angular
	.module('HelpCenter')
    .controller('helpAndSupportController',
    		
        ['$scope', '$rootScope', '$location', 'helpAndSupportService',
            function ($scope, $rootScope, $location, helpAndSupportService) {
        	$scope.isScheduleSuccess = false;
        	$scope.scheduleErrorMsg = "";
        	$scope.appDate;
        	$scope.appTime;
        	
        	$scope.ScheduleAppointment = function() {
        		helpAndSupportService.ScheduleAppointment($scope.appDate, $scope.appTime, 
        		 function(response) {
      		  console.log("controller response")
      		  if(response && response.isSuccess) {
      			  console.log("helppppppp")
    			  $scope.ScheduleErrorMsg = response.msg;
    			  
    			  $scope.isScheduleSuccess = true;
    			 
    		  } else {
    			  console.log("elseehelppppppp")
    			  $scope.scheduleErrorMsg = (response.msg)?response.msg : "Something went wrong";
    			  $scope.isScheduleSuccess = false;
    		  }
        	})
        	}
        	  
                // Do stuff

            }]);