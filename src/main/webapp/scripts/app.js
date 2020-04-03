'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Dashboard', []);
angular.module('TransactionRequest', []);
angular.module('CreditDebit', []);
angular.module('TransferFunds', []);
angular.module('Approvals', []);
angular.module('TAC', []);
angular.module('HelpCenter', []);
angular.module('DownloadStatement', []);


angular.module('SecureBankApp', [
    'Authentication',
    'Dashboard',
    'TransactionRequest',
    'CreditDebit',
    'TransferFunds',
    'HelpCenter',
    'Approvals',
    'TAC',
    'ui.router',
    'ngCookies',
    'anguFixedHeaderTable',
    'DownloadStatement'
    /*'angularjs-crypto'*/
])
 .directive('disableRightClick', function() {  
    return {  
        restrict: 'A',  
        link: function(scope, element, attr) {  
            element.bind('contextmenu', function(e) {  
                e.preventDefault();  
            })  
        }  
    }  
}) 
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
         .state('HelpCenter', {
        	url: '/help_center',
        	templateUrl: 'modules/help/views/help_and_support_center.html',
            controller: 'helpAndSupportController',
        })
        .state('Approvals', {
        	url: '/approvals',
        	templateUrl: 'modules/employee_approvals/views/approval_page.html',
            controller: 'ApprovalsController',
        })
        .state('TAC', {
        	url: '/tac',
        	templateUrl: 'modules/TAC/views/tac_page.html',
            controller: 'TACController',
        })
        .state('DownloadStatement', {
        	url: '/downloadStatement',
        	templateUrl: 'modules/downloadStatement/views/download_statement.html',
            controller: 'DownloadStatementController',
        })
        ;
 
	$urlRouterProvider.otherwise("/login"); 
}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
	/*$rootScope.base64Key = CryptoJS.enc.Hex.parse('0123456789abcdef0123456789abcdef')
	$rootScope.iv = CryptoJS.enc.Hex.parse('abcdef9876543210abcdef9876543210');*/
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