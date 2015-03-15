define(
    'common/utils',
    [
        'angular',
        'jsface'
    ],
    function utils(angular) {
        var $$window = window;

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
            now          : now(),
            toNgOptions  : toNgOptions,
            /**
             * Simple utility to retrieve an <b>angular</b> module name
             * @param module a module
             * @returns {String}
             */
            getModuleName: getModuleName,
            abstractFn   : abstractFn,
            emptyFn      : emptyFn,
            identityFn   : identityFn
        };

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
            var href = $window.location.href.split('/');
            return href[0] + '//' + href[2] + '/' + href[3] + '/';
        }

        function now() {
            return ($$window['performance'] && $$window['performance']['now']) ?
                function () {
                    return $$window['performance']['now']();
                } :
                (Date.now || (Date.now = function () {
                    return +new Date();
                }))
        }

        function toNgOptions(cfg) {
            if (angular.isArray(cfg)) {
                cfg = {
                    data: cfg
                };
            }
            var data = cfg.data,
                localData = [],
                labelMethod = cfg.getLabel,
                valueMethod = cfg.getValue;
            if (!angular.isDefined(labelMethod)) {
                labelMethod = identityFn
            }
            if (!angular.isDefined(valueMethod)) {
                valueMethod = identityFn
            }
            angular.forEach(data, function dataIt(chunk) {
                localData.push({
                    label: labelMethod.call(this, chunk),
                    value: valueMethod.call(this, chunk)
                })
            });
            return localData;
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
    }
);
