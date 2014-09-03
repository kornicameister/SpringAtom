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
        // angular injections
        'resources/componentResource'
    ],
    function calendar(calendarController) {
        return [
            {
                name      : 'calendar',
                definition: {
                    url        : '/sa/dashboard/calendar',
                    templateUrl: '/ui/pages/dashboard/calendar/calendar.html'
                }
            },
            {
                name      : 'calendar.organizer',
                definition: {
                    url        : '/organizer',
                    controller : calendarController,
                    templateUrl: '/ui/pages/dashboard/calendar/organizer.html',
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
                    url       : '/grid',
                    controller: calendarController,
                    template  : (function getTemplate() {
                        var local = [];
                        local.push('<dynamic-grid class="panel" config="calendarGridConfig" origin="\'infopage\'""></dynamic-grid>');
                        return local.join('');
                    }())
                }
            }
        ]
    }
);