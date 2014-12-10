package org.agatom.springatom.webmvc.controllers.aop;

import com.google.common.base.Stopwatch;
import org.agatom.springatom.core.web.DataResource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-20</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order(1000)
@Aspect
@Component
class DataResourceAOP {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataResourceAOP.class);

    @Pointcut("within(org.agatom.springatom.webmvc.controllers..*.*)")
    private void inControllers() {
    }

    @Pointcut("within(org.agatom.springatom.web.api..*.*)")
    private void inWebApi() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    private void withResponseBody() {

    }

    @Around("(inControllers() || inWebApi()) && withResponseBody()")
    public Object computeResourceValues(final ProceedingJoinPoint pjp) {
        final String name = pjp.getSignature().getName();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("computeResourceValues(pjp=%s)", name));
        }

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final ReturnedWrapper retVal = this.getResource(pjp);

        if (retVal == null) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("%s returned null value, no DS calculation", name));
                LOGGER.trace(String.format("computeResourceValues(pjp=%s) took %d ms", name, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
            }
            return null;
        }

        retVal.resource.setTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        retVal.resource.setSuccess(retVal.error == null);
        retVal.resource.setError(retVal.error);
        retVal.resource.setMessage(this.determineMessage(retVal.resource));
        retVal.resource.setSize(this.determineSize(retVal.resource));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("computeResourceValues(pjp=%s) took %d ms", name, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
        }

        return retVal.resource;
    }

    private ReturnedWrapper getResource(final ProceedingJoinPoint pjp) {
        Object retVal = null;
        Throwable error = null;
        try {
            final Object[] args = pjp.getArgs();
            if (args != null) {
                retVal = pjp.proceed(args);
            } else {
                retVal = pjp.proceed();
            }
        } catch (Throwable throwable) {
            LOGGER.warn("Failed to computeResourceValues", throwable);
            error = throwable;
        }
        if (retVal != null && ClassUtils.isAssignableValue(DataResource.class, retVal)) {
            return new ReturnedWrapper((DataResource<?>) retVal, error);
        }
        return null;
    }

    private String determineMessage(final DataResource<?> retVal) {
        String message = retVal.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = ClassUtils.getShortName(DataResource.class);
        }
        return message;
    }

    private int determineSize(final DataResource<?> resource) {
        final Object content = resource.getContent();
        if (ClassUtils.isAssignableValue(Collection.class, content)) {
            return ((Collection<?>) content).size();
        } else if (ClassUtils.isAssignableValue(Map.class, content)) {
            return ((Map<?, ?>) content).size();
        }
        return 1;
    }

    private static class ReturnedWrapper {
        DataResource<?> resource = null;
        Throwable       error    = null;

        public ReturnedWrapper(final DataResource<?> val, final Throwable error) {
            this.resource = val;
            this.error = error;
        }
    }

}
