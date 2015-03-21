/**
 * @ngdoc module
 *
 * @name sg.common.security
 * @module sg.common.security
 * @namespace sg
 * @description
 *
 * <b>sg.common.security</b> is a global module that contain all required components
 * to provide an information about application security state.
 * Supports serving security services for states, actions, hyperlinks etc.
 */
angular.module('sg.common.security', [
    'sg.common.state',
    'sg.common.data'
]);