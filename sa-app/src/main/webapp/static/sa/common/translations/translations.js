define(
    [
        'angular',
        './pl_PL',
        // angular dependencies
        'ngTranslate',
        'ngTranslateHandlerLog'
    ],
    function (angular, pl) {
        "use strict";

        return angular.module('sg.translations', ['pascalprecht.translate'])
            .constant('BROWSER_LANGUAGE', navigator.language || navigator.userLanguage)
            .config(['$translateProvider', 'BROWSER_LANGUAGE', configureTranslations]);

        function configureTranslations($translateProvider, BROWSER_LANGUAGE) {
            $translateProvider.translations('pl', pl);

            $translateProvider.fallbackLanguage('pl');
            $translateProvider.preferredLanguage('pl');
            $translateProvider.use(BROWSER_LANGUAGE);
            $translateProvider.useSanitizeValueStrategy('escaped');
            $translateProvider.useMissingTranslationHandlerLog();
        }
    }
);