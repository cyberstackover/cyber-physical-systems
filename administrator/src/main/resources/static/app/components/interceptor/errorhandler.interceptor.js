'use strict';

angular.module('web')
        .factory('errorHandlerInterceptor', function ($q, AlertService) {
            return {
                'responseError': function (response) {
                    console.log(response);
                    if (response.status === 401) {
                        
                    }
                    return $q.reject(response);
                },
                'response': function (response) {
                    return response || $q.when(response);
                }
            };
        });