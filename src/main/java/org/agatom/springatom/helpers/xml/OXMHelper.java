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

package org.agatom.springatom.helpers.xml;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class OXMHelper {
    private static final Logger LOGGER = Logger.getLogger(OXMHelper.class);
    protected Marshaller   marshaller;
    protected Unmarshaller unmarshaller;

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

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

    public Object convertFromXMLToObject(String xmlFile) throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(xmlFile);
            return getUnmarshaller().unmarshal(new StreamSource(is));
        } catch (NullPointerException exception) {
            LOGGER.fatal("Unmarshaller is null, could not convert xml>object", exception);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return null;
    }

    public Object convertFromXMLToObject(File xmlFile) throws IOException {
        return this.convertFromXMLToObject(xmlFile.getAbsolutePath());
    }
}
