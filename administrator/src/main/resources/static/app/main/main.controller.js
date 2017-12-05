'use strict';

angular.module('web')
        .controller('MainController', function ($scope, $rootScope, Device, authInterceptor) {
            $scope.isAuthenticated = authInterceptor.isAutethenticated();
            $scope.rootScope = $rootScope;

            $scope.init = function(){
                Device.client({}, function(result){
                    $scope.sensors = result.data;
                });
            }
            
        });
