angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('database', {
                        parent: 'site',
                        url: '/database',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/database/database.html',
                                controller: 'DatabaseController'
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
