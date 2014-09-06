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
 * Created by trebskit on 2014-09-07.
 */
define(
    [
        'config/module',
        'underscore',
        // filters
        'filters/booleanFilter'
    ],
    function (app, _) {
        var offset = 2,
            filters = (function getFilters(filters) {
                var local = [],
                    it = offset;
                for (it; it < filters.length; it++) {
                    local.push(filters[it]);
                }
                console.log('Read {d} filters'.format({d: local.length}));
                return local;
            }(arguments));

        return {
            configure: function () {
                _.each(filters, function (filter) {
                    app.filter(filter.name, filter.definition);
                })
            }
        }
    }
);