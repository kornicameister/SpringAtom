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

package org.agatom.springatom.web.infopages.mapping;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.infopages.SEntityInfoPage;
import org.agatom.springatom.web.infopages.SInfoPage;
import org.agatom.springatom.web.infopages.config.InfoPageConfigurationSource;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class InfoPageMappings
        implements Iterable<Class<?>> {
    // caches
    private final Map<Class<?>, SInfoPage>    domainToPageCache           = Maps.newHashMap();
    private final Map<String, SInfoPage>      relToPageCache              = Maps.newHashMap();
    private final Map<String, SInfoPage>      pathToPageCache             = Maps.newHashMap();
    private       InfoPageConfigurationSource infoPageConfigurationSource = null;

    /**
     * Caches the page in several cache repositories to support
     * faster load in the next requests.
     * <p/>
     * Works for either {@link org.agatom.springatom.web.infopages.SInfoPage}
     * or {@link org.agatom.springatom.web.infopages.SEntityInfoPage}
     *
     * @param page
     *         to be cached
     */
    private void cache(final SInfoPage page) {
        this.pathToPageCache.put(page.getPath(), page);
        this.relToPageCache.put(page.getRel(), page);
        if (page instanceof SEntityInfoPage) {
            final SEntityInfoPage infoPage = (SEntityInfoPage) page;
            this.domainToPageCache.put(infoPage.getDomain(), page);
        }
    }

    public SInfoPage getInfoPageForRel(final String rel) {
        if (this.relToPageCache.containsKey(rel)) {
            return this.relToPageCache.get(rel);
        }
        final SInfoPage infoPage = this.infoPageConfigurationSource.getFromRel(rel);
        if (infoPage != null) {
            this.cache(infoPage);
            return infoPage;
        }
        return null;
    }

    public SEntityInfoPage getInfoPageForEntity(final Class<?> domainClass) {
        if (this.domainToPageCache.containsKey(domainClass)) {
            return (SEntityInfoPage) this.domainToPageCache.get(domainClass);
        }
        final SInfoPage infoPage = this.infoPageConfigurationSource.getFromDomain(domainClass);
        if (infoPage != null) {
            this.cache(infoPage);
            return (SEntityInfoPage) infoPage;
        }
        return null;
    }

    public SInfoPage getInfoPageForPath(final String path) {
        if (this.pathToPageCache.containsKey(path)) {
            return this.pathToPageCache.get(path);
        }
        final SInfoPage infoPage = this.infoPageConfigurationSource.getFromPath(path);
        if (infoPage != null) {
            this.cache(infoPage);
            return infoPage;
        }
        return null;
    }

    public boolean hasInfoPageForDomain(final String domain) {
        return this.getInfoPageForPath(domain) != null;
    }

    public void setInfoPageConfigurationSource(final InfoPageConfigurationSource ipcs) {
        this.infoPageConfigurationSource = ipcs;
    }

    @Override
    public Iterator<Class<?>> iterator() {
        final Set<Class<?>> keysAsClazz = this.infoPageConfigurationSource.getAllInfoPageClasses();
        return keysAsClazz.iterator();
    }
}