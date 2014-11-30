/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.xml;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * <p>OXMLEngine class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class OXMLEngine
        implements XMLHelper {
    private static final Logger LOGGER = Logger.getLogger(OXMLEngine.class);
    protected Marshaller   marshaller;
    protected Unmarshaller unmarshaller;

    @Override
    public void convertFromObjectToXML(Object object, String filePath)
            throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            getMarshaller().marshal(object, new StreamResult(os));
        } catch (NullPointerException exception) {
            LOGGER.fatal("Marshaller is null, could not convert xml>object", exception);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * <p>Getter for the field <code>marshaller</code>.</p>
     *
     * @return a {@link org.springframework.oxm.Marshaller} object.
     */
    public Marshaller getMarshaller() {
        return marshaller;
    }

    /**
     * <p>Setter for the field <code>marshaller</code>.</p>
     *
     * @param marshaller a {@link org.springframework.oxm.Marshaller} object.
     */
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    /**
     * <p>convertFromXMLToObject.</p>
     *
     * @param xmlFile a {@link java.io.File} object.
     *
     * @return a {@link Object} object.
     *
     * @throws java.io.IOException if any.
     */
    @Override
    public Object convertFromXMLToObject(File xmlFile) throws IOException {
        return this.convertFromXMLToObject(xmlFile.getAbsolutePath());
    }

    /**
     * <p>convertFromXMLToObject.</p>
     *
     * @param xmlFile a {@link String} object.
     *
     * @return a {@link Object} object.
     *
     * @throws java.io.IOException if any.
     */
    @Override
    public Object convertFromXMLToObject(String xmlFile) throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(xmlFile);
            return this.convertFromXMLToObject(is);
        } catch (NullPointerException exception) {
            LOGGER.fatal("Unmarshaller is null, could not convert xml>object", exception);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return null;
    }

    @Override
    public Object convertFromXMLToObject(final InputStream stream) throws IOException {
        return getUnmarshaller().unmarshal(new StreamSource(stream));
    }

    /**
     * <p>Getter for the field <code>unmarshaller</code>.</p>
     *
     * @return a {@link org.springframework.oxm.Unmarshaller} object.
     */
    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    /**
     * <p>Setter for the field <code>unmarshaller</code>.</p>
     *
     * @param unmarshaller a {@link org.springframework.oxm.Unmarshaller} object.
     */
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
}
