'use strict';

angular.module('web')
        .controller('SavedQueryController', function ($rootScope, $scope, $state, $stateParams, Query, QueryResult) {

            var currentTime = new Date();
            $scope.month = ['Januar', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            $scope.monthShort = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
            $scope.weekdaysFull = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
            $scope.weekdaysLetter = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];
            $scope.disable = [false, 1, 7];
            $scope.today = 'Today';
            $scope.clear = 'Clear';
            $scope.close = 'Close';

            $scope.loadQuery = function () {
                Query.savedQuery({}, function (result) {
                    $scope.queries = result;
                });
            };

            $scope.detail = function () {
                Query.describe({database: $stateParams.database, query: $stateParams.query}, function (result) {
                    $scope.details = result.data;
                });
            };

            $scope.confirm = function (query) {
                $scope.query = query;
                $('#delete-form').openModal();
            };

            $scope.drop = function () {
                Query.delete({id: $scope.query}, function (result) {
                    $scope.loadQuery();
                });
            };

            $scope.getResultQuery = function () {
                QueryResult.all({query_id: $stateParams.query_id}, function (result) {
                    $scope.query_result = result;
                });
            };

            $scope.getResultQuery = function () {

                QueryResult.detail({id: $scope.querySelected, start: $scope.startdate, end: $scope.enddate}, function (result) {
                    $scope.queryResultDetail = result.data;
                    var keys = [];
                    for (var k in result.data[0]) {
                        keys.push(k);
                    }
                    $scope.queryResultLabel = keys.reverse();
                });
            }

            $scope.convertTimeStamptToDate = function (milis) {
                var d = new Date(milis);
                return d.getDay() + "-" + d.getMonth() + "-" + d.getFullYear() + " " + d.getMinutes() + ":" + d.getHours();
            }

        });
