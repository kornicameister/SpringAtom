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
 * Created by trebskit on 2014-08-25.
 */
define(
    [
        'config/module'
    ],
    function actionButtons(app) {
        var commonConfiguration = {
                restrict: 'E',
                scope   : {},
                replace : true,
                link    : function (scope, el, attrs) {
                    scope.title = attrs.title;
                }
            },
            addButtonDirective = function addButtonDirective() {
                return angular.extend({
                    templateUrl: '/cmp/button/add'
                }, commonConfiguration);
            },
            removeButtonDirective = function removeButtonDirective() {
                return angular.extend({
                    templateUrl: '/cmp/button/remove'
                }, commonConfiguration);
            },
            getAddButtonTpl = function getAddButtonTpl() {
                var local = [];
                local.push('<button class="btn-default btn-info btn-link btn" role="button" title="{{title}}">');
                local.push('<i class="fa fa-fw fa-inverse fa-plus"></i>');
                local.push('</button>');
                return local.join('');
            },
            getRemoveButtonTpl = function getRemoveButtonTpl() {
                var local = [];
                local.push('<button class="btn-default btn-info btn-link btn" role="button" title="{{title}}">');
                local.push('<i class="fa fa-fw fa-inverse fa-minus"></i>');
                local.push('</button>');
                return local.join('');
            },
            prepareTemplates = function ($templateCache) {
                $templateCache.put('/cmp/button/add', getAddButtonTpl());
                $templateCache.put('/cmp/button/remove', getRemoveButtonTpl());
            };

        // set up templates
        app.run(prepareTemplates);

        return [
            {
                name      : 'addButton',
                definition: addButtonDirective
            },
            {
                name      : 'removeButton',
                definition: removeButtonDirective
            }
        ]
    }
);
