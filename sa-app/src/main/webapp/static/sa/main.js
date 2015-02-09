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

    if (!require) {
        throw new Error('springatom_requireJs_root initialization failed due to missing requireJS');
    }
    
    var paths = {
            'angular'                 : 'vendor/angular/angular.min',
            'ngAnimate'               : 'vendor/angular-animate/angular-animate.min',
            'ngBootstrap'             : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
            'ngCalendar'              : 'vendor/angular-ui-calendar/src/calendar',
            'angularUiRouter'         : 'vendor/angular-ui-router/release/angular-ui-router',
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
            'jsface'                  : 'vendor/jsface/jsface',
            'moment'                  : 'vendor/moment/min/moment-with-locales.min',
            'ngGrid'                  : 'vendor/ng-grid/build/ng-grid.min',
            'ngProgress'              : 'vendor/ngprogress/build/ngProgress.min',
            'restangular'             : 'vendor/restangular/dist/restangular',
            'lodash'                  : 'vendor/lodash/dist/lodash.min',
            'ngMultiSelect'           : 'vendor/isteven-angular-multiselect/angular-multi-select',
            'angularLocalStorage'     : 'vendor/angular-local-storage/dist/angular-local-storage.min'
        };
    
    paths = (function adjustIfMin(paths){
        var devEnvRegexp = /[localhost|codio]/gi,
            isDevEnvironment = devEnvRegexp.test(document.location.hostname);
        
        for(var key in paths){
            if(isDevEnvironment && paths[key].search('.min') > -1){
                paths[key] = paths[key].replace('.min',''); 
            }
        }
        
        return paths;
    }(paths));
    
    require.config({
        baseUrl : '',
        paths   : paths,
        priority: [
            'angular'
        ],
        shim    : {
            'angular'                 : {
                exports: 'angular',
                deps   : [
                    'jquery'
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
            'angularUiRouter': {
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
