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
        'angular'
    ],
    function taskStepController(angular) {
        var taskStepController = function taskStepController($scope, $log, stepData) {
            var initData = stepData,
                contacts = $scope.$parent.formData.contacts,
                getContactTypes = function getContactTypes() {
                    return initData.getStepData('contactTypes').options;
                },
                getHooks = function getHooks() {
                    return {
                        addContact   : function addTask($event) {
                            $event.preventDefault();
                            $log.debug('Adding contact handler clicked');
                            // append empty task
                            contacts.push({
                                type   : undefined,
                                contact: undefined
                            });
                            if (contacts.length === 0) {
                                $scope.buttonsVisibility[0] = [false, true];
                            }
                            $scope.buttonsVisibility.push([false, true]);
                        },
                        removeContact: function removeTask($event, index) {
                            $event.preventDefault();
                            $log.debug('Remove contact handler clicked, index={i}'.format({
                                i: index
                            }));
                            contacts.splice(index, 1);
                            $scope.buttonsVisibility.splice(index, 1);
                            angular.forEach($scope.buttonsVisibility, function (bv, index) {
                                if (contacts.length === 1 && index === 0) {
                                    $scope.buttonsVisibility[index] = [true, false];
                                } else {
                                    $scope.buttonsVisibility[index] = [index === 0, contacts.length === 1];
                                }
                            });
                        }
                    }
                };

            angular.extend($scope, {
                contactTypes     : getContactTypes(),
                lh               : getHooks(),
                /**
                 * Buttons visibility allows to control whether or not an <b>Add</b> or <b>Remove</b> button
                 * are visible.
                 * It is required in order to prevent situations where user deletes all entries or is able to see
                 * add button when there are more than one task
                 */
                buttonsVisibility: [
                    [true, false]
                ]
            });

        };

        return taskStepController;
    }
);
