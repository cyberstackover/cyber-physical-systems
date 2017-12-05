'use strict';

angular.module('web')
        .factory('Report', function ($resource, API_ENDPOINT) {
            return $resource(API_ENDPOINT + 'kmeans/', {number_of_k: '@database'}, {
                'get': {
                    method: 'GET',
                    isArray: true,
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        });
