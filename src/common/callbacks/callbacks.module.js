(function callbacks() {
    angular.module('sg.common.callbacks', ['sg.common.log'])
        .service('sgCallbacks', ['$log', sgCallbacks]);

    function sgCallbacks($log) {
        var service = {},
            logger = $log.getInstance('sgCallbacks');

        service.cancelEvent = function (func) {
            logger.debug(_.format('cancelEvent(func={func})', {func: func}));
            _.wrap(func, cancelEvent)
        };

        return service;
    }

    function cancelEvent(func) {
        var args = _.toArray(arguments).slice(1);
        func(args);
        if (args.length > 0 && args[0].preventDefault) {
            args[0].preventDefault();
        }
        return false;
    }
}());