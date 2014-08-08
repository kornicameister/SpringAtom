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
 * Created by trebskit on 2014-07-30.
 */

(function SpringAtom_Components_Menus() {

	var app = angular.module('springatom.component.menu', ['springatom.component']),
		dropdownMenu = function dynamicDropdownMenu($log, navigationService) {
			$log.log('dynamicDropdownMenu-directive in progress...');

			// TODO extract to template in JSP file
			// recognize logout as none route action
			// check wizard actions
			function getTemplate() {
				var array = [];
				array.push('<ul class="dropdown-menu" ng-show="data" role="menu">');
				array.push('<li ng-repeat="action in data">');
				array.push('<a id="{{action.id}}" ui-sref="{{action.name}}" role="{{action.mode}}">');
				array.push('<i ng-show="action.iconClass" ng-class="action.iconClass"></i>');
				array.push('{{action.label}}');
				array.push('</a></li></ul>');
				return array.join(' ');
			}

			function link(scope, element, attrs) {
				var actionModel = attrs['actionmodel'],
					timeout = attrs['timeout'] || 100;

				$log.debug('link(actionModel={ac},timeout={t})'.format({
					ac: actionModel,
					t : timeout
				}));

				scope.btnLabel = attrs['btnlabel'];
				scope.btnClass = attrs['btnclass'];

				scope.hook = {};
				scope.hook.launchWizard = function (event, am) {
					event.preventDefault();
					var id = am.id;
					$log.debug('hook.launchWizard(id={i},wiz={w})'.format({i: id, w: am['wizardName']}));
					Spring.remoting.getLinkedResource(id, {
						popup: true,
						mode : 'embedded'
					}, true);
				};

				setTimeout(function onTimeout() {
					navigationService
						.loadNavigation(actionModel)
						.then(function onSuccess(data) {
							scope.data = data;
						}, function onError() {
							scope.data = 'ERROR: failed to load data';
						})
				}, timeout);
			}

			return {
				restrict   : 'E',
				templateUrl: '/app/cmp/nav/dropdown-menu',
				scope      : {},
				replace    : true,
				link       : link
			}
		},
		menuItemDirective = function menuItemDirective() {
			return {
				restrict  : 'E',
				transclude: true
			}
		};

	app.directive('dropdownMenu', ['$log', 'navigationService', dropdownMenu])
		.directive('dropdownMenuItem', ['$log', menuItemDirective])

}());