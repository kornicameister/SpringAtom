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

package org.agatom.springatom.web.rbuilder.data.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.agatom.springatom.SpringAtom;
import org.agatom.springatom.core.annotations.LazyComponent;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>ReportResourceHelper class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@LazyComponent(value = ReportResourceHelper.COMPONENT_NAME)
public class ReportResourceHelper {

	/** Constant <code>COMPONENT_NAME="org.agatom.springatom.RBReportResourceH"{trunked}</code> */
	protected static final String       COMPONENT_NAME   = "org.agatom.springatom.RBReportResourceHelper";
	private static final   Logger       LOGGER           = Logger.getLogger(ReportResourceHelper.class);
	@Autowired(required = false)
	@Qualifier(value = "rbuilderProperties")
	private                Properties   propertiesHolder = null;
	@Autowired(required = false)
	private                ObjectMapper jackson          = null;

	/**
	 * <p>toFile.</p>
	 *
	 * @param reportToFile a {@link org.agatom.springatom.web.rbuilder.data.resource.ReportToFile} object.
	 *
	 * @return a {@link org.agatom.springatom.web.rbuilder.data.resource.ReportResources} object.
	 *
	 * @throws java.lang.Exception if any.
	 */
	public ReportResources toFile(final ReportToFile reportToFile) throws Exception {
		Assert.notNull(reportToFile);

		final Report report = reportToFile.getReport();
		final JasperReport jasperReport = reportToFile.getJasperReport();
		final ReportConfiguration reportConfiguration = reportToFile.getReportConfiguration();

		return this.saveCompiledReportToFile(
				jasperReport,
				this.getResource(report, ReportResourceType.JASPER),
				reportConfiguration,
				this.getResource(report, ReportResourceType.CONFIGURATION)
		);
	}

	private ReportResources saveCompiledReportToFile(final JasperReport jasperReport,
	                                                 final Resource jasperResource,
	                                                 final ReportConfiguration reportConfiguration,
	                                                 final Resource reportResource) throws
			Exception {
		Assert.notNull(jasperResource);
		Assert.isTrue(!jasperResource.getFile().exists());
		JRSaver.saveObject(jasperReport, jasperResource.getFile());
		Assert.isTrue(jasperResource.getFile().setReadOnly());

		Assert.notNull(reportResource);
		Assert.isTrue(!reportResource.getFile().exists());
		this.jackson.writeValue(reportResource.getFile(), reportConfiguration);
		Assert.isTrue(reportResource.getFile().setReadOnly());

		return ReportResources.newReportResources(jasperResource, reportResource);
	}

	private Resource getResource(final Report report, final ReportResourceType builderResource) throws IOException {
		final String corePackageName = String.format("%s/", ClassUtils.getPackageName(SpringAtom.class).replaceAll("\\.", "/"));
		File file;
		try {
			file = ResourceUtils.getFile(String.format("classpath:%sjasper", corePackageName));
		} catch (FileNotFoundException fnfe) {
			file = this.createJasperHolderDirectory(corePackageName);
		}
		final FileSystemResource fileSystemResource = new FileSystemResource(file);
		return this.getFileSystemResource(fileSystemResource, report, builderResource);
	}

	private File createJasperHolderDirectory(final String corePackageName) throws IOException {
		final FileSystemResource fileSystemResource = new FileSystemResource(ResourceUtils.getFile(String.format("classpath:%s", corePackageName)));
		final Resource jasper = new FileSystemResource(new File(String.format("%s/jasper", fileSystemResource.getPath())));
		final boolean mkdir = jasper.getFile().mkdir();
		if (mkdir) {
			LOGGER.trace(String.format("Created JasperHolderDirectory at file=%s", jasper.getFile().getPath()));
			return jasper.getFile();
		} else {
			throw new NestedIOException("Failed to created JasperHolderDirectory");
		}
	}

	private FileSystemResource getFileSystemResource(final FileSystemResource fileSystemResource, final Report report, final ReportResourceType resourceType) {
		final String pathname = StringUtils.cleanPath(
				String.format(LocaleContextHolder.getLocale(), "%s/%s.%s",
						fileSystemResource.getPath(),
						this.getResourceName(resourceType, this.calculateFileNameHashCode(report)),
						resourceType.toString().toLowerCase()
				)
		);
		return new FileSystemResource(new File(pathname));
	}

	private String getResourceName(final ReportResourceType resourceType, final String fileName) {
		switch (resourceType) {
			case CONFIGURATION:
				return String.format("%s_%s", this.propertiesHolder.getProperty("reports.files.configuration"), fileName);
			case JASPER:
				return String.format("%s_%s", this.propertiesHolder.getProperty("reports.files.jasper"), fileName);
		}
		return null;
	}

	private String calculateFileNameHashCode(final Report report) {
		return String.valueOf(StringUtils.trimAllWhitespace(report.getTitle()).hashCode());
	}
}
