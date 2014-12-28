package org.agatom.springatom.webmvc.controllers.wizard;

import org.agatom.springatom.cmp.wizards.core.CreateObjectWizardProcessor;
import org.agatom.springatom.cmp.wizards.core.Submission;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.cmp.wizards.repository.WizardProcessorNotFoundException;
import org.agatom.springatom.cmp.wizards.repository.WizardsRepository;
import org.agatom.springatom.core.locale.SMessageSource;
import org.agatom.springatom.data.oid.SOidService;
import org.agatom.springatom.web.api.WizardController.Api;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test verifies accessibility and content type for {@link org.agatom.springatom.webmvc.controllers.wizard.SVWizardController}
 * as defined via {@link org.agatom.springatom.web.api.WizardController}.
 * Test does not concern about validating the response structure.
 */
public class SVWizardControllerWebApiTest {
    private static final String                              WIZARD_STEP_ID   = "test";
    private static final Locale                              LOCALE           = Locale.ENGLISH;
    @Mock
    private              WizardsRepository                   processorMap     = null;
    @Mock
    private              SMessageSource                      messageSource    = null;
    @Mock
    private              SOidService                         oidService       = null;
    @Mock
    private              CreateObjectWizardProcessor<String> wizardProcessor  = null;
    @InjectMocks
    private              SVWizardController                  wizardController = null;

    private MockMvc mockMvc = null;

    // mocks for the method calls/returns
    private WizardResult wizardResult = new WizardResult().setWizardId(WIZARD_STEP_ID).setStepId(WIZARD_STEP_ID);
    private ModelMap     modelMap     = new ModelMap();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LocaleContextHolder.setLocale(LOCALE);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.wizardController)
                .build();
    }

    @Test
    public void testOnWizardInit_500() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new NullPointerException());

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(request().asyncNotStarted());
    }

    @Test
    public void testOnStepInit_500() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new NullPointerException());

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(request().asyncNotStarted());
    }

    @Test
    public void testOnWizardSubmit_500() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new NullPointerException());

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(request().asyncNotStarted());

    }

    @Test
    public void testOnStepSubmit_500() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new NullPointerException());

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(request().asyncNotStarted());

    }

    @Test
    public void testOnWizardInit_NotFound() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new WizardProcessorNotFoundException(WIZARD_STEP_ID));

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").doesNotExist())
                .andExpect(jsonPath("content").doesNotExist());
    }

    @Test
    public void testOnStepInit_NotFound() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new WizardProcessorNotFoundException(WIZARD_STEP_ID));

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").doesNotExist())
                .andExpect(jsonPath("content").doesNotExist());
    }

    @Test
    public void testOnWizardSubmit_NotFound() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new WizardProcessorNotFoundException(WIZARD_STEP_ID));

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").doesNotExist())
                .andExpect(jsonPath("content").doesNotExist());

    }

    @Test
    public void testOnStepSubmit_NotFound() throws Exception {
        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenThrow(new WizardProcessorNotFoundException(WIZARD_STEP_ID));

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").doesNotExist())
                .andExpect(jsonPath("content").doesNotExist());

    }

    @Test
    public void testOnWizardInit_OK() throws Exception {
        this.successMockSettings();

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("submission", new IsEqual<Object>(Submission.INIT.toString())));
    }

    /**
     * Sets all required fields to allow successful {Http.OK} execution for controller methods
     *
     * @throws Exception if any
     */
    private void successMockSettings() throws Exception {
        Mockito.when(this.wizardProcessor.onStepInit(WIZARD_STEP_ID, LOCALE)).thenReturn(this.wizardResult);
        Mockito.when(this.wizardProcessor.onStepSubmit(WIZARD_STEP_ID, this.modelMap, LOCALE)).thenReturn(this.wizardResult);
        Mockito.when(this.wizardProcessor.onWizardInit(LOCALE)).thenReturn(this.wizardResult);
        Mockito.when(this.wizardProcessor.onWizardSubmit(this.modelMap, LOCALE)).thenReturn(this.wizardResult);

        Mockito.when(this.processorMap.get(WIZARD_STEP_ID)).thenReturn(this.wizardProcessor);
    }

    @Test
    public void testOnStepInit_OK() throws Exception {
        this.successMockSettings();

        this.mockMvc.perform(
                get(Api.ROOT + "/init/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("submission", new IsEqual<Object>(Submission.INIT_STEP.toString())));
    }

    @Test
    public void testOnWizardSubmit_OK() throws Exception {
        this.successMockSettings();

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}", WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("submission", new IsEqual<Object>(Submission.SUBMIT.toString())));

    }

    @Test
    public void testOnStepSubmit_OK() throws Exception {
        this.successMockSettings();

        this.mockMvc.perform(
                get(Api.ROOT + "/submit/{wizard}/step/{step}", WIZARD_STEP_ID, WIZARD_STEP_ID)
                        .param("wizard", WIZARD_STEP_ID)
                        .param("step", WIZARD_STEP_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("submission").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("submission", new IsEqual<Object>(Submission.SUBMIT_STEP.toString())));

    }
}