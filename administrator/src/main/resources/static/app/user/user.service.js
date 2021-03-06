'use strict';

angular.module('web')
        .factory('User', function ($resource, API_ENDPOINT) {
            return $resource(API_ENDPOINT + 'users/:id', {id: '@_id'}, {
                'query': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': {method: 'PUT'}
            });
        });
