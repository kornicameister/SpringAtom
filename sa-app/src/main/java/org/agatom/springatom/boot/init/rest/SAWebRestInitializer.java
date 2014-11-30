package org.agatom.springatom.boot.init.rest;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 */
public class SAWebRestInitializer
        implements WebApplicationInitializer {
    protected static final String SERVLET_NAME = "SpringAtomRest";

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        this.registerRest(servletContext);
    }

    private void registerRest(final ServletContext servletContext) {
        final SARepositoryRestDispatcherServlet servlet = new SARepositoryRestDispatcherServlet();
        final ServletRegistration.Dynamic registration = servletContext.addServlet(SERVLET_NAME, servlet);

        registration.setLoadOnStartup(2);
        registration.addMapping("/rest/*");
        registration.setAsyncSupported(true);
    }
}
