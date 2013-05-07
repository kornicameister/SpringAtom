package org.agatom.springatom.mvc.model.provider.impl;

import org.agatom.springatom.model.meta.SMetaData;
import org.agatom.springatom.mvc.model.provider.DataProvider;
import org.agatom.springatom.mvc.model.provider.SMetaDataProvider;
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
public class SMetaDataProviderImpl extends DataProvider implements SMetaDataProvider {
    private static final Logger LOGGER = Logger.getLogger(SMetaDataProviderImpl.class);

    private static final String TYPE = "type";

    @Override
    @Cacheable(value = "typedata")
    public Set<SMetaData> getAll(@NotNull final Class<? extends SMetaData> clazz) {
        if (this.getSession() == null) {
            LOGGER.warn(INACTIVE_SESSION);
            return null;
        }
        List<?> dataList = this.getSession()
                .createQuery(String.format(FROM_TABLE_BY_TABLE_NAME, clazz.getSimpleName()))
                .list();
        Set<SMetaData> dataSet = new HashSet<>();
        for (Object entity : dataList) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format(READ_ENTITY_FROM_TABLE, entity, clazz.getSimpleName()));
            }
            dataSet.add((SMetaData) entity);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(FROM_TABLE_RESULT_WITH_CLASS_NAME, dataSet.size(), clazz.getName()));
        }
        return dataSet;
    }

    @Override
    @Cacheable(value = "typedata", key = "#id")
    public SMetaData getById(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz) {
        return (SMetaData) this.getSession().load(clazz, id);
    }

    @Override
    @Cacheable(value = "typedata", key = "#type")
    public SMetaData getByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz) {
        return (SMetaData) this.getSession().byNaturalId(clazz).using(TYPE, type).load();
    }
}
