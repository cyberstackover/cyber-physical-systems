angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('devices', {
                        parent: 'site',
                        url: '/devices',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/device/device.html',
                                controller: 'DeviceController'
                            }
                        },
                        resolve: {
                            mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('main');
                                    return $translate.refresh();
                                }]
                        }
                    }).state('devices_create', {
                parent: 'devices',
                url: '/create',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/device/create.html',
                        controller: 'DeviceController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('main');
                            return $translate.refresh();
                        }]
                }
            }).state('device_edit', {
                parent: 'devices',
                url: '/edit/:id',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/device/edit.html',
                        controller: 'DeviceController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('main');
                            return $translate.refresh();
                        }]
                }
            }).state('device_show', {
                parent: 'devices',
                url: '/show/:id',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/device/show.html',
                        controller: 'DeviceController'
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
