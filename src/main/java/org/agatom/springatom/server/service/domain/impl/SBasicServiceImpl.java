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

package org.agatom.springatom.server.service.domain.impl;

import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.service.domain.SBasicService;
import org.agatom.springatom.server.service.domain.SUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.rest.core.event.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * {@code SBasicServiceImpl} is the locale class for all services
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@SuppressWarnings({"unchecked", "SpringJavaAutowiringInspection"})
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
abstract class SBasicServiceImpl<T extends Persistable<ID>, ID extends Serializable>
		implements SBasicService<T, ID>,
		ApplicationEventPublisherAware {
	@Autowired
	protected SBasicRepository<T, ID>   repository  = null;
	protected ApplicationEventPublisher publisher   = null;
	@Autowired(required = false)
	private SUserService userService = null;

	@PostConstruct
	private void initUserService() {
		if (ClassUtils.isAssignableValue(SUserService.class, this)) {
			this.userService = (SUserService) this;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	/**
	 * <p>toLong.</p>
	 *
	 * @param longs an array of long.
	 *
	 * @return an array of {@link java.lang.Long} objects.
	 */
	protected Long[] toLong(final long[] longs) {
		final Long[] bigLongs = new Long[longs.length];
		for (int i = 0, size = longs.length; i < size; i++) {
			bigLongs[i] = longs[i];
		}
		return bigLongs;
	}

	/** {@inheritDoc} */
	@Override
	public T findOne(final ID id) {
		return this.repository.findOne(id);
	}

	/** {@inheritDoc} */
	@Override
	public List<T> findAll() {
		return this.repository.findAll();
	}

	/** {@inheritDoc} */
	@Override
	public Page<T> findAll(final Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
	public T save(final T persistable) {
		Assert.notNull(persistable, "Persistable must not be null");
		boolean isNew = persistable.isNew();

		if (ClassUtils.isAssignableValue(AbstractAuditable.class, persistable)) {
			final AbstractAuditable<SUser, ID> abstractAuditable = (AbstractAuditable<SUser, ID>) persistable;
			final SUser user = this.userService.getAuthenticatedUser();
			final DateTime dateTime = DateTime.now();

			if (abstractAuditable.getCreatedBy() == null && isNew) {
				abstractAuditable.setCreatedBy(user);
				abstractAuditable.setCreatedDate(dateTime);
			}

			if (!isNew) {
				abstractAuditable.setLastModifiedBy(user);
				abstractAuditable.setLastModifiedDate(dateTime);
			}

		}

		if (isNew) {
			this.publisher.publishEvent(new BeforeCreateEvent(persistable));
		} else {
			this.publisher.publishEvent(new BeforeSaveEvent(persistable));
		}
		final T object = this.repository.saveAndFlush(persistable);
		if (isNew) {
			this.publisher.publishEvent(new AfterCreateEvent(object));
		} else {
			this.publisher.publishEvent(new AfterSaveEvent(object));
		}
		return object;
	}

	/** {@inheritDoc} */
	@Override
	public long count() {
		return this.repository.count();
	}

	/** {@inheritDoc} */
	@Override
	@Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
	public synchronized T deleteOne(final ID pk) {
		Assert.notNull(pk, "PK must not be null");
		final T source = findOne(pk);

		this.publisher.publishEvent(new BeforeDeleteEvent(source));
		this.repository.delete(pk);
		this.publisher.publishEvent(new AfterDeleteEvent(source));

		return source;
	}

	/** {@inheritDoc} */
	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		this.repository.deleteAll();
	}

	/** {@inheritDoc} */
	@Override
	public T withFullLoad(final T obj) {
		return obj;
	}

	/** {@inheritDoc} */
	@Override
	public List<T> withFullLoad(final Iterable<T> objects) {
		final List<T> tList = Lists.newArrayList();
		for (final T obj : objects) {
			tList.add(this.withFullLoad(obj));
		}
		return tList;
	}

	/** {@inheritDoc} */
	@Override
	public T detach(@NotNull final T object) {
		return this.repository.detach(object);
	}


}
