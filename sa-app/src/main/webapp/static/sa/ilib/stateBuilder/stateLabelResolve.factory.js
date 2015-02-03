define(
    [
        'angular',
        'lodash'
    ],
    function (angular, _) {
        "use strict";

        return ['$injector', function ($injector) {
            return {
                /**
                 * Resolves the label of the particular state, i.e. the verbose and reable name
                 * if such is presented in the state.definition.
                 * Accepts a literal
                 * <pre>
                 *     state = {
                 *      name: ' ',
                 *      definition : {
                 *       label: @string || @function
                 *      }
                 *     }
                 * </pre>
                 *
                 * If presented object contains no label property within the definition object
                 * an undefined value is returned, meaning that there is no label defined for given state.
                 * If label is recognized as a string, the string ir immediately returned.
                 * One last option is to provide a callable function that can be either an ordinary
                 * function or the one that supports dependency injection via angular.
                 *
                 * @param state state configuration
                 */
                resolveLabel: resolveLabel
            };

            function resolveLabel(state) {
                if (!state) {
                    return undefined;
                }

                var label = state.label || undefined;

                if (_.isUndefined(label)) {
                    return undefined;
                } else if (_.isString(label)) {
                    return label;
                }

                if (_.isFunction(label)) {
                    // create injection and call
                    if ($injector.annotate(label).length) {
                        label = $injector.invoke(label);
                    } else {
                        label = label();
                    }
                } else if (_.isArray(label) && _.isFunction(label[label.length - 1])) {
                    label = $injector.invoke(label)
                }

                return label;
            }
        }];

    }
);