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

package org.agatom.springatom.server.model.beans.user;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.agatom.springatom.server.model.beans.user.authority.SUserAuthority;
import org.agatom.springatom.server.model.beans.user.embeddable.SUserCredentials;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.user.SRole;
import org.agatom.springatom.server.model.types.user.SSecuredUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code SUser} is the customized version of Spring's {@link org.springframework.security.core.userdetails.User} class
 * boosted with ability
 * to be used with {@code QueryDSL} and being associated with {@link org.agatom.springatom.server.model.beans.person.SPerson}
 * instance.
 *
 * @author kornicamaister
 * @version 0.0.2
 * @since 0.0.1
 */

@Entity(name = SUser.ENTITY_NAME)
@Table(name = SUser.TABLE_NAME)
@AttributeOverride(
		name = "id",
		column = @Column(
				name = "idSUser",
				updatable = false,
				nullable = false)
)
@ReportableEntity
public class SUser
		extends PersistentVersionedObject
		implements SSecuredUser {
	protected static final String              ENTITY_NAME           = "SUser";
	protected static final String              TABLE_NAME            = "suser";
	private static final   long                serialVersionUID      = -5918876176226057267L;
	@Audited
	@Embedded
	private                SUserCredentials    credentials           = null;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "person", referencedColumnName = "idSPerson")
	private                SPerson             person                = null;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
	private                Set<SUserAuthority> roles                 = null;
	@Type(type = "boolean")
	@Column(name = "enabled")
	private                Boolean             enabled               = Boolean.TRUE;
	@Type(type = "boolean")
	@Column(name = "accountNonExpired")
	private                boolean             accountNonExpired     = Boolean.TRUE;
	@Type(type = "boolean")
	@Column(name = "accountNonLocked")
	private                boolean             accountNonLocked      = Boolean.TRUE;
	@Type(type = "boolean")
	@Column(name = "credentialsNonExpired")
	private                boolean             credentialsNonExpired = Boolean.TRUE;

	public SUser() {
		this.credentials = new SUserCredentials();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.roles == null) {
			this.roles = Sets.newHashSet();
		}
		Set<SAuthority> roles = new HashSet<>();
		for (SUserAuthority userToRole : this.roles) {
			roles.add(userToRole.getAuthority());
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return this.credentials.getPassword();
	}

	@Override
	public String getUsername() {
		return this.credentials.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public void setEnabled(final boolean disabled) {
		this.enabled = disabled;
	}

	@Override
	public boolean addAuthority(final GrantedAuthority authority) {
		return roles.add(new SUserAuthority(this, (SAuthority) authority));
	}

	@Override
	public boolean removeAuthority(final GrantedAuthority role) {
		SUserAuthority toDelete = null;
		for (SUserAuthority userToRole : this.roles) {
			if (userToRole.getAuthority().equals(role)) {
				toDelete = userToRole;
				break;
			}
		}
		return toDelete != null && this.roles.remove(toDelete);
	}

	@Override
	public boolean hasAuthority(final GrantedAuthority role) {
		final Set<? extends GrantedAuthority> rolesOut = Sets.newHashSet(role);
		return this.hasAuthorities(rolesOut);
	}

	@Override
	public boolean hasAuthorities(final Collection<? extends GrantedAuthority> roles) {
		final Set<SRole> rolesIn = Sets.newHashSet();
		for (SUserAuthority userToRole : this.roles) {
			rolesIn.add(userToRole.getAuthority().getRole());
		}
		return FluentIterable.from(rolesIn).filter(
				new Predicate<SRole>() {
					@Override
					public boolean apply(@Nullable final SRole input) {
						boolean result = false;
						for (final GrantedAuthority authority : roles) {
							if (authority instanceof SAuthority) {
								result = ((SAuthority) authority).getRole().equals(input);
								if (result) {
									break;
								}
							}
						}
						return result;
					}
				}
		).size() > 0;
	}

	public void setUsername(final String login) {
		this.credentials.setUsername(login);
	}

	public void setPassword(final String password) {
		this.credentials.setPassword(password);
	}

	public void setAuthorities(final Set<? extends GrantedAuthority> roles) {
		if (this.roles == null) {
			this.roles = new HashSet<>();
		}
		for (final GrantedAuthority role : roles) {
			this.addAuthority(role);
		}
	}

	public void clearAuthorities() {
		this.roles = null;
	}

	public SPerson getPerson() {
		return person;
	}

	public void setPerson(final SPerson person) {
		this.person = person;
	}
}
