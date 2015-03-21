angular
    .module('sg.common.directive')
    .factory('ControllerBoundDirective', _.constant(Class([], {
        bindToController: true,
        controllerAs    : 'vm',
        scope           : true,
        constructor     : function (cfg) {
            if (cfg.bindToController) {
                delete cfg.bindToController;
            }
            _.defaults(this, cfg);
            return this;
        }
    })));