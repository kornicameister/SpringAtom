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
        'jsface'
    ],
    function validationMessage() {
        var severityTypes = {
            FATAL  : 3,
            ERROR  : 2,
            INFO   : 0,
            WARNING: 1
        };
        return Class(Object, {
            constructor          : function (msg) {
                this.source = msg.source;
                this.text = msg.text;
                this.severity = msg.severity;
                if (!this.severity || this.severity === '') {
                    this.severity = 'INFO';
                }
            },
            getText              : function getText() {
                return this.text;
            },
            getSource            : function getSource() {
                return this.source;
            },
            getSeverity          : function getSeverity() {
                return this.severity;
            },
            getSeverityWeight    : function getSeverityWeight() {
                return severityTypes[this.severity];
            },
            asDialogConfiguration: function asDialogConfiguration() {
                return {
                    header: this.severity,
                    body  : this.text,
                    source: this.source
                }
            }
        });
    }
);
