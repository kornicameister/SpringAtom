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
        'angular'
    ],
    function stackTrace(angular) {
        var directive = function stackTraceDirective() {
            var linkMethod = function ($scope) {
                $scope.$watch('trace', function (argTrace) {
                    var stackTrace = argTrace || [],
                        trace = [];
                    angular.forEach(stackTrace, function (st) {
                        trace.push({
                            method: st['methodName'],
                            file  : st['fileName'],
                            class : st['className'],
                            native: st['nativeMethod'],
                            line  : st['lineNumber']
                        });
                    });
                    $scope.myTrace = trace;
                });
            };
            return {
                restrict   : 'E',
                scope      : {
                    trace: '='
                },
                templateUrl: '/static/sa/directives/stacktrace/stracktrace.jsp',
                link       : linkMethod
            }
        };
        return {
            name      : 'stacktrace',
            definition: directive
        }
    }
);
