'use strict';

angular.module('web')
        .controller('DatabaseController', function ($rootScope, $scope, Database) {
            $scope.users = [];
            $scope.databases = $rootScope.databases;
            $scope.loadAll = function () {
                Database.query({}, function (result) {
                    $scope.databases = result.data;
                    $rootScope.databases = $scope.databases;
                });
            };

            $scope.$watch(function () {
                return $rootScope.databases;
            }, function () {
                $scope.databases = $rootScope.databases;
            }, true);

            $scope.create = function () {

                Database.save({name: $scope.database}, function () {
                    $scope.loadAll();
                });
            };

            $scope.confirm = function (name) {
                $scope.database = name;
                $('#delete-form').openModal();
            };

            $scope.drop = function (name) {
                Database.delete({id: name}, function (result) {
                    $scope.loadAll();
                });
                $scope.database = '';
                $('#delete-form').closeModal();
            };

        });
