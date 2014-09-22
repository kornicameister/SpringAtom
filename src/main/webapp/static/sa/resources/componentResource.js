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
 * Created by trebskit on 2014-09-01.
 */
define(
    [
        'config/module',
        'utils',
        'underscore',
        // angular injections
        'restangular',
        'services/contextObjectService'
    ],
    function componentResource(app, utils, _) {

        var extensions = [
                {
                    route       : 'builders',
                    isCollection: true,
                    extension   : function buildersTransformer(element) {

                        element.getBuildersIds = function getBuildersIds() {
                            console.log('test');
                        };
                        element.getBuildersForBranch = function getBuildersForBranch(branch) {
                            return _.filter(this, function predicate(entry) {
                                return _.some(entry.definitions, function some(def) {
                                    return def['produces'] === branch;
                                })
                            });
                        };
                        element.getInfoPageBuilder = function getInfoPageBuilder(rel) {
                            var infoPageBuilders = this.getBuildersForBranch('PAGE_COMPONENT'),
                                builder = undefined;
                            _.each(infoPageBuilders, function (entry, key) {
                                if (_.isUndefined(builder)) {
                                    builder = _.find(entry.definitions, function (def) {
                                        return def['nestedMetaData']['componentId'] === rel;
                                    });
                                    if (!_.isUndefined(builder)) {
                                        builder = _.extend(builder, {
                                            builderId: entry.builderId
                                        })
                                    }
                                }
                            });
                            return builder;
                        };

                        return element;
                    }
                }
            ],
            setUpExtensions = function setUpExtensions() {
                var resource = this;
                _.each(extensions, function (tr) {
                    if (_.isUndefined(tr.isCollection)) {
                        tr.isCollection = false;
                    }
                    if (tr.isCollection) {
                        resource.extendCollection(tr.route, tr.extension)
                    } else {
                        resource.extendModel(tr.route, tr.extension)
                    }
                });
            };

        app.factory('componentResource', function navigationResource(Restangular, $log, contextObjectService) {
            var resource = Restangular.withConfig(function configurer(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('cmp/components');
                RestangularConfigurer.setRestangularFields({
                    selfLink: 'links.self'
                });
                RestangularConfigurer.addResponseInterceptor(function responseInterceptor(data, operation, what) {
                    var actualData = data.content,
                        toReturn = actualData,
                        links = data.links;

                    // set links
                    actualData.links = {};
                    _.each(links, function (link) {
                        actualData.links[link.rel] = link.href;
                    });

                    switch (operation) {
                        case 'getList':
                            toReturn = [];
                    }

                    // post process
                    switch (what) {
                        case 'builders':
                            _.each(actualData, function (val, key) {
                                if (key !== 'links') {
                                    toReturn.push({
                                        builderId  : key,
                                        definitions: val
                                    });
                                }
                            });
                            toReturn.links = actualData.links;
                            break;
                    }

                    return toReturn;
                });
            });

            resource.getBuildersMap = function () {
                return this.all('builders').getList();
            }.bind(resource);
            resource.getInfoPageComponentConfiguration = function (builder, oid) {
                return this.getComponentConfiguration(builder, {
                    oid   : oid,
                    pageId: builder.replace('InfoPageBuilder', ''),
                    type  : 'PAGE_COMPONENT'
                });
            }.bind(resource);
            resource.getTableComponentConfiguration = function (builder, oid) {
                return this.getComponentConfiguration(builder, {
                    oid : oid,
                    type: 'TABLE_COMPONENT'
                });
            }.bind(resource);
            resource.getComponentConfiguration = function (builderId, params) {
                return this.one('get/component', builderId).get(params);
            }.bind(resource);
            resource.reloadData = function (builderId) {
                var co = arguments.length === 2 ? arguments[1] : contextObjectService.getContextObject();
                return this.one('data/reload', builderId).get(co);
            }.bind(resource);

            setUpExtensions.bind(resource)();

            return resource;
        });
    }
);