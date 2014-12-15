package org.agatom.springatom.cmp.wizards.repository;

import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WizardsRepositoryImplTest {
    private static final Class<Wizard>              ANNOTATION_TYPE   = Wizard.class;
    private static final String[]                   WIZARD_NAMES      = {
            "TEST_1",
            "TEST_2"
    };
    @Mock
    private              DefaultListableBeanFactory beanFactory       = null;
    @InjectMocks
    private              WizardsRepositoryImpl      wizardsRepository = null;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_LoadProcessors() throws Exception {
        final InOrder inOrder = inOrder(this.beanFactory);

        this.setUpProcessorsInMock();

        this.wizardsRepository.loadProcessors();

        inOrder.verify(this.beanFactory, calls(1)).getBeanNamesForAnnotation(ANNOTATION_TYPE);

        for (final String wizardName : WIZARD_NAMES) {
            inOrder.verify(this.beanFactory, calls(1)).getBean(wizardName, WizardProcessor.class);
        }

        inOrder.verifyNoMoreInteractions();

        assertNotNull("wizardProcessorMap is null", this.wizardsRepository.wizardProcessorMap);
        assertFalse("wizardProcessorMap is empty", this.wizardsRepository.wizardProcessorMap.isEmpty());
        assertTrue("wizardProcessorMap should have two elements", this.wizardsRepository.wizardProcessorMap.size() == 2);

        for (final String name : this.wizardsRepository.wizardProcessorMap.keySet()) {
            Assert.assertTrue(Arrays.binarySearch(WIZARD_NAMES, name) > -1);
        }

    }

    private void setUpProcessorsInMock() {
        when(this.beanFactory.getBeanNamesForAnnotation(ANNOTATION_TYPE)).thenReturn(WIZARD_NAMES);
        for (final String wizardName : WIZARD_NAMES) {
            when(this.beanFactory.getBean(wizardName, WizardProcessor.class)).thenReturn(Mockito.mock(WizardProcessor.class));
        }
    }

    @Test
    public void test_LoadProcessors_NoProcessors() {
        final InOrder inOrder = inOrder(this.beanFactory);

        when(this.beanFactory.getBeanNamesForAnnotation(ANNOTATION_TYPE)).thenReturn(new String[]{});

        this.wizardsRepository.loadProcessors();

        inOrder.verify(this.beanFactory, calls(1)).getBeanNamesForAnnotation(ANNOTATION_TYPE);
        inOrder.verifyNoMoreInteractions();

        assertNotNull(this.wizardsRepository.wizardProcessorMap);
        assertTrue(this.wizardsRepository.wizardProcessorMap.isEmpty());
    }

    @Test
    public void test_getProcessor_Exists() throws WizardProcessorNotFoundException {

        when(this.beanFactory.getBeanNamesForAnnotation(ANNOTATION_TYPE)).thenReturn(WIZARD_NAMES);
        this.setUpProcessorsInMock();

        this.wizardsRepository.loadProcessors();

        Assert.assertNotNull(this.wizardsRepository.get(WIZARD_NAMES[0]));
        Assert.assertNotNull(this.wizardsRepository.get(WIZARD_NAMES[1]));
    }

    @Test(expected = WizardProcessorNotFoundException.class)
    public void test_getProcessor_DoesNotExists() throws WizardProcessorNotFoundException {
        when(this.beanFactory.getBeanNamesForAnnotation(ANNOTATION_TYPE)).thenReturn(new String[]{});

        this.wizardsRepository.loadProcessors();

        this.wizardsRepository.get(WIZARD_NAMES[0]);
        this.wizardsRepository.get(WIZARD_NAMES[1]);
    }
}