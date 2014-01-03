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

package org.agatom.springatom.web.component.helper.impl;

import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.component.helper.ComponentHelper;
import org.agatom.springatom.web.locale.SMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Link;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Qualifier(value = "ComponentHelper")
public class DefaultComponentHelper
        implements ComponentHelper {

    @Autowired
    protected SMessageSource messageSource;

    @Override
    public String entitleFromMessageKey(final Localized localized) {
        return this.messageSource.getMessage(
                localized.getMessageKey(),
                LocaleContextHolder.getLocale()
        );
    }

    @Override
    public Link getBuilderLink() {
        return new Link(String.format("/app/tableBuilder/inContext")).withRel("inContextBuilder");
    }
}
