angular.module('web')
        .config(function ($stateProvider) {
            $stateProvider
                    .state('query-editor', {
                        parent: 'site',
                        url: '/query-editor',
                        data: {
                            authorities: []
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/query_editor/query.editor.html',
                                controller: 'QueryEditorController'
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
