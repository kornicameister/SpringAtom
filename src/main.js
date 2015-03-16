require.config({
    paths   : {
        'angular'              : 'vendor/angular/angular',
        'angularUiGrid'        : 'vendor/angular-ui-grid/ui-grid',
        'angularUiRouter'      : 'vendor/angular-ui-router/release/angular-ui-router',
        'angularUiRouterExtras': 'vendor/ui-router-extras/release/ct-ui-router-extras',
        'angularUiBootstrap'   : 'vendor/angular-bootstrap/ui-bootstrap-tpls',
        'angularAnimate'       : 'vendor/angular-animate/angular-animate',
        'angularCalendar'      : 'vendor/angular-ui-calendar/src/calendar',
        'angularCookies'       : 'vendor/angular-cookies/angular-cookies',
        'angularSanitize'      : 'vendor/angular-sanitize/angular-sanitize',
        'angularTranslate'     : 'vendor/angular-translate/angular-translate',
        'angularTranslateLog'  : 'vendor/angular-translate-handler-log/angular-translate-handler-log',
        'angularLocalStorage'  : 'vendor/angular-local-storage/dist/angular-local-storage',
        'angularMoment'        : 'vendor/angular-moment/angular-moment',
        'angularFire'          : 'vendor/angularfire/dist/angularfire',
        'bootstrap'            : 'vendor/bootstrap/dist/js/bootstrap',
        'fullcalendar'         : 'vendor/fullcalendar/dist/fullcalendar',
        'fullcalendarLang'     : 'vendor/fullcalendar/dist/lang/pl',
        'jquery'               : 'vendor/jquery/dist/jquery',
        'jsface'               : 'vendor/jsface/jsface',
        'moment'               : 'vendor/moment/min/moment-with-locales',
        'lodash'               : 'vendor/lodash/lodash',
        'firebase'             : 'vendor/firebase/firebase-debug'
    },
    priority: [
        'angular',
        'jquery'
    ],
    map     : {
        '*': {
            'less': 'vendor/require-less/less' // path to less
        }
    },
    shim    : {
        'angular'              : {
            exports: 'angular',
            deps   : ['jquery']
        },
        'angularFire'          : ['angular', 'firebase'],
        'angularLocalStorage'  : ['angular'],
        'angularTranslate'     : ['angular'],
        'angularTranslateLog'  : ['angular', 'angularTranslate'],
        'angularSanitize'      : ['angular'],
        'angularUiRouter'      : ['angular'],
        'angularUiRouterExtras': ['angularUiRouter'],
        'angularUiBootstrap'   : ['angular'],
        'angularCalendar'      : {
            exports: 'angularCalendar',
            deps   : ['angular', 'jquery', 'fullcalendar']
        },
        'angularCookies'       : ['angular'],
        'angularMoment'        : ['angular', 'moment'],
        'fullcalendar'         : ['jquery'],
        'angularAnimate'       : ['angular'],
        'ngGrid'               : ['angular'],
        'bootstrap'            : ['jquery'],
        'ngMultiSelect'        : ['angular']
    },
    deps    : ['springatom', 'less!assets/less/springatom']
});