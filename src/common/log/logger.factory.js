(function () {
    angular
        .module('sg.common.log')
        .service('loggerFactory', loggerFactory);
    /**
     * @ngdoc service
     * @name sg.common.log.loggerFactory
     * @constructs $log
     * @description
     *  loggerFactory is an abstraction layer over $log which exposes
     *  factory function and applies {@link sg.common.log.loggerLevels}
     *  values over particular functions defined by {@link $log} API.
     */
    function loggerFactory($log, loggerLevels) {

        var loggerCache = {};

        return function loggerFactoryBuilder(name) {

            if (loggerCache[name]) {
                return loggerCache[name];
            }

            var instance = $log.getInstance(name);

            loggerCache[name] = (function () {

                function sliceArgs(args) {
                    return _.chain(args)
                        .toArray()
                        .slice(1)
                        .value()
                }

                function logIfTrue(val, fn, args) {
                    if (val) {
                        fn.apply(this, sliceArgs(args));
                    }
                }

                return {
                    log  : _.wrap(instance.log, function (fn) {
                        logIfTrue.bind(this)(loggerLevels.LOG, fn, arguments);
                    }),
                    info : _.wrap(instance.info, function (fn) {
                        logIfTrue.bind(this)(loggerLevels.INFO, fn, arguments);
                    }),
                    warn : _.wrap(instance.warn, function (fn) {
                        logIfTrue.bind(this)(loggerLevels.WARN, fn, arguments);
                    }),
                    debug: _.wrap(instance.debug, function (fn) {
                        logIfTrue.bind(this)(loggerLevels.DEBUG, fn, arguments);
                    }),
                    error: _.wrap(instance.error, function (fn) {
                        logIfTrue.bind(this)(loggerLevels.ERROR, fn, arguments);
                    })
                };
            }());

            return loggerCache[name]
        }
    }
}());