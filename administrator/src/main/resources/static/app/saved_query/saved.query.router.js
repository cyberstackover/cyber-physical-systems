angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('saved-query', {
                        parent: 'site',
                        url: '/saved-query',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/saved_query/saved.query.html',
                                controller: 'SavedQueryController'
                            }
                        },
                        resolve: {
                            mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('main');
                                    return $translate.refresh();
                                }]
                        }
                    })
                    .state('query_result', {
                        parent: 'site',
                        url: '/query-result/:query_id',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/saved_query/query.result.html',
                                controller: 'SavedQueryController'
                            }
                        },
                        resolve: {
                            mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('main');
                                    return $translate.refresh();
                                }]
                        }
                    })
        });
