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

package org.agatom.springatom.webmvc.controllers;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.cmp.locale.beans.LocalizedMessageRequest;
import org.agatom.springatom.cmp.locale.beans.SLocale;
import org.agatom.springatom.cmp.locale.beans.SLocalizedMessage;
import org.agatom.springatom.cmp.locale.beans.SLocalizedMessages;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * <p>SVLocaleController class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/data/lang")
public class SVLocaleController
        extends SVDefaultController {
    /** Constant <code>CONTROLLER_NAME="sa.controller.data.lang.LocaleDataResol"{trunked}</code> */
    protected static final String         CONTROLLER_NAME = "sa.controller.data.lang.LocaleDataResolverController";
    private static final   String[]       IGNORED_KEYS    = {"_dc", "page", "start", "limit"};
    private static final   Logger         LOGGER          = LoggerFactory.getLogger(SVLocaleController.class);
    @Value(value = "${sa.locale.supports}")
    private                String         appResources    = null;
    @Value(value = "${sa.delimiter}")
    private                String         delimiter       = null;
    @Autowired(required = false)
    private                SMessageSource messageSource   = null;

    /**
     * <p>initBinder.</p>
     *
     * @param binder a {@link org.springframework.web.bind.WebDataBinder} object.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.setDisallowedFields(IGNORED_KEYS);
        binder.setIgnoreInvalidFields(true);
        binder.setIgnoreUnknownFields(true);
    }

    /**
     * <p>getAvailableLocales.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    @ResponseBody
    @RequestMapping(
            value = "/available",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Set<SLocale> getAvailableLocales() {
        final Locale currentLocale = LocaleContextHolder.getLocale();

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

    /**
     * <p>getLocalizedPreferences.</p>
     *
     * @return a {@link SLocalizedMessages} object.
     */
    @ResponseBody
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SLocalizedMessages getLocalizedPreferences() {
        return this.messageSource.getLocalizedMessages(LocaleContextHolder.getLocale());
    }


    @ResponseBody
    @RequestMapping(
            value = "/by/locale",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getAllByLocale(@RequestParam(value = "lang", required = true) final Locale locale) {

        final SLocalizedMessages localizedMessages = this.messageSource.getLocalizedMessages(locale);
        final Set<SLocalizedMessage> preferences = localizedMessages.getPreferences();
        final Map<String, String> map = Maps.newHashMapWithExpectedSize(preferences.size());
        for (SLocalizedMessage preference : preferences) {
            map.put(preference.getKey(), preference.getMessage());
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/all/by/locale",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<Locale, SLocalizedMessages> getLocalizedPreferences(@RequestBody final Locale[] locales) {
        final Map<Locale, SLocalizedMessages> map = Maps.newHashMapWithExpectedSize(2);
        for (final Locale locale : locales) {
            map.put(locale, this.messageSource.getLocalizedMessages(locale));
        }
        return map;
    }

    /**
     * <p>getLocalizedPreferences.</p>
     *
     * @param request a {@link LocalizedMessageRequest} object.
     *
     * @return a {@link SLocalizedMessages} object.
     */
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
    public SLocalizedMessages getLocalizedPreferences(
            @RequestBody final LocalizedMessageRequest request
    ) {
        final Locale locale = LocaleContextHolder.getLocale();
        final Boolean pattern = request.isPattern();
        final String[] keys = request.getKeys();

        SLocalizedMessages localizedPreferences = new SLocalizedMessages();
        if (pattern) {
            localizedPreferences = messageSource.getLocalizedMessages(keys, locale, true);
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

    /**
     * <p>getBundle.</p>
     *
     * @param key    a {@link String} object.
     * @param locale a {@link java.util.Locale} object.
     *
     * @return a {@link SLocalizedMessage} object.
     */
    @ResponseBody
    @RequestMapping(value = "/bundle/{key:.+}")
    public SLocalizedMessage getBundle(@PathVariable("key") final String key, final Locale locale) {
        return this.messageSource.getLocalizedMessage(key, locale);
    }

}