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

package org.agatom.springatom.cmp.wizards.validation;

import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.agatom.springatom.cmp.wizards.validation.annotation.WizardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code ValidatorsRepositoryImpl} is application scoped {@link org.springframework.stereotype.Service}
 * that allows to look for {@link WizardValidator}
 * against <b>keys</b> that ara combined to match class name in format <pre>{key}Validator</pre>
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

@Service("validationRepository")
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_SUPPORT)
class ValidatorsRepositoryImpl
        implements ValidatorsRepository {
    public static final  String                     VALIDATOR_KEY_SUFFIX = "Validator";
    private static final Logger                     LOGGER               = LoggerFactory.getLogger(ValidatorsRepositoryImpl.class);
    @Autowired
    private              DefaultListableBeanFactory beanFactory          = null;
    private              Map<String, Object>        validatorsMap        = null;

    @PostConstruct
    private void loadValidators() {
        final long startTime = System.nanoTime();
        final String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(WizardValidator.class);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Located following %s=%s", ClassUtils.getShortName(WizardValidator.class), Arrays.toString(beanNames)));
        }
        this.validatorsMap = Maps.newHashMap();
        for (final String beanName : beanNames) {
            this.validatorsMap.put(beanName, this.beanFactory.getBean(beanName));
        }
        LOGGER.trace(String.format("Located %d WizardValidators in %d ms", this.validatorsMap.size(), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValidator(final String key) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getValidator(key=%s)", key));
        }
        if (!StringUtils.hasText(key)) {
            LOGGER.warn(String.format("key argument is empty or null, failed to find validator"));
            return null;
        } else if (WizardProcessor.DEFAULT_FORM_OBJECT_NAME.equals(key)) {
            LOGGER.warn(String.format("key argument is default one as set in %s, failed to find validator", ClassUtils.getShortName(WizardProcessor.class)));
            return null;
        }
        final String validatorKey = this.getValidatorKey(key);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("key=%s and equivalent validatorKey=%s", key, validatorKey));
        }
        return this.validatorsMap.get(validatorKey);
    }

    private String getValidatorKey(final String formObjectName) {
        return String.format("%s%s", formObjectName, VALIDATOR_KEY_SUFFIX);
    }
}
