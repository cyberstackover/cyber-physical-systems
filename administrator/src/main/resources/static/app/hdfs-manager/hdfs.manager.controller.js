'use strict';
angular.module('web')
        .controller('HdfsManagerController', function ($scope, API_ENDPOINT, HDFSManager, Upload, $timeout) {

            $scope.files = [];
            $scope.breadcrumbs = [];
            $scope.directory = "";

            $scope.listFile = function (path) {
                if (path === '..') {
                    $scope.breadcrumbs.pop();
                } else {
                    $scope.breadcrumbs.push(path);
                }
                var url = $scope.breadcrumbs.join('/').replace('//', '/');
                HDFSManager.get({path: url}, function (result) {
                    $scope.files = result.data;
                });
            }

            $scope.mkdir = function () {
                if ($scope.directory === "") {
                    $('#mkdir-form').openModal();
                } else {
                    $scope.breadcrumbs.push($scope.directory.name);
                    var dir = $scope.breadcrumbs.join('/').replace('//', '/');
                    console.log(dir);
                    HDFSManager.mkdir({directory: dir}, function (result) {
                        $scope.directory.name = "";
                        console.log($scope.breadcrumbs.pop());
                        $scope.listFile($scope.breadcrumbs.pop());
                    });
                }
            }

            $scope.remove = function (directory, isOpen) {
                if (isOpen) {
                    $scope.directory = directory;
                    $('#remove-form').openModal();
                } else {
                    $scope.breadcrumbs.push($scope.directory);
                    var dir = $scope.breadcrumbs.join('/').replace('//', '/');
                    HDFSManager.remove({path: dir}, function (result) {
                        $scope.directory = "";
                        $scope.breadcrumbs.pop();
                        $scope.listFile($scope.breadcrumbs.pop());
                    });
                }
            }


            $scope.upload = function (file) {
                if (file === '') {
                    $('#upload-form').openModal();
                    return;
                }

                var dir = $scope.breadcrumbs.join('/').replace('//', '/');
                console.log(dir);
                file.upload = Upload.upload({
                    url: API_ENDPOINT + 'hdfs-manager/upload',
                    fields: {path: dir},
                    file: file
                });

                file.upload.then(function (response) {
                    $timeout(function () {
                        file.result = response.data;
                        $scope.listFile($scope.breadcrumbs.pop());
                    });
                }, function (response) {
                    if (response.status > 0)
                        $scope.errorMsg = response.status + ': ' + response.data;
                }, function (evt) {
                    // Math.min is to fix IE which reports 200% sometimes
                    file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                });
            }

            $scope.convertTimeStamptToDate = function(milis) {
                var d = new Date(milis);
                return d.toISOString();
            }

            $scope.listFile("/");
        });