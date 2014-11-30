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
        'classes/cmp/component',
        'classes/cmp/ip/panel',
        'jsface'
    ],
    function (Component, InfoPagePanel) {
        return Class(Component, function () {

            var getPanels = function _getPanels(content, data) {
                var panels = [];
                angular.forEach(content, function (panel) {
                    panels.push(new InfoPagePanel(panel, data));
                });
                return panels;
            };

            return {
                constructor: function (config, data) {
                    this.id = config.id;
                    this.data = data;
                    this.panels = getPanels(config['content'], data);
                    this.$class.$super.call(this, config);    // Invoke parent's constructor
                },
                /**
                 * Return is of the InfoPage (i.e. config['id'])
                 * @return {string} the id
                 */
                getId      : function _getId() {
                    return this.id;
                },
                /**
                 * Returns panels defined in this infopage
                 * @return {panels|*}
                 */
                getPanels  : function _getPanels() {
                    return this.panels;
                }
            }
        });
    }
);
