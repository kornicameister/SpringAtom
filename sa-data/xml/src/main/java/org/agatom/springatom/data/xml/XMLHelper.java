package org.agatom.springatom.data.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface XMLHelper {
    void convertFromObjectToXML(Object object, String filePath) throws IOException;

    Object convertFromXMLToObject(File xmlFile) throws IOException;

    Object convertFromXMLToObject(String xmlFile) throws IOException;

    Object convertFromXMLToObject(InputStream stream) throws IOException;
}
