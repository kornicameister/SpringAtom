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

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.service.domain.SBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        implements SBasicService<T, ID> {

    @Autowired
    protected SBasicRepository<T, ID> repository;


    @Override
    public T findOne(final ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public T save(final T persistable) {
        Preconditions
                .checkArgument(persistable != null, "Persistable must not be null");
        return this.repository.saveAndFlush(persistable);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public void deleteOne(final ID pk) {
        Preconditions.checkArgument(pk != null, "PK must not be null");
        this.repository.delete(pk);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public T withFullLoad(final T obj) {
        return obj;
    }

    @Override
    public List<T> withFullLoad(final Iterable<T> objects) {
        final List<T> tList = Lists.newArrayList();
        for (final T obj : objects) {
            tList.add(this.withFullLoad(obj));
        }
        return tList;
    }

    protected Long[] toLong(final long[] longs) {
        final Long[] bigLongs = new Long[longs.length];
        for (int i = 0, size = longs.length ; i < size ; i++) {
            bigLongs[i] = longs[i];
        }
        return bigLongs;
    }
}
