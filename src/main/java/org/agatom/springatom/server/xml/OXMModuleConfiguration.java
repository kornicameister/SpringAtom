/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.server.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.StringUtils;

/**
 * <p>OXMModuleConfiguration class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = OXMModuleConfiguration.MODULE_NAME)
@PropertySource(
		name = "oxmModuleProperties",
		value = {
				"classpath:org/agatom/springatom/server/xml/xml.properties"
		}
)
public class OXMModuleConfiguration {
	/** Constant <code>MODULE_NAME="OXMModule"</code> */
	public static final String MODULE_NAME = "OXMModule";
	@Autowired
	private Environment        environment;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * <p>getOXMEngine.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.xml.OXMLEngine} object.
	 */
	@Bean(name = "OXMEngine")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public OXMLEngine getOXMEngine() {
		final OXMLEngine OXMLEngine = new OXMLEngine();
		OXMLEngine.setMarshaller(this.getJaxbMarshaller());
		OXMLEngine.setUnmarshaller(this.getJaxbMarshaller());
		return OXMLEngine;
	}

	/**
	 * <p>getJaxbMarshaller.</p>
	 *
	 * @return a {@link org.springframework.oxm.jaxb.Jaxb2Marshaller} object.
	 */
	@Bean(name = "jaxbMarshaller")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Jaxb2Marshaller getJaxbMarshaller() {
		final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setLazyInit(this.environment.getProperty("springatom.oxm.lazyInit", Boolean.class));
		jaxb2Marshaller.setPackagesToScan(StringUtils.split(this.environment.getProperty("springatom.oxm.packagesToScan"), ","));
		jaxb2Marshaller.setCheckForXmlRootElement(true);
		jaxb2Marshaller.setProcessExternalEntities(true);
		return jaxb2Marshaller;
	}

}
