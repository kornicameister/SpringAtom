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

package org.agatom.springatom.server.listeners;

import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.types.report.ReportResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.data.rest.core.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * {@code OnBeforeDeleteReportListener}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("Handles removing files from the FileSystem after report was successfully deleted")
public class SReportEventListener
        extends AbstractRepositoryEventListener<SReport> {

    private static final Logger                   LOGGER      = Logger.getLogger(SReportEventListener.class);
    private static final Class<BeforeDeleteEvent> EVENT_CLASS = BeforeDeleteEvent.class;

    @Override
    protected void onBeforeDelete(final SReport entity) {
        final String displayString = ObjectUtils.getDisplayString(entity);
        LOGGER.trace(String.format("Handling %s for %s", ClassUtils.getShortName(EVENT_CLASS), displayString));
        try {
            final ReportResource resource = entity.getResource();
            final File jasperFile = ResourceUtils.getFile(resource.getJasperPath());
            final File cfgFile = ResourceUtils.getFile(resource.getConfigurationPath());

            Assert.isTrue(jasperFile.exists());
            Assert.isTrue(jasperFile.delete());

            Assert.isTrue(cfgFile.exists());
            Assert.isTrue(cfgFile.delete());
        } catch (Exception exception) {
            LOGGER.fatal(String.format("Failed to delete resources for report=%s", displayString));
        }
        LOGGER.trace(String.format("Handled %s for %s", ClassUtils.getShortName(EVENT_CLASS), displayString));
    }
}
