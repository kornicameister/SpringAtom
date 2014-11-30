package org.agatom.springatom.webmvc.controllers;

import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-12</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
public class SVJspViewController
        extends SVDefaultController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView onIndex() {
        return new ModelAndView("home");
    }
}
