angular
    .module('sg.common.data')
    .run(configureInterceptors);

function configureInterceptors(Restangular, growl, DEBUG_MODE, loggerFactory) {
    var httpLogger = loggerFactory('http');

    Restangular.addErrorInterceptor(errorLogger);

    function errorLogger(response) {
        var respStatus = response.status,
            respUrl = response.url,
            errMsg = getErrMsg();

        httpLogger.error(errMsg);
        if (DEBUG_MODE) {
            // dump response into debug
            httpLogger.debug(response.toString());
        }
        growl.addErrorMessage(errMsg, {
            ttl: 10000
        });

        return true; // do not handle this error

        function getErrMsg() {
            return [
                'Retrieved error code ' + respStatus,
                ' when accessing url ' + respUrl
            ].join()
        }
    }
}