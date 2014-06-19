/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.webmvc.converters.du.converters;

import org.agatom.springatom.server.model.types.PersistentIdentity;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.infopages.InfoPageNotFoundException;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.agatom.springatom.webmvc.converters.du.component.IconGuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.LinkGuiComponent;
import org.agatom.springatom.webmvc.converters.du.component.TextGuiComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;
import org.springframework.util.ClassUtils;

import java.io.Serializable;

/**
 * {@code ToInfoPageLinkWebConverter}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = ToInfoPageLinkWebConverter.SELECTOR)
public class ToInfoPageLinkWebConverter
		extends AbstractWebConverter {
	/** Constant <code>SELECTOR="infoPageLink"</code> */
	public static final  String             SELECTOR   = "infoPageLink";
	private static final Logger             LOGGER     = Logger.getLogger(ToInfoPageLinkWebConverter.class);
	@Autowired
	private              InfoPageLinkHelper linkHelper = null;

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	protected Serializable doConvert(final String key, final Object value, final Persistable<?> persistable, final ComponentDataRequest webRequest) {
		if (!ClassUtils.isAssignableValue(Persistable.class, value)) {
			return null;
		}
		try {
			final String linkLabel = this.getLinkLabel(key, value, persistable);
			final Link link = this.linkHelper.getInfoPageLink((Persistable<Serializable>) value).withRel(linkLabel);

			final LinkGuiComponent linkGuiComponent = new LinkGuiComponent();

			linkGuiComponent.setValue(link.getHref());
			linkGuiComponent.setRawValue(link);
			linkGuiComponent.setLinkLabel(linkLabel);
			linkGuiComponent.setName(key);
			linkGuiComponent.setIcon(new IconGuiComponent().setCls("fa fa-link fa-fw"));

			return linkGuiComponent;
		} catch (InfoPageNotFoundException exp) {
			LOGGER.error(String.format("Failed to create InfoPage link component for key=%s,value=%s", key, value), exp);
			final TextGuiComponent component = new TextGuiComponent();
			component.setRawValue(((Persistable<Serializable>) value).getId());
			component.setValue(((PersistentIdentity) persistable).getIdentity());
			component.addDynamicProperty("exception", exp.getMessage());
			return component;
		}
	}

	protected String getLinkLabel(final String key, final Object value, final Persistable<?> persistable) {
		return ((PersistentIdentity) value).getIdentity();
	}
}
