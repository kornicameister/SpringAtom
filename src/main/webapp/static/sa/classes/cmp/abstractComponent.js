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
        'jsface'
    ],
    function abstractComponent() {
        var label = 'label',
            dynamicProps = 'dynamicProperties';
        return Class({
            constructor      : function (config) {
                this.label = config[ label];
                this.metaProperties = config[  dynamicProps] || {};
            },
            getLabel         : function getLabel() {
                return this.label;
            },
            getMetaProperties: function getDebugProps() {
                return this.metaProperties;
            }
        });
    }
);
