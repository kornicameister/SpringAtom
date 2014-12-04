package org.agatom.springatom.boot.init.sa;

import net.sf.ehcache.constructs.web.ShutdownListener;
import org.agatom.springatom.boot.init.sa.mvc.SAMvcConfiguration;
import org.agatom.springatom.boot.init.sa.root.SAAppConfiguration;
import org.agatom.springatom.boot.init.sa.root.SARestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.IntrospectorCleanupListener;
import org.springframework.web.util.Log4jConfigListener;
import ro.isdc.wro.http.ConfigurableWroFilter;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class SAWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    public static final  String SERVLET_NAME = "SpringAtom";
    private static final Logger LOGGER       = LoggerFactory.getLogger(SAWebAppInitializer.class);

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        this.setInitParams(servletContext);
        super.onStartup(servletContext);
        this.registerListeners(servletContext);
    }
//
//    @Override
//    protected WebApplicationContext createRootApplicationContext() {
//        final AnnotationConfigWebApplicationContext context = (AnnotationConfigWebApplicationContext) super.createRootApplicationContext();
//        context.getEnvironment().setActiveProfiles(
//                DevProfile.PROFILE_NAME
//        );
//        return context;
//    }

    @Override
    protected String getServletName() {
        return SERVLET_NAME;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/app/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                this.getOpenInViewFilterManager(),
                this.getWroFilter(),
                this.getCharacterEncodingFilter(),
                this.getHiddenHttpMethodFilter(),
                this.getHttpPutFormContentFilter()
        };
    }

    private Filter getOpenInViewFilterManager() {
        final OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
        filter.setEntityManagerFactoryBeanName("entityManagerFactory");
        return filter;
    }

    private Filter getWroFilter() {
        final ConfigurableWroFilter filter = new ConfigurableWroFilter();
        try {
            final FileSystemResource resource = new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/webmvc/wro/wro.properties"));
            final Properties properties = new Properties();
            properties.load(resource.getInputStream());
            filter.setProperties(properties);
        } catch (Exception e) {
            LOGGER.error("Failed to initialize WroFilter", e);
        }
        return filter;
    }

    private Filter getCharacterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    private Filter getHiddenHttpMethodFilter() {
        final HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        filter.setMethodParam("springatom");
        return filter;
    }

    private Filter getHttpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }

    @Override
    protected boolean isAsyncSupported() {
        return true;
    }

    private void setInitParams(final ServletContext servletContext) {
        servletContext.setInitParameter("defaultHtmlEscape", "true");
        servletContext.setInitParameter("spring.profiles.active", "dev");
    }

    private void registerListeners(final ServletContext servletContext) {
        servletContext.addListener(RequestContextListener.class);
        servletContext.addListener(Log4jConfigListener.class);
        servletContext.addListener(HttpSessionEventPublisher.class);
        servletContext.addListener(IntrospectorCleanupListener.class);
        servletContext.addListener(ContextCleanupListener.class);
        servletContext.addListener(ShutdownListener.class);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                SAAppConfiguration.class,
                SARestConfiguration.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                SAMvcConfiguration.class
        };
    }
}
