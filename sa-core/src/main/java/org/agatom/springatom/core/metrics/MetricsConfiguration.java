package org.agatom.springatom.core.metrics;

import org.springframework.aop.interceptor.DebugInterceptor;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.cache.aspectj.AspectJCachingConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ComponentScan(basePackageClasses = MetricsConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MetricsConfiguration
        extends AspectJCachingConfiguration {

    @Bean(name = "global_debug")
    public DebugInterceptor debugInterceptor() {
        final DebugInterceptor debugInterceptor = new DebugInterceptor();
        debugInterceptor.setUseDynamicLogger(true);
        debugInterceptor.setHideProxyClassNames(false);
        return debugInterceptor;
    }

    @Bean(name = "global_performance")
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        final PerformanceMonitorInterceptor monitorInterceptor = new PerformanceMonitorInterceptor();
        monitorInterceptor.setUseDynamicLogger(true);
        monitorInterceptor.setLogTargetClassInvocation(true);
        monitorInterceptor.setHideProxyClassNames(false);
        monitorInterceptor.setPrefix("SA_PERF");
        monitorInterceptor.setSuffix("MONITOR");
        return monitorInterceptor;
    }

}
