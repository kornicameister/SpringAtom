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
 * Created by trebskit on 2014-08-15.
 */
define(
    [
        'config/module',
        'utils',
        'services/cmp/wiz/wizardService',
        'underscore',
        // angular injections
        'resources/wizardResource'
    ],
    function newUser(app, utils, wizService, _) {
        var mainName = 'newUser',
            newUserService = function newUserService($log, wizardResource) {
                var credentialsState = wizService.getState(mainName, 'credentials'),
                    authoritiesState = wizService.getState(mainName, 'authorities'),
                    contactsState = wizService.getState(mainName, 'contacts'),
                    formDataKeysPerStep = {
                        'newUser.credentials': [
                            'username'    ,
                            'password'    ,
                            'firstname'   ,
                            'lastname',
                            'primarymail'
                        ],
                        'newUser.authorities': [
                            'authorities'
                        ],
                        'newUser.contacts'   : [
                            'contacts'
                        ]
                    },
                    adjusters = {
                        'newUser.credentials': function (data) {
                            var keys = _.keys(data),
                                stepKeys = formDataKeysPerStep[credentialsState],
                                local = {};
                            keys = _.intersection(stepKeys, keys);
                            _.each(keys, function keyIt(key) {
                                var dataForKey = data[key];
                                switch (key) {
                                    case 'firstname':
                                    case 'lastname':
                                    case 'primarymail':
                                        if (key === 'firstname') {
                                            local['person.firstName'] = dataForKey;
                                        } else if (key === 'lastname') {
                                            local['person.lastName'] = dataForKey;
                                        } else if (key === 'primarymail') {
                                            local['person.primaryMail'] = dataForKey;
                                        }
                                        break;
                                    default:
                                        local[key] = dataForKey;
                                }
                            });
                            return local;
                        },
                        'newUser.authorities': function (data) {
                            return {
                                authorities: data.authorities
                            };
                        },
                        'newUser.contacts'   : function (data) {
                            var local = [];
                            data = data.contacts;
                            _.each(data, function (contact) {
                                local.push({
                                    contact: contact.contact,
                                    type   : contact.type.value
                                })
                            });
                            return {
                                'person.contacts': local
                            };
                        }
                    },
                    getFormData = function getFormData() {
                        return {
                            username   : undefined,
                            password   : undefined,
                            firstname  : undefined,
                            lastname   : undefined,
                            primarymail: undefined,
                            authorities: [],
                            contacts   : [
                                {
                                    type   : undefined,
                                    contact: undefined
                                }
                            ]
                        }
                    },
                    isActionEnabled = function isActionEnabled(action, currentStep, wizardForm) {
                        $log.debug('Checking if action(name={n}) is enabled in step(step={s})'.format({
                            n: action,
                            s: currentStep
                        }));
                        var enabled = true;
                        return enabled;
                    },
                    getStepSubmissionData = function getStepSubmissionData($scope, activeStep) {
                        var data = {},
                            formData = $scope.formData;
                        _.extend(data, adjusters[activeStep](formData));
                        return data;
                    },
                    getSubmissionData = function getSubmissionData($scope) {
                        var localData = {},
                            formData = $scope.formData;
                        _.extend(localData, adjusters[credentialsState](formData));
                        _.extend(localData, adjusters[authoritiesState](formData));
                        _.extend(localData, adjusters[contactsState](formData));
                        return localData;
                    };

                return wizService.getProvider({
                    getFormData          : getFormData,
                    getStepSubmissionData: getStepSubmissionData,
                    isActionEnabled      : isActionEnabled,
                    getSubmissionData    : getSubmissionData
                });
            };

        app.factory(mainName, ['$log', 'wizardResource', newUserService]);
    }
);
