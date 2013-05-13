package org.agatom.springatom.mvc.model.provider.impl;

import org.agatom.springatom.model.meta.SMetaData;
import org.agatom.springatom.mvc.model.provider.DefaultDataProvider;
import org.agatom.springatom.mvc.model.provider.SMetaDataProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings("SpringElInspection")
@Repository(value = "STypeDataDao")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class SMetaDataProviderImpl extends DefaultDataProvider<SMetaData> implements SMetaDataProvider {
    private static final String TYPE = "type";

    @Override
    @Cacheable(value = "typedata")
    public Set<SMetaData> getAll(@NotNull final Class<? extends SMetaData> clazz) {
        this.setTarget(clazz);
        return this.getAll();
    }

    @Override
    @Cacheable(value = "typedata", key = "#id")
    public SMetaData getById(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz) {
        this.setTarget(clazz);
        return this.getById(id);
    }

    @Override
    @Cacheable(value = "typedata", key = "#type")
    public SMetaData getByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz) {
        return (SMetaData) this.getSession().byNaturalId(clazz).using(TYPE, type).load();
    }
}
