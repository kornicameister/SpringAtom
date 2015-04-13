(function utilsModule() {
    angular
        .module('sg.common.utils', [])
    /**
     * @constant
     * @ngdoc object
     * @name DEBUG_MODE
     * @module sg.common.utils
     * @description
     *  Takes either <b>true</b> or <b>false</b> as value. If true
     *  application is considered to run under debug mode
     * @example
     *      curl http://my_host/app/?debug/#/
     *
     */
        .constant('DEBUG_MODE', isDebug())
    /**
     * @constant
     * @ngdoc object
     * @name LOG_LEVEL
     * @module sg.common.utils
     * @description
     *  LOG_LEVEL may take any value, but one of the following are considered:
     *  <ol>
     *      <li>info</li>
     *      <li>debug</li>
     *      <li>off</li>
     *  </ol>
     *  @example
     *      curl http://my_host/app/?logLevel=info/#/
     */
        .constant('LOG_LEVEL', getLogLevel())
        .run(['$rootScope', 'DEBUG_MODE', function ($scope, isDebug) {
            $scope['DEBUG_MODE'] = isDebug;
        }])
        .service('sgUtils', function () {
            return {
                /**
                 * Returns application URL. Format is: [protocol]://[hostname]/[appName]/
                 * @param $window window object
                 * @returns {string} href as string
                 */
                getAppUrl    : getAppUrl,
                getUrlParams : urlParams,
                urlDecode    : urlDecode,
                now          : now(), // to return actual function
                /**
                 * Simple utility to retrieve an <b>angular</b> module name
                 * @param module a module
                 * @returns {String}
                 */
                getModuleName: getModuleName,
                abstractFn   : abstractFn,
                emptyFn      : emptyFn,
                identityFn   : identityFn
            }
        });

    /**
     * Establishes current log level, at the moment of script being loaded,
     * If logLevel is not set and debug flag is not set log level is set to <b>info</b>.
     * Otherwise logLevel get value takes precedence over debug flag.
     *
     * @returns {string} log level value
     * @see isDebug
     */
    function getLogLevel() {
        var url = window.location.href,
            params = urlParams(url),
            isDebug = isDebug(url);

        if (params['logLevel']) {
            return params.logLevel.toLowerCase()
        }
        return isDebug ? 'debug' : 'info';
    }

    function urlParams(url) {
        url = url || window.location.href;
        if (!url || (url.indexOf("?") < 0 && url.indexOf("&") < 0)) {
            return {};
        }
        if (url.indexOf('#') > -1) {
            url = url.substr(0, url.indexOf('#'));
        }
        var params = url.substr(url.indexOf("?") + 1);
        return urlDecode(params);
    }

    function urlDecode(string, overwrite) {
        var obj = {},
            pairs = string.split('&'),
            name,
            value;
        angular.forEach(pairs, function (pair) {
            pair = pair.split('=');
            name = decodeURIComponent(pair[0]);
            value = decodeURIComponent(pair[1]);
            obj[name] = overwrite || !obj[name] ? value : [].concat(obj[name]).concat(value);
        });
        return obj;
    }

    /**
     * Determines if application is under <b>debug</b> mode.
     * Returns <b>true</b> if param <i>debug</i> is defined among request parameters
     * and have one of the following values : [true,1,yes], otherwise debug mode
     * is considered to be false
     *
     * @returns {boolean} true if debug mode enabled, false otherwise
     */
    function isDebug() {
        var params = urlParams(window.location.href),
            value;
        if (params['debug']) {
            value = params['debug'];
            if (value) {
                value = value.toLowerCase();
                switch (value) {
                    case '1':
                    case 'true':
                    case 'yes':
                        return true
                }
            }
        }
        return false;
    }

    function getAppUrl() {
        var href = window.location.href.split('/');
        return href[0] + '//' + href[2] + '/' + href[3] + '/';
    }

    function now() {
        return (window['performance'] && window['performance']['now']) ?
            function () {
                return window['performance']['now']();
            } :
            (Date.now || (Date.now = function () {
                return +new Date();
            }))
    }

    function identityFn(arg) {
        return arg
    }

    function abstractFn() {
        var msg = 'abstractFn must be redefined';
        if (isDebug()) {
            alert(msg)
        } else {
            throw new Error(msg);
        }
    }

    function emptyFn() {
    }

    function getModuleName(module) {
        return module.name;
    }
}());