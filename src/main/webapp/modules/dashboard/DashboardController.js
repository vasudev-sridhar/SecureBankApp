'use strict';
 
angular.module('Dashboard')
 
.controller('DashboardController',
    ['$scope', '$rootScope','$state','DashboardService', 'AuthenticationService',
    function ($scope, $rootScope, $state, DashboardService, AuthenticationService) {
      console.log("DashboardService")
      $rootScope.uncheckedMenu = 'w3-bar-item w3-button w3-padding'
	  $rootScope.checkedMenu = 'w3-bar-item w3-button w3-padding w3-blue'
      $scope.accountList = [];
	  $rootScope.isEmployee = false;
	  $rootScope.isTier1 = false;
	  $rootScope.isAdmin = false;
      $rootScope.isTAC = ($rootScope.isTAC)? $rootScope.isTAC : false;
      $scope.getAccounts = function() {
    	  var userId = ($rootScope.isTAC)? $rootScope.tacUser.id : $rootScope.userId;
    	  $scope.dataLoading = true;
    	  DashboardService.getAccounts(userId, function(response) {
    		  console.log("controller response")
    		  console.log(response)
    		  if(response) {
    			  $scope.accountList = response.accounts;
    		  }
    		  $scope.dataLoading = true;
    	  })
      }
      
      $scope.getUser = function(userId) {
    	  $scope.dataLoading = true;
    	  DashboardService.getUser(userId, function(response) {
    		  if(response) {
    			  $rootScope.user = response;
    			  console.log(response)
    			  var a = response.authRole.roleType;
    			  if(a == "ADMIN" || a == "TIER1" || a == "TIER2") {
    				  console.log("employee!")
    				  $rootScope.isEmployee = true;
    				  console.log($rootScope.isEmployee)
				  }
				  if(a == "TIER1") {
    				  console.log("isTier1!")
    				  $rootScope.isTier1 = true;
    				  console.log($rootScope.isTier1)
				  }
				  if(a == "ADMIN") {
    				  console.log("isAdmin!")
    				  $rootScope.isAdmin = true;
    				  console.log($rootScope.isAdmin)
    			  }
    		  }
    		  $scope.dataLoading = true;
    	  })
      }
      
      $rootScope.logout = function () {
          $scope.dataLoading = true;

          AuthenticationService.Logout( function(response) {
              if(response) {
              	console.log("UserId " + response.userId);
              	$rootScope.userId = undefined;
              	$rootScope.user = undefined;
              	$rootScope.isTAC = false;
              	$rootScope.tacUser = undefined;

              } else {
              	if(response.status=403){
	                	$scope.isAccessDenied = true;
	                    $scope.error = response.message;
	                    $scope.dataLoading = false;
              	}
              }
          });
      };
      
      $rootScope.formatDate = function(timestamp) {

	    	 // Months array
	    	 var months_arr = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

	    	 // Convert timestamp to milliseconds
	    	 var date = new Date(timestamp);

	    	 // Year
	    	 var year = date.getFullYear();

	    	 // Month
	    	 var month = months_arr[date.getMonth()];

	    	 // Day
	    	 var day = date.getDate();

	    	 // Hours
	    	 var hours = date.getHours();

	    	 // Minutes
	    	 var minutes = "0" + date.getMinutes();

	    	 // Seconds
	    	 var seconds = "0" + date.getSeconds();

	    	 // Display date time in MM-dd-yyyy h:m:s format
	    	 var convdataTime = month+'-'+day+'-'+year+' '+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
	    	 
	    	 return convdataTime;
	    	}
      
      $rootScope.goLogout = function() {
    	  //call Logout
    	  if($rootScope) {
    		  $rootScope.logout();
    		  $rootScope.stateName = 'Login'    		  
    	  }
    	  $state.go('Login')
      }
      
      $rootScope.goTransaction = function() {
    	  $rootScope.stateName = 'Transaction'
    	  $state.go('Transaction')
      }
      
      $rootScope.goDashboard = function() {
    	  $rootScope.stateName = 'Dashboard'
    	  $state.go('Dashboard')
      }
      
      $rootScope.goCreditDebit = function() {
    	  $rootScope.stateName = 'CreditDebit'
    	  $state.go('CreditDebit')
      }
	    
      $rootScope.goAccountRequest = function() {
       	  $state.go('AccountRequest')
      }
      
      $rootScope.goTransferFunds = function() {
    	  $rootScope.stateName = 'TransferFunds'
    	  $state.go('TransferFunds')
      }
      
      $rootScope.goHelpandSupport = function() {
    	  $rootScope.stateName = 'HelpCenter'
    	  $state.go('HelpCenter')
      }
      
      $rootScope.goApprovals = function() {
    	  $rootScope.stateName = 'Approvals'
    	  $state.go('Approvals')
      }
      
      $rootScope.goTAC = function() {
    	  $state.go('TAC')
      }
      
      $rootScope.exitTAC = function() {
    	  $rootScope.isTAC = false;
    	  $rootScope.tacUser = undefined;
    	  $rootScope.stateName = 'Dashboard'
    	  $state.go('Dashboard')
	  }
	  
	  $rootScope.goDownloadStatement = function() {
		  $rootScope.stateName = 'DownloadStatement'
    	  $state.go('DownloadStatement')
	  }
	  
	  $rootScope.goIssueCheque = function() {
		  $rootScope.stateName = 'IssueCheque'
    	  $state.go('IssueCheque')
	  }

	  $rootScope.goApproveCheque = function() {
		  $rootScope.stateName = 'ChequeApprovals'
    	  $state.go('ChequeApprovals')
	  }
	  
	  $rootScope.goAdminLog = function() {
		  $rootScope.stateName = 'AdminLog'
    	  $state.go('AdminLog')
	  }

      $scope.getUser($rootScope.userId)
      $scope.getAccounts();
    }]);
