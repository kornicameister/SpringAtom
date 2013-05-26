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

package org.agatom.springatom.cache;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.agatom.springatom.util.ResourceLoaderHelper;
import org.agatom.springatom.xml.OXMHelper;
import org.agatom.springatom.xml.beans.CachedClass;
import org.agatom.springatom.xml.beans.Caches;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CacheResolverHelper {
    private static final Logger LOGGER = Logger.getLogger(CacheResolverHelper.class);
    protected static CacheResolverHelper cacheResolverHelper;
    private static Integer ACCESS_COUNT = 0;
    private final Caches caches;

    protected CacheResolverHelper() {
        this.caches = null;
    }

    protected CacheResolverHelper(final Caches caches) {
        this.caches = caches;
    }

    public static CacheResolverHelper newInstance(
            final ResourceLoaderHelper resourceLoaderHelper,
            final OXMHelper oxmHelper,
            final String cachesPath
    ) throws IOException {
        LOGGER.info(String.format("Accessing %s/%d, params=[%s,%s,%s]",
                CacheResolverHelper.class.getSimpleName(),
                CacheResolverHelper.ACCESS_COUNT++,
                resourceLoaderHelper,
                oxmHelper,
                cachesPath)
        );
        if (CacheResolverHelper.cacheResolverHelper == null) {
            final File xmlFile = resourceLoaderHelper.getResource(cachesPath).getFile();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("%s > loading caches names, path=%s",
                        CacheResolverHelper.class.getName(),
                        xmlFile.getAbsolutePath())
                );
            }

            Caches caches = (Caches) oxmHelper.convertFromXMLToObject(
                    xmlFile
            );
            CacheResolverHelper.cacheResolverHelper = new CacheResolverHelper(caches);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("%s > loaded caches names, count=%d",
                        CacheResolverHelper.class.getName(),
                        CacheResolverHelper.cacheResolverHelper.caches.getDefinition().getCachedClass().size())
                );
            }
        }
        return CacheResolverHelper.cacheResolverHelper;
    }

    public CachedClass getCachesForName(final String clazzName) {
        Collection<CachedClass> cachedClassList = Collections2.filter(
                this.caches.getDefinition().getCachedClass(),
                new CachesForNamePredicate(clazzName)
        );
        return cachedClassList.iterator().next();
    }

    private static class CachesForNamePredicate implements Predicate<CachedClass> {
        private final String clazzName;

        private CachesForNamePredicate(final String clazzName) {
            this.clazzName = clazzName;
        }

        @Override
        public boolean apply(@Nullable final CachedClass cachedClass) {
            return cachedClass != null && cachedClass.getDao().equals(this.clazzName);
        }
    }
}
