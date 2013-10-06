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

package org.agatom.springatom.web.support.breadcrumbs;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.support.breadcrumbs.beans.SBreadcrumbPath;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SBreadcrumbResolver
        implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(SBreadcrumbResolver.class);
    private Map<String, SBreadcrumbPath> cache;
    private boolean useCache = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.useCache) {
            this.cache = Maps.newConcurrentMap();
        }
    }

    public SBreadcrumbPath getBreadcrumbPath(final View view) {
        if (view != null) {
            if (view.getClass().isAssignableFrom(TilesView.class)) {
                LOGGER.trace(String
                        .format("Resolving breadcrumb path using=%s", Tiles3ViewBreadcrumbResolver.class
                                .getSimpleName()));
                return Tiles3ViewBreadcrumbResolver.getBreadcrumbPath((TilesView) view);
            }
        }
        return new SBreadcrumbPath();
    }

    public void setUseCache(final boolean useCache) {
        this.useCache = useCache;
    }

    private static class Tiles3ViewBreadcrumbResolver {
        private static final Logger LOGGER_2 = Logger.getLogger(Tiles3ViewBreadcrumbResolver.class);

        public static SBreadcrumbPath getBreadcrumbPath(final TilesView tilesView) {
            LOGGER_2.trace(String.format("Resolving from tilesView=%s", tilesView));
            final String url = tilesView.getUrl();
            return new SBreadcrumbPath();
        }
    }
}
