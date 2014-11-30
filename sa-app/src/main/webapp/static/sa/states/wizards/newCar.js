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

define(
    [
        'utils',
        'services/cmp/wiz/wizardService',
        'views/cmp/wiz/wizardController',
        'views/cmp/wiz/newCar/carStepController',
        'views/cmp/wiz/newCar/ownersStepController',
        // provider of the state
        'services/cmp/wiz/handler/newCar'
    ],
    function newCar(utils, wizService, wizCtrl, carStepCtrl, ownersStepCtrl) {
        var url = '/new/car',
            mainUrl = wizService.getUrl(url),
            mainName = 'newCar';
        return [
            {
                rule: {
                    when: mainUrl,
                    then: mainUrl + '/vin'
                }
            },
            {
                name      : mainName,
                definition: {
                    url        : mainUrl,
                    templateUrl: '/static/sa/views/wiz/wizard.html',
                    controller : wizCtrl,
                    resolve    : wizService.getResolve(mainName),
                    onEnter    : function (navigationService) {
                        navigationService.setNavigatorModel();
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'vin'),
                definition: {
                    url        : '/vin',
                    templateUrl: '/static/sa/views/wiz/newCar/vin.jsp'
                }
            },
            {
                name      : wizService.getState(mainName, 'car'),
                definition: {
                    url        : '/car',
                    templateUrl: '/static/sa/views/wiz/newCar/car.jsp',
                    controller : carStepCtrl,
                    resolve    : {
                        carStepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'car');
                        }]
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'owner'),
                definition: {
                    url        : '/owner',
                    templateUrl: '/static/sa/views/wiz/newCar/owner.jsp',
                    controller : ownersStepCtrl,
                    resolve    : {
                        ownersStepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'owner');
                        }]
                    }
                }
            }
        ]
    }
);
