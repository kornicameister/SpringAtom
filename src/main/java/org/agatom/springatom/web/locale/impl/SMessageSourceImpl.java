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

package org.agatom.springatom.web.locale.impl;

import com.google.common.collect.Lists;
import org.agatom.springatom.server.SpringAtomServer;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.support.locale.SLocale;
import org.agatom.springatom.web.support.locale.SLocalizedPreference;
import org.agatom.springatom.web.support.locale.SLocalizedPreferences;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMessageSourceImpl
        extends ReloadableResourceBundleMessageSource
        implements SMessageSource {
    private final static Logger      LOGGER           = Logger.getLogger(SMessageSourceImpl.class);
    private static final String      DEFAULT_SPLIT_BY = "=";
    private              StorageMode storageMode      = null;
    private              String      splitBy          = null;
    @Autowired
    private SpringAtomServer server;

    @PostConstruct
    public void init() {
        if (this.storageMode == null) {
            this.storageMode = StorageMode.COMBINED;
            LOGGER.trace(String.format("StorageMode was not initialized, fallback to = %s", StorageMode.COMBINED));
        }
        if (this.splitBy == null) {
            this.splitBy = DEFAULT_SPLIT_BY;
            LOGGER.trace(String.format("SplitBy was not initialized, fallback to = %s", DEFAULT_SPLIT_BY));
        }
    }

    @Override
    public void setStorageMode(final StorageMode mode) {
        this.storageMode = mode;
    }

    @Override
    public void setSplitBy(String splitBy) {
        this.splitBy = splitBy;
    }

    @Override
    public String getMessage(final String key, final Locale locale) {
        return this.getMessage(key, null, locale);
    }

    @Override
    public SLocalizedPreferences getAll(final Locale locale) {
        final SLocalizedPreferences preferences = this.getSLocalizedPreferencesForLocale(locale);
        final Set<String> keys = this.getKeys(locale);

        for (final String key : keys) {
            preferences.put(this.resolveAgaintsKey(key, locale));
        }

        return preferences;
    }

    @Override
    public SLocalizedPreferences getAll(final String key, final Locale locale) {
        final SLocalizedPreferences preferences = this.getSLocalizedPreferencesForLocale(locale);
        return preferences.put(this.resolveAgaintsKey(key, locale));
    }

    /**
     * Creates new instance of {@link SLocalizedPreferences} for given {@code locale}
     *
     * @param locale
     *         locale
     *
     * @return new localized preferences
     */
    private SLocalizedPreferences getSLocalizedPreferencesForLocale(final Locale locale) {
        return new SLocalizedPreferences()
                .setLocale(
                        new SLocale()
                                .setLanguage(locale.getLanguage())
                                .setCountry(locale.getCountry())
                                .setTag(locale.toLanguageTag())
                )
                .setParamName(this.server.getProperty("sa.locale.requestParam"));
    }

    private String getValueForKey(final String key, final Locale locale) {
        return this.getMergedProperties(locale).getProperty(key);
    }

    /**
     * Returns all the keys from all given resource bundles ({@link ReloadableResourceBundleMessageSource#setBasenames(String...)}
     *
     * @param locale
     *         locale
     *
     * @return set of keys
     */
    private Set<String> getKeys(final Locale locale) {
        return this.getMergedProperties(locale).getProperties().stringPropertyNames();
    }

    /**
     * Resolves all values from given resource bundles ({@link ReloadableResourceBundleMessageSource#setBasenames(String...)}
     * keeping focus on {@link StorageMode#COMBINED} storage mode. Which means that read value will be processed in the
     * following order:
     * <ol>
     * <li>value is transformed with {@link String#split(String)} method where {@link
     * org.agatom.springatom.server.SServer#getDelimiter()} is used</li>
     * <li>each given chunk is than processed with {@link String#split(String)} method accordingly to the {@link
     * SMessageSource#setSplitBy(String)}</li>
     * </ol>
     *
     * @param value
     *         value to be processed
     *
     * @return processed list of pairs key=value embedded into instances of {@link SLocalizedPreference}
     */
    private List<SLocalizedPreference> resolveAgaintsCombinedStorageMode(final String value) {
        final List<SLocalizedPreference> preferences = Lists.newArrayList();
        final String[] values = value.split(this.server.getDelimiter());
        for (final String chunk : values) {
            final String[] chunky = chunk.split(this.splitBy);
            preferences.add(new SLocalizedPreference().setKey(chunky[0]).setValue(chunky[1]));
        }
        return preferences;
    }

    private List<SLocalizedPreference> resolveAgaintsKey(final String key, final Locale locale) {
        final String value = this.getValueForKey(key, locale);
        if (value != null) {
            switch (this.storageMode) {
                case COMBINED:
                    return this.resolveAgaintsCombinedStorageMode(value);
                case SINGLE:
                    return Lists.newArrayList(new SLocalizedPreference().setKey(key).setKey(value));
            }
        }
        LOGGER.warn(String.format("For key=%s,locale=%s found none corresponding value", key, locale));
        return Lists.newArrayList();
    }
}
