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
 * Created by trebskit on 2014-08-21.
 */
define(
    [
        'angular',
        // angular injections
        'dialogs/newBrandModel/def'
    ],
    function carStepController(angular) {
        var carStepController = function carStepController($scope, $log, $filter, brandModelDialog, carStepData) {
            var initData = carStepData,
                getFuelTypes = function getFuelTypes() {
                    return initData.getStepData('fuelTypes').options;
                },
                getCarMasters = function getCarMasters() {
                    return initData.getStepData('carMasters').options;
                },
                hooks = {
                    /**
                     * Pops up dialog that allows to create new brandModel entry.
                     * Brand+Model is an input in this dialog
                     * @param $event current event
                     */
                    newCarMaster: function newCarMaster($event) {
                        $event.preventDefault();
                        brandModelDialog
                            .dialog({
                                carMasters: getCarMasters()
                            })
                            .result.then(addNewCarMaster);
                    }
                },
                addNewCarMaster = function addNewCarMaster(data) {
                    data = {
                        value: -1,
                        label: data
                    };
                    $scope.carMasters.push(data);
                    $scope.$parent['wizardForm'].carMaster.$setViewValue(data);
                };

            angular.extend($scope, {
                fuelTypes     : getFuelTypes(),
                carMasters    : getCarMasters(),
                carCtrlActions: {
                    newCarMaster: hooks.newCarMaster
                }
            });

        };

        return carStepController;
    }
);
