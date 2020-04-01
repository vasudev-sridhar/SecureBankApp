// JavaScript source code
'use strict';

angular
	.module('HelpCenter')
    .controller('helpAndSupportController',
    		
        ['$scope', '$rootScope', '$location', 'helpAndSupportService',
            function ($scope, $rootScope, $location, helpAndSupportService) {
        	$scope.isScheduleSuccess = false;
        	$scope.isUpdateSuccess = false;        	
        	$scope.scheduleErrorMsg = "";
        	$scope.updateErrorMsg = "";
        	$scope.appDate;
        	$scope.appTime;
        	$scope.options = ["EmailID", "Address", "Phone No", "Date of Birth"];
        	$scope.selectedOption;
        	$scope.newInfo;
        	
        	$scope.UpdateContact = function(){
        		if($scope.selectedOption == "EmailID") {
        			console.log("controller response update contact")
        			console.log($scope.newInfo)
        			helpAndSupportService.UpdateEmail($scope.newInfo, function(response) {
        	      		  console.log("controller response update contact")
        	      		  if(response && response.isSuccess) {
        	      			  console.log("helppppppp")
        	    			  $scope.updateErrorMsg = response.msg;
        	    			  $scope.isUpdateSuccess = true;
        	    			 
        	    		  } else {
        	    			  console.log("elseehelppppppp")
        	    			  $scope.updateErrorMsg = (response.msg)?response.msg : "Something went wrong";
        	    			  $scope.isUpdateSuccess = false;
        	    		  }
        	        	})
        		}
        		if($scope.selectedOption == "Address") {
        			console.log("controller response update contact")
        			console.log($scope.newInfo)
        			helpAndSupportService.UpdateAddress($scope.newInfo, function(response) {
        	      		  console.log("controller response update contact")
        	      		  if(response && response.isSuccess) {
        	      			  console.log("helppppppp")
        	    			  $scope.updateErrorMsg = response.msg;
        	    			  $scope.isUpdateSuccess = true;
        	    			 
        	    		  } else {
        	    			  console.log("elseehelppppppp")
        	    			  $scope.updateErrorMsg = (response.msg)?response.msg : "Something went wrong";
        	    			  $scope.isUpdateSuccess = false;
        	    		  }
        	        	})
        		}
        		if($scope.selectedOption == "Phone No") {
        			console.log("controller response update contact")
        			console.log($scope.newInfo)
        			helpAndSupportService.UpdatePhone($scope.newInfo, function(response) {
        	      		  console.log("controller response update contact")
        	      		  if(response && response.isSuccess) {
        	      			  console.log("helppppppp")
        	    			  $scope.updateErrorMsg = response.msg;
        	    			  $scope.isUpdateSuccess = true;
        	    			 
        	    		  } else {
        	    			  console.log("elseehelppppppp")
        	    			  $scope.updateErrorMsg = (response.msg)?response.msg : "Something went wrong";
        	    			  $scope.isUpdateSuccess = false;
        	    		  }
        	        	})
        		}
        		if($scope.selectedOption == "Date of Birth") {
        			console.log("controller response update contact")
        			console.log($scope.newInfo)
        			helpAndSupportService.UpdateDOB($scope.newInfo, function(response) {
        	      		  console.log("controller response update contact")
        	      		  if(response && response.isSuccess) {
        	      			  console.log("helppppppp")
        	    			  $scope.updateErrorMsg = response.msg;
        	    			  $scope.isUpdateSuccess = true;
        	    			 
        	    		  } else {
        	    			  console.log("elseehelppppppp")
        	    			  $scope.updateErrorMsg = (response.msg)?response.msg : "Something went wrong";
        	    			  $scope.isUpdateSuccess = false;
        	    		  }
        	        	})
        		}
        	
        	
        	}
        	
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
        	 

            }]);