'use strict';

angular.module('web')
        .controller('TableController', function ($rootScope, $scope, $state, $stateParams, Table) {
            $scope.details = [];
            $scope.labels = [];
            $scope.tables = $rootScope.tables;
            $scope.database = $rootScope.database;
            $scope.fields = [];
            $scope.index = 0;
            $scope.type = ['string', 'int', 'float', 'double'];
            $scope.table = '';

            $scope.$watch(function () {
                return $rootScope.tables;
            }, function () {
                $scope.tables = $rootScope.tables;
            }, true);

            $scope.loadAll = function () {
                if ($scope.database) {
                    Table.get({database: $scope.database}, function (result) {
                        $scope.tables = result.data;
                        $rootScope.tables = $scope.tables;
                    });
                }
            };

            $scope.create = function () {
                $scope.table.fields = $scope.fields;
                $scope.table.database = $rootScope.database;
                Table.save($scope.table, function (result) {
                    Materialize.toast(result.data, 4000);
                    $scope.loadAll();
                    $state.go('tables');
                });
            };

            $scope.detail = function () {

                Table.describe({database: $stateParams.database, table: $stateParams.table}, function (result) {
                    $scope.details = result.data;
                });
            };

            $scope.confirm = function (table) {
                $scope.table = table;
                $('#delete-form').openModal();
            };

            $scope.drop = function (table) {
                Table.delete({database: $scope.database, table: table}, function (result) {
                    Materialize.toast(result.data, 4000);
                    $scope.loadAll();
                    $state.go('tables');
                });
            };

            $scope.addField = function () {
                var data = {
                    type: $scope.type
                };
                $scope.index++;
                $scope.inputs.push(data);
            };

            $scope.inputs = [{
                    type: $scope.type
                }, {
                    type: $scope.type
                }];

        }).
        directive('fieldDirective', function ($compile) {
            return {
                template: '<div class="row">' +
                        '<div class="input-field col s6">' +
                        '<input placeholder="Placeholder" ng-model="fields[$index].name" id="field_name" type="text" class="validate">' +
                        '<label for="field_name">Field Name </label>' +
                        '</div>' +
                        '<div class="input-field col s4">' +
                        '<select class="" material-select watch ng-model="fields[$index].type">' +
                        '<option ng-repeat="name in input.type">{{name}}</option>' +
                        '</select>' +
                        '<label for="type">Data Type</label>' +
                        '</div>' +
                        '<div class="input-field col s2">' +
                        '<input type="checkbox" ng-model="fields[$index].primary_key" id="primary_key{{$index}}" />' +
                        '<label for="primary_key{{$index}}">Primary Key</label>' +
                        '</div>' +
                        '</div>',
                replace: true
            }
        });
;
