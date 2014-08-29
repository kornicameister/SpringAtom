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

(function mainJs() {
    var prefix = 'static/sa',
        adjustPath = function adjustPath(val) {
            val = val.substr(0, val.length - 3);
            return val;
        },
        config = {
            baseUrl : '/app/' + prefix,
            paths   : {
                'angular'              : adjustPath('../libs/angular/angular.min.js'),
                'ngAnimate'            : adjustPath('../libs/angular-animate/angular-animate.min.js'),
                'ngBootstrap'          : adjustPath('../libs/angular-bootstrap/ui-bootstrap-tpls.min.js'),
                'ngCalendar'           : adjustPath('../libs/angular-ui-calendar/src/calendar.js'),
                'ngRouter'             : adjustPath('../libs/angular-ui-router/release/angular-ui-router.js'),
                'ngCookies'            : adjustPath('../libs/angular-cookies/angular-cookies.min.js'),
                'ngDialogs'            : adjustPath('../libs/angular-dialog-service/dialogs.min.js'),
                'ngDialogsTranslations': adjustPath('../libs/angular-dialog-service/dialogs-default-translations.min.js'),
                'ngSanitize'           : adjustPath('../libs/angular-sanitize/angular-sanitize.min.js'),
                'ngTranslate'          : adjustPath('../libs/angular-translate/angular-translate.min.js'),
                'angular-moment'       : adjustPath('../libs/angular-moment/angular-moment.min.js'),
                'bootstrap'            : adjustPath('../libs/bootstrap/dist/js/bootstrap.min.js'),
                'bProgressBar'         : adjustPath('../libs/bootstrap-progressbar/bootstrap-progressbar.min.js'),
                'bJasny'               : adjustPath('../libs/jasny-bootstrap/dist/js/jasny-bootstrap.min.js'),
                'fullcalendar'         : adjustPath('../libs/fullcalendar/fullcalendar.min.js'),
                'jquery'               : adjustPath('../libs/jquery/dist/jquery.min.js'),
                'jquery-ui'            : adjustPath('../libs/jquery-ui/ui/minified/jquery-ui.min.js'),
                'jsface'               : adjustPath('../libs/jsface/dist/jsface.all.min.js'),
                'moment'               : adjustPath('../libs/moment/min/moment-with-langs.min.js'),
                'ngGrid'               : adjustPath('../libs/ng-grid/build/ng-grid.min.js'),
                'ngProgress'           : adjustPath('../libs/ngprogress/build/ngProgress.min.js'),
                'underscore'           : adjustPath('../libs/underscore/underscore-min.js'),
                'ngMultiSelect'        : adjustPath('../libs/isteven-angular-multiselect/angular-multi-select.js')
            },
            priority: [
                'angular'
            ],
            shim    : {
                'angular'              : {
                    exports: 'angular',
                    deps   : [
                        'jquery',
                        'jquery-ui'
                    ]
                },
                'ngProgress'           : {
                    deps: [
                        'angular'
                    ]
                },
                'ngDialogs'            : {
                    deps: [
                        'ngTranslate',
                        'ngSanitize',
                        'ngDialogsTranslations'
                    ]
                },
                'ngTranslate'          : {
                    deps: [
                        'angular'
                    ]
                },
                'ngDialogsTranslations': {
                    deps: [
                        'angular'
                    ]
                },
                'ngSanitize'           : {
                    deps: [
                        'angular'
                    ]
                },
                'ngRouter'             : {
                    deps: ['angular']
                },
                'ngCalendar'           : {
                    exports: 'ngCalendar',
                    deps   : [
                        'angular',
                        'jquery',
                        'jquery-ui',
                        'fullcalendar'
                    ]
                },
                'ngCookies'            : {
                    deps: [
                        'angular'
                    ]
                },
                'angular-moment'       : {
                    deps: [
                        'angular',
                        'moment'
                    ]
                },
                'fullcalendar'         : {
                    deps: [
                        'jquery'
                    ]
                },
                'ngBootstrap'          : ['angular'],
                'ngAnimate'            : ['angular'],
                'ngGrid'               : ['angular'],
                'bootstrap'            : {
                    deps: [
                        'jquery'
                    ]
                },
                'ngMultiSelect'        : ['angular'],
                'bProgressBar'         : ['bootstrap'],
                'bJasny'               : ['bootstrap']
            }
        };

    require.config(config);

    require(['app'], function bootstrap(app) {
        app.init();
    });

}());
