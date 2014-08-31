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
        'services/cmp/wiz/wizardService'
    ],
    function newCar(app, utils, wizService) {
        var mainName = 'newAppointment',
            newAppointmentService = function newAppointmentService() {
                var defineState = wizService.getState(mainName, 'definition'),
                    tasksState = wizService.getState(mainName, 'tasks'),
                    commentState = wizService.getState(mainName, 'comment'),
                    formDataKeysPerStep = {
                        'newAppointment.definition': [
                            'beginDate',
                            'beginTime',
                            'endDate',
                            'endTime',
                            'reporter',
                            'assignee',
                            'car',
                            'owner'
                        ],
                        'newAppointment.tasks'     : [
                            'tasks'
                        ],
                        'newAppointment.comment'   : [
                            'comment'
                        ]
                    },
                    adjusters = {
                        'newAppointment.definition': function (data) {
                            var keys = _.keys(data),
                                stepKeys = formDataKeysPerStep[defineState],
                                localData = {};
                            keys = _.intersection(stepKeys, keys);
                            _.each(keys, function keyIt(key) {
                                var chunk = data[key];
                                switch (key) {
                                    case 'assignee':
                                    case 'reporter':
                                    case 'car':
                                    case 'owner':
                                        localData[key] = chunk.value;
                                        break;
                                    default :
                                        localData[key] = chunk;
                                }
                            });
                            return localData;
                        },
                        'newAppointment.comment'   : function (data) {
                            return {
                                comment: data.comment
                            };
                        },
                        'newAppointment.tasks'     : function (data) {
                            var local = [];
                            data = data.tasks;
                            _.each(data, function (task) {
                                local.push({
                                    task: task.task,
                                    type: task.type.value
                                })
                            });
                            return {
                                'tasks': local
                            };
                        }
                    },
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
                    getStepSubmissionData = function getStepSubmissionData($scope, activeStep) {
                        var data = {},
                            formData = $scope.formData;
                        _.extend(data, adjusters[activeStep](formData));
                        return data;
                    },
                    getSubmissionData = function getSubmissionData($scope) {
                        var localData = {},
                            formData = $scope.formData;
                        _.extend(localData, adjusters[defineState](formData));
                        _.extend(localData, adjusters[commentState](formData));
                        _.extend(localData, adjusters[tasksState](formData));
                        return localData;
                    };

                return wizService.getProvider({
                    getFormData          : getFormData,
                    getStepSubmissionData: getStepSubmissionData,
                    isActionEnabled      : isActionEnabled,
                    getSubmissionData    : getSubmissionData
                });
            };

        app.factory(mainName, newAppointmentService);
    }
);
