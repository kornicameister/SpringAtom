/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

/**
 * Created by kornicameister on 18.07.14.
 */
define(
    [
        'underscore',
        'utils',
        // extensions
        'config/ext'
    ],
    function infoPageController(_, utils) {

        var getPageId = function getPageId(definition) {
                return definition.id;
            },
            getPageLabel = function getPageLabel(definition) {
                return definition.label;
            },
            getAttributeRenderer = function getAttributeRenderer(attribute, dataChunk) {
                var displayType = attribute['displayAs'],
                    renderAs = dataChunk['renderAs'];
                if (_.isUndefined(displayType) && !_.isUndefined(renderAs)) {
                    return renderAs.toLowerCase();
                } else if (!_.isUndefined(displayType)) {
                    switch (displayType) {
                        case 'VALUE_ATTRIBUTE':
                            return 'text';
                        case 'TABLE_ATTRIBUTE':
                            return 'table';
                        case 'INFOPAGE_ATTRIBUTE':
                            return 'link';
                    }
                }
                return 'no_value';
            },
            getAttributesOfPanel = function getAttributesOfPanel(panel, data) {
                var attributes = panel.content,
                    attributesOfPanel = _.pluck(attributes, 'path'),
                    dataOfPanel = _.pick(data, attributesOfPanel),
                    attrs = [];
                _.each(attributes, function attrIt(attribute) {
                    var path = attribute.path,
                        dataChunk = dataOfPanel[path] || {},
                        item = {
                            path : path,
                            label: attribute.label,
                            value: dataChunk.value,
                            as   : getAttributeRenderer(attribute, dataChunk)
                        };
                    attrs.push(item);
                });
                return attrs;
            },
            getPanels = function getPanels(definition, data) {
                var content = definition.content,
                    panels = [];
                _.each(content, function contentIt(panel) {
                    panels.push({
                        label     : panel.label,
                        icon      : panel['iconCfg']['iconCls'],
                        attributes: getAttributesOfPanel(panel, data)
                    })
                });
                return panels;
            };

        return function _infoPageController($log, $scope, oid, configuration) {
            $scope.debug = utils.isDebug();

            var data = configuration.data,
                definition = configuration.definition;

            // setup view variables
            $scope.uniqueId = _.uniqueId('infopage');
            $scope.pageId = getPageId(definition);
            $scope.pageLabel = getPageLabel(definition);
            $scope.panels = getPanels(definition, data);
            $scope.context = oid;
        };
    }
);
