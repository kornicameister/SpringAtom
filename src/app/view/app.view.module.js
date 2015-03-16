define(
    'app/view/app.view.module',
    [
        'angular',
        'app/view/about/about.index.js',
        'app/view/abstract/abstract.index',
        'app/view/admin/admin.index',
        'app/view/home/home.index',
        'app/view/dashboard/dashboard.index'
    ],
    function (angular) {
        return angular.module('sg.app.view', [
            'sg.app.view.index',
            'sg.app.view.home',
            'sg.app.view.about',
            'sg.app.view.admin',
            'sg.app.view.dashboard'
        ]);
    }
);