define(
    [
        'lodash'
    ],
    function (_) {
        "use strict";
        return function () {
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
        }
    }
);