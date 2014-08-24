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
        'angular',
        'jsface'
    ],
    function feedbackMessage(angular) {
        var feedbackTypes = {
            CONFIRMATION: 2,
            SUCCESS     : 1,
            FAILURE     : 4,
            ERROR       : 5,
            INFO        : 0,
            WARNING     : 3
        };
        return Class(function FeedbackMessage() {
            return {
                constructor          : function (msg) {
                    this.message = msg.message;
                    this.title = msg.title;
                    this.type = msg.type;
                    if (!angular.isDefined(this.type)) {
                        this.type = 'INFO';
                    }
                },
                getMessage           : function getMessage() {
                    return this.message;
                },
                getTitle             : function getTitle() {
                    return this.title;
                },
                getType              : function getType() {
                    return this.type;
                },
                getSeverity          : function getSeverity() {
                    return feedbackTypes[this.type];
                },
                asDialogConfiguration: function asDialogConfiguration() {
                    return {
                        header: this.title,
                        body  : this.message
                    }
                }
            }
        });
    }
);
