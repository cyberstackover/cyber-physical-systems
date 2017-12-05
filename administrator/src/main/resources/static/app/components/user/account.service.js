'use strict';

angular.module('web')
    .factory('Account', function Account($resource) {
        return $resource('user', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        return response;
                    }
                }
            }
        });
    });
