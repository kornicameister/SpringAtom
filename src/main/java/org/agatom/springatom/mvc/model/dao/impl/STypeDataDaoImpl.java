package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.model.meta.STypeData;
import org.agatom.springatom.mvc.model.dao.DaoSupport;
import org.agatom.springatom.mvc.model.dao.STypeDataDao;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.agatom.springatom.util.QueryConstants.Error.INACTIVE_SESSION;
import static org.agatom.springatom.util.QueryConstants.QueryBody.FROM_TABLE_BY_TABLE_NAME;
import static org.agatom.springatom.util.QueryConstants.QueryResult.FROM_TABLE_RESULT_WITH_CLASS_NAME;
import static org.agatom.springatom.util.QueryConstants.QueryTrace.READ_ENTITY_FROM_TABLE;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository(value = "STypeDataDao")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class STypeDataDaoImpl extends DaoSupport implements STypeDataDao {
    private static final Logger LOGGER = Logger.getLogger(STypeDataDaoImpl.class);
    private static final String TYPE = "type";

    @Override
    @Cacheable(value = "typedata")
    public Set<STypeData> getAll(@NotNull final Class<? extends STypeData> clazz) {
        if (this.getSession() == null) {
            LOGGER.warn(INACTIVE_SESSION);
            return null;
        }
        List<?> dataList = this.getSession()
                .createQuery(String.format(FROM_TABLE_BY_TABLE_NAME, clazz.getSimpleName()))
                .list();
        Set<STypeData> dataSet = new HashSet<>();
        for (Object entity : dataList) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format(READ_ENTITY_FROM_TABLE, entity, clazz.getSimpleName()));
            }
            dataSet.add((STypeData) entity);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(FROM_TABLE_RESULT_WITH_CLASS_NAME, dataSet.size(), clazz.getName()));
        }
        return dataSet;
    }

    @Override
    @Cacheable(value = "typedata", key = "#id")
    public STypeData getById(@NotNull final Long id, @NotNull final Class<? extends STypeData> clazz) {
        return (STypeData) this.getSession().load(clazz, id);
    }

    @Override
    @Cacheable(value = "typedata", key = "#type")
    public STypeData getByType(@NotNull final String type, @NotNull final Class<? extends STypeData> clazz) {
        return (STypeData) this.getSession().byNaturalId(clazz).using(TYPE, type).load();
    }
}
