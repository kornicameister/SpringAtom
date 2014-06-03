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

package org.agatom.springatom.server.service.domain;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;
import org.agatom.springatom.core.RegexpPatterns;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.support.constraints.BrandOrModel;
import org.agatom.springatom.server.service.support.constraints.LicencePlatePL;
import org.agatom.springatom.server.service.support.constraints.VinNumber;
import org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.agatom.springatom.server.service.support.exceptions.UnambiguousResultServiceException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>SCarService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SCarService
		extends SService<SCar, Long, Integer> {

	/** {@inheritDoc} */
	@Override
	SCar save(final SCar persistable);

	/**
	 * <p>findByMaster.</p>
	 *
	 * @param brand a {@link java.lang.String} object.
	 * @param model a {@link java.lang.String} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SCar> findByMaster(
			@BrandOrModel
			final String brand,
			@BrandOrModel
			final String model);

	/**
	 * <p>findByMaster.</p>
	 *
	 * @param masterId a {@link java.lang.Long} object.
	 *
	 * @return a {@link java.util.List} object.
	 */
	@NotNull
	List<SCar> findByMaster(
			@NotNull
			@NotEmpty
			final Long... masterId);

	/**
	 * <p>findMaster.</p>
	 *
	 * @param carId a long.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCarMaster} object.
	 */
	@NotNull
	SCarMaster findMaster(final long carId);

	/**
	 * <p>findBy.</p>
	 *
	 * @param attribute a {@link org.agatom.springatom.server.service.domain.SCarService.SCarAttribute} object.
	 * @param value     a {@link java.lang.Object} object.
	 *
	 * @return a {@link java.util.List} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.UnambiguousResultServiceException if any.
	 */
	@NotNull
	List<SCar> findBy(
			@NotNull
			final SCarAttribute attribute,
			@NotNull
			final Object value) throws
			UnambiguousResultServiceException;

	/**
	 * <p>newCar.</p>
	 *
	 * @param brand        a {@link java.lang.String} object.
	 * @param model        a {@link java.lang.String} object.
	 * @param licencePlate a {@link java.lang.String} object.
	 * @param vinNumber    a {@link java.lang.String} object.
	 * @param ownerId      a long.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException if any.
	 */
	@NotNull
	SCar newCar(
			@Pattern(regexp = RegexpPatterns.BIG_FIRST_LETTER_PATTERN, message = "Brand or service must starts with the capitalized letter")
			final String brand,
			@Pattern(regexp = RegexpPatterns.BIG_FIRST_LETTER_PATTERN, message = "Brand or service must starts with the capitalized letter")
			final String model,
			@LicencePlatePL
			final String licencePlate,
			@VinNumber
			final String vinNumber,
			final long ownerId) throws EntityDoesNotExistsServiceException;

	/**
	 * <p>newOwner.</p>
	 *
	 * @param idCar    a long.
	 * @param idClient a long.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 *
	 * @throws org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException if any.
	 * @throws org.agatom.springatom.server.service.domain.SCarService$InvalidOwnerException               if any.
	 */
	@NotNull
	SCar newOwner(final long idCar, final long idClient) throws EntityDoesNotExistsServiceException, InvalidOwnerException;

	/**
	 * Retrieves collection of {@link org.agatom.springatom.server.service.domain.SCarService.Owner}
	 *
	 * @return collection of {@link org.agatom.springatom.server.service.domain.SCarService.Owner}
	 */
	@NotNull
	Collection<Owner> getOwners();

	public static enum SCarAttribute {
		LICENCE_PLATE(QSCar.sCar.licencePlate),
		OWNER(QSCar.sCar.owner.id),
		VIN_NUMBER(QSCar.sCar.vinNumber);
		private final Path<?> expression;

		SCarAttribute(final Path<?> expression) {
			this.expression = expression;
		}

		@SuppressWarnings("unchecked")
		public BooleanExpression eq(final Object value) {
			if (value instanceof String) {
				final StringPath path = (StringPath) this.expression;
				return path.eq((String) value);
			} else if (value instanceof Long) {
				final NumberPath<Long> path = (NumberPath<Long>) this.expression;
				return path.eq((Long) value);
			}
			return null;
		}
	}

	/**
	 * {@link org.agatom.springatom.server.service.domain.SCarService.Owner}
	 * wraps {@link org.agatom.springatom.server.model.beans.user.SUser} that are possible
	 * to be associated with {@link org.agatom.springatom.server.model.beans.car.SCar} or
	 * are already associated with it
	 */
	public static class Owner
			implements Serializable {
		private static final long      serialVersionUID = 4675008331100577522L;
		private              SUser     owner            = null;
		private              Set<SCar> cars             = Sets.newHashSet();

		public SUser getOwner() {
			return owner;
		}

		public Owner setOwner(final SUser owner) {
			this.owner = owner;
			return this;
		}

		public Set<SCar> getCars() {
			return cars;
		}

		public Owner setCars(final Set<SCar> cars) {
			this.cars = cars;
			return this;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(serialVersionUID, owner, cars);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Owner that = (Owner) o;

			return Objects.equal(this.owner, that.owner) &&
					Objects.equal(this.cars, that.cars);
		}

	}

	public static class InvalidOwnerException
			extends ServiceException {
		private static final long serialVersionUID = 9104651445939425769L;

		public InvalidOwnerException(final SCar car, final SUser owner) {
			super(SCar.class, String.format("Owner for %s does not differ from current one %s", car, owner));
		}
	}
}
