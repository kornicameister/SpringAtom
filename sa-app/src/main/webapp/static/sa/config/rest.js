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
 * Created by trebskit on 2014-09-03.
 */
define(
    [
        'config/module',
        'restangular'
    ],
    function rest(app) {
        var configureRest = function configureRest(RestangularProvider) {
            RestangularProvider.setBaseUrl('/');
            RestangularProvider.setExtraFields([
                '_links',
                '_embedded',
            ]);
            RestangularProvider.setDefaultHttpFields({
                cache: true
            });
            RestangularProvider.setDefaultHeaders({
                token: "sa-restangular"
            });
        };
        return {
            configure: function configureRestAngular() {
                app.config(configureRest);
            }
        }
    }
);