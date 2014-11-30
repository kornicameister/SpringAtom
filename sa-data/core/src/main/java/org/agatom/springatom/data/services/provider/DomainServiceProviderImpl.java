package org.agatom.springatom.data.services.provider;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import org.agatom.springatom.data.services.SDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class DomainServiceProviderImpl
        implements DomainServiceProvider {
    private static final Logger                           LOGGER           = LoggerFactory.getLogger(DomainServiceProviderImpl.class);
    private              ListableBeanFactory              beanFactory      = null;
    private              Map<Class<?>, SDomainService<?>> domainServiceMap = null;

    public DomainServiceProviderImpl(final ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.domainServiceMap = Maps.newConcurrentMap();
        this.populateServices();
    }

    @SuppressWarnings("ConstantConditions")
    private void populateServices() {
        final Stopwatch started = Stopwatch.createStarted();
        final String domainServiceClassName = ClassUtils.getShortName(SDomainService.class);
        final Map<String, SDomainService> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(this.beanFactory, SDomainService.class);
        if (!CollectionUtils.isEmpty(map)) {
            final Collection<SDomainService> services = map.values();
            for (SDomainService service : services) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(String.format("Analyzing %s", service));
                }
                final Class<?> domainTA = GenericTypeResolver.resolveTypeArgument(service.getClass(), SDomainService.class);
                Assert.notNull(domainTA, String.format("%s has no type arguments", service));

                final Class<?> typeArgument = ClassUtils.getUserClass(domainTA);
                this.domainServiceMap.put(typeArgument, service);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Collected %d of %s", this.domainServiceMap.size(), domainServiceClassName));
        }
        if (LOGGER.isTraceEnabled()) {
            for (Map.Entry<Class<?>, SDomainService<?>> entry : this.domainServiceMap.entrySet()) {
                final String nameK = ClassUtils.getShortName(entry.getKey());
                final String nameV = ClassUtils.getUserClass(entry.getValue().getClass()).getName();
                LOGGER.trace(String.format("For %s collected %s=%s", nameK, domainServiceClassName, nameV));
            }
            LOGGER.trace(String.format("Collecting took %d ms", started.stop().elapsed(TimeUnit.MILLISECONDS)));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Persistable<Long>> SDomainService<T> getServiceFor(final Class<T> clazz) {
        return (SDomainService<T>) this.domainServiceMap.get(clazz);
    }
}
