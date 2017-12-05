'use strict';

angular.module('web')
        .service('authInterceptor', function (Auth, $rootScope, $cookies) {
            var sessionInjector = {
                request: function (config) {
                    var access_token = Auth.getAccessToken();
                    // config.headers['Authorization'] = 'Bearer ' + access_token;
                    return config;
                },
                loginUrl: function () {
                    window.location.href = "login";
                },
                logout: function () {
                    $cookies.remove('matagaruda_session');
                    window.location.href = "http://iout-er2c.com:8001/account/login?logout";
                },
                getUserPricinpal: function () {
                   
                },
                isAutethenticated: function () {
                    if ($cookies.get('matagaruda_session') === undefined) {
                        return false;
                    } else {
                        $rootScope.token = Auth.getAccessToken();
                        return true;
                    }
                }
            };
            return sessionInjector;
        });

angular.module('web').config(function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $rootScope) {
        return {
            'request': function (config) {
                if (config.url.indexOf('.html') === -1 && $rootScope.access_token) {
                    config.url = config.url + '?access_token=' + $rootScope.access_token.access_token;
                }
                return config || $q.when(config);
            }

        }
    });
});
