package org.agatom.springatom.mvc.model.dao;

import org.agatom.springatom.model.meta.STypeData;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface STypeDataDao {
    Set<STypeData> getAll(@NotNull final Class clazz);

    STypeData getById(@NotNull final Long id, @NotNull final Class clazz);

    STypeData getByType(@NotNull final String type, @NotNull final Class clazz);
}
