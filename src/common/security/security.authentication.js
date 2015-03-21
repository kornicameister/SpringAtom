(function () {
    angular.module('sg.common.security').factory('securityAuthentication', function () {
        return function (auth) {
            if (!auth || _.isNull(auth)) {
                return {
                    authenticated: false,
                    username     : ''
                }
            }
            return {
                authenticated: true,
                username     : auth.password.email,
                auth         : auth
            }
        }
    });
}());