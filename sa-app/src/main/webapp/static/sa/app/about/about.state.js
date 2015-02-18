define(
    [
        './about.module',
        'common/navigation/navigation.provider'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateBuilderProvider', '$navigationProvider', function ($stateBuilderProvider,
                                                                                        $navigationProvider) {
            $stateBuilderProvider.state({
                name   : 'sg.about',
                url    : '/about',
                resolve: {
                    label: ['$translate', function ($translate) {
                        return $translate('sg.about')
                    }],  
                },
                views  : {
                    'content@': {
                        template: '<h1>SpringAtom</h1>'
                    }
                }
            });

            $navigationProvider.navigation('sg.about', [
                'sg.home',
                'sg.dashboard',
                'sg.about'
            ]);
        }]);

    }
);