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

package org.agatom.springatom.web.rbuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * {@code IN_PROGRESS} -> for further updates
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
//TODO needs rework if needed -> at the moment it is unused
public class ReportView
		extends JasperReportsMultiFormatView {

	private Map<String, JasperReport> subReports        = null;
	private List<String>              subReportDataKeys = null;
	private ModelMap                  parameters        = null;
	//    @Autowired
//    @Qualifier(value = "rbuilderProperties")
	private Properties                propertiesHolder  = null;

	/** {@inheritDoc} */
	@Override
	protected void onInit() {
		final ReportViewDescriptor reportViewDescriptor = this.getReportViewDescriptor();
		{
			this.parameters = reportViewDescriptor.getParameters();
		}
		this.loadSubreports();
	}

	/** {@inheritDoc} */
	@Override
	protected void renderMergedOutputModel(
			final Map<String, Object> model,
			final HttpServletRequest request,
			final HttpServletResponse response
	) throws Exception {
		if (this.subReports != null) {
			// Expose sub-reports as model attributes.
			model.putAll(this.subReports);

			// Transform any collections etc into JRDataSources for sub reports.
			if (this.subReportDataKeys != null) {
				for (String key : this.subReportDataKeys) {
					model.put(key, convertReportData(model.get(key.replaceFirst("#", ""))));
				}
			}
		}
		super.renderMergedOutputModel(model, request, response);
	}

	private ReportViewDescriptor getReportViewDescriptor() {
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.notNull(requestAttributes);

		final String nameOfBean = StringUtils.uncapitalize(ClassUtils.getShortName(ReportViewDescriptor.class));
		Object attribute = requestAttributes.getAttribute(nameOfBean, RequestAttributes.SCOPE_SESSION);

		if (attribute == null) {
			try {
				attribute = this.getWebApplicationContext().getBean(nameOfBean);
			} catch (BeansException exp) {
				attribute = null;
			}
		}

		Assert.notNull(attribute);
		Assert.isInstanceOf(ReportViewDescriptor.class, attribute);

		return (ReportViewDescriptor) attribute;
	}

	private void loadSubreports() {
		final Set<?> subReports = (Set<?>) this.parameters.get(this.propertiesHolder.getProperty("reports.reportSubReportsKey"));
		final String reportsPrefix = this.propertiesHolder.getProperty("reports.reportsPrefix");

		if (!CollectionUtils.isEmpty(subReports)) {

			this.subReports = Maps.newHashMap();
			this.subReportDataKeys = Lists.newArrayList();

			for (final Object subReportObject : subReports) {
				Assert.isInstanceOf(Report.class, subReportObject);
				final Report subReport = (Report) subReportObject;

				final String jasperPath = String.format("%s%s", reportsPrefix, subReport.getResource().getJasperFilename());
				final Resource resource = this.getApplicationContext().getResource(jasperPath);

				this.subReports.put(this.generateFakeLoadedIdForModel((SReport) subReport), this.loadReport(resource));
				this.subReportDataKeys.add(this.generateFakeLoadedIdForModel((SReport) subReport));

			}
			this.setSubReportDataKeys(this.subReportDataKeys.toArray(new String[this.subReportDataKeys.size()]));
		}
	}

	private String generateFakeLoadedIdForModel(final SReport subReport) {
		return String.format("#%s", this.generateFakeDataKeyId(subReport));
	}

	private String generateFakeDataKeyId(final SReport subReport) {
		return String.format("%s_%d", propertiesHolder.getProperty("reports.reportDataKey"), subReport.getId());
	}


}
