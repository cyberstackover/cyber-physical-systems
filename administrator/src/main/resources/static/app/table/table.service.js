'use strict';

angular.module('web')
        .factory('Table', function ($resource, API_ENDPOINT) {
            return $resource(API_ENDPOINT + 'tables/:database/:table/', {database: '@database', table: '@table'}, {
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'describe': {
                    url: API_ENDPOINT + 'tables/describe/:database/:table/',
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'columns': {
                    url: API_ENDPOINT + 'tables/columns/:database/:table/',
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': {method: 'PUT'}
            });
        });
