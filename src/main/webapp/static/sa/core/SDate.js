/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

/**
 * @class SC.core.SDate
 * SDate is a convenient wrapper around Date class from standard JavaScript.
 * It wraps around {@link Ext.Date} and adds some custom functionality.
 */
Ext.define('SC.core.SDate', function (SD) {
    var units = {
        DAY  : 1000,
        MONTH: 1000 * 60,
        YEAR : 1000 * 60 * 60
    };
    return {
        singleton    : true,
        /**
         * @default true
         * @type Boolean
         * @property use24HourTime
         */
        use24HourTime: true,
        statics      : {
            UNIT: SD.units
        },
        /**
         * Returns the time duration between two dates in the specified unit.
         * @param start start date
         * @param end end date
         * @param {Number} unit (optional). Use one of the {@link SC.core.SDate.statics.UNIT}. If none is provided,
         * therefore it is assumed that number of 'ms' will be returned.
         * @returns {Number} time difference between given dates
         */
        diff         : function (start, end, unit) {
            var denominator = unit || 1,
                difference = end.getTime() - start.getTime();

            if (denominator !== units.DAY || denominator !== units.MONTH || denominator !== units.YEAR) {
                denominator = 1;
            }

            return Math.round(difference / denominator);
        }
    }
});
