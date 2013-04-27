package org.agatom.springatom.mvc.model.bo.impl;

import org.agatom.springatom.model.meta.STypeData;
import org.agatom.springatom.mvc.model.bo.STypeDataBo;
import org.agatom.springatom.mvc.model.dao.STypeDataDao;
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
public class STypeDataBoImpl implements STypeDataBo {

    @Autowired
    STypeDataDao typeDataDao;

    @Override
    public Set<STypeData> getAll(@NotNull final Class clazz) {
        return this.typeDataDao.getAll(clazz);
    }

    @Override
    public STypeData getById(@NotNull final Long id, @NotNull final Class clazz) {
        return this.typeDataDao.getById(id, clazz);
    }

    @Override
    public STypeData getByType(@NotNull final String type, @NotNull final Class clazz) {
        return this.typeDataDao.getByType(type, clazz);
    }
}
