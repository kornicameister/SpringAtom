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
            configureLang = function (amMoment, $cookies, $translate) {
                var params = utils.getUrlParams(),
                    lang = defaultLang;
                if (angular.isDefined(params['lang'])) {
                    lang = params['lang'];
                }
                var cookiesLangDefined = angular.isDefined($cookies.lang);
                if (cookiesLangDefined) {
                    if ($cookies.lang !== lang) {
                        $cookies.lang = lang;
                    } else {
                        lang = $cookies.lang;
                    }
                } else {
                    $cookies.lang = lang;
                }
                amMoment.changeLanguage(lang);
                $translate.use(lang);
            },
            dialogsTranslations = function ($translateProvider) {
                // TODO add reading those values from a server
                //var translations = {
                //    DIALOGS_ERROR            : "Błąd",
                //    DIALOGS_ERROR_MSG        : "Wystąpił nieznany błąd",
                //    DIALOGS_CLOSE            : "Zamknij",
                //    DIALOGS_PLEASE_WAIT      : "Proszę czekać",
                //    DIALOGS_PLEASE_WAIT_ELIPS: "Proszę czekać...",
                //    DIALOGS_PLEASE_WAIT_MSG  : "Operacja w trakcie, proszę czekać...",
                //    DIALOGS_PERCENT_COMPLETE : "% zakończono",
                //    DIALOGS_NOTIFICATION     : "Powiadomienie",
                //    DIALOGS_NOTIFICATION_MSG : "Nieznane powiadomienie...",
                //    DIALOGS_CONFIRMATION     : "Potwierdzenie",
                //    DIALOGS_CONFIRMATION_MSG : "Wymagane potwierdzenie",
                //    DIALOGS_OK               : "Ok",
                //    DIALOGS_YES              : "Tak",
                //    DIALOGS_NO               : "Nie"
                //};

                //noinspection JSUnresolvedFunction
                $translateProvider
                    .preferredLanguage(window['SA_LANG']);
                $translateProvider.useSanitizeValueStrategy('escaped');
                //noinspection JSUnresolvedFunction
                $translateProvider.useUrlLoader('/app/rest/locales');
                //noinspection JSUnresolvedFunction
                $translateProvider.useLocalStorage();
                //noinspection JSUnresolvedFunction
                $translateProvider.useMissingTranslationHandlerLog();

            };

        module.run(configureLang)
            .config(['$translateProvider', dialogsTranslations])
    }
);
