package org.agatom.springatom.webmvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.core.locale.SMessageSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SVLocaleControllerTest {
    private static final Locale              LOCALE           = Locale.ENGLISH;
    private static final int                 SIZE             = 10;
    private static final Map<String, String> MSGS             = Maps.newHashMapWithExpectedSize(SIZE);
    private static final MediaType           APPLICATION_JSON = MediaType.APPLICATION_JSON;
    @Mock
    private              SMessageSource      messageSource    = null;
    @InjectMocks
    private              SVLocaleController  localeController = null;
    private              MockMvc             mockMvc          = null;

    @BeforeClass
    public static void init() {
        for (int it = 0; it < SIZE; it++) {
            final String value = String.valueOf(it);
            MSGS.put(value, "msg");
        }
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LocaleContextHolder.setLocale(LOCALE);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.localeController)
                .build();
    }

    @Test
    public void testGetAvailableLocales_OK() throws Exception {
        ReflectionTestUtils.setField(this.localeController, "supportedLocales", "pl");

        this.mockMvc.perform(get("/rest/locales/").contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(this.getAvailableLocaleContentString()));
    }

    private String getAvailableLocaleContentString() throws JsonProcessingException {
        final Set<Locale> set = Sets.newHashSet();
        set.add(Locale.forLanguageTag("pl"));
        return new ObjectMapper().writeValueAsString(set);
    }

    @Test
    public void testGetAllMessages_OK() throws Exception {
        when(this.messageSource.getAllMessages(LOCALE)).thenReturn(MSGS);

        this.mockMvc.perform(
                get("/rest/locales/{locale}", LOCALE)
                        .param("locale", LOCALE.toLanguageTag())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(MSGS)));
    }

    @Test
    public void testGetMessage_OK() throws Exception {
        for (final String key : MSGS.keySet()) {
            when(this.messageSource.getMessage(key, LOCALE)).thenReturn(MSGS.get(key));
        }

        for (final String key : MSGS.keySet()) {
            this.mockMvc.perform(
                    get("/rest/locales/{locale}/{key:.+}", LOCALE, key)
                            .param("locale", LOCALE.toLanguageTag())
                            .param("key", key)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().string(MSGS.get(key)));
        }

    }

    @Test
    public void testGetMessage_NotFound_Exp() throws Exception {
        final String key = "theKey";
        when(this.messageSource.getMessage(key, LOCALE))
                .thenThrow(new NoSuchMessageException(key, LOCALE));

        this.mockMvc.perform(
                get("/rest/locales/{locale}/{key}", LOCALE, key)
                        .param("locale", LOCALE.toLanguageTag())
                        .param("key", key)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMessage_NotFound_IdenticalAsKey() throws Exception {
        final String key = "theKey";
        when(this.messageSource.getMessage(key, LOCALE))
                .thenReturn(key);

        this.mockMvc.perform(
                get("/rest/locales/{locale}/{key}", LOCALE, key)
                        .param("locale", LOCALE.toLanguageTag())
                        .param("key", key)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}