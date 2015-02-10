define(
    [
        'lodash',
        // translations
        './pl_PL/common',
        './pl_PL/states',
        './pl_PL/buttons'
    ],
    function (_) {
        "use strict";

        var array = _.toArray(arguments).slice(1);

        return (function (translations) {

            var pl = {};

            _.forEachRight(translations, function (tr) {
                pl = _.merge(pl, tr);
            });

            return pl;

        }(array))
    }
);