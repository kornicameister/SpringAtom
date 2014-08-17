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
            newCarService = function newCarService($log, $http, $q) {
                var vinState = wizService.getState(mainName, 'vin'),
                    carState = wizService.getState(mainName, 'car'),
                    ownerState = wizService.getState(mainName, 'owner'),
                    getTitle = function getTitle() {
                        return 'Nowy samochód';
                    },
                    getFormData = function getFormData() {
                        return {
                            vinNumber    : '',
                            newBrandModel: false,
                            brand        : '',
                            model        : '',
                            carMaster    : '',
                            licencePlate : '',
                            fuelType     : '',
                            year         : moment().year(),
                            owner        : ''
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

                                if (wizardForm.newBrandModel.$modelValue === true) {
                                    enabled = wizardForm.brand.$valid && wizardForm.model.$valid;
                                } else {
                                    enabled = wizardForm.carMaster.$valid;
                                }

                                enabled = enabled && (wizardForm.licencePlate.$valid
                                    && wizardForm.fuelType.$valid
                                    && wizardForm.year.$valid);

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
                    };

                return wizService.getProvider({
                    getFormData    : getFormData,
                    getTitle       : getTitle,
                    isActionEnabled: isActionEnabled
                });
            };

        app.factory(mainName, ['$log', '$http', '$q', newCarService]);
    }
);
