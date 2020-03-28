'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Dashboard', []);
angular.module('TransactionRequest', []);
angular.module('CreditDebit', []);
angular.module('TransferFunds', []);

angular.module('SecureBankApp', [
    'Authentication',
    'Dashboard',
    'TransactionRequest',
    'CreditDebit',
    'TransferFunds',
    'ui.router',
    'ngCookies',
    'anguFixedHeaderTable'
])
 
.config(['$stateProvider','$urlRouterProvider',function ($stateProvider, $urlRouterProvider) {
	console.log("$stateProvider")
	$stateProvider 
        .state('Login', {
        	url: '/login',
        	templateUrl: 'modules/authentication/views/login.html',
            controller: 'LoginController',
        })
 
        .state('Dashboard', {
        	url: '/dashboard',
        	templateUrl: 'modules/dashboard/views/dashboard.html',
            controller: 'DashboardController'
        })
        .state('Transaction', {
        	url: '/transaction',
        	templateUrl: 'modules/TransactionRequest/views/transaction_request_page.html',
            controller: 'TransactionRequestController',
        })
        .state('CreditDebit', {
        	url: '/credit_debit',
        	templateUrl: 'modules/credit_debit/views/credit_debit.html',
            controller: 'CreditDebitController',
        })
        .state('TransferFunds', {
        	url: '/transfer_funds',
        	templateUrl: 'modules/transfer_funds/views/transfer_funds.html',
            controller: 'TransferFundsController',
        })
        ;  //credit_debit/CreditDebitController.js
 
	$urlRouterProvider.otherwise("/login"); 
}])
 
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
	console.log("run")
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
 
//        $rootScope.$on('$locationChangeStart', function (event, next, current) {
//            // redirect to login page if not logged in
//            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
//                $location.path('/login');
//            }
//        });
    }]);