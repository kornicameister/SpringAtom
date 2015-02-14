define(
    [
        'lodash'
    ],
    function callbacks(_) {
        "use strict";
        return {
            cancelEvent: function cancelEvent(func) {
                var args = _.toArray(arguments).slice(1);
                func(args);
                if (args[0].preventDefault) {
                    args[0].preventDefault();
                }
                return false;
            }
        }
    }
);