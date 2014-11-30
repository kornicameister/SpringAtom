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
        'views/cmp/wiz/newUser/authoritiesController',
        'views/cmp/wiz/newUser/contactsController',
        // provider of the state
        'services/cmp/wiz/handler/newUser'
    ],
    function newUser(utils, wizService, wizCtrl, authoritiesController, contactsController) {
        var url = '/new/user',
            mainUrl = wizService.getUrl(url),
            mainName = 'newUser';
        return [
            {
                rule: {
                    when: mainUrl,
                    then: mainUrl + '/credentials'
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
                name      : wizService.getState(mainName, 'credentials'),
                definition: {
                    url        : '/credentials',
                    templateUrl: '/static/sa/views/wiz/newUser/credentials.html',
                    resolve    : {
                        stepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'credentials');
                        }]
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'authorities'),
                definition: {
                    url        : '/authorities',
                    templateUrl: '/static/sa/views/wiz/newUser/authorities.html',
                    controller : authoritiesController,
                    resolve    : {
                        stepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'authorities');
                        }]
                    }
                }
            },
            {
                name      : wizService.getState(mainName, 'contacts'),
                definition: {
                    url        : '/contacts',
                    templateUrl: '/static/sa/views/wiz/newUser/contacts.html',
                    controller : contactsController,
                    resolve    : {
                        stepData: ['wizardResource', function (wizardResource) {
                            return wizardResource.stepInit(mainName, 'contacts');
                        }]
                    }
                }
            }
        ]
    }
);
