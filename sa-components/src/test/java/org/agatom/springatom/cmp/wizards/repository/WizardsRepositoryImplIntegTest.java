package org.agatom.springatom.cmp.wizards.repository;

import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.agatom.springatom.cmp.wizards.WizardsConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                WizardsRepositoryImplIntegTest.WizardsRepositoryImplIntegTestConf.class,
                WizardsConfiguration.class
        }
)
public class WizardsRepositoryImplIntegTest {
    private static final String WZ_NAME = "mockProcessor";
    @Autowired
    private WizardsRepository wizardsRepository;

    @Test
    public void testGet() throws Exception {
        // At this we have everything bootstrapped, so we can verify the content of repository
        final WizardProcessor wizardProcessor = this.wizardsRepository.get(WZ_NAME);
        Assert.assertNotNull(wizardProcessor);
    }

    @Test(expected = WizardProcessorNotFoundException.class)
    public void testGet_NotFound() throws Exception {
        this.wizardsRepository.get("I_DONT_EXISTS");
    }

    @Configuration
    public static class WizardsRepositoryImplIntegTestConf {

        @Bean
        public SMessageSource messageSource() {
            return Mockito.mock(SMessageSource.class);
        }

        @Bean
        public WizardProcessor mockProcessor() {
            return Mockito.mock(MockProcessor.class);
        }

    }

    @Wizard(value = WZ_NAME)
    private static abstract class MockProcessor
            implements WizardProcessor {
    }
}