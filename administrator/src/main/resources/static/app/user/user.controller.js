'use strict';

angular.module('web')
        .controller('UserController', function ($scope, User) {
            $scope.data = [];
            $scope.user = [];
            $scope.users = [];
            $scope.authorities = ['ROLE_ADMIN', 'ROLE_DEVELOPER', 'ROLE_USER', 'ROLE_CLIENT'];

            $scope.loadAll = function () {
                User.query({}, function (result) {
                    $scope.users = result.content;
                    $scope.data = result;
                });
            };

            $scope.changePage = function (page) {
                User.query({}, function (result) {
                    $scope.users = result.content;
                });
            };

            $scope.loadAll();

            $scope.showFormUpdate = function (user) {
                $scope.user = user;
                $('#input_roles').material_select();
                $('#create-form').openModal();
            };

            $scope.showFormCreate = function () {
                $('#input_roles').material_select();
                $('#create-form').openModal();
            };

            $scope.create = function () {
                User.save($scope.user, function () {
                    $scope.loadAll();
                });
            };

            $scope.confirm = function (name) {
                $scope.user = name;
                $('#delete-form').openModal();
            };

            $scope.drop = function (user) {
                User.delete({id: user.id}, function (result) {
                    $scope.loadAll();
                });
                $('#delete-form').closeModal();
            };

        });