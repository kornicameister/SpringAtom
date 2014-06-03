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

package org.agatom.springatom.server.model.beans.car;

import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.car.CarMaster;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>SCarMaster class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SCarMaster.TABLE_NAME)
@Entity(name = SCarMaster.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSCarMaster", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SCarMaster
		extends PersistentObject<Long>
		implements CarMaster<SCar> {
	/** Constant <code>TABLE_NAME="car_master"</code> */
	public static final  String                      TABLE_NAME        = "car_master";
	/** Constant <code>ENTITY_NAME="SCarMaster"</code> */
	public static final  String                      ENTITY_NAME       = "SCarMaster";
	private static final long                        serialVersionUID  = -4932035593494629555L;
	@Embedded
	private              SCarMasterManufacturingData manufacturingData = null;
	@BatchSize(size = 10)
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH}, orphanRemoval = true, mappedBy = "carMaster")
	private              Set<SCar>                   children          = Sets.newHashSet();
	@Column(nullable = true, length = 1000, name = "thumbnailPath")
	private              String                      thumbnailPath     = null;

	/**
	 * <p>Constructor for SCarMaster.</p>
	 */
	public SCarMaster() {
		super();
		this.manufacturingData = new SCarMasterManufacturingData();
	}

	/**
	 * <p>Getter for the field <code>thumbnailPath</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}

	/**
	 * <p>Setter for the field <code>thumbnailPath</code>.</p>
	 *
	 * @param thumbnailPath a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	public SCarMaster setThumbnailPath(final String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getBrand() {
		return this.manufacturingData.getBrand();
	}

	/** {@inheritDoc} */
	@Override
	public String getModel() {
		return this.manufacturingData.getModel();
	}

	/** {@inheritDoc} */
	@Override
	public CountryCode getManufacturedIn() {
		return this.manufacturingData.getManufacturedIn();
	}

	/** {@inheritDoc} */
	@Override
	public String getManufacturedBy() {
		return this.manufacturingData.getManufacturedBy();
	}

	/**
	 * <p>setBrand.</p>
	 *
	 * @param brand a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 */
	public SCarMasterManufacturingData setBrand(final String brand) {
		return this.manufacturingData.setBrand(brand);
	}

	/**
	 * <p>setModel.</p>
	 *
	 * @param model a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 */
	public SCarMasterManufacturingData setModel(final String model) {
		return this.manufacturingData.setModel(model);
	}

	/**
	 * <p>setManufacturedIn.</p>
	 *
	 * @param manufacturedIn a {@link com.neovisionaries.i18n.CountryCode} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 */
	public SCarMasterManufacturingData setManufacturedIn(final CountryCode manufacturedIn) {
		return this.manufacturingData.setManufacturedIn(manufacturedIn);
	}

	/**
	 * <p>setManufacturedBy.</p>
	 *
	 * @param manufacturedBy a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 */
	public SCarMasterManufacturingData setManufacturedBy(final String manufacturedBy) {
		return this.manufacturingData.setManufacturedBy(manufacturedBy);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<SCar> iterator() {
		return this.getChildren().iterator();
	}

	/** {@inheritDoc} */
	@Override
	public Set<SCar> getChildren() {
		if (this.children == null) {
			this.children = Sets.newHashSet();
		}
		return children;
	}

	/**
	 * <p>Setter for the field <code>children</code>.</p>
	 *
	 * @param children a {@link java.util.Collection} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	public SCarMaster setChildren(final Collection<SCar> children) {
		this.children = Sets.newHashSet(children);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return this.getManufacturingData().getIdentity();
	}

	/**
	 * <p>Getter for the field <code>manufacturingData</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 */
	public SCarMasterManufacturingData getManufacturingData() {
		return manufacturingData;
	}

	/**
	 * <p>Setter for the field <code>manufacturingData</code>.</p>
	 *
	 * @param manufacturingData a {@link org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	public SCarMaster setManufacturingData(final SCarMasterManufacturingData manufacturingData) {
		this.manufacturingData = manufacturingData;
		return this;
	}
}
