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

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.execution.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VFlowAjaxTilesView
        extends VAjaxTilesView {

    protected String[] getRenderFragments(Map<String, Object> model, HttpServletRequest request,
                                          HttpServletResponse response) {
        final RequestContext context = RequestContextHolder.getRequestContext();
        if (context == null) {
            return super.getRenderFragments(model, request, response);
        } else {
            String[] fragments = (String[]) context.getFlashScope().get(View.RENDER_FRAGMENTS_ATTRIBUTE);
            if (fragments == null) {
                final Event currentEvent = context.getCurrentEvent();
                if (currentEvent != null) {

                    final AttributeMap<Object> attributes = currentEvent.getAttributes();
                    final Object _frags = attributes.get("fragments");
                    if (ClassUtils.isAssignable(String.class, _frags.getClass())) {
                        fragments = StringUtils.addStringToArray(new String[]{}, (String) _frags);
                    } else if (ClassUtils.isAssignable(String[].class, _frags.getClass())) {
                        fragments = (String[]) _frags;
                    }
                    if (fragments != null) {
                        context.getFlashScope().put(View.RENDER_FRAGMENTS_ATTRIBUTE, fragments);
                    }

                }
            }
            if (fragments == null) {
                return super.getRenderFragments(model, request, response);
            }
            return fragments;
        }
    }

}
