'use strict';

angular.module('web')
        .factory('Data', function ($resource, API_ENDPOINT) {
            return $resource(API_ENDPOINT + 'data/:database/:table', {database : '@database',table: '@table'}, {
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
