/* globals $ */
'use strict';

angular.module('web')
    .directive('webAppPagination', function() {
        return {
            templateUrl: 'api/components/form/pagination.html'
        };
    });
