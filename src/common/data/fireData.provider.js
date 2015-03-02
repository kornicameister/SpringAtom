define(
    [
        'lodash',
        './fireData.module'
    ],
    function fireDataProvider(_, module) {
        "use strict";
        return module.provider('$fireData', fireData);

        function fireData() {
            var firebaseRoot,
                provider = this,
                fireUrl;

            provider.setFireUrl = setFireUrl;
            provider.$get = ['$firebase', '$firebaseAuth', serviceFunction];

            function setFireUrl(url) {
                if (_.isEmpty(url)) {
                    throw new Error('Cannot configure fire url, because provider URL is empty');
                } else if (firebaseRoot) {
                    throw new Error('Firebase has been already configured');
                }

                fireUrl = _.endsWith(url, '/') ? url : url + '/';
                firebaseRoot = new Firebase(fireUrl);

                return provider;
            }

            function serviceFunction($firebase, $firebaseAuth) {
                var service = {};

                service.resourceUrl = resourceUrl.bind(service);
                service.nativeResource = nativeResource.bind(service);
                service.resource = resource.bind(service);
                service.auth = auth.bind(service);

                return service;

                function resourceUrl(path) {
                    if (!path) {
                        return fireUrl;
                    }
                    return _.startsWith(path, '/') ? path.substring(1) : path;
                }

                function nativeResource(path) {
                    return firebaseRoot.child(resourceUrl(path));
                }

                function resource(path, opts) {
                    return $firebase(nativeResource(path), opts || {});
                }

                function auth() {
                    return $firebaseAuth(firebaseRoot);
                }
            }
        }
    }
);