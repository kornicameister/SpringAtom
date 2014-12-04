package org.agatom.springatom.data.hades.service.reference;

import org.agatom.springatom.data.hades.model.reference.NEntityReference;
import org.agatom.springatom.data.repositories.provider.RepositoriesHelper;
import org.agatom.springatom.data.services.ref.EntityReferenceHelper;
import org.agatom.springatom.data.types.reference.EntityReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.core.CrudInvoker;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class DefaultEntityReferenceHelper
        implements EntityReferenceHelper {

    @Autowired
    private RepositoriesHelper repositoriesHelper = null;

    @Override
    @SuppressWarnings("unchecked")
    public Persistable<Long> fromReference(final EntityReference entityReference) {
        final CrudInvoker<?> crudInvoker = this.repositoriesHelper.getCrudInvoker(entityReference.getRefClass());
        if (crudInvoker != null) {
            return (Persistable<Long>) crudInvoker.invokeFindOne(entityReference.getRefId());
        }
        return null;
    }

    @Override
    public EntityReference toReference(final Persistable<Long> persistable) {
        return new NEntityReference().setRefClass(ClassUtils.getUserClass(persistable)).setRefId(persistable.getId());
    }
}
