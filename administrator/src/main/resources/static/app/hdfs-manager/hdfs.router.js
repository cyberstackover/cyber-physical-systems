angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('hdfs_manager', {
                        parent: 'site',
                        url: '/hdfs-manager',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/hdfs-manager/hdfs.manager.html',
                                controller: 'HdfsManagerController'
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
