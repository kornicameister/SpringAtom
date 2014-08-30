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
        'moment'
    ],
    function newCar(app, utils, wizService, moment) {
        var mainName = 'newCar',
            newCarService = function newCarService($log) {
                var vinState = wizService.getState(mainName, 'vin'),
                    carState = wizService.getState(mainName, 'car'),
                    ownerState = wizService.getState(mainName, 'owner'),
                    formDataKeysPerStep = {
                        'newCar.vin'  : [
                            'vinNumber'
                        ],
                        'newCar.car'  : [
                            'carMaster',
                            'licencePlate',
                            'fuelType',
                            'yearOfProduction'
                        ],
                        'newCar.owner': [
                            'owner'
                        ]
                    },
                    adjusters = {
                        'newCar.car'  : function (data) {
                            var keys = _.keys(data),
                                stepKeys = formDataKeysPerStep[carState],
                                local = {};
                            keys = _.intersection(stepKeys, keys);
                            _.each(keys, function keyIt(key) {
                                var dataForKey = data[key];
                                if (key === 'carMaster' && dataForKey.value === -1) {
                                    var split = data.label.split(' ');
                                    local['brand'] = split[0];
                                    local['model'] = split[1];
                                } else if (key === 'yearOfProduction' || key === 'fuelType' || key === 'carMaster') {
                                    local[key] = data.value;
                                } else {
                                    local[key] = dataForKey;
                                }
                            });
                            return local;
                        },
                        'newCar.vin'  : function (data) {
                            return {
                                vinNumber: data['vinNumber']
                            };
                        },
                        'newCar.owner': function (data) {
                            return {
                                owner: data['owner'].value
                            }
                        }
                    },
                    getFormData = function getFormData() {
                        return {
                            vinNumber       : '',
                            carMaster       : '',
                            licencePlate    : '',
                            fuelType        : '',
                            yearOfProduction: moment().year(),
                            owner           : ''
                        }
                    },
                    isActionEnabled = function isActionEnabled(action, currentStep, wizardForm) {
                        $log.debug('Checking if action(name={n}) is enabled in step(step={s})'.format({
                            n: action,
                            s: currentStep
                        }));
                        var enabled = true;
                        try {
                            if (currentStep === vinState && action === 'next') {
                                enabled = wizardForm.vinNumber.$valid;
                            } else if (currentStep === vinState && action === 'finish') {
                                enabled = false;
                            } else if (currentStep === carState && action === 'finish') {
                                enabled = false;
                            } else if (currentStep === carState && action === 'next') {

                                enabled = wizardForm.carMaster.$valid
                                    && wizardForm.licencePlate.$valid
                                    && wizardForm.fuelType.$valid
                                    && wizardForm.yearOfProduction.$valid;

                            } else if (currentStep === ownerState) {
                                if (action === 'next') {
                                    enabled = false;
                                } else if (action === 'finish' && angular.isDefined(wizardForm.owner)) {
                                    enabled = wizardForm.owner.$valid;
                                }
                            }
                        } catch (ignore) {
                            enabled = true;
                        }
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
                        _.extend(localData, adjusters[vinState](formData));
                        _.extend(localData, adjusters[carState](formData));
                        _.extend(localData, adjusters[ownerState](formData));
                        return localData;
                    };

                return wizService.getProvider({
                    getFormData          : getFormData,
                    getStepSubmissionData: getStepSubmissionData,
                    isActionEnabled      : isActionEnabled,
                    getSubmissionData    : getSubmissionData
                });
            };

        app.factory(mainName, ['$log', newCarService]);
    }
);
