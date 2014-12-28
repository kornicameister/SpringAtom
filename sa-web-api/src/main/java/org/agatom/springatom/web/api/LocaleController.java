package org.agatom.springatom.web.api;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Description("LocaleController allows to access resource bundle information over HTTP protocol")
@RequestMapping(
        value = LocaleController.ROOT,
        method = GET,
        produces = APPLICATION_JSON_VALUE
)
public interface LocaleController {
    static final String ROOT = "/rest/locales";

    @ResponseBody
    @RequestMapping("/")
    Collection<Locale> getAvailableLocales();

    @ResponseBody
    @RequestMapping("/{locale}")
    Map<String, String> getAllMessages(@PathVariable("locale") final Locale locale);

    @ResponseBody
    @RequestMapping("/{locale}/{key:.+}")
    String getMessage(@PathVariable("key") final String key, @PathVariable("locale") final Locale locale) throws Exception;
}
