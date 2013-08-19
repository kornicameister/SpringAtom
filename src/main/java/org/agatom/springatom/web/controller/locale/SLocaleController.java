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

package org.agatom.springatom.web.controller.locale;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.controller.locale.bean.SLocale;
import org.agatom.springatom.web.controller.locale.bean.SLocalizedPreference;
import org.agatom.springatom.web.controller.locale.bean.SLocalizedPreferences;
import org.agatom.springatom.web.util.SServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/lang")
public class SLocaleController
        implements MessageSourceAware {
    public static final  String   SA_UI_COMPONENTS_KEY = "sa.ui.components";
    public static final  String   SA_LOCALE_SUPPORTS   = "sa.locale.supports";
    private static final String[] IGNORED_KEYS         = {"_dc", "page", "start", "limit"};
    private static final Logger   LOGGER               = Logger.getLogger(SLocaleController.class);
    @Autowired
    protected SServer             server;
    private   MessageSource       messageSource;
    private   Map<String, String> uiComponents;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.setDisallowedFields(IGNORED_KEYS);
        binder.setIgnoreInvalidFields(true);
        binder.setIgnoreUnknownFields(true);
    }

    @RequestMapping(
            value = "/available",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody Callable<Set<SLocale>> readLocales() {
        final String appResources = this.server.getProperty(SA_LOCALE_SUPPORTS);
        final String delimiter = this.server.getDelimiter();
        return new Callable<Set<SLocale>>() {
            @Override
            public Set<SLocale> call() throws Exception {
                final String[] array = appResources.split(delimiter);
                final Set<SLocale> locales = Sets.newHashSet();
                for (final String locale : array) {
                    final Locale loc = Locale.forLanguageTag(locale);
                    locales.add(new SLocale().setTag(locale).setCountry(loc.getCountry())
                                             .setLanguage(loc.getLanguage()));
                }
                return locales;
            }
        };
    }

    @RequestMapping(
            value = "/read",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody Callable<SLocalizedPreferences> getUIMetaData(final String key) {
        final String componentKey = this.uiComponents.get(key);
        final Locale locale = LocaleContextHolder.getLocale();
        final String cfg = messageSource.getMessage(componentKey, null, null, locale);

        return new Callable<SLocalizedPreferences>() {
            @Override
            public SLocalizedPreferences call() throws Exception {

                final List<String> splitted = Arrays.asList(cfg.split(","));
                final List<SLocalizedPreference> preferences = new ArrayList<>();

                for (final String part : splitted) {
                    final String[] strings = part.split("=");
                    final String cmpKey = strings[0];
                    final String cmpValue = strings[1];
                    preferences.add(new SLocalizedPreference().setKey(cmpKey)
                                                              .setValue(cmpValue));
                }

                final SLocalizedPreferences dataUI = new SLocalizedPreferences()
                        .setKey(key)
                        .setLocale(
                                new SLocale()
                                        .setTag(locale.toLanguageTag())
                                        .setCountry(locale.getCountry())
                                        .setLanguage(locale.getLanguage())
                        )
                        .setPreferences(preferences);

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String.format("For key=%s and lang=%s returning dataUi=%s", key, locale, dataUI));
                }

                return dataUI;
            }
        };
    }

    @Override
    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s initialized with rb=%s", SLocaleController.class
                    .getSimpleName(), this.messageSource));
        }
        this.uiComponents = this.resolveUIComponents();
    }

    private Map<String, String> resolveUIComponents() {
        final Map<String, String> map = Maps.newHashMap();
        final String unParsed = this.messageSource
                .getMessage(SA_UI_COMPONENTS_KEY, null, null, LocaleContextHolder.getLocale());
        if (unParsed != null) {
            final String[] parsed = unParsed.split(",");
            for (final String chunk : parsed) {
                final String[] chunkCC = chunk.split("=");
                map.put(chunkCC[0], chunkCC[1]);
            }
        }
        return map;
    }

}
