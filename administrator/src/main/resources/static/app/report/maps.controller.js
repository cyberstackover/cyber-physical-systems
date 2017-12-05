'use strict';

angular.module('web')
        .controller('MapsController', function ($scope, $http, API_ENDPOINT, Report) {

            $scope.icons = [
                'https://maps.gstatic.com/mapfiles/ms2/micons/blue-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/ltblue-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/yellow-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/purple-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/pink-dot.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/blue-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/red-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/grn-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/ltblu-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/ylw-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/purple-pushpin.png',
                'https://maps.gstatic.com/mapfiles/ms2/micons/pink-pushpin.png'
            ];

            $scope.number_of_k = 6;

            $scope.point = [];

            $scope.cluster = [];

            $scope.refresh = function () {
                Report.get({number_of_k: $scope.number_of_k}, function (result) {
                    $scope.point = [];
                    for (var i in result) {
                        var row = result[i];
                        console.log(row['number_of_k'] === undefined);
                        var point = {
                            name: row['number_of_k'] + " : " + row['number_of_k'],
                            position: [
                                row['latitude'],
                                row['longitude']
                            ],
                            icon: $scope.icons[row['cluster_id']]
                        };
                        if(row['number_of_k'] !== undefined)
                        $scope.point.push(point);
                    }
                });
            }
            $scope.refresh();
        });
