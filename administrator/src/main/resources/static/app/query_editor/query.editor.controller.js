'use strict';

angular.module('web')
        .controller('QueryEditorController', function ($rootScope, $stateParams, $scope, Query) {
            $scope.datas = [];
            $scope.labels = [];
            $scope.query = {database: $rootScope.database, sql: "dsd"};
            $scope.sql = "";

            $scope.aceLoaded = function (editor) {
                // Editor part

                var session = editor.getSession();

                editor.scope = $scope;

                session.setUndoManager(new ace.UndoManager());

                editor.completers.push({
                    getCompletions: function (editor, session, pos, prefix, callback) {
                        callback(null, editor.scope.getCompletions());
                    }
                })

                session.on("change", function (e) {
                    $scope.query.sql = editor.getValue();
                    $scope.sql = editor.getValue();
                });

            };
            $scope.getCompletions = function () {
                var complete = [];
                var tables = $rootScope.tables;
                for (var i in tables) {
                    var value = $rootScope.database + '.' + tables[i].tab_name;
                    complete.push({value: value, score: 1000, meta: "table"});
                }
                return complete;
            }

            $scope.submitQuery = function () {
                Query.save($scope.query, function (result) {
                    $scope.datas = result.result;
                    var keys = [];
                    for (var k in result.result[0]) {
                        keys.push(k);
                    }
                    $scope.labels = keys;

                });
            }

            $scope.history = function () {
                Query.history({}, function (result) {
                    $scope.histories = result.content;
                });
            }

            $scope.explainQuery = function () {
                var query = $scope.query;
                query.sql = 'explain ' + $scope.query.sql;
                Query.save(query, function (result) {
                    $scope.datas = result.result;
                    var keys = [];
                    for (var k in result.result[0]) {
                        keys.push(k);
                    }
                    $scope.labels = keys;
                    $scope.query.sql = $scope.sql;
                });
            }

            $scope.saveQuery = function (openForm) {
                if (openForm) {
                    $('#save-form').openModal();
                    return;
                }
                var query = $scope.query;
                query.sql = $scope.query.sql;
                console.log(query);

                Query.storeQuery(query, function (result) {

                });

            }

            $scope.history();
        });
