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
 * Created by trebskit on 2014-08-13.
 */
define(
    [
        'config/module',
    ],
    function pageHelper(app) {
        var defaultPageSize = 25,
            /**
             * Returns the sizes of the pages.
             * Ideally it should be server configured for the first call and then stored locally in the script
             * @private
             */
            getPageSizes = function _getPageSizes() {
                var step = 5,
                    arr = [],
                    it = 1;
                while (it !== step) {
                    arr.push(it * defaultPageSize);
                    it += 1;
                }
                return arr;
            },
            /**
             * Returns default page size
             * @returns {number} default page size
             * @private
             */
            getDefaultPageSize = function _getDefaultPageSize() {
                return defaultPageSize;
            },
            /**
             * Returns initial page for the grid
             * @returns {{page: number, limit: number}}
             * @private
             */
            getInitialPage = function _getInitialPage() {
                return {
                    page : 1,
                    limit: defaultPageSize
                }
            },
            /**
             * Service definition method
             * @returns {*} service definition
             */
            gridPageHelper = function gridPageHelper() {
                return {
                    getPageSizes      : getPageSizes,
                    getDefaultPageSize: getDefaultPageSize,
                    getInitialPage    : getInitialPage
                }
            },
            serviceName = 'gridPageHelper';

        app.factory(serviceName, gridPageHelper);
    }
);
