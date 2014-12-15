package org.agatom.springatom.cmp.wizards.repository;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
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
        implements WizardsRepository, ApplicationListener<ContextRefreshedEvent> {
    public static final  Class<Wizard>                WIZARD_PROC_ANNOTATION = Wizard.class;
    private static final Logger                       LOGGER                 = LoggerFactory.getLogger(WizardsRepositoryImpl.class);
    protected            Map<String, WizardProcessor> wizardProcessorMap     = null;
    @Autowired
    private              DefaultListableBeanFactory   beanFactory            = null;

    /**
     * Reacts upon refreshing the context (i.e. context is ready) in order to detect all
     * {@link org.agatom.springatom.cmp.wizards.WizardProcessor} that haven't been discovered
     * during {@link #loadProcessors()}{@link javax.annotation.PostConstruct} operation.
     *
     * @param event context refreshed event
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(WIZARD_PROC_ANNOTATION);
        if (beanNames != null && beanNames.length > 0) {

            if (!this.getWizardProcessorMap().isEmpty()) {
                final Set<String> haveWizardProcessorsNames = this.getWizardProcessorMap().keySet();
                final Set<String> diff = Sets.newHashSetWithExpectedSize(beanNames.length);
                for (final String beanName : beanNames) {
                    if (!haveWizardProcessorsNames.contains(beanName)) {
                        diff.add(beanName);
                    }
                }
                if (!diff.isEmpty()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("{} >> collected {} new wizard processors in context", event, diff.size());
                    }
                    beanNames = diff.toArray(new String[diff.size()]);
                }
            }

            this.loadProcessorsFromBeanNames(beanNames);
        } else if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("{} >> No new beanNames", event);
        }
    }

    private Map<String, WizardProcessor> getWizardProcessorMap() {
        if (this.wizardProcessorMap == null) {
            this.wizardProcessorMap = Maps.newConcurrentMap();
        }
        return this.wizardProcessorMap;
    }

    protected void loadProcessorsFromBeanNames(final String[] beanNames) {
        for (final String beanName : beanNames) {
            final WizardProcessor bean = this.beanFactory.getBean(beanName, WizardProcessor.class);
            this.getWizardProcessorMap().put(beanName, bean);
        }
    }

    /**
     * Fired after {@code this} has been successfully initialized in order to read every occurrence of {@link org.agatom.springatom.cmp.wizards.WizardProcessor}
     * annotated with {@link org.agatom.springatom.cmp.wizards.Wizard} annotation.
     */
    @PostConstruct
    protected void loadProcessors() {
        final Stopwatch startTime = Stopwatch.createStarted();
        final String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(WIZARD_PROC_ANNOTATION);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Located following %s=%s", ClassUtils.getShortName(WIZARD_PROC_ANNOTATION), Arrays.toString(beanNames)));
        }
        if (beanNames.length > 0) {
            this.loadProcessorsFromBeanNames(beanNames);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Located %d Wizards in %d ms", this.getWizardProcessorMap().size(), startTime.stop().elapsed(TimeUnit.MILLISECONDS)));
        }
        if (LOGGER.isTraceEnabled()) {
            for (Map.Entry<String, WizardProcessor> entry : this.getWizardProcessorMap().entrySet()) {
                final String nameK = entry.getKey();
                final String nameV = ClassUtils.getUserClass(entry.getValue().getClass()).getName();
                LOGGER.trace(String.format("As Wizard collected %s=%s", nameK, nameV));
            }
        }
    }

    @Override
    public WizardProcessor get(@NotNull final String key) throws WizardProcessorNotFoundException {
        WizardProcessor processor = this.getWizardProcessorMap().get(key);
        if (processor == null) {
            processor = this.getFromBeanFactory(key);
        }
        if (processor == null) {
            throw new WizardProcessorNotFoundException(String.format("Wizard(key=%s) not found", key));
        }
        return processor;
    }

    protected WizardProcessor getFromBeanFactory(final String key) {
        if (!this.beanFactory.containsBeanDefinition(key) && !this.beanFactory.isCurrentlyInCreation(key)) {
            return null;
        }
        final WizardProcessor processor = this.beanFactory.getBean(key, WizardProcessor.class);
        if (processor != null) {
            this.getWizardProcessorMap().put(key, processor);
        }
        return processor;
    }

}
