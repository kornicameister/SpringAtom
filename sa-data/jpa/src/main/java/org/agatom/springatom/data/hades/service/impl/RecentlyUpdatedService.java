package org.agatom.springatom.data.hades.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.agatom.springatom.data.hades.service.NRecentlyUpdatedService;
import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
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

import java.util.*;

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
class RecentlyUpdatedService
        extends BaseDomainService
        implements NRecentlyUpdatedService {
    private static final Logger                 LOGGER                = LoggerFactory.getLogger(RecentlyUpdatedService.class);
    private static final String                 PROP_KEY              = "org.agatom.springatom.data.services.SRecentlyUpdatedService.supported";
    private static final String                 PROP_KEY_QS           = "org.agatom.springatom.data.services.SRecentlyUpdatedService.queueSize";
    private static final ClassLoader            CLASS_LOADER          = RecentlyUpdatedService.class.getClassLoader();
    @Autowired
    @Qualifier("applicationProperties")
    private              Properties             applicationProperties = null;
    private              Collection<Class<?>>   supportedTypes        = null;
    private              Collection<Class<?>>   notSupportedCache     = Lists.newArrayList();
    private              Queue<RecentlyUpdated> recentlyUpdatedQueue  = null;

    @Override
    protected void init() {
        super.init();
        this.readSupportedTypes(this.applicationProperties.getProperty(PROP_KEY));
        this.recentlyUpdatedQueue = this.initQueue();
    }

    private void readSupportedTypes(final String property) {
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

    private Queue<RecentlyUpdated> initQueue() {
        final int capacity = Integer.parseInt(this.applicationProperties.getProperty(PROP_KEY_QS, "10"));
        return new PriorityQueue<>(capacity, new Comparator<RecentlyUpdated>() {
            @Override
            public int compare(final RecentlyUpdated o1, final RecentlyUpdated o2) {
                return ComparisonChain.start()
                        .compare(o1.updateType, o2.updateType)
                        .compare(o1.ts, o2.ts)
                        .result();
            }
        });
    }

    @Override
    protected void registerListeners(final ListenerAppender listenerAppender) {
        listenerAppender.add(new PersistenceEventListenerAdapter<Persistable<Long>>() {

            private Logger logger = LoggerFactory.getLogger(this.getClass());

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

            @Override
            protected void onAfterCreate(final Persistable<Long> entity) {
                final boolean offer = recentlyUpdatedQueue.offer(new RecentlyUpdated(entity, UpdateType.CREATE));
                if (!offer) {
                    logger.info("{} was not offered into the recently updated queue for key={}", entity, UpdateType.CREATE);
                }
            }

            @Override
            protected void onAfterSave(final Persistable<Long> entity) {
                final boolean offer = recentlyUpdatedQueue.offer(new RecentlyUpdated(entity, UpdateType.UPDATE));
                if (!offer) {
                    logger.info("{} was not offered into the recently updated queue for key={}", entity, UpdateType.UPDATE);
                }
            }
        });
    }

    @Override
    public Collection<RecentUpdateBean> getRecentlyUpdated() {
        return null;
    }

    @Override
    public Page<RecentUpdateBean> getRecentlyUpdated(final Pageable pageable) {
        return null;
    }

    private static enum UpdateType {
        CREATE,
        UPDATE
    }

    private static class RecentlyUpdated {
        DateTime   ts;
        UpdateType updateType;
        Class<?>   clazz;
        Long       id;

        public RecentlyUpdated(final Persistable<Long> entity, final UpdateType updateType) {
            this.updateType = updateType;
            this.clazz = ClassUtils.getUserClass(entity);
            this.id = entity.getId();
            this.ts = DateTime.now();
        }
    }
}
