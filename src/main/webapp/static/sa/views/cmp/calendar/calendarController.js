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
        'underscore',
        'utils',
        // angular injections
        'services/cmp/ip/ip'
    ],
    function calendarController(_, utils) {

        return function ($scope, $stateParams, $state, ipService, organizerDefinition) {
            var calendarName = "_CAL_",
                utils = {
                    requiredProperties: ['_embedded'],
                    mappingProperties : {
                        _embedded: 'data',
                        _links   : 'links'
                    },
                    propertiesToRemove: ['begin', 'end', 'messageKey', 'new'],
                    propertiesRemover : function (value) {
                        if (_.isObject(value)) {
                            _.each(utils.propertiesToRemove, function (prop) {
                                if (value[prop]) {
                                    delete value[prop];
                                }
                            });
                            return value;
                        } else {
                            throw new Error('Invalid argument exception [value=' + value + '] is not object');
                        }
                    },
                    rpHandlers        : {
                        _embedded: function (value) {
                            if (_.isObject(value)) {
                                if (value['appointments']) {
                                    value = value['appointments'];
                                }
                            }
                            if (_.isArray(value)) {
                                var arr = [];
                                _.each(value, function (val) {
                                    val = utils.propertiesRemover.apply(this, [val]);
                                    if (val) {
                                        arr.push(val);
                                    }
                                    if (val['_links']) {
                                        val[utils.mappingProperties['_links']] = val['_links'];
                                        delete val['_links'];
                                    }
                                });
                                return arr;
                            }
                            return undefined;
                        }
                    },
                    isValidResponse   : function (eventData) {
                        return !_.isUndefined(eventData);
                    },
                    eventDataTransform: function eventDataTransform(eventData) {
                        if (!utils.isValidResponse(eventData)) {
                            return undefined;
                        }

                        var getIdFromSelfLink = function getIdFromSelfLink(links) {
                            var id = undefined;
                            _.find(links, function linksIt(link) {
                                if (link.href === 'self') {
                                    id = link.href.split('/');
                                    id = id[id.length - 1];
                                    return id;
                                }
                            })
                        };

                        if (_.isUndefined(eventData.id)) {
                            eventData.id = getIdFromSelfLink(eventData.links);
                        }

                        return {
                            id    : eventData.id,
                            start : eventData['beginTs'],
                            end   : eventData['endTs'],
                            allDay: eventData['allDay'],
                            links : eventData['links'],
                            title : (_.has(eventData, 'title') ? eventData.title : eventData.id)
                        }
                    },
                    successLoad       : function successLoad(data) {
                        if (_.isObject(data)) {
                            var ret = {};
                            _.each(data, function (value, key) {
                                if (_.indexOf(utils.requiredProperties, key) !== -1) {
                                    ret[utils.mappingProperties[key]] = utils.rpHandlers[key].apply(this, [value]);
                                }
                            });
                            if (!ret['data']) {
                                return [];
                            }
                            return ret['data'];
                        }
                        return [];
                    },
                    eventClick        : function eventClick(event) {
                        var links = event.links;
                        // update context children of the navigator and breadcrumb path
                        return false;
                    }
                },
                baseConfig = {
                    header             : {
                        left  : 'prev,next today',
                        center: 'title',
                        right : 'month,agendaWeek,agendaDay'
                    },
                    //select             : launchWizardOnSelect,
                    eventClick         : utils.eventClick,
                    defaultEventMinutes: 30,
                    weekends           : false,
                    weekNumbers        : true,
                    firstDay           : 1,
                    height             : 666,
                    aspectRatio        : 2.2,
                    firstHour          : 8,
                    minTime            : 7,
                    maxTime            : 21,
                    selectHelper       : true,
                    selectable         : true,
                    editable           : true
                },
                baseConfigES = {
                    cache             : true,
                    data              : {
                        sort: 'begin'
                    },
                    eventDataTransform: utils.eventDataTransform,
                    success           : utils.successLoad
                },
                calendar = {
                    getEventSources: function getEventSources() {
                        var eventSources = organizerDefinition.eventSources,
                            sources = [];
                        _.each(eventSources, function eventSourceIt(eventSource) {
                            sources.push(_.extend(eventSource, baseConfigES));
                        });
                        return sources;
                    },
                    initCalendar   : function initCalendar() {
                        return{
                            name    : calendarName,
                            calendar: baseConfig
                        }
                    },
                    init           : function init() {
                        _.extend($scope, {
                            uiConfig    : calendar.initCalendar(),
                            eventSources: calendar.getEventSources()
                        });
                    }
                };

            calendar.init();
        }
    }
);