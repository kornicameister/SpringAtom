angular
    .module('sg.common.log')
    .constant('LOG_PRIORITY', {
        'info' : 0,
        'log'  : 0,
        'debug': 1
    })
/**
 * @ngdoc object
 * @name sg.common.log.loggerLevels
 * @description Provides set of properties informing what log levels are enabled/disabled
 */
    .factory('loggerLevels', function (DEBUG_MODE, LOG_LEVEL, LOG_PRIORITY) {

        var logLevel = _.clone(LOG_LEVEL),
            logPriority = LOG_PRIORITY,
            maxPriority = logPriority[_.takeRight(_.keys(logPriority), 1)],
            isOff,
            priority;

        if (_.isEmpty(logLevel)) {
            logLevel = 'info'
        } else {
            logLevel = logLevel.logLevel.toLowerCase();
        }

        isOff = isLoggingOff();
        if (!isOff) {
            priority = logPriority[logLevel]
        }

        // wrap properties in _.constant to ensure they are fully immutable and encapsulated

        return {
            /**
             * @ngdoc property
             * @propertyof sg.common.log.loggerLevels
             * @name sg.common.log.loggerLevels.LOG
             * @description
             *  If true, <b>logLevel=info</b> level is enabled
             */
            LOG  : _.constant(isInfoEnabled())(),
            /**
             * @ngdoc property
             * @propertyof sg.common.log.loggerLevels
             * @name sg.common.log.loggerLevels.INFO
             * @description
             *  If true, <b>logLevel=info</b> level is enabled
             */
            INFO : _.constant(isInfoEnabled())(),
            /**
             * @ngdoc property
             * @propertyof sg.common.log.loggerLevels
             * @name sg.common.log.loggerLevels.DEBUG
             * @description
             *  If true, <b>logLevel=debug</b> level is enabled.
             *  This can be set to true also if {@link DEBUG_MODE} is true.
             */
            DEBUG: _.constant(isDebugEnabled())(),
            /**
             * @ngdoc property
             * @propertyof sg.common.log.loggerLevels
             * @name sg.common.log.loggerLevels.WARN
             * @description
             *  If true, <b>logLevel=warn</b> level is enabled.
             *  This log level is disabled only if <b>LOG_LEVEL</b> took
             *  one of the following values:
             *  <ol>
             *      <li>negative number</li>
             *      <li>none</li>
             *      <li>off</li>
             *  </ol>
             *
             */
            WARN : _.constant(!isOff)(),
            /**
             * @ngdoc property
             * @propertyof sg.common.log.loggerLevels
             * @name sg.common.log.loggerLevels.ERROR
             * @description
             *  If true, <b>logLevel=log</b> level is enabled.
             *  This particular log level is always enabled
             */
            ERROR: _.constant(true)() // always keep errors enabled
        };

        function isInfoEnabled() {
            return !isOff && (logLevel === 'info' || priority >= maxPriority);
        }

        function isDebugEnabled() {
            return !isOff && (DEBUG_MODE && priority >= maxPriority);
        }

        function isLoggingOff() {
            return logLevel === 'off'
                || logLevel === 'none'
                || 0 > getLLAsInt();
        }

        function getLLAsInt() {
            try {
                return parseInt(logLevel);
            } catch (err) {
                //ignore
            }
            return 0;
        }
    })
    .run(function (loggerLevels, DEBUG_MODE) {
        if (DEBUG_MODE) {
            console.log('LoggerLevels =>' + loggerLevels);
        }
    });