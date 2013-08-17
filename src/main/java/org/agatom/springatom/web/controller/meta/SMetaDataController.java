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

package org.agatom.springatom.web.controller.meta;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.controller.meta.bean.SMetaDataUI;
import org.apache.log4j.Logger;
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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/meta")
public class SMetaDataController
        implements MessageSourceAware {
    public static final  String SA_UI_COMPONENTS_KEY = "sa.ui.components";
    private static final Logger LOGGER               = Logger.getLogger(SMetaDataController.class);
    private MessageSource       messageSource;
    private Map<String, String> uiComponents;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.setAllowedFields("keys");
    }

    private Map<String, String> resolveUIComponents() {
        final Map<String, String> map = Maps.newLinkedHashMap();
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

    @RequestMapping(
            value = "/ui",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody Callable<SMetaDataUI> getUIMetaData(final String cmp) {
        final String componentKey = this.uiComponents.get(cmp);
        final Locale locale = LocaleContextHolder.getLocale();
        final String cfg = messageSource.getMessage(componentKey, null, null, locale);

        return new Callable<SMetaDataUI>() {
            @Override
            public SMetaDataUI call() throws Exception {
                final SMetaDataUI dataUI = new SMetaDataUI(String
                        .format("%s.%s", cmp, locale.getLanguage()));

                final List<String> splitted = Arrays.asList(cfg.split(","));

                for (final String part : splitted) {
                    final String[] strings = part.split("=");
                    final String cmpKey = strings[0];
                    final String cmpValue = strings[1];
                    dataUI.put(cmpKey, cmpValue);
                }

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String.format("For cmp=%s and lang=%s returning dataUi=%s", cmp, locale, dataUI));
                }

                return dataUI;
            }
        };
    }

    @Override
    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s initialized with rb=%s", SMetaDataController.class
                    .getSimpleName(), this.messageSource));
        }
        this.uiComponents = this.resolveUIComponents();
    }
}
