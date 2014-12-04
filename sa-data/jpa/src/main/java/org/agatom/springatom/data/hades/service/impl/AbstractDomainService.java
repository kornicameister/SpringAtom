package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.repo.NRepository;
import org.agatom.springatom.data.services.SDomainService;
import org.agatom.springatom.data.services.ref.EntityReferenceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

@Transactional
abstract class AbstractDomainService<T extends Persistable<Long>>
        extends BaseDomainService
        implements SDomainService<T> {
    private static final Logger                LOGGER                = LoggerFactory.getLogger(AbstractDomainService.class);
    @Autowired
    protected            NRepository<T>        repository            = null;
    @Autowired
    protected            EntityReferenceHelper entityReferenceHelper = null;

    @Override
    public T findOne(final Long id) {
        if (id == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("findOne(id=null) called with null id to look for");
            }
            return null;
        }
        return this.repository.findOne(id);
    }

    @Override
    public Iterable<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = false)
    public T save(final @NotNull T persistable) {
        return this.repository.save(persistable);
    }

    @Override
    public T deleteOne(final @NotNull Long pk) {
        final T source = this.findOne(pk);
        this.repository.delete(source);
        return source;
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public void delete(final Iterable<T> toDelete) {
        this.repository.deleteInBatch(toDelete);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

}
