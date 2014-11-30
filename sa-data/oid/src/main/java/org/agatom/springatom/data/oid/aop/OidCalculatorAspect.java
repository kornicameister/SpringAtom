package org.agatom.springatom.data.oid.aop;

import com.google.common.base.Stopwatch;
import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.SOidService;
import org.agatom.springatom.data.repositories.SRepository;
import org.agatom.springatom.data.types.PersistentBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect
@Component
class OidCalculatorAspect {
    private static final Logger      LOGGER     = LoggerFactory.getLogger(OidCalculatorAspect.class);
    @Autowired
    private              SOidService oidService = null;

    @Around("findCall() && target(org.agatom.springatom.data.repositories.SRepository)")
    public Object calculateOid(final ProceedingJoinPoint pjp) {
        final String name = pjp.getSignature().getName();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("calculateOid(pjp=%s)", name));
        }

        final Stopwatch stopwatch = Stopwatch.createStarted();
        Object retVal = this.getReturnedValue(pjp);

        if (retVal == null) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("%s returned null value, no oid calculation", name));
                LOGGER.trace(String.format("calculateOid(pjp=%s) took %d ms", name, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
            }
            return null;
        }

        final Object target = pjp.getTarget();
        if (!ClassUtils.isAssignableValue(SRepository.class, target)) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("%s is not oid calculable", target));
                LOGGER.trace(String.format("calculateOid(pjp=%s) took %d ms", name, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
            }
            return retVal;
        }

        if (ClassUtils.isAssignableValue(Iterable.class, retVal)) {
            retVal = this.calculateFromIterate((Iterable<?>) retVal);
        } else if (ClassUtils.isAssignableValue(Page.class, retVal)) {
            retVal = this.calculateFromPage((Page<?>) retVal);
        } else {
            retVal = this.calculateFromObj(retVal);
        }

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("calculateOid(pjp=%s) took %d ms", name, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
        }

        return retVal;
    }

    private Object getReturnedValue(final ProceedingJoinPoint pjp) {
        Object retVal = null;
        try {
            final Object[] args = pjp.getArgs();
            if (args != null) {
                retVal = pjp.proceed(args);
            } else {
                retVal = pjp.proceed();
            }
        } catch (Throwable throwable) {
            LOGGER.warn("Failed to calculateOid", throwable);
        }
        return retVal;
    }

    private Object calculateFromIterate(final Iterable<?> retVal) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("calculateFromIterate(retVal=%s)", retVal));
        }
        for (final Object obj : retVal) {
            this.calculateFromObj(obj);
        }
        return retVal;
    }

    private Object calculateFromPage(final Page<?> retVal) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("calculateFromPage((retVal=%s)", retVal));
        }
        final Object iterations = this.calculateFromIterate(retVal.getContent());
        final BeanWrapper wrapper = new BeanWrapperImpl(retVal);
        wrapper.setPropertyValue("content", iterations);
        return wrapper.getWrappedInstance();
    }

    private Object calculateFromObj(Object retVal) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("calculateFromObj(retVal=%s)", retVal));
        }
        try {
            if (ClassUtils.isAssignableValue(Persistable.class, retVal)) {
                if (((Persistable<?>) retVal).isNew()) {
                    return retVal;
                }
            }
            if (ClassUtils.isAssignableValue(PersistentBean.class, retVal)) {
                final BeanWrapper wrapper = new BeanWrapperImpl(retVal);
                final SOid oid = this.oidService.getOid(retVal);
                wrapper.setPropertyValue("oid", oid);
                retVal = wrapper.getWrappedInstance();
            } else if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("Failed to calculateFromObj(retVal=%s), retVal is not %s", retVal, PersistentBean.class.getSimpleName()));
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Error for oid calculation from %s", retVal));
        }
        return retVal;
    }

    @Pointcut("execution(* find*(..))")// the pointcut expression
    private void findCall() {
    }
}
