/**
 * Created by Tomasz on 2015-01-09.
 */
define(
    [
        'jsface',
        'lodash'
    ],
    function configurationHelper() {
        /**
         * <b>ConfigurationHelper</b> is a utility class
         * to wrap up configuring similar types of objects
         * via consistent API. Therefore it allows
         * to select particular set of dependencies and run
         * a configuration over them
         *
         * @constructor
         */
        function ConfigurationHelper() {

            function dependencyIterator(dependency) {
                if (window.DEBUG_CONFIGURATION_HELPER) {
                    console.log('callback(dependency=' + dependency + ')');
                }
                this.call(this, dependency);
            }

            function verifyCallback(callback) {
                if (_.isUndefined(callback)) {
                    throw new Error('Failed to load configuration because supplied callback is null/undefined');
                }
                if (!_.isFunction(callback)) {
                    throw new Error('Failed to load configuration because supplied callback not a function');
                }
            }

            function getIterationScope(args) {
                if (args.length === 1) {
                    return args[0];
                }
                return args[1] || {};
            }

            return {
                constructor  : function constructor() {
                    this.dependencies = [];
                },
                /**
                 * Pushes dependencies into the queue of expecting dependencies to be loaded.
                 * Optionally <b>dep</b> parameter may be a {@link Function} that will be called to retrieve
                 * the dependencies
                 *
                 * @param dep
                 *      {@link Array} or {@link Object} representing dependency
                 * @returns {jsface} ConfigurationHelper instance
                 */
                addDependency: function addDependency(dep) {
                    if (DEBUG_CONFIGURATION_HELPER) {
                        console.log('addDependency(dep=' + dep + ')');
                    }
                    if (_.isFunction(dep)) {
                        dep = dep();
                    }
                    dep = _.isArray(dep) ? dep : [dep];
                    _.forEachRight(dep, function (dependency) {
                        if (_.isArray(dependency)) {
                            this.addDependency(dependency);
                        } else {
                            this.dependencies.push(dependency);
                        }
                    }, this);
                    return this;
                },
                /**
                 * Configures a given set of dependencies by explicitly calling given <b>callback</b> function.
                 * Function may accept additional param defining the scope under which single dependency configuration will
                 * be performed.
                 *
                 * @param callback
                 *      {@link Function} accepting single object param
                 */
                configure    : function configure(callback) {
                    verifyCallback(callback);
                    _.forEachRight(this.dependencies || [], dependencyIterator, getIterationScope(arguments));
                }
            }
        }

        var cls = Class(Object, ConfigurationHelper);

        cls.newHelper = function () {
            return new cls();
        };

        return cls;
    }
);