define(
    [
        'lodash'
    ],
    function callbacks(_) {
        "use strict";
        return {
            cancelEvent: cancelEvent
        };

        function cancelEvent(func) {
            var args = _.toArray(arguments).slice(1);
            func(args);
            if (args.length > 0 && args[0].preventDefault) {
                args[0].preventDefault();
            }
            return false;
        }
    }
);