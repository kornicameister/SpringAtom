package org.agatom.springatom.cmp.component.select.factory;

import com.google.common.collect.Lists;
import org.agatom.springatom.cmp.component.ComponentsConfiguration;
import org.agatom.springatom.cmp.component.select.SelectComponent;
import org.agatom.springatom.cmp.component.select.SelectOption;
import org.agatom.springatom.cmp.locale.MessageSourceConfiguration;
import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.data.services.enumeration.SEnumerationService;
import org.agatom.springatom.data.types.enumeration.Enumeration;
import org.agatom.springatom.data.types.enumeration.EnumerationEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Persistable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        MessageSourceConfiguration.class,
        ComponentsConfiguration.class
})
public class SelectComponentFactoryImplTest {
    private static final String                                   ENUM_NAME              = "TEST";
    @Mock
    private              SMessageSource                           messageSource          = null;
    @Mock
    private              SEnumerationService<?, EnumerationEntry> enumerationService     = null;
    @InjectMocks
    private              SelectComponentFactory                   selectComponentFactory = new SelectComponentFactoryImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFromEnumeration() throws Exception {
        final Locale locale = Locale.ENGLISH;
        when(this.enumerationService.getEnumeration(ENUM_NAME)).thenReturn(this.getMockedEnumeration());
        when(this.messageSource.getMessage(ENUM_NAME, locale)).thenReturn(ENUM_NAME);
        final SelectComponent<String, String> component = this.selectComponentFactory.fromEnumeration(ENUM_NAME);

        Assert.assertNotNull("Component must not be null", component);

        final Collection<SelectOption<String, String>> options = component.getOptions();
        Assert.assertNotNull("Options must not be null", options);
        Assert.assertEquals(ENUM_NAME, component.getLabel());
        Assert.assertEquals("There must be only one option", options.size(), 1);

        for (SelectOption<String, String> option : options) {
            Assert.assertNotNull("Option must not be null", option);
            Assert.assertEquals(ENUM_NAME, option.getLabel());
            Assert.assertEquals(ENUM_NAME, option.getTooltip());
            Assert.assertEquals(ENUM_NAME, option.getValue());
        }
    }

    private TestEnumeration getMockedEnumeration() {
        return new TestEnumeration();
    }

    private static class TestEnumeration
            implements Persistable<Long>, Enumeration<EnumerationEntry> {
        private static final long serialVersionUID = 7827687393993550925L;

        @Override
        public Iterator<EnumerationEntry> iterator() {
            return this.getEntries().iterator();
        }

        @Override
        public Iterable<EnumerationEntry> getEntries() {
            return Lists.<EnumerationEntry>newArrayList(
                    new EnumerationEntry() {
                        @Override
                        public String getValue() {
                            return ENUM_NAME;
                        }

                        @Override
                        public String getKey() {
                            return ENUM_NAME;
                        }

                        @Override
                        public String getComment() {
                            return ENUM_NAME;
                        }

                        @Override
                        public int compareTo(final EnumerationEntry o) {
                            return 0;
                        }
                    }
            );
        }

        @Override
        public String getName() {
            return ENUM_NAME;
        }

        @Override
        public int size() {
            return ((List<?>) this.getEntries()).size();
        }

        @Override
        public Long getId() {
            return null;
        }

        @Override
        public boolean isNew() {
            return false;
        }


    }
}