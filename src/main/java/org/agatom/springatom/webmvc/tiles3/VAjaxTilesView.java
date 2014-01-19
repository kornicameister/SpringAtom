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

package org.agatom.springatom.webmvc.tiles3;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Definition;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.jsp.JspRequest;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.springframework.js.ajax.AjaxHandler;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.tiles3.TilesView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * {@code VAjaxTilesView} is a rewritten {@link org.springframework.js.ajax.tiles2.AjaxTilesView} (for Apache Tiles 2.x) to work
 * with <b>Apache Tiles 3.x.x</b>.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VAjaxTilesView
        extends TilesView {
    private static final String             FRAGMENTS_PARAM    = "fragments";
    private              ApplicationContext applicationContext = null;
    private              AjaxHandler        ajaxHandler        = new SpringJavascriptAjaxHandler();

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    public AjaxHandler getAjaxHandler() {
        return ajaxHandler;
    }

    public void setAjaxHandler(AjaxHandler ajaxHandler) {
        this.ajaxHandler = ajaxHandler;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final ServletContext servletContext = this.getServletContext();

        if (this.ajaxHandler.isAjaxRequest(request, response)) {
            String[] fragmentsToRender = this.getRenderFragments(model, request, response);
            if (fragmentsToRender.length == 0) {
                logger.warn("An Ajax request was detected, but no fragments were specified to be re-rendered.  "
                        + "Falling back to full page render.  This can cause unpredictable results when processing "
                        + "the ajax response on the client.");
                super.renderMergedOutputModel(model, request, response);
                return;
            }

            this.applicationContext = ServletUtil.getApplicationContext(getServletContext());
            final TilesContainer container = TilesAccess.getContainer(this.applicationContext);
            if (container == null) {
                throw new ServletException("Tiles container is not initialized. "
                        + "Have you added a TilesConfigurer to your web application context?");
            }

            this.exposeModelAsRequestAttributes(model, request);
            JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));

            final Request tilesRequestContext = new ServletRequest(this.applicationContext, request, response);
            final Definition compositeDefinition = container.getDefinition(
                    this.getUrl(),
                    tilesRequestContext
            );

            final Map<String, Object> flattenedAttributeMap = Maps.newHashMap();
            this.flattenAttributeMap(container, tilesRequestContext, flattenedAttributeMap, compositeDefinition, request, response);
            this.addRuntimeAttributes(container, flattenedAttributeMap, request, response);

            if (fragmentsToRender.length > 1) {
                request.setAttribute(JspRequest.FORCE_INCLUDE_ATTRIBUTE_NAME, true);
            }

            for (final String aFragmentsToRender : fragmentsToRender) {
                final Attribute attributeToRender = (Attribute) flattenedAttributeMap.get(aFragmentsToRender);

                if (attributeToRender == null) {
                    throw new ServletException("No tiles attribute with a name of '" + aFragmentsToRender
                            + "' could be found for the current view: " + this);
                } else {
                    container.startContext(tilesRequestContext).inheritCascadedAttributes(compositeDefinition);
                    container.render(attributeToRender, tilesRequestContext);
                    container.endContext(tilesRequestContext);
                }
            }
        } else {
            super.renderMergedOutputModel(model, request, response);
        }
    }

    protected String[] getRenderFragments(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        final String attrName = request.getParameter(FRAGMENTS_PARAM);
        final String[] renderFragments = StringUtils.commaDelimitedListToStringArray(attrName);
        return StringUtils.trimArrayElements(renderFragments);
    }

    /**
     * <p>
     * Iterate over all attributes in the given Tiles definition. Every attribute value that represents a template (i.e.
     * start with "/") or is a nested definition is added to a Map. The method class itself recursively to traverse
     * nested definitions.
     * </p>
     *
     * @param container
     *         the TilesContainer
     * @param requestContext
     *         the TilesRequestContext
     * @param resultMap
     *         the output Map where attributes of interest are added to.
     * @param compositeDefinition
     *         the definition to search for attributes of interest.
     * @param request
     *         the servlet request
     * @param response
     *         the servlet response
     */
    protected void flattenAttributeMap(TilesContainer container, Request requestContext,
                                       Map<String, Object> resultMap,
                                       Definition compositeDefinition,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        final Set<String> localAttributeNames = this.getCollectionNullSafe(compositeDefinition.getLocalAttributeNames());
        final Set<String> cascadedAttributeNames = this.getCollectionNullSafe(compositeDefinition.getCascadedAttributeNames());

        for (String attributeName : localAttributeNames) {
            final Attribute attribute = compositeDefinition.getAttribute(attributeName);
            if (attribute.getValue() == null || !(attribute.getValue() instanceof String)) {
                continue;
            }
            final String value = attribute.getValue().toString();
            if (value.startsWith("/")) {
                resultMap.put(attributeName, attribute);
            } else if (container.isValidDefinition(value, new ServletRequest(this.applicationContext, request, response))) {
                resultMap.put(attributeName, attribute);
                final Definition nestedDefinition = container.getDefinition(value, requestContext);
                {
                    Assert.isTrue(nestedDefinition != compositeDefinition, "Circular nested definition: " + value);
                }
                this.flattenAttributeMap(container, requestContext, resultMap, nestedDefinition, request, response);
            }
        }

        for (String attributeName : cascadedAttributeNames) {
            final Attribute attribute = compositeDefinition.getAttribute(attributeName);
            if (attribute.getValue() == null || !(attribute.getValue() instanceof String)) {
                continue;
            }
            final String value = attribute.getValue().toString();
            if (value.startsWith("/")) {
                resultMap.put(attributeName, attribute);
            } else if (container.isValidDefinition(value, new ServletRequest(this.applicationContext, request, response))) {
                resultMap.put(attributeName, attribute);
                final Definition nestedDefinition = container.getDefinition(value, requestContext);
                {
                    Assert.isTrue(nestedDefinition != compositeDefinition, "Circular nested definition: " + value);
                }
                this.flattenAttributeMap(container, requestContext, resultMap, nestedDefinition, request, response);
            }
        }

    }

    /**
     * <p>
     * Iterate over dynamically added Tiles attributes (see "Runtime Composition" in the Tiles documentation) and add
     * them to the output Map passed as input.
     * </p>
     *
     * @param container
     *         the Tiles container
     * @param resultMap
     *         the output Map where attributes of interest are added to.
     * @param request
     *         the Servlet request
     * @param response
     *         the servlet response
     */
    protected void addRuntimeAttributes(TilesContainer container, Map<String, Object> resultMap, HttpServletRequest request,
                                        HttpServletResponse response) {
        final AttributeContext attributeContext = container.getAttributeContext(new ServletRequest(this.applicationContext, request, response));
        final Set<String> attributeNames = Sets.newHashSet();
        if (attributeContext.getLocalAttributeNames() != null) {
            attributeNames.addAll(attributeContext.getLocalAttributeNames());
        }
        if (attributeContext.getCascadedAttributeNames() != null) {
            attributeNames.addAll(attributeContext.getCascadedAttributeNames());
        }
        for (final String name : attributeNames) {
            final Attribute attr = attributeContext.getAttribute(name);
            resultMap.put(name, attr);
        }
    }

    private Set<String> getCollectionNullSafe(Set<String> set) {
        return Collections.unmodifiableSet(set == null ? Sets.<String>newHashSet() : set);
    }

}
