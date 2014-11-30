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
 * Created by trebskit on 2014-08-24.
 */
define(
    [
        'config/module',
        'utils'
    ],
    function (app) {
        app.factory(
            'stateLoadingService',
            ['$rootScope', '$timeout', 'ngProgress', '$cookies', 'timeoutDelay',
                function ($rootScope, $timeout, ngProgress, $cookies, timeoutDelay) {
                    var stateChangeErrorHandler = function stateChangeErrorHandler() {
                        },
                        stateChangeStartHandler = function stateChangeStartHandler() {
                            ngProgress.start();
                        },
                        stateChangeSuccessHandler = function stateChangeSuccessHandler() {
                            ngProgress.complete();
                            setTimeout(function resetAfterTimeout() {
                                ngProgress.reset();
                            }, timeoutDelay)
                        },
                        stateChangeStartCookieHandler = function stateChangeStartCookieHandler(event,
                                                                                               toState,
                                                                                               toParams,
                                                                                               fromState) {
                            var lastState = fromState.name === '' ? 'home' : fromState.name,
                                currentState = toState.name;
                            if (lastState === currentState) {
                                // do not save to cookie if last state is the same as current state
                                return;
                            }
                            $cookies.lastState = lastState;
                            $cookies.currentState = currentState;
                        };

                    // return empty service, do not want this service to expose any public API
                    return {
                        init: function () {
                            $rootScope.$on('$stateChangeSuccess', stateChangeSuccessHandler);
                            $rootScope.$on('$stateChangeStart', stateChangeStartHandler);
                            $rootScope.$on('$stateChangeSuccess', stateChangeStartCookieHandler);
                            $rootScope.$on('$stateChangeError', stateChangeErrorHandler);
                        }
                    };
                }
            ]
        );
    }
);
