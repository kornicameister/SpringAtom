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
 * aInteger with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.rbuilder.bean;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

/**
 * {@code RBuilderAssociation} is a plain {@code JavaBean} carrying information about
 * single {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} and all possible associations
 * that can be made with it.
 * <p/>
 * <b>Association</b> means that given {@link org.springframework.data.domain.Persistable} used to create {@link
 * RBuilderEntity} can be linked in either {@link javax.persistence.OneToMany} or {@link
 * javax.persistence.ManyToOne} with another {@link org.springframework.data.domain.Persistable}.
 * <p/>
 * <b>Information about one entity being in association with another</b> is carried by values retrieved from {@link org.agatom.springatom.web.rbuilder.bean.RBuilderBean#getId()}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RBuilderAssociation
		implements Serializable {

	private static final long         serialVersionUID = -8970556120241893260L;
	private              Integer      master           = -1;
	private              Set<Integer> children         = Sets.newHashSet();

	/**
	 * <p>Getter for the field <code>master</code>.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getMaster() {
		return master;
	}

	/**
	 * <p>Setter for the field <code>master</code>.</p>
	 *
	 * @param master a {@link java.lang.Integer} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation} object.
	 */
	public RBuilderAssociation setMaster(final Integer master) {
		this.master = master;
		return this;
	}

	/**
	 * <p>Getter for the field <code>children</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<Integer> getChildren() {
		return children;
	}

	/**
	 * <p>Setter for the field <code>children</code>.</p>
	 *
	 * @param children a {@link java.util.Set} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderAssociation} object.
	 */
	public RBuilderAssociation setChildren(final Set<Integer> children) {
		this.children = children;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(master, children);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RBuilderAssociation that = (RBuilderAssociation) o;

		return Objects.equal(this.master, that.master) &&
				Objects.equal(this.children, that.children);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(master)
				.addValue(children)
				.toString();
	}
}
