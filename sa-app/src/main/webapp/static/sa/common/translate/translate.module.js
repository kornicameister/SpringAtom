define(
    [
        'angular',
        'ngTranslate',
        'ngTranslateLoader',
        'ngTranslateLocalStorage',
        'ngTranslateHandlerLog'
    ],
    function sgTranslateModule(angular) {
        "use strict";
        return angular.module('sg.translate', ['pascalprecht.translate']);
    }
);