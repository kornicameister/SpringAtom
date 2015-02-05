define(
    [
        'angular',
        // module deps
        './abstract.state'
        './header/header.controller'
    ],
    function (angular,
              rootViewState,
              headerCtrl) {
        "use strict";
        
        return angular.module('sg.app.home', [])
    }
    
    /**
     * EXP_1
     * hack to disable auto scrolling on hashchange because we're 
     * using ui-router to manage states, instead of the core angular 
     * router which cannot handle states discussion
     * on this here: https://github.com/angular-ui/ui-router/issues/110
     * /
);