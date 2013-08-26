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
Ext.define('SC.core.SDate', function () {
    /**
     *
     * @type {{DAY: number, MONTH: number, YEAR: number}}
     */
    var units = {
        DAY  : 1000,
        MONTH: 1000 * 60,
        YEAR : 1000 * 60 * 60
    };
    /**
     * @private
     * @cfg cfg object for calculating difference between dates for day,month,year unit
     * @type {{DAY_DIFF: string, MONTH_DIFF: string, YEAR_DIFF: string, diff: Function}}
     */
    var diffHelper = {
        DAY_DIFF  : 'days',
        MONTH_DIFF: 'months',
        YEAR_DIFF : 'years',
        /**
         * Calculates difference between two dates internally based on given @code unit
         * @param start start date
         * @param end  end date
         * @param unit on of the {@link diffHelper}
         * @returns {number}
         */
        diff      : function (start, end, unit) {
            var s = moment(start),
                e = moment(end);

            if (e.isBefore(s)) {
                s = moment(end);
                e = moment(start);
            }

            return e.diff(s, unit);
        }
    };
    /**
     * Wrapper around {@link Ext.Date.clearTime}
     * @param date data to be cloned
     * @param clone {Boolean} true if with cloning, false otherwise, defaults to false
     * @returns {Date|*}
     */
    var clearTime = function (date) {
        var clone = arguments[1];
        return Ext.Date.clearTime(date, clone || false);
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
            UNIT: units
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
        },
        diffDays     : function (start, end) {
            return diffHelper.diff(start, end, diffHelper.DAY_DIFF);
        },
        /**
         * Returns amount of full months between given dates
         * @param start start date
         * @param end  end date
         * @returns {Number} amount of months
         */
        diffMonths   : function (start, end) {
            return diffHelper.diff(start, end, diffHelper.MONTH_DIFF);
        },
        diffYears    : function (start, end) {
            return diffHelper.diff(start, end, diffHelper.YEAR_DIFF);
        },
        isWeekend    : function (date) {
            return date.getDay() % 6 === 0;
        },
        isWeekday    : function (date) {
            return date.getDay() % 6 !== 0;
        },
        isMidnight   : function (date) {
            return date.getHours() === 0 && date.getMinutes() === 0;
        },
        isToday      : function (date) {
            return this.diffDays(date, this.today()) === 0;
        },
        isLeapYear   : function (date) {
            return moment().isLeapYear(date);
        },
        today        : function () {
            return clearTime(new Date());
        }
    }
});
