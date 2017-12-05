'use strict';

angular.module('web')
        .directive('myAlert', function (AlertService) {
            return {
                template: '<div>My chart</div>',
                restrict: 'E',
                link: function postLink(scope, element, attrs) {
                    element.text('this is a chart');
                }
            }
        });