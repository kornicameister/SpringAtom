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
 * Created by kornicameister on 18.07.14.
 */
(function SpringAtom_Components() {

	"use strict";

	var templates = {
		iconDirectiveTemplate: function _iconDirectiveTemplate() {
			var iconDirective;
			if (!iconDirective) {
				var array = [];
				array.push('<span>');
				array.push('<i ng-show="configuration.cls" ng-class="configuration.cls">&nbsp;</i>');
				array.push('<img ng-show="configuration.path" ng-src="configuration.path">&nbsp;</i>');
				array.push('</span>');
				iconDirective = array.join('');
				templates.iconDirectiveTemplate = function () {
					return iconDirective;
				}
			}
			return iconDirective;
		}
	};

	var app = angular.module('springatom.component', []),
		defineAbstractComponent = function defineAbstractComponent() {
			return Class({
				constructor      : function (config) {
					this.label = config['label'];
					this.metaProperties = config['dynamicProperties'] || {};
				},
				getLabel         : function getLabel() {
					return this.label;
				},
				getMetaProperties: function getDebugProps() {
					return this.metaProperties;
				}
			});
		},
		defineComponentClass = function defineComponentClass(AbstractComponent) {
			return Class(AbstractComponent, function () {
				return {
					constructor: function (config) {
						this.position = config['position'];
						this.$class.$super.$super.call(this, config);    // Invoke parent's constructor
					},
					getPosition: function getPosition() {
						return this.position;
					}
				}
			});
		},
		iconDirective = function iconDirective() {

			return {
				restrict: 'E',
				scope   : {
					configuration: '='
				},
				template: templates.iconDirectiveTemplate()
			}
		};

	app.factory('AbstractComponent', defineAbstractComponent)
		.factory('Component', ['AbstractComponent', defineComponentClass])
		.directive('icon', iconDirective)

}());