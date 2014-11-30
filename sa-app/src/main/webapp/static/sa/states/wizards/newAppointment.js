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
        'views/cmp/wiz/newAppointment/definitionStepController',
        'views/cmp/wiz/newAppointment/taskStepController',
        // handler
        'services/cmp/wiz/handler/newAppointment'
    ],
    function newCar(utils, wizService, wizCtrl, definitionStepController, taskStepController) {
        var url = '/new/appointment',
            mainUrl = wizService.getUrl(url),
            mainName = 'newAppointment';
        return [
            {
                rule: {
                    when: mainUrl,
                    then: mainUrl + '/definition'
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
                name      : wizService.getState(mainName, 'definition'),
                definition: {
                    url        : '/definition',
                    templateUrl: '/static/sa/views/wiz/newAppointment/content_1.jsp',
                    controller : definitionStepController,
                    resolve    : {
                        definitionStepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'definition');
                        }]
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'tasks'),
                definition: {
                    url        : '/tasks',
                    templateUrl: '/static/sa/views/wiz/newAppointment/content_2.jsp',
                    controller : taskStepController,
                    resolve    : {
                        tasksStepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'tasks');
                        }]
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'comment'),
                definition: {
                    url        : '/comment',
                    templateUrl: '/static/sa/views/wiz/newAppointment/content_3.jsp'
                }
            }
        ]
    }
);
