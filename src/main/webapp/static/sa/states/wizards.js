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
 * Created by trebskit on 2014-08-14.
 */


/**
 * Created by trebskit on 2014-08-14.
 */
define(
    [
        'config/module',
        'services/cmp/wiz/wizardService',
        'views/cmp/wiz/wizardController',
        // steps services
        'services/cmp/wiz/newCarWizardService',
        // angular
        'services/navigation'
    ],
    function ipStates(app, wizService, wizCtrl) {
        var getResolve = function getResolve(mainName) {
                return {
                    service   : mainName,
                    actions   : ['navigationService', function (navigationService) {
                        return navigationService.loadNavigation('wiz.navBar');
                    }],
                    title     : function getTitle(service) {
                        return service.getTitle();
                    },
                    definition: function getDefinition(service) {
                        return service.getDefinition(mainName);
                    },
                    labels    : function getLabels(service) {
                        return service.getLabels();
                    }
                }
            },
            newCarWizard = (function newCarWizard() {
                var mainUrl = wizService.getUrl('/new/car'),
                    mainName = 'newCar';
                return [
                    {
                        name      : mainName,
                        definition: {
                            url        : mainUrl,
                            templateUrl: '/ui/wiz/wizard.jsp',
                            controller : wizCtrl,
                            resolve    : getResolve(mainName)
                        }
                    },
                    {
                        name      : wizService.getState(mainName, 'vin'),
                        definition: {
                            url        : '/vin',
                            templateUrl: '/ui/wiz/newCar/vin.jsp'
                        }
                    },
                    {
                        name      : wizService.getState(mainName, 'car'),
                        definition: {
                            url        : '/car',
                            templateUrl: '/ui/wiz/newCar/car.jsp'
                        }
                    },
                    {
                        name      : wizService.getState(mainName, 'owner'),
                        definition: {
                            url        : '/owner',
                            templateUrl: '/ui/wiz/newCar/owner.jsp'
                        }
                    }
                ]
            }());
        return [
            newCarWizard
        ]
    }
);
