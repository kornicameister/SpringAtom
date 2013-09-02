/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.web.interceptors;

import org.agatom.springatom.server.SpringAtomServer;
import org.agatom.springatom.web.bean.command.search.SSearchCommandBean;
import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SCommonDataAppenderInterceptor
        extends HandlerInterceptorAdapter {
    private static final Logger LOGGER                  = Logger.getLogger(SCommonDataAppenderInterceptor.class);
    private static final String SA_LOCALE_REQUEST_PARAM = "sa.locale.requestParam";
    private static final String SA_UI_PAGE_TITLE_KEY    = "sa.ui.page.title.key";
    private static final String SA_UI_PAGE_TITLE_PREFIX = "sa.ui.page.title.prefix";
    @Autowired
    private SpringAtomServer server;
    @Autowired
    private SMessageSource   messageSource;
    private String           localeParamKey;
    private String           pageTitleKey;
    private String           pageTitlePrefix;

    @PostConstruct
    private void init() {
        this.localeParamKey = this.server.getProperty(SA_LOCALE_REQUEST_PARAM);
        this.pageTitleKey = this.server.getProperty(SA_UI_PAGE_TITLE_KEY);
        this.pageTitlePrefix = this.server.getProperty(SA_UI_PAGE_TITLE_PREFIX);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String
                    .format("Initialized for properties names=%s", Arrays
                            .toString(new String[]{SA_LOCALE_REQUEST_PARAM, SA_UI_PAGE_TITLE_KEY, SA_UI_PAGE_TITLE_PREFIX})));
        }
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws
            Exception {
        if (modelAndView != null && modelAndView.getModelMap() != null) {
            final ModelMap modelMap = modelAndView.getModelMap();

            modelMap.put(this.localeParamKey, this.server.getServerLocale());
            modelMap.put(this.pageTitleKey, this.getCurrentViewTitle(modelAndView.getViewName()));
            modelMap.addAttribute(SSearchCommandBean.Name.GLOBAL.getName(), new SSearchCommandBean());

        }
    }

    private String getCurrentViewTitle(final String viewName) {
        final String keyForTitle = this.pageTitlePrefix.replace("*", viewName != null ? viewName : "index");
        final String pageTitle = this.messageSource.getMessage(keyForTitle, this.server.getServerLocale());
        LOGGER.debug(String.format("Resolved title=%s for viewName=%s", pageTitle, viewName));
        return pageTitle;
    }
}
