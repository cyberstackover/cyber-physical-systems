angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('reports', {
                        parent: 'site',
                        url: '/reports',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/report/maps.html',
                                controller: 'MapsController'
                            }
                        },
                        resolve: {
                            mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('main');
                                    return $translate.refresh();
                                }]
                        }
                    })
                    .state('maps', {
                        parent: 'site',
                        url: '/maps',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/report/maps.html',
                                controller: 'MapsController'
                            }
                        },
                        resolve: {
                            mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('main');
                                    return $translate.refresh();
                                }]
                        }
                    });
        });
