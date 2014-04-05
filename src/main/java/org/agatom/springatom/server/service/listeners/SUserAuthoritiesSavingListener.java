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

package org.agatom.springatom.server.service.listeners;

import com.google.common.collect.Sets;
import org.agatom.springatom.core.annotations.EventListener;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.agatom.springatom.server.model.beans.user.authority.SUserAuthority;
import org.agatom.springatom.server.repository.repositories.SAuthorityRepository;
import org.agatom.springatom.server.repository.repositories.user.SUserAuthorityRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * {@code SUserAuthoritiesSavingListener} is designed to persist {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority}
 * connected with given user over {@link org.agatom.springatom.server.model.beans.user.authority.SUserAuthority} link.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 05.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EventListener
public class SUserAuthoritiesSavingListener extends AbstractRepositoryEventListener<SUser> {
	private static final Logger                   LOGGER                  = Logger.getLogger(SUserAuthoritiesSavingListener.class);
	@Autowired
	private              SUserAuthorityRepository userAuthorityRepository = null;
	@Autowired
	private              SAuthorityRepository     authorityRepository     = null;

	@Override
	protected void onAfterCreate(final SUser user) {
		LOGGER.trace(String.format("onAfterCreate(user=%s)", user));
		final Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		if (!CollectionUtils.isEmpty(authorities)) {
			final Collection<SUserAuthority> authoritySet = Sets.newHashSetWithExpectedSize(authorities.size());
			for (GrantedAuthority authority : authorities) {
				if (ClassUtils.isAssignableValue(SAuthority.class, authority)) {
					final SAuthority entity = (SAuthority) authority;
					if (entity.isNew()) {
						final SAuthority sAuthority = this.authorityRepository.save(entity);
						authoritySet.add(new SUserAuthority(user, sAuthority));
					}
				}
			}
			if (!CollectionUtils.isEmpty(authoritySet)) {
				this.userAuthorityRepository.save(authoritySet);
			}
		} else {
			LOGGER.trace(String.format("%s has no authorities, skipping", user));
		}
	}

}
