'use strict';

angular.module('web')
        .controller('DataController', function ($rootScope, $stateParams, $scope, Query, Table) {
            $scope.datas = [];
            $scope.labels = [];
            $scope.database =  $stateParams.database;
            $scope.table = $stateParams.table;
            
            $scope.loadAll = function () {
                Query.preview({database:  $stateParams.database, table: $stateParams.table}, function (result) {
                    $scope.datas = result.data;

                    var keys = [];
                    for (var k in result.data[0]) {
                        keys.push(k);
                    }
                    $scope.labels = keys;
                });
                
                Table.columns({database:  $stateParams.database, table: $stateParams.table}, function (result) {
                    $scope.columns = result.data;
                });
            };

        });
