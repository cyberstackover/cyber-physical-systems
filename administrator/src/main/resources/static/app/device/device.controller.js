'use strict';

angular.module('web')
        .controller('DeviceController', function ($scope, $state, $stateParams, Device) {
            $scope.data = [];
            $scope.devices = [];
            
            $scope.loadAll = function () {
                Device.query({}, function (result) {
                    $scope.devices = result.content;
                    $scope.data = result;
                });
            };

            $scope.changePage = function (page) {
                Device.query({}, function (result) {
                    $scope.devices = result.content;
                });
            };

            $scope.show = function () {
                Device.get({id: $stateParams.id}, function (result) {
                    $scope.device = result.data;
                    var keys = [];
                    for (var k in result.data) {
                        keys.push(k);
                    }
                    $scope.labels = keys;
                });
            };

            $scope.create = function () {
                $scope.device.authorities = $scope.serializeArray($scope.device.authorities);
                $scope.device.authorizedGrantTypes = $scope.serializeArray($scope.device.authorizedGrantTypes);
                $scope.device.scope = $scope.serializeArray($scope.device.scope);

                Device.save($scope.device, function (result) {
                    Materialize.toast(result.data, 4000);
                    $state.go('devices');
                });
            };

            $scope.confirm = function (device) {
                $scope.device = device;
                $('#delete-form').openModal();
            };

            $scope.drop = function (device) {
                Device.delete({id: device.clientId}, function (result) {
                    Materialize.toast(result.data, 4000);
                    $scope.loadAll();
                });
            };

            $scope.serializeArray = function (data) {
                var index;
                var str = "";
                var comma = false;
                for (index = 0; index < data.length; ++index) {
                    if (comma) {
                        str += ",";
                    }
                    comma = true;
                    str += data[index];
                }
                return str;
            }

            $('.input_select').material_select();
        });
