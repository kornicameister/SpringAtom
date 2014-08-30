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
 * Created by trebskit on 2014-08-14.
 */
define(
    [
        'services/navigation',
        'resources/wizardResource'
    ],
    function wizardService() {
        var directions = {
                NEXT: 1,
                PREV: -1
            },
            /**
             * List of all methods that single provider needs to have defined
             * @type {{}}
             */
            providerMap = [
                'getFormData',
                'isActionEnabled',
                'getStepSubmissionData',
                'getSubmissionData'
            ],
            getNext = function getNext(step) {
                return step.next || false;
            },
            getPrev = function getPrev(step) {
                return step.prev || false;
            },
            canGo = function canGo(from, to) {
                return (from.next || false) === to.state;
            },
            getUrl = function getUrl(url, localPrefix) {
                if (!angular.isDefined(localPrefix)) {
                    localPrefix = prefix;
                }
                return localPrefix.format({ url: url });
            },
            getSubStateUrl = function getSubStateUrl(nextUrl, url) {
                return '{a}{b}'.format({
                    a: url,
                    b: nextUrl
                })
            },
            getState = function getState(parent, state) {
                return '{p}.{s}'.format({ p: parent, s: state });
            },
            /**
             * Evaluates {@code provider} against having all required methods
             * used further by wizardController.
             * @param provider
             */
            getProvider = function getProvider(provider) {
                angular.forEach(providerMap, function (prop) {
                    var providerProperty = provider[prop];
                    if (angular.isUndefined(providerProperty)) {
                        throw new Error('{prop} is not defined in the provider'.format({prop: prop}));
                    }
                    if (!angular.isFunction(providerProperty)) {
                        throw new Error('{prop} is defined but is not a function in the provider'.format({prop: prop}));
                    }
                });
                return provider;
            },
            getHandlerResolve = function getHandlerResolve(key) {
                return {
                    wizardHandler: key,
                    wizardKey    : function getWizardKey() {
                        return key;
                    }
                };
            },
            getResolve = function getResolve(serviceName) {
                return angular.extend(getHandlerResolve(serviceName), {
                    actions     : ['navigationService', function (navigationService) {
                        return navigationService.loadNavigation('wiz.navBar');
                    }],
                    wizardResult: ['wizardResource', function (wizardResource) {
                        return wizardResource.init(serviceName);
                    }]
                });
            },
            prefix = '/sa/wizard{url}';

        return {
            getNext           : getNext,
            getPrev           : getPrev,
            canGo             : canGo,
            directions        : directions,
            getUrl            : getUrl,
            getSubStateUrl    : getSubStateUrl,
            getState          : getState,
            getProvider       : getProvider,
            getResolve        : getResolve,
            getProviderResolve: getHandlerResolve
        };
    }
);
