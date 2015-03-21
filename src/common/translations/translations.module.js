angular
    .module('sg.common.translations', [
        'pascalprecht.translate',
        'sg.common.translations.pl',
        'sg.common.log'
    ])
    .constant('BROWSER_LANGUAGE', navigator.language || navigator.userLanguage)
    .config(['$translateProvider', 'BROWSER_LANGUAGE', function ($translateProvider,
                                                                 BROWSER_LANGUAGE) {
        $translateProvider.fallbackLanguage('pl');
        $translateProvider.preferredLanguage('pl');
        $translateProvider.use(BROWSER_LANGUAGE);
        $translateProvider.useSanitizeValueStrategy('escaped');
        $translateProvider.useMissingTranslationHandlerLog();
    }]);