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
import org.agatom.springatom.server.SServer;
import org.agatom.springatom.server.locale.SMessageSource;
import org.agatom.springatom.server.locale.bean.SLocale;
import org.agatom.springatom.server.locale.bean.SLocalizedPreferences;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/lang")
public class SLocaleController {
    public static final  String   SA_UI_COMPONENTS_KEY = "sa.ui.components";
    public static final  String   SA_LOCALE_SUPPORTS   = "sa.locale.supports";
    private static final String[] IGNORED_KEYS         = {"_dc", "page", "start", "limit"};
    private static final Logger   LOGGER               = Logger.getLogger(SLocaleController.class);
    @Autowired
    protected SServer             server;
    @Autowired
    private   SMessageSource      messageSource;
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
    public @ResponseBody Callable<Set<SLocale>> getAvailableLocales() {
        final String appResources = this.server.getProperty(SA_LOCALE_SUPPORTS);
        final String delimiter = this.server.getDelimiter();
        final Locale currentLocale = this.server.getServerLocale();
        return new Callable<Set<SLocale>>() {
            @Override
            public Set<SLocale> call() throws Exception {
                final String[] array = appResources.split(delimiter);
                final Set<SLocale> locales = Sets.newHashSet();
                for (final String locale : array) {
                    final Locale loc = Locale.forLanguageTag(locale);
                    final SLocale sLocale = new SLocale().setTag(locale).setCountry(loc.getCountry())
                                                         .setLanguage(loc.getLanguage());
                    if (loc.equals(currentLocale)) {
                        sLocale.setIsSet(true);
                    } else {
                        sLocale.setIsSet(false);
                    }
                    locales.add(sLocale);
                }
                return locales;
            }
        };
    }

    @RequestMapping(
            value = "/read",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody Callable<SLocalizedPreferences> getLocalizedPreferences() {
        final Locale locale = LocaleContextHolder.getLocale();
        final SMessageSource source = this.messageSource;
        return new Callable<SLocalizedPreferences>() {
            @Override
            public SLocalizedPreferences call() throws Exception {
                return source.getAll(locale);
            }
        };
    }

    @RequestMapping(
            value = "/read/{requestKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public
    @ResponseBody Callable<SLocalizedPreferences> getLocalizedPreferences(final @PathVariable String requestKey) {
        final String componentKey = this.uiComponents.get(requestKey);
        final Locale locale = LocaleContextHolder.getLocale();
        final SMessageSource source = this.messageSource;

        return new Callable<SLocalizedPreferences>() {
            @Override
            public SLocalizedPreferences call() throws Exception {
                final SLocalizedPreferences dataUI = source.getAll(componentKey, locale);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String
                            .format("For requestKey=%s and lang=%s returning dataUi=%s", requestKey, locale, dataUI));
                }
                return dataUI;
            }
        };
    }

    @PostConstruct
    private void resolveUIComponents() {
        final Map<String, String> map = Maps.newHashMap();
        final String unParsed = this.server.getProperty(SA_UI_COMPONENTS_KEY);
        if (unParsed != null) {
            final String[] parsed = unParsed.split(this.server.getDelimiter());
            for (final String chunk : parsed) {
                final String[] chunkCC = chunk.split(this.server.getValueDelimiter());
                map.put(chunkCC[0], chunkCC[1]);
            }
        }
        this.uiComponents = map;
    }

}
