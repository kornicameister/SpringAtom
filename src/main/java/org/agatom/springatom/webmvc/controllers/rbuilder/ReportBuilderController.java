/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.webmvc.controllers.rbuilder;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.rbuilder.ReportRepresentation;
import org.agatom.springatom.web.rbuilder.ReportRepresentation.Representation;
import org.agatom.springatom.web.rbuilder.ReportViewDescriptor;
import org.agatom.springatom.web.rbuilder.data.service.ReportBuilderService;
import org.agatom.springatom.webmvc.ViewHelper;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(value = ReportBuilderController.CONTROLLER_NAME)
@RequestMapping(value = "/reportBuilder")
public class ReportBuilderController
        extends WebApplicationObjectSupport {
    public static final  String CONTROLLER_NAME = "reportBuilderController";
    private static final String VIEW_NAME       = "springatom.tiles.dashboard.reports.Download";
    private static final Logger LOGGER          = Logger.getLogger(ReportBuilderController.class);

    @Autowired
    private ReportBuilderService service;
    @Autowired
    private SMessageSource       messageSource;
    @Autowired
    private ReportViewDescriptor descriptor;

    @RequestMapping(value = "/{reportId}", method = {RequestMethod.POST})
    public ModelAndView buildReport(@PathVariable("reportId") final Long reportId, final ModelMap modelMap, final HttpServletResponse response) throws
            ControllerTierException {
        try {
            final SReport report = this.service.getReport(reportId);
            final Map<String, ReportRepresentation> availableRepresentations = this.service.getAvailableRepresentations();

            LOGGER.info(String.format("/buildReport report=%s :: formats=%s", report, availableRepresentations));

            modelMap.put("report", report);
            modelMap.put("representations", availableRepresentations);
            modelMap.put("links", this.createDownloadLinks(availableRepresentations.keySet(), report));
            modelMap.put("title", this.messageSource
                    .getMessage("sa.msg.download.what", new Object[]{report.getTitle()}, LocaleContextHolder.getLocale()));

            ViewHelper.asDojoModal(response);

            return new ModelAndView(VIEW_NAME, modelMap);
        } catch (Exception se) {
            final String message = String.format("/buildReport threw exception during processing report=%d", reportId);
            LOGGER.error(message, se);
            throw new ControllerTierException(message, se);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{reportId}", method = {RequestMethod.DELETE})
    public Report deleteReport(@PathVariable("reportId") final Long reportId, final ModelMap modelMap) throws
            ControllerTierException {
        try {
            final Report report = this.service.deleteReport(reportId);
            modelMap.addAttribute(ClassUtils.getShortName(Report.class), report);
            return report;
        } catch (Exception se) {
            final String message = String.format("/deleteReport threw exception during processing report=%d", reportId);
            LOGGER.error(message, se);
            throw new ControllerTierException(message, se);
        }
    }

    @RequestMapping(value = "/download/{reportId}")
    public ModelAndView downloadReport(@PathVariable("reportId") final Long reportId) throws ServiceException {
        LOGGER.info(String.format("/downloadReport name=%s", reportId));
        return this.downloadReportInFormat(reportId, Representation.EXCEL.getId());
    }

    @RequestMapping(value = "/download/{reportId}/{format}")
    public ModelAndView downloadReportInFormat(@PathVariable("reportId") final Long reportId,
                                               @PathVariable("format") final String format) throws ServiceException {
        LOGGER.info(String.format("/downloadReportInFormat name=%s :: format=%s", reportId, format));
        this.service.populateReportViewDescriptor(reportId, format, this.descriptor);
        return new ModelAndView(
                this.descriptor.getViewName(),
                this.descriptor.getParameters()
        );
    }

    private Map<String, Link> createDownloadLinks(final Set<String> availableRepresentations, final SReport report) {
        return FluentIterable.from(availableRepresentations)
                             .toMap(new Function<String, Link>() {
                                 @Nullable
                                 @Override
                                 public Link apply(@Nullable final String format) {
                                     try {
                                         return linkTo(methodOn(ReportBuilderController.class).downloadReportInFormat(report.getId(), format))
                                                 .withRel(format);
                                     } catch (Exception e) {
                                         LOGGER.warn(String.format("Failed to generate link report=%s/format=%s", report.getTitle(), format));
                                     }
                                     return null;
                                 }
                             });
    }

}
