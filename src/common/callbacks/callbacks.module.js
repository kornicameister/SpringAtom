(function callbacks() {
    angular.module('sg.common.callbacks', [])
        .service('sgCallbacks', ['$log', sgCallbacks]);

    function sgCallbacks() {
        var service = {};

        service.cancelEvent = function (func) {
            return _.wrap(func, cancelEvent)
        };
        service.skipAbstractState = function (func) {
            return _.wrap(func, skipAbstractState)
        };

        return service;
    }

    function skipAbstractState(func) {
        var args = _.toArray(arguments).slice(1),
            state = args[1];
        if (!state.abstract) {
            func.apply(this, args);
        }
        return true;
    }

    function cancelEvent(func) {
        var args = _.toArray(arguments).slice(1);
        func.apply(this, args);
        if (args.length > 0 && args[0] && args[0].preventDefault) {
            args[0].preventDefault();
        }
        return false;
    }
}());