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
        'config/module'
    ],
    function wizardService(app) {
        var directions = {
                NEXT: 1,
                PREV: -1
            },
            getNext = function getNext(step) {
                return step.next || false;
            },
            getPrev = function getPrev(step) {
                return step.prev || false;
            },
            canGo = function canGo(from, to) {
                return (from.next || false) === to.state;
            },
            prefix = '/sa/wizard{url}';

        return {
            getNext       : getNext,
            getPrev       : getPrev,
            canGo         : canGo,
            directions    : directions,
            getUrl        : function getUrl(url, localPrefix) {
                if (!angular.isDefined(localPrefix)) {
                    localPrefix = prefix;
                }
                return localPrefix.format({ url: url });
            },
            getSubStateUrl: function getSubStateUrl(nextUrl, url) {
                return '{a}{b}'.format({
                    a: url,
                    b: nextUrl
                })
            },
            getState      : function getState(parent, state) {
                return '{p}.{s}'.format({ p: parent, s: state });
            }
        };
    }
);
