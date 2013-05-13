package org.agatom.springatom.mvc.model.provider;

import org.agatom.springatom.model.Persistable;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.agatom.springatom.util.QueryConstants.Error.INACTIVE_SESSION;
import static org.agatom.springatom.util.QueryConstants.QueryBody.FROM_TABLE_BY_TABLE_NAME;
import static org.agatom.springatom.util.QueryConstants.QueryResult.FROM_TABLE_RESULT_WITH_CLASS_NAME;
import static org.agatom.springatom.util.QueryConstants.QueryTrace.READ_ENTITY_FROM_TABLE;


@SuppressWarnings("unchecked")
@Repository(value = "DaoSupport")
abstract public class DefaultDataProvider<T extends Persistable> implements DataProvider<T> {
    private static final Logger LOGGER = Logger.getLogger(DefaultDataProvider.class);

    @Resource
    SessionFactory sessionFactory;

    @NotNull
    private Class<? extends Persistable> target;

    protected void setTarget(final Class<? extends Persistable> target) {
        this.target = target;
    }

    @Override
    public T getById(@NotNull final Long id) {
        if (this.getSession() == null) {
            LOGGER.warn(INACTIVE_SESSION);
            return null;
        }
        return (T) this.getSession().load(this.target, id);
    }

    @Override
    public Set<T> getAll() {
        if (this.getSession() == null) {
            LOGGER.warn(INACTIVE_SESSION);
            return null;
        }
        List<?> dataList = this.getSession()
                .createQuery(String.format(FROM_TABLE_BY_TABLE_NAME, this.target.getSimpleName()))
                .list();
        Set<T> dataSet = new HashSet<>();
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

    protected Session getSession() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Getting session factory, sessionFactory=%s", this.sessionFactory));
        }
        return this.sessionFactory.getCurrentSession();
    }

    @Autowired(required = true)
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Initialized sessionFactory=%s", this.sessionFactory));
        }
    }
}
