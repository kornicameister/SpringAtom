/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.action.reader.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import javassist.NotFoundException;
import org.agatom.springatom.web.action.model.AbstractAction;
import org.agatom.springatom.web.action.model.Action;
import org.agatom.springatom.web.action.model.ActionModel;
import org.agatom.springatom.web.action.model.ActionSecurityCheck;
import org.agatom.springatom.web.action.model.actions.LinkAction;
import org.agatom.springatom.web.action.model.actions.WizardAction;
import org.agatom.springatom.web.action.reader.ActionsModelReader;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.action.WizardEventAction;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DefaultActionsModelReader
        implements ActionsModelReader {
    private static final Logger                               LOGGER              = Logger.getLogger(DefaultActionsModelReader.class);
    private static final String                               ACTION_MODELS_KEY   = "actionModels";
    private static final String                               RESOURCE_BUNDLE_KEY = "resourceBundle";
    private              String                               modelFile           = null;
    private              Boolean                              parseOnLoad         = false;
    private              JsonNode                             actionModel         = null;
    private              ObjectMapper                         objectMapper        = null;
    private              Map<String, ActionModelReferenceMap> flattenActionModel  = null;
    private              SMessageSource                       messageSource       = null;

    /** {@inheritDoc} */
    @Override
    public Set<ActionModel> getActionModels() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ActionModel getActionModel(final String name) throws NotFoundException {
        LOGGER.debug(String.format("getActionModel(name=%s)", name));
        final ActionModelReferenceMap map = flattenActionModel.get(name);
        if (map == null) {
            throw new NotFoundException(String.format("No action model found for key=%s", name));
        }
        try {
            final long startTime = System.nanoTime();
            final ActionModel model = new ActionModel()
                    .setContent(this.resolveContent(map, Sets.<Action>newTreeSet()));

            JsonNode tmpNode;
            if ((tmpNode = map.node.findValue("description")) != null) {
                model.setDescription(tmpNode.asText());
            }
            if ((tmpNode = map.node.findValue("name")) != null) {
                model.setName(tmpNode.asText());
            }

            final long endTime = System.nanoTime() - startTime;

            LOGGER.trace(String.format("getActionModel(name=%s) took %d ms", name, TimeUnit.NANOSECONDS.toMillis(endTime)));

            return model;
        } catch (Exception ignore) {

        }
        return null;
    }

    private Set<Action> resolveContent(final ActionModelReferenceMap map, final Set<Action> actions) {
        if (!map.actions.isEmpty()) {
            for (final JsonNode node : map.actions) {
                actions.add(this.resolveFromAction((ObjectNode) node));
            }
        }
        if (!map.submodel.isEmpty()) {
            for (final String subModelKey : map.submodel.keySet()) {

                final ActionModelReferenceMap actionModel = this.flattenActionModel.get(subModelKey);
                final Set<Action> actionModelActions = this.resolveContent(actionModel, Sets.<Action>newLinkedHashSet());

                final ActionModel model = new ActionModel();
                model.setName(actionModel.node.findValue("name").textValue());
                model.setDescription(actionModel.node.findValue("description").textValue());
                model.setContent(actionModelActions);
                model.setLabel(this.getLabel(actionModel.node.findValue(RESOURCE_BUNDLE_KEY).textValue()));

                actions.add(model);
            }
        }
        return actions;
    }

    private String getLabel(String name) {
        return this.messageSource.getMessage(name, LocaleContextHolder.getLocale());
    }

    private Action resolveFromAction(final ObjectNode node) {
        final ObjectNode command = (ObjectNode) node.get("command");
        final String type = command.get("type").textValue();
        AbstractAction action = null;
        switch (type) {
            case "wizard": {
                action = new WizardAction();
                ((WizardAction) action).setWizardName(command.get("url").textValue());
                ((WizardAction) action).setType(ClassUtils.resolveClassName(node.get("type").textValue(), this.getClass().getClassLoader()));
            }
            break;
            case "wizardEvent": {
                action = new WizardEventAction();
                ((WizardEventAction) action).setEventIconClass(command.get("iconClass").asText());
            }
            break;
            case "link": {
                // nothing to be done here
                action = new LinkAction();
            }
        }
        if (action != null) {

            if (ClassUtils.isAssignableValue(LinkAction.class, action)) {
                //noinspection ConstantConditions
                action = ((LinkAction) action).setUrl(command.get("url").textValue());
            }

            action.setName(node.get("name").textValue());
            action.setLabel(this.getLabel(node.get(RESOURCE_BUNDLE_KEY).textValue()));

            if (node.has("iconClass")) {
                action.setIconClass(node.get("iconClass").textValue());
            }

            if (node.has("security")) {
                final JsonNode security = node.get("security");
                final boolean enabled = security.get("enabled").asBoolean(false);
                if (enabled && !(security.has("checkUrl") || security.has("roles"))) {
                    LOGGER.warn("Invalid state, security enabled but neither roles no checkUrl defined");
                }
                action.setSecurity(new ActionSecurityCheck().setEnabled(enabled));
                if (security.has("checkUrl")) {
                    action.getSecurity().setPattern(security.get("checkUrl").textValue());
                }
                if (security.has("roles")) {
                    action.getSecurity().setRoles(StringUtils.commaDelimitedListToSet(security.get("roles").textValue()));
                }
            }
            action.setOrder(node.has("index") ? node.get("index").shortValue() : -1);
        }
        return action;
    }

    @PostConstruct
    private void initializeFileRead() throws Exception {
        String pathToUse = this.modelFile;
        if (!pathToUse.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
            pathToUse = String.format("%s%s", ResourceUtils.CLASSPATH_URL_PREFIX, this.modelFile);
        }
        try {
            final File file = ResourceUtils.getFile(pathToUse);
            if (file.exists() && file.canRead()) {
                final String stringContent = FileCopyUtils.copyToString(new BufferedReader(new FileReader(file)));
                if (StringUtils.hasText(stringContent)) {
                    this.actionModel = this.objectMapper.readTree(stringContent);
                } else {
                    throw new Exception("Invalid content");
                }
            }
        } catch (FileNotFoundException exp) {
            LOGGER.error(String.format("Could not locate action-model file at %s", pathToUse), exp);
        } catch (IOException exp) {
            LOGGER.error(String.format("Could not read action-model file at %s", pathToUse), exp);
        } catch (Exception exp) {
            LOGGER.error(String.format("Generic exception when reading => %s", exp.getMessage()));
        }
        if (this.parseOnLoad) {
            // TODO add appropriate exception when actionModel is null
            this.parseActionModels();
        }
    }

    private void parseActionModels() throws Exception {
        Assert.notNull(this.actionModel);
        LOGGER.trace("Starting parsing actionModel");
        try {
            final long startTime = System.nanoTime();
            {
                final JsonNode actionModelsNode = this.actionModel.get(ACTION_MODELS_KEY);
                Assert.isTrue(actionModelsNode.getNodeType().equals(JsonNodeType.ARRAY));

                final ArrayNode actionModels = (ArrayNode) actionModelsNode;
                final int length = actionModels.size();
                LOGGER.trace(String.format("%d actionModels found", length));

                final Map<String, ActionModelReferenceMap> referenceMap = Maps.newHashMap();
                for (final JsonNode node : actionModels) {
                    referenceMap.put(node.get("name").textValue(), this.flattenActionModels((ObjectNode) node));
                }
                this.flattenActionModel = referenceMap;
            }
            LOGGER.info(String.format("Loaded actionModel in %dms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
        } catch (Exception exp) {
            LOGGER.error("Error in parsing actionModel", exp);
            throw exp;
        }
    }

    private ActionModelReferenceMap flattenActionModels(final ObjectNode object) throws Exception {
        Assert.notNull(object, "ActionModel can not be null");

        final ActionModelReferenceMap map = new ActionModelReferenceMap();
        map.node = object;

        if (this.hasSubmodels(object)) {
            final ArrayNode submodels = (ArrayNode) object.get("submodels");
            for (final JsonNode node : submodels) {
                map.submodel.put(node.textValue(), node);
            }
        }
        if (this.hasActions(object)) {
            final ArrayNode actions = (ArrayNode) object.get("actions");
            for (final JsonNode action : actions) {
                map.actions.add(action);
            }
        }

        return map;
    }

    private boolean hasSubmodels(final JsonNode next) {
        return !ClassUtils.isAssignableValue(MissingNode.class, next.findPath("submodels"));
    }

    private boolean hasActions(final JsonNode next) {
        return !ClassUtils.isAssignableValue(MissingNode.class, next.findPath("actions"));
    }

    /**
     * <p>Setter for the field <code>modelFile</code>.</p>
     *
     * @param modelFile a {@link java.lang.String} object.
     */
    public void setModelFile(final String modelFile) {
        Assert.hasText(modelFile, "modelFile has no text");
        LOGGER.trace(String.format("Setting modelFile => %s", modelFile));
        this.modelFile = modelFile;
    }

    /**
     * <p>Setter for the field <code>parseOnLoad</code>.</p>
     *
     * @param parseOnLoad a {@link java.lang.Boolean} object.
     */
    public void setParseOnLoad(final Boolean parseOnLoad) {
        LOGGER.trace(String.format("Setting parseOnLoad => %s", parseOnLoad));
        this.parseOnLoad = parseOnLoad;
    }

    /**
     * <p>Setter for the field <code>objectMapper</code>.</p>
     *
     * @param objectMapper a {@link com.fasterxml.jackson.databind.ObjectMapper} object.
     */
    public void setObjectMapper(final ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "objectMapper has no text");
        LOGGER.trace(String.format("Setting objectMapper => %s", objectMapper));
        this.objectMapper = objectMapper;
    }

    /**
     * <p>Setter for the field <code>messageSource</code>.</p>
     *
     * @param messageSource a {@link org.agatom.springatom.web.locale.SMessageSource} object.
     */
    public void setMessageSource(final SMessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource has no text");
        LOGGER.trace(String.format("Setting messageSource => %s", messageSource));
        this.messageSource = messageSource;
    }

    private class ActionModelReferenceMap {
        private ObjectNode            node     = null;
        private Set<JsonNode>         actions  = Sets.newHashSet();
        private Map<String, JsonNode> submodel = Maps.newHashMap();
    }
}
