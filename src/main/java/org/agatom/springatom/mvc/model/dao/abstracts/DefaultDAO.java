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

package org.agatom.springatom.mvc.model.dao.abstracts;

import org.agatom.springatom.model.Persistable;
import org.agatom.springatom.util.QueryConstants;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.agatom.springatom.util.QueryConstants.Error.FAILED_TO_SAVE_N_OBJECTS;
import static org.agatom.springatom.util.QueryConstants.Error.FAILED_TO_SAVE_OBJECT;
import static org.agatom.springatom.util.QueryConstants.QueryBody.FROM_TABLE_BY_TABLE_NAME;
import static org.agatom.springatom.util.QueryConstants.QueryResult.*;
import static org.agatom.springatom.util.QueryConstants.QueryTrace.READ_ENTITY_FROM_TABLE;


@SuppressWarnings({"UnusedDeclaration", "unchecked", "SpringElInspection"})
@Repository(value = "DefaultCRUDRepository")
@Transactional
abstract public class DefaultDAO<T, ID extends Serializable> extends _DefaultDao
        implements CrudRepository<T, ID> {
    private static final Logger LOGGER = Logger.getLogger(DefaultDAO.class);
    @Resource
    SessionFactory sessionFactory;

    public DefaultDAO() {
        super();
        this.target = this.getTargetClazz();
    }

    @Override
    public void setTarget(final Class<? extends Persistable> target) {
        this.target = target;
    }

    @Autowired(required = true)
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Initialized sessionFactory=%s", this.sessionFactory));
        }
    }

    public void update(final Object obj) {
        if (this.isQueryPossible(String.format(UPDATE_METHOD, obj))) {
            this.getSession().update(obj);
        }
    }

    @Override
    protected Session getSession() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Getting session factory, sessionFactory=%s", this.sessionFactory));
        }
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public <S extends T> S save(final S s) {
        if (!this.isQueryPossible(String.format(CREATE_METHOD, s.getClass()))) {
            return null;
        }
        Serializable savedObject = this.getSession().save(s);
        if (savedObject == null && LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(FAILED_TO_SAVE_OBJECT, s.getClass().getName()));
        } else if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(SAVED_OBJECT_TO_TABLE, s.getClass().getName()));
        }
        return (S) this.getSession().load(this.target, savedObject);
    }

    @Override
    @Transactional
    public Iterable save(final Iterable iterable) {
        if (!this.isQueryPossible(String.format(CREATE_METHOD, iterable))) {
            return null;
        }
        Set<Object> saved = new HashSet<>();
        String clazzName = null;
        for (Object object : iterable) {
            final Object savedObject = this.save((T) object);
            if (savedObject != null) {
                if (clazzName == null) {
                    clazzName = savedObject.getClass().getName();
                }
                saved.add(savedObject);
            }
        }
        if (saved.size() != 0 && LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(SAVED_N_OBJECTS_TO_TABLE, saved.size(), clazzName));
        } else if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(FAILED_TO_SAVE_N_OBJECTS, saved.size(), clazzName));
        }
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "singleObjectCache", key = "#serializable")
    public T findOne(final Serializable serializable) {
        if (!this.isQueryPossible(String.format(FIND_METHOD, serializable))) {
            return null;
        }
        return (T) this.getSession().load(this.target, serializable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "singleObjectCache", key = "#serializable")
    public boolean exists(final Serializable serializable) {
        return this.isQueryPossible(String.format(EXISTS_METHOD, serializable)) &&
                this.getSession().byId(this.target).getReference(serializable) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public Iterable findAll() {
        if (!this.isQueryPossible(String.format(FIND_METHOD, "all"))) {
            return null;
        }
        List<?> dataList = this.getSession()
                .createQuery(String.format(FROM_TABLE_BY_TABLE_NAME, this.target.getSimpleName()))
                .list();
        HashSet<T> dataSet = new HashSet<>();
        for (Object entity : dataList) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format(READ_ENTITY_FROM_TABLE, entity, this.target.getSimpleName()));
            }
            dataSet.add((T) entity);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(FROM_TABLE_RESULT_WITH_CLASS_NAME, dataSet.size(), this.target.getName()));
        }
        return dataSet;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable findAll(final Iterable iterable) {
        return this.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        if (this.isQueryPossible(COUNT_METHOD)) {
            List<?> dataList = this.getSession()
                    .createQuery(String.format(FROM_TABLE_BY_TABLE_NAME, this.target.getSimpleName()))
                    .list();
            return dataList.size();
        }
        return 0;
    }

    @Override
    @Transactional
    public void delete(final Serializable serializable) {
        if (this.isQueryPossible(String.format(DELETE_METHOD, serializable))) {
            this.delete(this.load(serializable));
        }
    }

    @Override
    @Transactional
    public void delete(final T t) {
        if (this.isQueryPossible(String.format(DELETE_METHOD, t))) {
            final T load = this.load(t);
            if (t != null) {
                this.getSession().delete(load);
            }
        }
    }

    @Override
    @Transactional
    public void delete(final Iterable iterable) {
        if (this.isQueryPossible(String.format(DELETE_METHOD, iterable))) {
            int deleted = 0;
            for (Object object : iterable) {
                this.delete((T) object);
                deleted++;
            }
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format(QueryConstants.QueryResult.DELETED_N_OBJECTS_TO_TABLE, deleted, this.target.getName()));
            }
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        if (this.isQueryPossible(String.format(DELETE_METHOD, "all"))) {

            int deletedBeans = 0;
            Iterable<T> allObjects = this.findAll();
            for (T entity : allObjects) {
                this.delete(entity);
                deletedBeans++;
            }

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format(QueryConstants.QueryResult.DELETED_N_OBJECTS_TO_TABLE, deletedBeans, this.target.getName()));
            }
        }
    }

    protected T load(T t) {
        Persistable persistable = (Persistable) t;
        if (this.isQueryPossible(String.format(LOAD_METHOD, persistable.getId()))) {
            return (T) this.getSession().load(this.target, persistable.getId());
        }
        return null;
    }

    protected T load(final Serializable serializable) {
        if (this.isQueryPossible(String.format(LOAD_METHOD, serializable))) {
            return (T) this.getSession().load(this.target, serializable);
        }
        return null;
    }

}
