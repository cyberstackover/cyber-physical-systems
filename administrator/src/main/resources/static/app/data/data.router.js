angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('data', {
                        parent: 'site',
                        url: '/data/:database/:table',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/data/data.html',
                                controller: 'DataController'
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
