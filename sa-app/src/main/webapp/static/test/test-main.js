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
        'angular'                 : 'vendor/angular/angular',
        'ngAnimate'               : 'vendor/angular-animate/angular-animate',
        'ngBootstrap'             : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
        'ngCalendar'              : 'vendor/angular-ui-calendar/src/calendar',
        'angularUiRouter': 'vendor/angular-ui-router/release/angular-ui-router',
        'ngCookies'               : 'vendor/angular-cookies/angular-cookies.min',
        'ngDialogs'               : 'vendor/angular-dialog-service/dist/dialogs.min',
        'ngDialogsTranslations'   : 'vendor/angular-dialog-service/dist/dialogs-default-translations.min',
        'ngSanitize'              : 'vendor/angular-sanitize/angular-sanitize.min',
        'ngTranslate'             : 'vendor/angular-translate/angular-translate.min',
        'ngTranslateLoader'       : 'vendor/angular-translate-loader-url/angular-translate-loader-url.min',
        'ngTranslateLocalStorage' : 'vendor/angular-translate-storage-local/angular-translate-storage-local.min',
        'ngTranslateCookieStorage': 'vendor/angular-translate-storage-cookie/angular-translate-storage-cookie.min',
        'ngTranslateHandlerLog'   : 'vendor/angular-translate-handler-log/angular-translate-handler-log.min',
        'angular-moment'          : 'vendor/angular-moment/angular-moment.min',
        'bootstrap'               : 'vendor/bootstrap/dist/js/bootstrap.min',
        'bJasny'                  : 'vendor/jasny-bootstrap/dist/js/jasny-bootstrap.min',
        'fullcalendar'            : 'vendor/fullcalendar/fullcalendar.min',
        'jquery'                  : 'vendor/jquery/jquery.min',
        'jquery-ui'               : 'vendor/jquery-ui/ui/minified/jquery-ui.min',
        'jsface'                  : 'vendor/jsface/jsface',
        'moment'                  : 'vendor/moment/min/moment-with-locales.min',
        'ngGrid'                  : 'vendor/ng-grid/build/ng-grid.min',
        'ngProgress'              : 'vendor/ngprogress/build/ngProgress.min',
        'restangular'             : 'vendor/restangular/dist/restangular',
        'lodash'                  : 'vendor/lodash/dist/lodash.min',
        'ngMultiSelect'           : 'vendor/isteven-angular-multiselect/angular-multi-select',
        'angularLocalStorage'     : 'vendor/angular-local-storage/dist/angular-local-storage.min'
    };

    paths['angularMocks'] = 'vendor/angular-mocks/angular-mocks';
    paths['angularScenario'] = 'vendor/angular-scenario/angular-scenario';
    paths['ngMidwayTester'] = '../test/node_modules/ng-midway-tester/src/ngMidwayTester';

    require.config({
        baseUrl : '/base/sa',
        paths   : paths,
        shim    : {
            'angular'                 : {
                exports: 'angular',
                deps   : ['jquery', 'jquery-ui']
            },
            'ngMidwayTester'          : ['angular'],
            'angularMocks'            : {deps: ['angular'], exports: 'angular.mock'},
            'angularScenario'         : ['angular', 'angularMocks'],
            'restangular'             : ['angular', 'lodash'],
            'angularLocalStorage'     : ['angular'],
            'ngTranslateHandlerLog'   : ['angular', 'ngTranslate'],
            'ngTranslateCookieStorage': ['angular', 'ngTranslate'],
            'ngTranslateLocalStorage' : ['angular', 'ngTranslate', 'ngTranslateCookieStorage'],
            'ngTranslateLoader'       : ['angular', 'ngTranslate'],
            'ngProgress'              : ['angular'],
            'ngDialogs'               : ['ngTranslate', 'ngSanitize', 'ngDialogsTranslations'],
            'ngTranslate'             : ['angular'],
            'ngDialogsTranslations'   : ['angular'],
            'ngSanitize'              : ['angular'],
            'angularUiRouter': ['angular'],
            'ngCalendar'              : {
                exports: 'ngCalendar',
                deps   : [
                    'angular',
                    'jquery',
                    'jquery-ui',
                    'fullcalendar'
                ]
            },
            'ngCookies'               : ['angular'],
            'angular-moment'          : ['angular', 'moment'],
            'fullcalendar'            : ['jquery'],
            'ngBootstrap'             : ['angular'],
            'ngAnimate'               : ['angular'],
            'ngGrid'                  : ['angular'],
            'bootstrap'               : ['jquery'],
            'ngMultiSelect'           : ['angular'],
            'bJasny'                  : ['bootstrap'],
            'jquery-ui'               : ['jquery']
        },
        deps    : allTestFiles,
        callback: window.__karma__.start
    });

}());