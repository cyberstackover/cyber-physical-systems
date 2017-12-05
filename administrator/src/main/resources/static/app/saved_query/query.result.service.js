'use strict';

angular.module('web')
        .factory('QueryResult', function ($resource, API_ENDPOINT, errorHandlerInterceptor) {
            return $resource(API_ENDPOINT + 'query-result/:id', {}, {
                'detail': {
                    method: 'GET',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        });
