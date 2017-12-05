angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('users', {
                        parent: 'site',
                        url: '/users',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/user/user.html',
                                controller: 'UserController'
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