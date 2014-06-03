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

import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.car.Car;
import org.agatom.springatom.server.model.types.car.FuelType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>SCar class.</p>
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SCar.TABLE_NAME)
@Entity(name = SCar.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSCar", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SCar
		extends PersistentVersionedObject
		implements Car {
	/** Constant <code>TABLE_NAME="cars"</code> */
	public static final  String     TABLE_NAME       = "cars";
	/** Constant <code>ENTITY_NAME="SCar"</code> */
	public static final  String     ENTITY_NAME      = "SCar";
	private static final long       serialVersionUID = -1473162805427581686L;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "carMaster", referencedColumnName = "idSCarMaster", updatable = false)
	private              SCarMaster carMaster        = null;
	@NotNull
	@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "owner", referencedColumnName = "idSUser", updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private              SUser      owner            = null;
	@Audited
	@NotNull
	@Column(nullable = false, length = 45, name = "licencePlate", unique = true)
	@NaturalId(mutable = true)
	private              String     licencePlate     = null;
	@NotNull
	@Column(nullable = false, length = 17, name = "vinNumber", unique = true)
	@NaturalId(mutable = true)
	private              String     vinNumber        = null;
	@Audited
	@NotNull
	@Type(type = "org.hibernate.type.EnumType")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "sc_fuelType", updatable = true, unique = false, length = 50, nullable = true)
	private              FuelType   fuelType         = null;
	@NotNull
	@Min(value = 1900, message = "Year Production must be past 1900")
	@Column(name = "sc_yp", updatable = true, unique = false, length = 50, nullable = true)
	private              Long       yearOfProduction = null;

	/**
	 * <p>Getter for the field <code>carMaster</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	public SCarMaster getCarMaster() {
		return carMaster;
	}

	/**
	 * <p>Setter for the field <code>carMaster</code>.</p>
	 *
	 * @param carMaster a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setCarMaster(final SCarMaster carMaster) {
		this.carMaster = carMaster;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getLicencePlate() {
		return licencePlate;
	}

	/**
	 * <p>Setter for the field <code>licencePlate</code>.</p>
	 *
	 * @param registrationNumber a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setLicencePlate(final String registrationNumber) {
		this.licencePlate = registrationNumber;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public SUser getOwner() {
		return owner;
	}

	/**
	 * <p>Setter for the field <code>owner</code>.</p>
	 *
	 * @param client a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setOwner(final SUser client) {
		this.owner = client;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getBrand() {
		return this.carMaster.getBrand();
	}

	/**
	 * <p>setBrand.</p>
	 *
	 * @param brand a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setBrand(final String brand) {
		this.carMaster.setBrand(brand);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getModel() {
		return this.carMaster.getModel();
	}

	/**
	 * <p>setModel.</p>
	 *
	 * @param model a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setModel(final String model) {
		this.carMaster.setModel(model);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public FuelType getFuelType() {
		return this.fuelType;
	}

	/**
	 * <p>Setter for the field <code>fuelType</code>.</p>
	 *
	 * @param fuelType a {@link org.agatom.springatom.server.model.types.car.FuelType} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setFuelType(final FuelType fuelType) {
		this.fuelType = fuelType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Long getYearOfProduction() {
		return this.yearOfProduction;
	}

	/**
	 * <p>Setter for the field <code>yearOfProduction</code>.</p>
	 *
	 * @param yearOfProduction a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setYearOfProduction(final Long yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
		return this;
	}

	/**
	 * <p>Getter for the field <code>vinNumber</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getVinNumber() {
		return vinNumber;
	}

	/**
	 * <p>Setter for the field <code>vinNumber</code>.</p>
	 *
	 * @param vinNumber a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	public SCar setVinNumber(final String vinNumber) {
		this.vinNumber = vinNumber;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentity() {
		return String.format("%s [%s]", this.licencePlate, this.vinNumber);
	}
}
