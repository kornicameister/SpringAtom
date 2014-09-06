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
 * Created by trebskit on 2014-09-05.
 */
define(
    [
        'underscore',
        // angular injections
        'resources/rest/appointmentResource'
    ],
    function gridController(_) {

        /**
         * Structure holds appointments that were already selected.
         * This is useful because single appointment has associations
         * to other resources that need to be resolved
         * @type {{}}
         */
        var selectedAppointmentCache = {},
            serverData = undefined;

        return function ($scope, $translate, appointmentResource, timeoutDelay, translations) {

            var helpers = {
                getColumns       : function () {
                    return [
                        {
                            field      : 'beginTime',
                            displayName: translations['sappointment.begintime']
                        },
                        {
                            field      : 'endTime',
                            displayName: translations['sappointment.endtime']
                        },
                        {
                            field      : 'beginDate',
                            displayName: translations['sappointment.begindate']
                        },
                        {
                            field      : 'endDate',
                            displayName: translations['sappointment.enddate']
                        },
                        {
                            field      : 'allDay',
                            displayName: translations['sappointment.allday'],
                            cellFilter : 'booleanFilter'
                        },
                        {
                            field      : 'assigned',
                            displayName: translations['sappointment.assigned']
                        },
                        {
                            field      : 'closed',
                            displayName: translations['sappointment.closed'],
                            cellFilter : 'booleanFilter'
                        }
                    ]
                },
                setPagingData    : function (data) {
                    $scope.gridData = data.plain();
                    $scope.totalServerItems = data.page['totalElements'];

                    serverData = data;

                    if (!$scope.$$phase) {
                        $scope.$apply();
                    }
                },
                getPagedDataAsync: function (pageSize, page) {
                    setTimeout(function () {
                        appointmentResource.getList({
                            page: page - 1,
                            size: pageSize
                        }).then(function data(data) {
                            helpers.setPagingData(data);
                        });
                    }, timeoutDelay);
                }
            };

            $scope.totalServerItems = 0;
            $scope.pagingOptions = {
                pageSizes  : [25, 50, 75],
                pageSize   : 25,
                currentPage: 1
            };

            $scope.gridSelection = [];

            _.extend($scope, {
                gridData           : [],
                gridColumns        : helpers.getColumns(),
                gridOptions        : {
                    data              : 'gridData',
                    columnDefs        : 'gridColumns',
                    totalServerItems  : 'totalServerItems',
                    pagingOptions     : $scope.pagingOptions,
                    selectedItems     : $scope.gridSelection,
                    multiSelect       : false,
                    border            : true,
                    enablePaging      : true,
                    enableRowSelection: true,
                    showFooter        : true,
                    showColumnMenu    : true,
                    showGroupPanel    : true
                },
                selectedAppointment: undefined
            });

            $scope.$watch('pagingOptions', function (newVal, oldVal) {
                if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                    helpers.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
                }
            }, true);

            $scope.$watchCollection('gridSelection', function (selected) {
                if (_.isUndefined(selected)) {
                    $scope.selectedAppointment = undefined;
                } else {
                    selected = selected[0];
                    if (_.isUndefined(selected)) {
                        $scope.selectedAppointment = undefined;
                    } else {
                        var serverSelected = _.findWhere(serverData, {id: selected['id']});

                        // TODO, wrong url is built, additional / after appointment ???
                        // TODO need to add some directive that lazily resoles promise

                        $scope.selectedAppointment = {
                            heading: selected['beginDate'] + ' :: ' + selected['endDate'],
                            body   : [
                                {
                                    label: translations['sappointment.allday'],
                                    value: selected['allDay']
                                },
                                {
                                    label: translations['sappointment.car'],
                                    value: serverSelected.one('car').get().$object,
                                    tpl  : 'licencePlate'
                                },
                                {
                                    label: translations['sappointment.assignee'],
                                    value: serverSelected.one('assignee').get().$object,
                                    tpl  : 'identity'
                                },
                                {
                                    label: translations['sappointment.reporter'],
                                    value: serverSelected.one('reporter').get().$object,
                                    tpl  : 'identity'
                                },
                                {
                                    value: selected['comment'],
                                    label: translations['sactivity.comment']
                                },
                                {
                                    label : translations['sappointment.tasks'],
                                    value : serverSelected.all('tasks').getList().$object,
                                    method: function (data) {
                                        var toReturn = [];
                                        toReturn.push('<ul class="list-group">')
                                        _.each(data, function (sap) {
                                            toReturn.push('<li class="list-group-item">{type} - {task}</li>'.format({
                                                type: translations[sap.type],
                                                task: sap.task
                                            }))
                                        });
                                        toReturn.push('</ul>');
                                        return toReturn.join('');
                                    }
                                }
                            ]
                        }
                    }
                }
            }, true);
            // load data
            helpers.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        }
    }
);