/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

(function springatom_requireJs_root(require) {

    if (!!require) {
        throw new Error('springatom_requireJs_root initialization failed due to missing requireJS');
    }

    require.config({
        baseUrl : '/app/static/sa',
        paths   : {
            'angular'                 : 'vendor/angular/angular.min.js',
            'ngAnimate'               : 'vendor/angular-animate/angular-animate.min.js',
            'ngBootstrap'             : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min.js',
            'ngCalendar'              : 'vendor/angular-ui-calendar/src/calendar.js',
            'ngRouter'                : 'vendor/angular-ui-router/release/angular-ui-router.js',
            'ngCookies'               : 'vendor/angular-cookies/angular-cookies.min.js',
            'ngDialogs'               : 'vendor/angular-dialog-service/dist/dialogs.min.js',
            'ngDialogsTranslations'   : 'vendor/angular-dialog-service/dist/dialogs-default-translations.min.js',
            'ngSanitize'              : 'vendor/angular-sanitize/angular-sanitize.min.js',
            'ngTranslate'             : 'vendor/angular-translate/angular-translate.min.js',
            'ngTranslateLoader'       : 'vendor/angular-translate-loader-url/angular-translate-loader-url.min.js',
            'ngTranslateLocalStorage' : 'vendor/angular-translate-storage-local/angular-translate-storage-local.min.js',
            'ngTranslateCookieStorage': 'vendor/angular-translate-storage-cookie/angular-translate-storage-cookie.min.js',
            'ngTranslateHandlerLog'   : 'vendor/angular-translate-handler-log/angular-translate-handler-log.min.js',
            'angular-moment'          : 'vendor/angular-moment/angular-moment.min.js',
            'bootstrap'               : 'vendor/bootstrap/dist/js/bootstrap.min.js',
            'bJasny'                  : 'vendor/jasny-bootstrap/dist/js/jasny-bootstrap.min.js',
            'fullcalendar'            : 'vendor/fullcalendar/fullcalendar.min.js',
            'jquery'                  : 'vendor/jquery/jquery.min.js',
            'jquery-ui'               : 'vendor/jquery-ui/ui/minified/jquery-ui.min.js',
            'jsface'                  : 'vendor/jsface/jsface.js',
            'moment'                  : 'vendor/moment/min/moment-with-locales.min.js',
            'ngGrid'                  : 'vendor/ng-grid/build/ng-grid.min.js',
            'ngProgress'              : 'vendor/ngprogress/build/ngProgress.min.js',
            'restangular'             : 'vendor/restangular/dist/restangular.js',
            'lodash'                  : 'vendor/lodash/dist/lodash.min.js',
            'ngMultiSelect'           : 'vendor/isteven-angular-multiselect/angular-multi-select.js',
            'angularLocalStorage'     : 'vendor/angular-local-storage/dist/angular-local-storage.min.js'
        },
        priority: [
            'angular'
        ],
        shim    : {
            'angular'                 : {
                exports: 'angular',
                deps   : [
                    'jquery',
                    'jquery-ui'
                ]
            },
            'restangular'             : {
                deps: [
                    'angular',
                    'lodash'
                ]
            },
            'angularLocalStorage'     : {
                deps: [
                    'angular'
                ]
            },
            'ngTranslateHandlerLog'   : {
                deps: [
                    'angular',
                    'ngTranslate'
                ]
            },
            'ngTranslateCookieStorage': {
                deps: [
                    'angular',
                    'ngTranslate'
                ]
            },
            'ngTranslateLocalStorage' : {
                deps: [
                    'angular',
                    'ngTranslate',
                    'ngTranslateCookieStorage'
                ]
            },
            'ngTranslateLoader'       : {
                deps: [
                    'angular',
                    'ngTranslate'
                ]
            },
            'ngProgress'              : {
                deps: [
                    'angular'
                ]
            },
            'ngDialogs'               : {
                deps: [
                    'ngTranslate',
                    'ngSanitize',
                    'ngDialogsTranslations'
                ]
            },
            'ngTranslate'             : {
                deps: [
                    'angular'
                ]
            },
            'ngDialogsTranslations'   : {
                deps: [
                    'angular'
                ]
            },
            'ngSanitize'              : {
                deps: [
                    'angular'
                ]
            },
            'ngRouter'                : {
                deps: ['angular']
            },
            'ngCalendar'              : {
                exports: 'ngCalendar',
                deps   : [
                    'angular',
                    'jquery',
                    'jquery-ui',
                    'fullcalendar'
                ]
            },
            'ngCookies'               : {
                deps: [
                    'angular'
                ]
            },
            'angular-moment'          : {
                deps: [
                    'angular',
                    'moment'
                ]
            },
            'fullcalendar'            : {
                deps: [
                    'jquery'
                ]
            },
            'ngBootstrap'             : ['angular'],
            'ngAnimate'               : ['angular'],
            'ngGrid'                  : ['angular'],
            'bootstrap'               : {
                deps: [
                    'jquery'
                ]
            },
            'ngMultiSelect'           : ['angular'],
            'bJasny'                  : ['bootstrap'],
            'jquery-ui'               : {
                deps: [
                    'jquery'
                ]
            }
        }
    });

    require(['app'], function bootstrap(app) {
        app.init();
    });

}(window.require));
