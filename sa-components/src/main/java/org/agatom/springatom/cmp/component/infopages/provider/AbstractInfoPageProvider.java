package org.agatom.springatom.cmp.component.infopages.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMapping;
import org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMappingService;
import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class AbstractInfoPageProvider
        implements InfoPageProviderService {
    private static final Logger                 LOGGER                 = LoggerFactory.getLogger(AbstractInfoPageProvider.class);
    @Autowired
    protected            ObjectMapper           objectMapper           = null;
    @Autowired
    private              InfoPageMappingService infoPageMappingService = null;

    @PostConstruct
    private void postConstruct() {
        Assert.notNull(this.objectMapper, "ObjectMapper not initialized");
    }

    /** {@inheritDoc} */
    @Override
    public final InfoPage getInfoPage(final Persistable<?> persistable) throws Exception {
        LOGGER.debug(String.format("getInfoPage(persistable=%s)", persistable));
        if (persistable == null) {
            throw new IllegalArgumentException("persistable", new NullPointerException());
        }
        return this.getInfoPage(persistable.getClass());
    }

    @Override
    public Collection<InfoPageMapping> getRegisteredInfoPages() {
        return this.infoPageMappingService.getInfoPageMappings();
    }
}
