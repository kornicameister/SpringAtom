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
        'resources/wizardResource',
        'resources/vinNumberResource'
    ],
    function newCar(app, utils, wizService, moment) {
        var mainName = 'newCar',
            newCarService = function newCarService($log, wizardResource, vinNumberResource) {
                var vinState = wizService.getState(mainName, 'vin'),
                    carState = wizService.getState(mainName, 'car'),
                    ownerState = wizService.getState(mainName, 'owner'),
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
                    next = function next(conf) {

                        var state = conf.$scope.activeState,
                            form = conf.$scope['wizardForm'],
                            successCallback = conf.success,
                            failureCallback = conf.failure;

                        if (state === vinState) {
                            vinNumberResource.decode(form.vinNumber.$modelValue).then(
                                function onDecodeSuccess(data) {
                                    angular.extend(conf.$scope, {
                                        years: (function toNgOption(years) {
                                            var obj = [];
                                            angular.forEach(years, function (year) {
                                                obj.push({
                                                    label: year,
                                                    value: year
                                                });
                                            });
                                            return obj;
                                        }(data.years))
                                    });
                                    angular.extend(conf.$scope.formData, {
                                        manufacturedIn: data.manufacturedIn
                                    });
                                    successCallback.call(this);
                                },
                                function onDecodeError(data) {
                                    failureCallback.call(this, data);
                                }
                            );
                        } else {
                            successCallback.call(this);
                        }

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
                                    if (key === 'carMaster' && chunk.value === -1) {
                                        var split = chunk.label.split(' ');
                                        localData['brand'] = split[0];
                                        localData['model'] = split[1];
                                    } else if (key === 'yearOfProduction' || key === 'fuelType' || key === 'owner' || key === 'carMaster') {
                                        localData[key] = chunk.value;
                                    } else {
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

        app.factory(mainName, ['$log', 'wizardResource', 'vinNumberResource', newCarService]);
    }
);
