'use strict';

angular.module('web')
        .factory('Principal', function Principal($rootScope, Account, $cookies) {
            return {
                identity: function () {
                    var user = $cookies.getObject('user_session');
                    console.log(user);
                    if (!user) {
                        Account.get({}, function (e) {
                            user = e.data;
                            $cookies.putObject('user_session', e.data);
                        });
                    }
                    $rootScope.principal = user;
                }
            };
        });