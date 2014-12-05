package org.agatom.springatom.data.hades.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.core.annotations.profile.DevProfile;
import org.agatom.springatom.core.annotations.profile.ProductionProfile;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.agatom.springatom.data.hades.model.reference.NEntityReference;
import org.agatom.springatom.data.hades.model.rupdate.NRecentUpdate;
import org.agatom.springatom.data.hades.repo.repositories.rupdate.NRecentUpdateRepository;
import org.agatom.springatom.data.hades.service.NRecentlyUpdatedService;
import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
import org.agatom.springatom.data.types.reference.EntityReference;
import org.agatom.springatom.data.types.rupdate.RecentUpdateType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@DevProfile
@ProductionProfile
class RecentlyUpdatedService
        extends AbstractDomainService<NRecentUpdate>
        implements NRecentlyUpdatedService {
    private static final Logger               LOGGER                = LoggerFactory.getLogger(RecentlyUpdatedService.class);
    private static final String               PROP_KEY              = "org.agatom.springatom.data.services.SRecentlyUpdatedService.supported";
    private static final ClassLoader          CLASS_LOADER          = RecentlyUpdatedService.class.getClassLoader();
    private static final String               DEFAULT_VALUE         = "";
    @Autowired
    @Qualifier("applicationProperties")
    private              Properties           applicationProperties = null;
    private              Collection<Class<?>> supportedTypes        = null;
    private              Collection<Class<?>> notSupportedCache     = Lists.newArrayList();

    protected NRecentUpdateRepository repo() {
        return (NRecentUpdateRepository) this.repository;
    }

    @Override
    protected void init() {
        super.init();
        this.readSupportedTypes(this.applicationProperties.getProperty(PROP_KEY, DEFAULT_VALUE));
    }

    private void readSupportedTypes(final String property) {
        if (!StringUtils.hasLength(property)) {
            LOGGER.info(String.format("No supported types for recently updated found"));
            return;
        }
        final String[] split = property.split(",");
        final Collection<Class<?>> supports = Lists.newArrayListWithExpectedSize(split.length);
        for (final String className : split) {
            final Class<?> aClass = ClassUtils.resolveClassName(className, CLASS_LOADER);
            if (!ClassUtils.isAssignable(Persistable.class, aClass)) {
                throw new BeanInitializationException(String.format("%s is not Persistable class", aClass.getSimpleName()));
            }
            supports.add(aClass);
        }
        this.supportedTypes = supports;
    }

    @Override
    protected void registerListeners(final ListenerAppender listenerAppender) {
        listenerAppender.add(this.getRecentUpdateListener());
    }

    protected NRecentUpdate newRecentUpdate(final Persistable<Long> persistable) {
        final NEntityReference entityReference = (NEntityReference) this.entityReferenceHelper.toReference(persistable);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Using entity reference %s", entityReference));
        }
        return this.save(new NRecentUpdate().setRef(entityReference).setTs(DateTime.now()).setType(RecentUpdateType.CREATE));
    }

    protected NRecentUpdate getRecentUpdate(final Persistable<Long> persistable) {
        final EntityReference entityReference = this.entityReferenceHelper.toReference(persistable);
        this.repo().findAll();
        return null;
    }

    @Override
    public Collection<RecentUpdateBean> getRecentlyUpdated() {
        return null;
    }

    @Override
    public Page<RecentUpdateBean> getRecentlyUpdated(final Pageable pageable) {
        return null;
    }

    protected PersistenceEventListenerAdapter<Persistable<Long>> getRecentUpdateListener() {
        return new PersistenceEventListenerAdapter<Persistable<Long>>() {

            private Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            protected void onAfterCreate(final Persistable<Long> entity) {
                final NRecentUpdate recentUpdate = newRecentUpdate(entity);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("recentUpdate(%s) saved for persistable(%s)", recentUpdate, entity));
                }
            }

            @Override
            protected void onAfterSave(final Persistable<Long> entity) {

            }

            @Override
            public boolean canAccept(final Object object) {
                final Class<?> aClass = object.getClass();
                if (notSupportedCache.contains(aClass)) {
                    return false;
                }
                final boolean contains = FluentIterable.from(supportedTypes).anyMatch(new Predicate<Class<?>>() {
                    @Override
                    public boolean apply(final Class<?> input) {
                        return ClassUtils.isAssignable(input, aClass);
                    }
                });
                if (!contains) {
                    notSupportedCache.add(aClass);
                }
                if (contains && logger.isTraceEnabled()) {
                    logger.trace("{} will be included in recently updated list", aClass.getSimpleName());
                }
                return contains;
            }
        };
    }

}
