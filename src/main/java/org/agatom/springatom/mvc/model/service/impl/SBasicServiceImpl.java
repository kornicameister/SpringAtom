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

package org.agatom.springatom.mvc.model.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import org.agatom.springatom.jpa.SBasicRepository;
import org.agatom.springatom.jpa.impl.SRepositoryImpl;
import org.agatom.springatom.mvc.model.service.base.SBasicService;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * {@code SBasicServiceImpl} is the base class for all services
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@SuppressWarnings("unchecked")
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
abstract class SBasicServiceImpl<T extends Persistable, ID extends Serializable, R extends JpaRepository>
        implements SBasicService<T, ID, R> {
    private static final Logger LOGGER = Logger.getLogger(SBasicServiceImpl.class);
    private SBasicRepository    basicRepository;
    private Pageable            pageable;
    private OrderSpecifier<?>   order;
    private List<Expression<?>> groups;

    @Override
    public void autoWireRepository(final R repo) {
        this.basicRepository = (SBasicRepository) repo;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Repository set to %s", this.basicRepository));
        }
    }

    @Override
    public T findOne(final ID id) {
        return (T) this.basicRepository.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return this.basicRepository.findAll();
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return this.basicRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public T save(final T persistable) {
        Preconditions
                .checkArgument(persistable != null, "Persistable must not be null");
        return (T) this.basicRepository.saveAndFlush(persistable);
    }

    @Override
    public Long count() {
        return this.basicRepository.count();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public void deleteOne(final ID pk) {
        Preconditions.checkArgument(pk != null, "PK must not be null");
        this.basicRepository.delete(pk);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        this.basicRepository.deleteAll();
    }

    public JPQLQuery retrieveQuery() {
        final SRepositoryImpl sRepository = (SRepositoryImpl) this.basicRepository;
        return sRepository.createQuery();
    }

    protected void addFlex(Object param) {
        if (param.getClass().isAssignableFrom(Pageable.class)) {
            this.pageable = (Pageable) param;
        } else if (param.getClass().isAssignableFrom(OrderSpecifier.class)) {
            this.order = (OrderSpecifier) param;
        } else if (param.getClass().isArray()) {
            final Object[] obj = (Object[]) param;
            final List<Expression<?>> expressions = Lists.newArrayList();
            for (final Object tObject : obj) {
                if (tObject.getClass().isAssignableFrom(Expression.class)) {
                    expressions.add((Expression<?>) tObject);
                }
            }
            this.groups = expressions;
        }
    }

}
