package org.agatom.springatom.cmp.wizards.repository;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service("wizardRepository")
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_SUPPORT)
class WizardsRepositoryImpl
        implements WizardsRepository {
    private static final Logger                       LOGGER             = LoggerFactory.getLogger(WizardsRepositoryImpl.class);
    @Autowired
    private              DefaultListableBeanFactory   beanFactory        = null;
    private              Map<String, WizardProcessor> wizardProcessorMap = null;

    @PostConstruct
    private void loadProcessors() {
        final Stopwatch startTime = Stopwatch.createStarted();
        final String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(Wizard.class);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Located following %s=%s", ClassUtils.getShortName(Wizard.class), Arrays.toString(beanNames)));
        }
        this.wizardProcessorMap = Maps.newConcurrentMap();
        for (final String beanName : beanNames) {
            final Object bean = this.beanFactory.getBean(beanName);
            Assert.isInstanceOf(WizardProcessor.class, bean, String.format("%s annotated as Wizard, however is not subclass of %s", beanName, WizardProcessor.class));
            this.wizardProcessorMap.put(beanName, (WizardProcessor) bean);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Located %d Wizards in %d ms", this.wizardProcessorMap.size(), startTime.stop().elapsed(TimeUnit.MILLISECONDS)));
        }
        if (LOGGER.isTraceEnabled()) {
            for (Map.Entry<String, WizardProcessor> entry : this.wizardProcessorMap.entrySet()) {
                final String nameK = entry.getKey();
                final String nameV = ClassUtils.getUserClass(entry.getValue().getClass()).getName();
                LOGGER.trace(String.format("As Wizard collected %s=%s", nameK, nameV));
            }
        }
    }

    @Override
    public WizardProcessor get(@NotNull final String key) throws WizardProcessorNotFoundException {
        WizardProcessor processor = this.wizardProcessorMap.get(key);
        if (processor == null) {
            processor = this.reGetFromFactory(key);
        }
        if (processor == null) {
            throw new WizardProcessorNotFoundException(String.format("Wizard(key=%s) not found", key));
        }
        return processor;
    }

    private WizardProcessor reGetFromFactory(final String key) {
        if (!this.beanFactory.containsBeanDefinition(key)) {
            return null;
        }
        final Object bean = this.beanFactory.getBean(key);
        if (bean != null) {
            this.wizardProcessorMap.put(key, (WizardProcessor) bean);
        }
        return (WizardProcessor) bean;
    }

}
