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
        'moment',
        // angular injections
        'resources/wizardResource'
    ],
    function newCar(app, utils, wizService, moment) {
        var mainName = 'newAppointment',
            newAppointmentService = function newAppointmentService($log, wizardResource) {
                var vinState = wizService.getState(mainName, 'define'),
                    carState = wizService.getState(mainName, 'tasks'),
                    ownerState = wizService.getState(mainName, 'comment'),
                    getFormData = function getFormData() {
                        var date = new Date().dt;
                        return {
                            beginDate: date,
                            beginTime: date,
                            endDate  : date,
                            endTime  : date,
                            reporter : undefined,
                            assignee : undefined,
                            car      : undefined,
                            tasks    : [
                                {
                                    type: undefined,
                                    task: undefined
                                }
                            ],
                            comment  : undefined
                        }
                    },
                    isActionEnabled = function isActionEnabled(action, currentStep, wizardForm) {
                        return true;
                    },
                    next = function next(conf) {
                        var state = conf.$scope.activeState,
                            form = conf.$scope['wizardForm'],
                            successCallback = conf.success,
                            failureCallback = conf.failure;

                        successCallback.call(this);
                    },
                    submit = function submit(conf) {
                        var successCallback = conf.success,
                            failureCallback = conf.failure,
                            /**
                             * Cleans up the data package. It is required to do, due to
                             * data binding requirements defined on server side which automatically
                             * binds properties to context object of a single form's context object
                             * @param data raw data
                             * @returns {*} cleaned up data
                             */
                            clearData = function clearData(data) {
                                var localData = {};
                                angular.forEach(data, function (chunk, key) {
                                    switch (key) {
                                        case 'assignee':
                                        case 'reporter':
                                        case 'car':
                                        case 'owner':
                                            localData[key] = chunk.value;
                                            break;
                                        case 'tasks':
                                        {
                                            var tasks = [];
                                            angular.forEach(chunk, function tIt(val) {
                                                tasks.push({
                                                    task: val.task,
                                                    type: val.type.value
                                                });
                                            });
                                            localData[key] = tasks;
                                        }
                                            break;
                                        default :
                                            localData[key] = chunk;
                                    }
                                });
                                return localData;
                            };
                        wizardResource
                            .submit(mainName, clearData(conf.$scope.formData))
                            .then(successCallback, failureCallback);
                    };

                return wizService.getProvider({
                    getFormData    : getFormData,
                    isActionEnabled: isActionEnabled,
                    next           : next,
                    submit         : submit
                });
            };

        app.factory(mainName, ['$log', 'wizardResource', newAppointmentService]);
    }
);
