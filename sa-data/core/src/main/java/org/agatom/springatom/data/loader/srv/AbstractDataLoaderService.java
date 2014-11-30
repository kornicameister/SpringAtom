package org.agatom.springatom.data.loader.srv;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractDataLoaderService
        implements DataLoaderService {

    protected InputStream getStream(final String path) throws IOException {
        final Resource file = new FileSystemResourceLoader().getResource(path);
        return file.getInputStream();
    }

}
