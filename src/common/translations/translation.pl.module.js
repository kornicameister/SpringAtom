angular
    .module('sg.common.translations.pl', ['pascalprecht.translate']);
(function () {
    angular.module('sg.common.translations.pl').config(['$translateProvider', configureTranslations]);

    function configureTranslations($translateProvider) {
        $translateProvider.translations('pl', getTranslationsMap([
            getButtons(),
            getCommon(),
            getDialogs(),
            getStates(),
            getModelObject()
        ]));
    }

    function getTranslationsMap(translations) {
        var pl = {};
        _.forEachRight(translations, function (tr) {
            pl = _.merge(pl, tr);
        });
        return pl;
    }

    function getStates() {
        "use strict";
        return {
            'sg.home'               : 'Strona główna',
            'sg.about'              : 'O SpringAtom',
            'sg.admin'              : 'Panel administracyjny',
            'sg.dashboard'          : 'Panel sterowania',
            'sg.dashboard.calendar' : 'Kalendarz',
            'sg.dashboard.cars'     : 'Samochody',
            'sg.dashboard.clients'  : 'Klienci',
            'sg.dashboard.mechanics': 'Mechanicy',
            'sg.dashboard.lifts'    : 'Stanowiska',
            'sg.dashboard.reports'  : 'Raporty'
        }
    }

    function getDialogs() {
        return {
            // login dialog
            'login.popup.title'     : 'Zaloguj się',
            'login.label.username'  : 'Login',
            'login.label.password'  : 'Hasło',
            'login.label.rememberMe': 'Zapamiętaj mnie',
            // logout dialog
            'logout.popup.title'    : 'Wyloguj się',
            'logout.popup.msg'      : 'Za chwilę zostaniesz wylogowany z aplikacji'
        }
    }

    function getButtons() {
        "use strict";
        return {
            'button.ok'            : 'Ok',
            'button.cancel'        : 'Anuluj',
            'button.exit'          : 'Wyjdź',
            'button.login'         : 'Zaloguj się',
            'button.logout'        : 'Wyloguj się',
            'button.register'      : 'Rejestracja',
            'button.password.reset': 'Resetuj hasło'
        }
    }

    function getCommon() {
        "use strict";
        return {
            'goto.state': 'Kliknij aby przejść'
        };
    }

    function getModelObject() {
        return {
            // car
            'model.car.licencePlate': 'Rejestracja',
            'model.car.vin'         : 'Vin',
            'model.car.owner'       : 'Właściciel'
        }
    }
}());