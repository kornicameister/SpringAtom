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
        'config/module',
        'underscore',
        // angular injections
        'resources/navigationResource'
    ],
    function (app) {
        app.factory('navigationService', function navigationService($rootScope,
                                                                    localStorageService,
                                                                    navigationResource) {

            // This will keep last navigator model between refreshes of the page
            var navigatorModel = 'navigatorModel',
                model = localStorageService.get(navigatorModel);

            if (!_.isUndefined(model)) {
                $rootScope.viewActionModel = model;
            }

            return {
                getNavigationModel: function (key) {
                    return navigationResource.getActionModel(key);
                },
                setNavigatorModel : function (model) {
                    if (!!model.toDirective) {
                        model = model.toDirective();
                    }
                    $rootScope.viewActionModel = model;
                    localStorageService.set(navigatorModel, model);
                }
            }
        })
    }
);
