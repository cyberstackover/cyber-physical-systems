'use strict';

angular.module('web')
        .factory('Auth', function Auth($cookies, $rootScope) {
            return {
                getAccessToken: function () {
                    var json = angular.fromJson($cookies.get('matagaruda_session'));
                    var access_token = angular.fromJson(json);
                    $rootScope.access_token = access_token;
                    return access_token;
                },
                getToken: function () {
                    if ($cookies.get('matagaruda_session')) {
                        return this.getAccessToken().access_token;
                    } else {
                        return "";
                    }
                }
            };
        });
