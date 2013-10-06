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

package org.agatom.springatom.web.support.actions.xml;

import org.agatom.springatom.web.support.actions.beans.SActions;
import org.agatom.springatom.web.support.actions.exception.SActionRepositoryException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SActionsXMLResolver {
    private static final Logger LOGGER = Logger.getLogger(SActionsXMLResolver.class);
    private Jaxb2Marshaller resolver;

    @Required
    public void setResolver(final Jaxb2Marshaller resolver) {
        this.resolver = resolver;
    }

    public SActions getDefinitions(final String path) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Reading definition from file=%s", path));
        }
        FileInputStream is = null;
        try {
            try {
                is = new FileInputStream(path);
                final SActions actions = (SActions) this.resolver.unmarshal(new StreamSource(is));
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(String.format("Read definition\n->file=%s\n->definition=%s", path, actions));
                }
                return actions;
            } catch (NullPointerException exception) {
                LOGGER.fatal("Unmarshaller is null, could not convert xml>object", exception);
                throw exception;
            } catch (FileNotFoundException exception) {
                LOGGER.fatal("File was not found, could not convert xml>object", exception);
                throw exception;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (Exception exception) {
            throw new SActionRepositoryException(String
                    .format("Failed to read the definition of from file=%s", path), exception);
        }
    }
}
