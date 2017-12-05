'use strict';

angular.module('web')
        .controller('NavbarController', function ($rootScope, $scope, localStorageService, authInterceptor, Principal, Database, Table) {

            $scope.database = "default";
            $scope.databases = [];
            $scope.tables = $rootScope.tables;
            $rootScope.database = $scope.database;

            $scope.$watch(function () {
                return $rootScope.databases;
            }, function () {
                $scope.databases = $rootScope.databases;
            }, true);

            $scope.isAuthenticated = function () {
                return authInterceptor.isAutethenticated();
            };

            $scope.login = function () {
                authInterceptor.loginUrl();
            };

            $scope.logout = function () {
                authInterceptor.logout();
            };

            $scope.updateDatabase = function () {
                localStorageService.set("database_selected", $scope.database);
                if ($scope.isAuthenticated()) {
                    $scope.initTable(true);
                }
                $rootScope.database = $scope.database;

            };

            $scope.init = function () {

                $('.collapsible').collapsible({
                    accordion: true
                });

                $('.button-collapse').sideNav({
                    closeOnClick: false
                });

                if ($scope.isAuthenticated() === false) {
                    $scope.hideSideBar();
                } else {
                    Principal.identity();
                    $scope.principal = $rootScope.principal;
                    $scope.initDatabase();
                }

            }

            $scope.initDatabase = function () {
                Database.query({}, function (result) {
                    $scope.databases = result.data;
                    $scope.database = "default";
                    $rootScope.databases = result.data;
                    $rootScope.database = $scope.database;

                    $scope.initTable();
                    if (localStorageService.get("database_selected")) {
                        localStorageService.set("database_selected", "default");
                    }
                });
            }

            $scope.initTable = function () {
                if ($scope.database) {
                    Table.get({database: $scope.database}, function (result) {
                        var tables = [];
                        for (var tab in result.data) {
                            var table = {
                                tab_name: result.data[tab].tab_name,
                                columns: []
                            };
                            tables.push(table);
                        }
                        $scope.tables = tables;
                        $rootScope.tables = result.data;
                    });
                }
            }

            $scope.hideSideBar = function () {
                $('main').attr('style', 'padding-left:0px');
                $('#nav-mobile').hide();
            }

            $scope.fetchColumns = function (table) {
                if (!table.columns.length) {
                    Table.columns({database: $scope.database, table: table.tab_name}, function (result) {
                        for (var i in result.data) {
                            var column = result.data[i];
                            if (!column.col_name && !column.data_type && !column.comment) {
                                break;
                            }
                            table.columns.push(column);
                        }
                    });
                }
            }

            $scope.init();
        });
