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

package org.agatom.springatom.boot.init.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.convert.converters.StringToEnum;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 21.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy
@Configuration
@ComponentScan(
        basePackageClasses = RepositoryRestController.class,
        includeFilters = @ComponentScan.Filter(RepositoryRestController.class),
        useDefaultFilters = false
)
class SARepositoryRestMvcConfiguration
        extends RepositoryRestMvcConfiguration {
    /** Constant <code>STRING_TO_ENUM</code> */
    public static final  StringToEnum               STRING_TO_ENUM        = new StringToEnum();
    private static final Logger                     LOGGER                = LoggerFactory.getLogger(SARepositoryRestMvcConfiguration.class);
    @Autowired
    @Qualifier("applicationProperties")
    private              Properties                 properties        = null;
    private              Map<Boolean, List<Object>> enableDisableFeatures = Maps.newHashMap();

    @PostConstruct
    private void postConstruct() {
        Assert.notNull(this.properties, "restProperties not found [null]");
        this.populateEnableDisable();
    }

    @Override
    protected void configureRepositoryRestConfiguration(final RepositoryRestConfiguration configuration) {
        configuration.setReturnBodyOnCreate(Boolean.parseBoolean(this.properties.getProperty("rest.returnBodyOnCreate")));
        configuration.setReturnBodyOnUpdate(Boolean.parseBoolean(this.properties.getProperty("rest.returnBodyOnUpdate")));
        configuration.setMaxPageSize(Integer.parseInt(this.properties.getProperty("rest.maxPageSize")));
        configuration.setLimitParamName(this.properties.getProperty("rest.limitParamName"));
        configuration.setPageParamName(this.properties.getProperty("rest.pageParamName"));
        configuration.setSortParamName(this.properties.getProperty("rest.sortParamName"));
        configuration.setDefaultPageSize(Integer.parseInt(this.properties.getProperty("rest.defaultPageSize")));
    }

    private void populateEnableDisable() {
        this.populateForProperty("rest.jackson.enableFeatures", true, STRING_TO_ENUM);
        this.populateForProperty("rest.jackson.disableFeatures", false, STRING_TO_ENUM);
    }

    private void populateForProperty(final String propProperties, final boolean key, final StringToEnum stringToEnum) {
        String[] val = this.properties.getProperty(propProperties).split(",");
        List<Object> objects = Lists.newArrayListWithExpectedSize(val.length);

        for (final String prop : val) {
            final String property = prop.substring(prop.lastIndexOf(".") + 1);
            try {
                final Class<?> propertyClazz = this.getPropertyClass(prop);
                objects.add(stringToEnum.convertSourceToTargetClass(property, propertyClazz));
            } catch (Exception exp) {
                LOGGER.error(String.format("Error in resolving enableDisable feature from prop=%s", prop), exp);
            }
        }
        this.enableDisableFeatures.put(key, objects);
    }

    private Class<?> getPropertyClass(final String prop) throws Exception {
        if (prop.contains(SerializationFeature.class.getName())) {
            return SerializationFeature.class;
        } else if (prop.contains(DeserializationFeature.class.getName())) {
            return DeserializationFeature.class;
        }
        throw new Exception(
                String.format("%s does not relate to either %s or %s",
                        prop,
                        ClassUtils.getShortName(SerializationFeature.class),
                        ClassUtils.getShortName(DeserializationFeature.class)
                )
        );
    }

    /** {@inheritDoc} */
    @Override
    protected void configureJacksonObjectMapper(final ObjectMapper objectMapper) {
        super.configureJacksonObjectMapper(objectMapper);

        for (final Boolean key : this.enableDisableFeatures.keySet()) {
            final List<Object> objects = this.enableDisableFeatures.get(key);
            for (final Object obj : objects) {
                if (key) {
                    if (ClassUtils.isAssignableValue(SerializationFeature.class, obj)) {
                        objectMapper.enable((SerializationFeature) obj);
                    } else {
                        objectMapper.enable((DeserializationFeature) obj);
                    }
                } else {
                    if (ClassUtils.isAssignableValue(SerializationFeature.class, obj)) {
                        objectMapper.disable((SerializationFeature) obj);
                    } else {
                        objectMapper.disable((DeserializationFeature) obj);
                    }
                }
            }
        }
    }
}
