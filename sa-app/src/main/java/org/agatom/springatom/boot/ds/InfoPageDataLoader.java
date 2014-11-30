package org.agatom.springatom.boot.ds;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.cmp.component.infopages.persister.InfoPagePersistHandler;
import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.AbstractDataLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.io.InputStream;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order(4)
@DataLoader
class InfoPageDataLoader
        extends AbstractDataLoaderService {
    private static final Logger                 LOGGER         = LoggerFactory.getLogger(InfoPageDataLoader.class);
    private static final String                 PATH           = "classpath:org/agatom/springatom/boot/ds/infopages.json";
    @Autowired
    private              InfoPagePersistHandler persistHandler = null;
    @Autowired
    private              ObjectMapper           objectMapper   = null;

    @Override
    public InstallationMarker loadData() {
        final InstallationMarker marker = new InstallationMarker();
        try {

            final InputStream stream = this.getStream(PATH);
            JsonNode jsonNode = this.objectMapper.readTree(stream);
            jsonNode = jsonNode.get("pages");
            for (final JsonNode node : jsonNode) {
                final String pathToPage = this.getPathToPage(node);
                final InputStream pathToPageStream = this.getStream(pathToPage);
                final InfoPage infoPage = this.objectMapper.readValue(pathToPageStream, InfoPage.class);
                this.persistHandler.saveToPersistentStorage(infoPage);
            }

            marker.setHash(stream.hashCode());
            marker.setPath(PATH);

        } catch (Exception exp) {
            LOGGER.error("Failed to load infopage data", exp);
            marker.setError(exp);
        }
        return marker;
    }

    private String getPathToPage(final JsonNode node) {
        return String.format("classpath:%s", node.asText());
    }
}
