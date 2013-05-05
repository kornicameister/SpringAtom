package org.agatom.springatom.mvc.model.bo.impl;

import org.agatom.springatom.model.meta.SMetaData;
import org.agatom.springatom.mvc.model.bo.SMetaDataBo;
import org.agatom.springatom.mvc.model.dao.SMetaDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = "STypeDataBo")
public class SMetaDataBoImpl implements SMetaDataBo {

    @Autowired
    SMetaDataDao typeDataDao;

    @Override
    public Set<SMetaData> getAll(@NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.getAll(clazz);
    }

    @Override
    public SMetaData getById(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.getById(id, clazz);
    }

    @Override
    public SMetaData getByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.getByType(type, clazz);
    }
}
