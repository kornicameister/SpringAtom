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
 * Created by trebskit on 2014-08-13.
 */
define(
    [
        'config/module',
        'utils',
        'angular-moment'
    ],
    function langModule(module, utils) {
        var defaultLang = 'pl',
            configureLang = function (amMoment) {
                var params = utils.getUrlParams(),
                    lang = defaultLang;
                if (angular.isDefined(params['lang'])) {
                    lang = params['lang'];
                    if (lang.indexOf('_') > 0) {
                        lang = lang.substring(0, lang.indexOf('_'));
                    }
                }
                amMoment.changeLanguage(lang);
            };

        module.run(configureLang);
    }
);
