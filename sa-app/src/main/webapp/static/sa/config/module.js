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

/**
 * Created by trebskit on 2014-08-11.
 */
define(
    [
        'angular',
        'utils',
        // angular injections
        'ngRouter',
        'ngAnimate',
        'ngBootstrap',
        'ngCalendar',
        'ngGrid',
        'ngCookies',
        'ngDialogs',
        'angular-moment',
        'bJasny',
        'ngProgress',
        'ngMultiSelect',
        'ngTranslateLoader',
        'ngTranslateLocalStorage',
        'ngTranslateHandlerLog',
        'angularLocalStorage',
        'restangular'
    ],
    function (angular, utils) {
        var appName = 'springatom',
            dependencies = [
                'ui.bootstrap',
                'ui.calendar',
                'ui.router',
                'ngAnimate',
                'ngCookies',
                'ngGrid',
                'dialogs.main',
                'dialogs.default-translations',
                'pascalprecht.translate',
                'angularMoment',
                'ngProgress',
                'multi-select',
                'LocalStorageModule',
                'restangular'
            ],
            module = angular.module(appName, dependencies),
            generalConf = function configureApp($httpProvider) {
                $httpProvider.defaults.headers.common['SA-APP'] = 'SpringAtom';
            },
            urlHelperProvider = function URLHelperProvider() {
                this.$get = function () {
                    return {
                        urlParams: utils.getUrlParams,
                        urlDecode: utils.urlDecode,
                        isDebug  : utils.isDebug,
                        appUrl   : utils.getAppUrl
                    }
                }
            },
            getByPropertyFilter = function _getByPropertyFilter() {
                return function (propertyName, propertyValue, collection) {
                    var i = 0, len = collection.length;
                    for (; i < len; i++) {
                        if (collection[i][propertyName] === propertyValue) {
                            return collection[i];
                        }
                    }
                    return undefined;
                }
            },
            configureDialogs = function (dialogsProvider) {
                dialogsProvider['useBackdrop'](true);
                dialogsProvider['useEscClose'](true);
                dialogsProvider['useCopy'](true);
            };

        module
            .config(['$httpProvider', generalConf])
            .config(configureDialogs)
            .provider('urlHelper', urlHelperProvider)
            .filter('getByProperty', getByPropertyFilter)
            .constant('appName', 'SpringAtom')
            .constant('timeoutDelay', 666);

        return module;
    }
);
