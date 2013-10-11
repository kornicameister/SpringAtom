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

package org.agatom.springatom.webmvc.servlet.locale;

import com.google.common.collect.Sets;
import org.agatom.springatom.server.SpringAtomServer;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.LocalizedMessageRequest;
import org.agatom.springatom.web.locale.beans.SLocale;
import org.agatom.springatom.web.locale.beans.SLocalizedMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/data/lang")
public class SLocaleController {
    public static final  String   SA_LOCALE_SUPPORTS = "sa.locale.supports";
    private static final String[] IGNORED_KEYS       = {"_dc", "page", "start", "limit"};
    private static final Logger   LOGGER             = Logger.getLogger(SLocaleController.class);
    @Autowired
    protected SpringAtomServer server;
    @Autowired
    private   SMessageSource   messageSource;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.setDisallowedFields(IGNORED_KEYS);
        binder.setIgnoreInvalidFields(true);
        binder.setIgnoreUnknownFields(true);
    }

    @ResponseBody
    @RequestMapping(
            value = "/available",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Callable<Set<SLocale>> getAvailableLocales() {
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

    @ResponseBody
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Callable<SLocalizedMessages> getLocalizedPreferences() {
        final Locale locale = LocaleContextHolder.getLocale();
        final SMessageSource source = this.messageSource;
        return new Callable<SLocalizedMessages>() {
            @Override
            public SLocalizedMessages call() throws Exception {
                return source.getLocalizedMessages(locale);
            }
        };
    }

    @ResponseBody
    @RequestMapping(
            value = "/read",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.TEXT_PLAIN_VALUE
            }
    )
    public Callable<SLocalizedMessages> getLocalizedPreferences(
            @RequestBody final LocalizedMessageRequest request
    ) {
        final Locale locale = LocaleContextHolder.getLocale();
        final Boolean pattern = request.isPattern();
        final String[] keys = request.getKeys();

        return new Callable<SLocalizedMessages>() {
            @Override
            public SLocalizedMessages call() throws Exception {
                SLocalizedMessages localizedPreferences = new SLocalizedMessages();
                if (pattern) {
                    localizedPreferences = messageSource.getLocalizedMessages(keys, locale, pattern);
                } else {
                    for (final String key : keys) {
                        final String msg = messageSource.getMessage(key, locale);
                        if (msg != null) {
                            localizedPreferences.put(key, msg, locale);
                        }
                    }
                }
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String
                            .format("For keys=%s and lang=%s returning msgs=%d", Arrays
                                    .toString(keys), locale, localizedPreferences.size()));
                }
                return localizedPreferences;
            }
        };
    }

}