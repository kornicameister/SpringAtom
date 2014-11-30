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
        'utils',
        'moment'
    ],
    function definitionStepController(angular, utils, moment) {
        var definitionStepController = function definitionStepController($scope, $log, $filter, definitionStepData) {
            var initData = definitionStepData,
                getDataFormat = function getDateFormat() {
                    return initData.getStepData('dateFormat');
                },
                getTimeFormat = function getTimeFormat() {
                    return initData.getStepData('timeFormat');
                },
                getCars = function getCars() {
                    return initData.getStepData('cars').options;
                },
                getAssignees = function getAssignees() {
                    return initData.getStepData('assignees').options;
                },
                getReporters = function getReporters() {
                    return initData.getStepData('reporters').options;
                },
                getPickerLabels = function getPickerLabels() {
                    return initData.getStepData('pickerLabels');
                },
                isDateDisabled = function isDateDisabled(date, mode) {
                    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
                },
                helpers = {
                    setOpenedFlag: function setOpenedFlag(key, val) {
                        $scope.lh[key].opened = val;
                    }
                },
                datePickerOptions = {
                    startingDay: 1,
                    showWeeks  : false
                },
                hooks = {
                    beginDate: {
                        opened  : false,
                        open    : function ($event) {
                            $event.stopPropagation();
                            helpers.setOpenedFlag('beginDate', true);
                        },
                        disabled: isDateDisabled,
                        options : datePickerOptions,
                        initDate: moment()
                    },
                    endDate  : {
                        opened  : false,
                        open    : function ($event) {
                            $event.stopPropagation();
                            helpers.setOpenedFlag('endDate', true);
                        },
                        disabled: isDateDisabled,
                        options : datePickerOptions,
                        initDate: moment()
                    }
                };

            angular.extend($scope, {
                dateFormat  : getDataFormat(),
                timeFormat  : getTimeFormat(),
                options     : {
                    cars     : getCars(),
                    assignees: getAssignees(),
                    reporters: getReporters()
                },
                pickerLabels: getPickerLabels(),
                lh          : hooks
            });

        };

        return definitionStepController;
    }
);
