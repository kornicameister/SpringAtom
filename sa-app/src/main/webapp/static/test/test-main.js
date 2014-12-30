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

require.config({
    baseUrl : '/base/sa',
    paths   : {
        'angular'                 : '../libs/angular/angular',
        'ngAnimate'               : '../libs/angular-animate/angular-animate',
        'ngBootstrap'             : '../libs/angular-bootstrap/ui-bootstrap-tpls',
        'ngCalendar'              : '../libs/angular-ui-calendar/src/calendar',
        'ngRouter'                : '../libs/angular-ui-router/release/angular-ui-router',
        'ngCookies'               : '../libs/angular-cookies/angular-cookies',
        'ngDialogs'               : '../libs/angular-dialog-service/dist/dialogs.min',
        'ngDialogsTranslations'   : '../libs/angular-dialog-service/dist/dialogs-default-translations.min',
        'ngSanitize'              : '../libs/angular-sanitize/angular-sanitize',
        'ngTranslate'             : '../libs/angular-translate/angular-translate',
        'ngTranslateLoader'       : '../libs/angular-translate-loader-url/angular-translate-loader-url',
        'ngTranslateLocalStorage' : '../libs/angular-translate-storage-local/angular-translate-storage-local',
        'ngTranslateCookieStorage': '../libs/angular-translate-storage-cookie/angular-translate-storage-cookie',
        'ngTranslateHandlerLog'   : '../libs/angular-translate-handler-log/angular-translate-handler-log',
        'angular-moment'          : '../libs/angular-moment/angular-moment',
        'bootstrap'               : '../libs/bootstrap/dist/js/bootstrap',
        'bJasny'                  : '../libs/jasny-bootstrap/dist/js/jasny-bootstrap',
        'fullcalendar'            : '../libs/fullcalendar/fullcalendar',
        'jquery'                  : '../libs/jquery/jquery.min',
        'jquery-ui'               : '../libs/jquery-ui/ui/minified/jquery-ui.min',
        'jsface'                  : '../libs/jsface/jsface',
        'moment'                  : '../libs/moment/min/moment-with-locales',
        'ngGrid'                  : '../libs/ng-grid/build/ng-grid',
        'ngProgress'              : '../libs/ngprogress/build/ngProgress',
        'underscore'              : '../libs/underscore/underscore-min',
        'restangular'             : '../libs/restangular/dist/restangular',
        'lodash'                  : '../libs/lodash/dist/lodash',
        'ngMultiSelect'           : '../libs/isteven-angular-multiselect/angular-multi-select',
        'angularLocalStorage'     : '../libs/angular-local-storage/dist/angular-local-storage',
        // test dependencies
        'angularMocks'            : '../libs/angular-mocks/angular-mocks',
        'angularScenario'         : '../libs/angular-scenario/angular-scenario'
    },
    shim    : {
        'angular'                 : {
            exports: 'angular',
            deps   : ['jquery', 'jquery-ui']
        },
        'angularMocks'            : ['angular'],
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
        'ngRouter'                : ['angular'],
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
