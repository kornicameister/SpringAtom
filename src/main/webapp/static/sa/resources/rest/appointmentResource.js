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
 * Created by trebskit on 2014-09-04.
 */
define(
    [
        'config/module',
        'underscore',
        // angular injections
        'restangular',
        'resources/rest/baseRestResource'
    ],
    function appointmentsResource(app, _) {

        var knownAssociations = {
                getAssignee: 'assignee',
                getCar     : 'car',
                getReporter: 'reporter',
                getTasks   : 'tasks'
            },
            resource = function ($log, $location, $rootScope, baseRestResource) {
                var route = 'appointment',
                    apps = baseRestResource
                        .create(route)
                        .addNestedInterceptors({
                            tasks: function (data) {
                                var actual = data._embedded;
                                return actual;
                            }
                        }),
                    search = function (what, argMap, page) {
                        var configuration = argMap;
                        if (!_.isUndefined(page)) {
                            configuration.page = page;
                        }
                        return this.all(route + '/search/' + what).getList(configuration);
                    };

                apps.queries = {
                    feed              : function (begin, end) {
                        return search('feed', {
                            begin: begin,
                            end  : end,
                            sort : 'begin'
                        });
                    }.bind(apps),
                    findByAssignee    : function (assignee, page) {
                        return search('findByAssignee', {
                            assignee: assignee
                        }, page);
                    }.bind(apps),
                    findByReporter    : function (reporter, page) {
                        return search('findByReporter', {
                            reporter: reporter
                        }, page);
                    }.bind(apps),
                    findByLicencePlate: function (lp, page) {
                        return search('findByCarLicencePlate', {
                            licencePlate: lp
                        }, page);
                    }.bind(apps)
                };

                apps.extendModel(route, function (appointment) {
                    _.each(knownAssociations, function (path, name) {
                        appointment.addRestangularMethod(name, 'get', path, {}, {Resource: 'appointmentResource'});
                    });
                    return appointment;
                });

                return apps;
            };
        app.factory('appointmentResource', resource);
    }
);