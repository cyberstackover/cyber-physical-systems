'use strict';

angular.module('web')
        .factory('Query', function ($resource, API_ENDPOINT, errorHandlerInterceptor) {
            return $resource(API_ENDPOINT + 'query', {}, {
                'query': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }},
                'save': {
                    method: 'POST',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'preview': {
                    url: API_ENDPOINT + 'query/preview/:database/:table',
                    method: 'GET',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'history': {
                    url: API_ENDPOINT + 'query/history/',
                    method: 'GET',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'delete': {
                    url: API_ENDPOINT + 'query/:id',
                    method: 'DELETE',
                    interceptor: {responseError: errorHandlerInterceptor}
                },
                'savedQuery': {
                    url: API_ENDPOINT + 'query/saved',
                    method: 'GET',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'storeQuery': {
                    url: API_ENDPOINT + 'query/store',
                    method: 'POST',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': {method: 'PUT'}
            });
        });
