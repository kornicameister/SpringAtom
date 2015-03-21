(function () {
    angular
        .module('sg.common.log')
        .config(['$provide', logDecorator]);

    function logDecorator($provide) {
        $provide.decorator('$log', ["$delegate", function ($delegate) {
            enhanceLoggerFn($delegate, '::');
            return $delegate;
        }]);
    }

    function enhanceLoggerFn($log, separator) {
        var _$log = (function ($log) {
                return {
                    log  : $log.log,
                    info : $log.info,
                    warn : $log.warn,
                    debug: $log.debug,
                    error: $log.error
                };
            }($log)),
            supplant = ('{ts} - {cn}{msg}');

        $log.getInstance = getInstance;

        return $log;

        function colorify(message, colorCSS) {
            var isChrome = bowser.chrome || bowser.phantomjs,
                canColorize = isChrome && (colorCSS !== undefined);

            return canColorize ? ["%c" + message, colorCSS] : [message];
        }

        function prepareLogFn(logFn, className, colorCSS) {
            /**
             * Invoke the specified `logFn` with the supplant functionality...
             */
            var enhancedLogFn = function () {
                try {
                    var args = Array.prototype.slice.call(arguments),
                        now = moment().format('DD.MM.YYYY@HH:mm:ss');

                    // prepend a timestamp and optional classname to the original output message
                    args[0] = _.format(supplant, {
                        ts : now,
                        cn : className,
                        msg: args[0]
                    });
                    args = colorify(args[0], colorCSS);

                    logFn.apply(null, args);
                }
                catch (error) {
                    $log.error("LogEnhancer ERROR: " + error);
                }

            };

            // Only needed to support angular-mocks expectations
            enhancedLogFn.logs = [];

            return enhancedLogFn;
        }

        function getInstance(className, colorCSS, customSeparator) {
            className = (className !== undefined) ? className + (customSeparator || separator) : "";

            var instance = {
                log  : prepareLogFn(_$log.log, className, colorCSS),
                info : prepareLogFn(_$log.info, className, colorCSS),
                warn : prepareLogFn(_$log.warn, className, colorCSS),
                debug: prepareLogFn(_$log.debug, className, colorCSS),
                error: prepareLogFn(_$log.error, className)
            };

            _.forEachRight(instance, function (fn, key) {
                instance[key] = _.wrap(fn, function (func) {
                    var args = _.toArray(arguments).slice(1);
                    func(args);
                    return instance;
                })
            });

            if (angular.isDefined(angular.makeTryCatch)) {
                // Attach instance specific tryCatch() functionality...
                instance.tryCatch = angular.makeTryCatch(instance.error, instance);
            }

            return instance;
        }
    }
}());