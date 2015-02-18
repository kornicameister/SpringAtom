define(
    [
        'jsface',
        'lodash'
    ],
    function controllerBoundDirective(jsface, _) {
        "use strict";
        return Class([], {
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
        })
    }
);