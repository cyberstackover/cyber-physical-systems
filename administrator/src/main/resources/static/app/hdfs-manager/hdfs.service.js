'use strict';

angular.module('web')
        .factory('HDFSManager', function ($resource, API_ENDPOINT, errorHandlerInterceptor) {
            return $resource(API_ENDPOINT + 'hdfs-manager', {}, {
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'mkdir': {
                    url: API_ENDPOINT + 'hdfs-manager/mkdir',
                    method: 'POST',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'remove': {
                    url: API_ENDPOINT + 'hdfs-manager',
                    method: 'DELETE',
                    interceptor: {responseError: errorHandlerInterceptor},
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        });
