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
 * Created by kornicameister on 01.09.14.
 */
define(
    [
        'views/cmp/calendar/calendarController',
        'views/cmp/calendar/gridController',
        // angular injections
        'resources/componentResource',
        'resources/rest/appointmentResource'
    ],
    function calendar(calendarController, gridController) {
        // TODO: need to enter calendar.organizer at opening of calendar
        return [
            {
                rule: {
                    when: '/sa/dashboard/calendar',
                    then: '/sa/dashboard/calendar/organizer'
                }
            },
            {
                name      : 'calendar',
                definition: {
                    url        : '/sa/dashboard/calendar',
                    templateUrl: '/static/sa/views/cmp/calendar/calendar.html',
                    resolve    : {
                        actionModel: function (navigationService) {
                            return navigationService.getNavigationModel('dashboard.page.calendar')
                        }
                    },
                    onEnter    : function (actionModel, navigationService) {
                        navigationService.setNavigatorModel(actionModel);
                    }
                }
            },
            {
                name      : 'calendar.organizer',
                definition: {
                    url        : '/organizer',
                    controller : calendarController,
                    templateUrl: '/static/sa/views/cmp/calendar/organizer.html',
                    resolve    : {
                        'organizerDefinition': function (componentResource) {
                            return componentResource.getOtherDefinition('calendarBuilder')
                        }
                    }
                }
            },
            {
                name      : 'calendar.grid',
                definition: {
                    url        : '/grid',
                    resolve    : {
                        translations: function ($translate) {
                            return $translate([
                                'sappointment.begintime',
                                'sappointment.endtime',
                                'sappointment.begindate',
                                'sappointment.enddate',
                                'sappointment.allday',
                                'sappointment.closed',
                                'sactivity.comment',
                                'sappointment.tasks',
                                'sappointment.car',
                                'sappointment.assignee',
                                'sappointment.assigned',
                                'sappointment.reporter',
                                'sappointment.interval',
                                'sappointment',
                                // task types [TMP solution]
                                'SAT_OIL_CHANGE',
                                'SAT_REPAIR',
                                'SAT_NORMAL'
                            ]);
                        }
                    },
                    controller : gridController,
                    templateUrl: '/static/sa/views/cmp/calendar/grid.html'
                }
            }
        ]
    }
);