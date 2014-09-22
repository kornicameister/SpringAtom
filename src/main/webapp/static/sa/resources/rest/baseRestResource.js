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
 * Created by trebskit on 2014-09-03.
 */
define(
    [
        'config/module',
        'underscore',
        // angular injections
        'restangular'
    ],
    function baseRestResource(app, _) {

        var baseUrl = 'rest',
            operations = {
                GET_LIST: 'getList',
                PUT     : 'put',
                GET     : 'get'
            },
            resource = function resource($log, $location, Restangular) {
                var location = {
                        host    : $location.host(),
                        port    : $location.port(),
                        protocol: $location.protocol()
                    },
                    getIdFromSelfLink = function (href) {
                        href = href.split('/');
                        return Number(href[href.length - 1]);
                    },
                    getPageNavigation = function (links) {
                        var keys = _.keys(links),
                            nav = {};

                        if (_.indexOf(keys, 'next') > -1) {
                            nav.next = links.next.href;
                        }
                        if (_.indexOf(keys, 'prev') > -1) {
                            nav.next = links.prev.href;
                        }
                        if (_.indexOf(keys, 'self') > -1) {
                            nav.reload = links.self.href;
                        }

                        return nav;
                    },
                    handleSingleGet = function (data) {
                        var links = data._links;
                        delete data._links;
                        data.associations = links;
                        _.each(data.associations, function (assoc, key) {
                            if (key === 'self') {
                                data.id = getIdFromSelfLink(assoc.href);
                            }
                        });
                        return data;
                    },
                    handleGetList = function (data, dataRoot) {
                        var unwrappedData = data._embedded;

                        if (!_.isUndefined(unwrappedData)) {
                            unwrappedData = unwrappedData[dataRoot];
                        } else {
                            throw new Error('Failed to resolve data');
                        }

                        _.each(unwrappedData, function (nr) {
                            nr.associations = nr._links;
                            _.each(nr.associations, function (assoc, key) {
                                if (key === 'self') {
                                    nr.id = getIdFromSelfLink(assoc.href);
                                }
                            });
                            delete nr._links;
                        });

                        // append page information
                        unwrappedData['page'] = data.page;
                        unwrappedData['navigation'] = getPageNavigation(data._links);

                        return unwrappedData;
                    };

                return {
                    operations  : operations,
                    restLocation: location,
                    baseUrl     : baseUrl,
                    create      : function service(route) {
                        var instance = Restangular.withConfig(function configurer(RestangularConfigurer) {
                            RestangularConfigurer.setBaseUrl(
                                '{protocol}://{host}:{port}/{base}/'.format({
                                    port    : location.port,
                                    protocol: location.protocol,
                                    host    : location.host,
                                    base    : baseUrl
                                })
                            );
                            RestangularConfigurer.setRequestSuffix('/');
                            RestangularConfigurer.setRestangularFields({
                                selfLink: 'associations.self.href'
                            });
                            RestangularConfigurer.setErrorInterceptor(function errorInterceptor(response) {
                                if (response.status === 400) {
                                    var msg = response.data.message,
                                        statusText = response.statusText,
                                        url = response.config.url;

                                    var errMsg = 'Error occurred when accessing {url}\n\tStatus={s}\n\ttext={t}\n\tmessage={m}'.format({
                                        url: url,
                                        s  : response.status,
                                        t  : statusText,
                                        m  : msg
                                    });

                                    $log.error(errMsg);

                                    return false;
                                }
                                return true;
                            });
                        });

                        instance.addResponseInterceptor(function (data, operation, what, url, response, deffered) {
                            var self = this,
                                nestedInterceptors = self.nestedInterceptors,
                                newResponse = undefined;
                            try {
                                switch (operation) {
                                    case operations.GET_LIST:
                                        var embeddedData = data._embedded,
                                            keys = _.keys(embeddedData),
                                            dataRoot = keys[0];
                                        newResponse = handleGetList(data, dataRoot);
                                        break;
                                    case operations.GET:
                                        newResponse = handleSingleGet(data);
                                }
                            } catch (error) {
                                deffered.reject(error);
                            }
                            return nestedInterceptors[what] ? nestedInterceptors[what](newResponse) : newResponse;
                        }.bind(instance));

                        instance.getAll = function () {
                            return this.all(route).getList();
                        }.bind(instance);
                        instance.getOne = function (id) {
                            return this.one(route, id).get();
                        }.bind(instance);

                        // register nested interceptors
                        instance.addNestedInterceptors = function (hash) {
                            var self = this;
                            if (_.isUndefined(hash)) {
                                hash = {};
                            }
                            self.nestedInterceptors = hash;
                            return self;
                        }.bind(instance);

                        return instance;
                    }
                };

            };

        app.factory('baseRestResource', resource);
    }
);