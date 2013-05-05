package org.agatom.springatom.mvc.model.dao;

import org.agatom.springatom.model.meta.SMetaData;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SMetaDataDao {
    Set<SMetaData> getAll(@NotNull final Class<? extends SMetaData> clazz);

    SMetaData getById(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz);

    SMetaData getByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz);
}
