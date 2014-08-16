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
 * Created by trebskit on 2014-08-11.
 */
define(
    [
        'config/module',
        'utils',
        // states
        'states/navigator',
        'states/ip',
        'states/wizards'
        // states
    ],
    function configStates(app, utils) {
        'use strict';

        var otherwiseRoute = '/sa',
            offset = 2,
            states = (function getStates(states) {
                var local = [],
                    it = offset;
                for (it; it < states.length; it++) {
                    local.push(states[it]);
                }
                return local;
            }(arguments)),
            _register = function localRegister($urlRouterProvider, states) {
                var me = this;
                if (!angular.isDefined(states)) {
                    var msg = 'States are not defined, can not initialize';
                    if (utils.isDebug()) {
                        alert(msg);
                    } else {
                        throw new Error(msg)
                    }
                }
                if (states instanceof Array && states.length > 0) {
                    var i;
                    for (i = 0; i < states.length; i++) {
                        _register.call(me, $urlRouterProvider, states[i]);
                    }
                } else if (states.name && states.definition) {
                    me.state(states.name, states.definition);
                } else if (states.rule) {
                    $urlRouterProvider.when(states.rule.when, states.rule.then);
                }
            },
            configureStateListeners = function ($rootScope, $cookies) {
                var onStateChangeStart = function onStateChangeStart(event, toState, toParams, fromState) {
                    var lastState = fromState.name === '' ? 'home' : fromState.name,
                        currentState = toState.name;
                    if (lastState === currentState) {
                        // do not save to cookie if last state is the same as current state
                        return;
                    }
                    $cookies.lastState = lastState;
                    $cookies.currentState = currentState;
                };
                $rootScope.$on('$stateChangeStart', onStateChangeStart)
            };
        return {
            configure: function () {
                app.config(function localConfigure($stateProvider, $urlRouterProvider) {
                    _register.call($stateProvider, $urlRouterProvider, states);
                    $urlRouterProvider.otherwise(otherwiseRoute);
                });
                app.run(['$rootScope', '$cookies', configureStateListeners]);
            }
        };
    }
);
