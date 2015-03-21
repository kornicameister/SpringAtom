(function utilsModule() {
    angular.module('sg.common.utils', ['sg.common.log'])
        .constant('DEBUG_MODE', isDebug(undefined))
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
                isDebug      : isDebug,
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
        })
        .run(['$log', function ($log) {
            $log.getInstance('sg.common.utils').debug('sg.common.utils initialized...');
        }]);

    function urlParams(url) {
        url = url || window.location.href;
        if (!url || (url.indexOf("?") < 0 && url.indexOf("&") < 0)) {
            return {};
        }
        url = url.substr(0, url.indexOf('#'));
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

    function isDebug(url) {
        url = url || window.location.href;
        var params = urlParams(url);
        return params['debug'] || false;
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