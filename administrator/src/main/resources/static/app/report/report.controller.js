'use strict';

angular.module('web')
        .controller('ReportController', function ($scope) {
            
            $scope.init = function () {
                $scope.hideSideBar();
            }

            $scope.hideSideBar = function () {
                $('main').attr('style', 'padding-left:0px');
                $('#nav-mobile').hide();
            }
        });
