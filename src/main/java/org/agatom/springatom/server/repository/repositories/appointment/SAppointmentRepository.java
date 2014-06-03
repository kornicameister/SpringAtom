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

package org.agatom.springatom.server.repository.repositories.appointment;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * {@code SAppointmentRepository} supports CRUD operations, backend with {@link org.springframework.data.jpa.repository.support.Querydsl}
 * support.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(value = SAppointmentRepository.REPO_NAME)
@RepositoryRestResource(
		itemResourceRel = SAppointmentRepository.REST_REPO_REL,
		path = SAppointmentRepository.REST_REPO_PATH,
		collectionResourceRel = SAppointmentRepository.COLLECTION_REL,
		itemResourceDescription = @Description("Get single SAppointment"),
		collectionResourceDescription = @Description("Get collection of SAppointments")
)
public interface SAppointmentRepository
		extends SBasicRepository<SAppointment, Long> {
	/** Constant <code>REPO_NAME="AppointmentsRepository"</code> */
	String REPO_NAME      = "AppointmentsRepository";
	/** Constant <code>REST_REPO_REL="rest.appointment"</code> */
	String REST_REPO_REL  = "rest.appointment";
	/** Constant <code>REST_REPO_PATH="appointment"</code> */
	String REST_REPO_PATH = "appointment";
	/** Constant <code>COLLECTION_REL="appointments"</code> */
	String COLLECTION_REL = "appointments";

	/**
	 * Returns {@link org.springframework.data.domain.Page} of {@link org.agatom.springatom.server.model.beans.appointment.SAppointment} which are
	 * located between two dates on the timeliness. Note that the the date range is exclusive in this case.
	 *
	 * @param begin    begin date
	 * @param end      end date
	 * @param pageable pageable interface
	 *
	 * @return {@link org.springframework.data.domain.Page} of {@link org.agatom.springatom.server.model.beans.appointment.SAppointment}
	 */
	@RestResource(
			rel = "beginAfterAndEndBefore",
			path = "beginAfterAndEndBefore",
			description = @Description("Looks up for appointment in given range using DateTime as boundaries")
	)
	Page<SAppointment> findByBeginAfterAndEndBefore(@Param(value = "begin") DateTime begin, @Param(value = "end") DateTime end, Pageable pageable);

	/**
	 * <p>findByBeginAfterAndEndBefore.</p>
	 *
	 * @param begin    a long.
	 * @param end      a long.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(
			rel = "beginAfterAndEndBeforeTimestamp",
			path = "beginAfterAndEndBeforeTimestamp",
			description = @Description("Looks up for appointment in given range using timestamps as boundaries")
	)
	@Query(value = "select t from SAppointment as t where t.beginTs >= :begin and t.endTs <= :end")
	Page<SAppointment> findByBeginAfterAndEndBefore(@Param(value = "begin") long begin, @Param(value = "end") long end, Pageable pageable);

	/**
	 * <p>findByBeginAfter.</p>
	 *
	 * @param begin    a {@link org.joda.time.DateTime} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByBeginAfter(@Param(value = "begin") DateTime begin, Pageable pageable);

	/**
	 * <p>findByBeginBefore.</p>
	 *
	 * @param begin    a {@link org.joda.time.DateTime} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByBeginBefore(@Param(value = "begin") DateTime begin, Pageable pageable);

	/**
	 * <p>findByEndAfter.</p>
	 *
	 * @param begin    a {@link org.joda.time.DateTime} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByEndAfter(@Param(value = "end") DateTime begin, Pageable pageable);

	/**
	 * <p>findByEndBefore.</p>
	 *
	 * @param begin    a {@link org.joda.time.DateTime} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByEndBefore(@Param(value = "end") DateTime begin, Pageable pageable);

	/**
	 * <p>findByCarLicencePlate.</p>
	 *
	 * @param licencePlate a {@link java.lang.String} object.
	 * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByCarLicencePlate(@Param(value = "licencePlate") String licencePlate, Pageable pageable);

	/**
	 * <p>findByAssignee.</p>
	 *
	 * @param assignee a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByAssignee(@Param(value = "assignee") SUser assignee, Pageable pageable);

	/**
	 * <p>findByReporter.</p>
	 *
	 * @param assignee a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<SAppointment> findByReporter(@Param(value = "reporter") SUser assignee, Pageable pageable);
}
