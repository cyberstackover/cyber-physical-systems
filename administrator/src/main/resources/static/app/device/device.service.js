'use strict';

angular.module('web')
        .factory('Device', function ($resource, API_ENDPOINT) {
            return $resource(API_ENDPOINT + 'devices/:id', {id: '@_id'}, {
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
                'client': {
                    method: 'GET',
                     url: API_ENDPOINT + 'devices/client-credentials',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': {method: 'PUT'}
            });
        });
