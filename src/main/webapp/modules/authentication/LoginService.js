'use strict';
 
angular.module('Authentication')
 
.factory('AuthenticationService',
    ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
    function (Base64, $http, $cookieStore, $rootScope, $timeout) {
        var service = {};

        service.Login = function (username, password, callback) {
        	console.log("LOGIN TRYING...");
            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
            /*$timeout(function(){
                var response = { isSuccess: username === 'test' && password === 'test' };
                if(!response.isSuccess) {
                    response.message = 'Username or password is incorrect';
                }
                callback(response);
            }, 1000);*/


            /* Use this for real authentication
             ----------------------------------------------*/
        	$http.post('/api/login', { username: username, password: password })
                .success(function (response) {
                	console.log(response);
                	if(!response.isSuccess) {
                      response.message = 'Username or password is incorrect';
                  }
                    callback(response);
                });

        };
 
        service.SetCredentials = function (username, password) {
            var authdata = Base64.encode(username + ':' + password);
 
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata
                }
            };
 
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        };
 
        service.ClearCredentials = function () {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };

        // Create a new user.
        service.CreateUser = function (name, birthday, email, phone, address, username, password, callback) {
        	var body = {
        			"name" :name,
        			"dob" : birthday,
        			"emailId" : email,
        			"contact" : phone,
        			"address" : address,
        			"username" : username,
        			"password" : password
        	};
        	console.log(body);
            // Log in the new user.
            $http.post('/api/user/signup', body)
                .success(function (response) {
                    console.log(response);
                    if (!response.isSuccess) {
                        response.message = 'Username or password is incorrect';
                    }
                    callback(response);
                });
        };

        // Check is password is valid.
        service.ValidatePassword = function (password) {
            // Regex taken from https://martech.zone/javascript-password-strength/. This is a citation.
            var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
            var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
            var enoughRegex = new RegExp("(?=.{6,}).*", "g");

            var response = {};

            if (password.length < 8) {
                response.message = "Password must be at least 8 characters.";
                response.ok = false;
            }
            else if (enoughRegex.test(password) === false) {
                response.message = "Password needs more characters.";
                response.ok = false;
            }
            else if (mediumRegex.test(password) === false) {
                response.message = "Password is medium. Try adding more special characters.";
                response.ok = true;
            }
            else if (strongRegex.test(password) === false) {
                response.message = "Password is strong!";
                response.ok = true;
            }
            else {
                response.message = "Password is weak. Try adding more special characters, and do not use known words.";
                response.ok = true;
            }
            return response;
            //callback(response);
        };

        // Check if username is already taken.
        service.CheckUserNameAvailability = function (username) {
            // Log in the new user.
            $http.get('/api/user/doesUsernameExist/'+ username )
                .success(function (response) {
                    console.log(response);                    
                    if(response == '0') {
                    	return false;
                    }               
                    else {
                    	return true;
                    }
                });
        };
 
        return service;
    }])
 
.factory('Base64', function () {
    /* jshint ignore:start */
 
    var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
 
    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
 
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
 
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
 
                output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);
 
            return output;
        },
 
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));
 
                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;
 
                output = output + String.fromCharCode(chr1);
 
                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }
 
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
 
            } while (i < input.length);
 
            return output;
        }
    };
 
    /* jshint ignore:end */
});