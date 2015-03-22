angular
    .module('sg.common.data')
    .service('sgDataService', [
        '$firebase',
        '$firebaseAuth',
        '$firebaseArray',
        '$firebaseObject',
        'DATA_API_URL',
        'loggerFactory',
        function ($firebase, $firebaseAuth, $firebaseArray, $firebaseObject, DATA_API_URL, loggerFactory) {
            var service = this,
                logger = loggerFactory('sgDataService'),
                fireUrl = (function setFireUrl(url) {
                    if (_.isEmpty(url)) {
                        throw new Error('Cannot configure fire url, because provider URL is empty');
                    }
                    url = _.endsWith(url, '/') ? url : url + '/';
                    logger.debug(_.format('Setting API url to {u}', {u: url}));
                    return url;
                }(DATA_API_URL));

            service.$firebase = getResource.bind(service);
            service.$firebaseObject = getObject.bind(service);
            service.$firebaseArray = getArray.bind(service);
            service.$firebaseAuth = getAuth.bind(service);
            service.firebase = getNativeResource.bind(service);

            function getResource(path, opts) {
                return $firebase(getNativeResource(path), opts || {});
            }

            function getObject(path, opts) {
                return $firebaseObject(getNativeResource(path), opts || {});
            }

            function getArray(path, opts) {
                return $firebaseArray(getNativeResource(path), opts || {});
            }

            function getAuth() {
                return $firebaseAuth(getNativeResource());
            }

            function resourceUrl(path) {
                if (!path) {
                    return fireUrl;
                }
                return fireUrl + (_.startsWith(path, '/') ? path.substring(1) : path);
            }

            function getNativeResource(path) {
                return new Firebase(resourceUrl(path));
            }
        }
    ]);