define(
    [
        'angular',
        // module deps
        './abstract.state'
    ],
    function (angular,
              abstractViewState) {
        "use strict";
        
        return angular.module('sg.app.index', [])
            .value('$anchorScroll', angular.noop) // EXP_1
            .config(['$stateBuilderProvider', registerRootState]);

        function registerRootState($stateBuilderProvider) {
            $stateBuilderProvider.state(abstractViewState);
        }
    }
    
    /**
     * EXP_1
     * hack to disable auto scrolling on hashchange because we're 
     * using ui-router to manage states, instead of the core angular 
     * router which cannot handle states discussion
     * on this here: https://github.com/angular-ui/ui-router/issues/110
     * /
);