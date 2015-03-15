(function () {
    "use strict";
    var allTestFiles = [];
    var TEST_REGEXP = /(spec|test)\.js$/i;

    var pathToModule = function (path) {
        path = path.replace(/^\/base\//, '').replace(/\.js$/, '');
        return '../' + path;
    };

    Object.keys(window.__karma__.files).forEach(function (file) {
        if (TEST_REGEXP.test(file)) {
            allTestFiles.push(pathToModule(file));
        }
    });

    var paths = {
        'angular'              : 'vendor/angular/angular',
        'ngAnimate'            : 'vendor/angular-animate/angular-animate',
        'ngBootstrap'          : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
        'ngCalendar'           : 'vendor/angular-ui-calendar/src/calendar',
        'angularUiRouter'      : 'vendor/angular-ui-router/release/angular-ui-router',
        'angularUiRouterExtras': 'vendor/ui-router-extras/release/ct-ui-router-extras',
        'ngCookies'            : 'vendor/angular-cookies/angular-cookies.min',
        'ngDialogs'            : 'vendor/angular-dialog-service/dist/dialogs.min',
        'ngDialogsTranslations': 'vendor/angular-dialog-service/dist/dialogs-default-translations.min',
        'ngSanitize'           : 'vendor/angular-sanitize/angular-sanitize.min',
        'angularTranslate'     : 'vendor/angular-translate/angular-translate.min',
        'angularTranslateLog'  : 'vendor/angular-translate-handler-log/angular-translate-handler-log.min',
        'angular-moment'       : 'vendor/angular-moment/angular-moment.min',
        'bootstrap'            : 'vendor/bootstrap/dist/js/bootstrap.min',
        'bJasny'               : 'vendor/jasny-bootstrap/dist/js/jasny-bootstrap.min',
        'fullcalendar'         : 'vendor/fullcalendar/fullcalendar.min',
        'jquery'               : 'vendor/jquery/jquery.min',
        'jquery-ui'            : 'vendor/jquery-ui/ui/minified/jquery-ui.min',
        'jsface'               : 'vendor/jsface/jsface',
        'moment'               : 'vendor/moment/min/moment-with-locales.min',
        'ngGrid'               : 'vendor/ng-grid/build/ng-grid.min',
        'ngProgress'           : 'vendor/ngprogress/build/ngProgress.min',
        'restangular'          : 'vendor/restangular/dist/restangular',
        'lodash'               : 'vendor/lodash/lodash.min',
        'ngMultiSelect'        : 'vendor/isteven-angular-multiselect/angular-multi-select',
        'angularLocalStorage'  : 'vendor/angular-local-storage/dist/angular-local-storage.min',
        'firebase'             : 'vendor/firebase/firebase',
        'angularFire'          : 'vendor/angularfire/dist/angularfire'
    };

    paths['angularMocks'] = 'vendor/angular-mocks/angular-mocks';
    paths['angularScenario'] = 'vendor/angular-scenario/angular-scenario';
    paths['ngMidwayTester'] = '../test/node_modules/ng-midway-tester/src/ngMidwayTester';

    require.config({
        baseUrl : '/base/sa',
        paths   : paths,
        map     : {
            '*': {
                'less': 'vendor/require-less/less' // path to less
            }
        },
        shim    : {
            'templates'            : ['angular'],
            'angular'              : {
                exports: 'angular',
                deps   : ['jquery', 'jquery-ui']
            },
            'angularFire'          : ['angular', 'firebase'],
            'ngMidwayTester'       : ['angular'],
            'angularMocks'         : {deps: ['angular'], exports: 'angular.mock'},
            'angularScenario'      : ['angular', 'angularMocks'],
            'restangular'          : ['angular', 'lodash'],
            'angularLocalStorage'  : ['angular'],
            'angularTranslate'     : ['angular'],
            'angularTranslateLog'  : ['angular', 'angularTranslate'],
            'ngProgress'           : ['angular'],
            'ngDialogs'            : ['angularTranslate', 'ngSanitize', 'ngDialogsTranslations'],
            'ngDialogsTranslations': ['angular'],
            'ngSanitize'           : ['angular'],
            'angularUiRouter'      : ['angular'],
            'angularUiRouterExtras': ['angularUiRouter'],
            'ngCalendar'           : {
                exports: 'ngCalendar',
                deps   : [
                    'angular',
                    'jquery',
                    'jquery-ui',
                    'fullcalendar'
                ]
            },
            'ngCookies'            : ['angular'],
            'angular-moment'       : ['angular', 'moment'],
            'fullcalendar'         : ['jquery'],
            'ngBootstrap'          : ['angular'],
            'ngAnimate'            : ['angular'],
            'ngGrid'               : ['angular'],
            'bootstrap'            : ['jquery'],
            'ngMultiSelect'        : ['angular'],
            'bJasny'               : ['bootstrap'],
            'jquery-ui'            : ['jquery']
        },
        deps    : allTestFiles,
        callback: window.__karma__.start
    });

}());