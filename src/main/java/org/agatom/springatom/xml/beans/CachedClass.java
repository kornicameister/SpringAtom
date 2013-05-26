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

package org.agatom.springatom.xml.beans;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.*;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="impl">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="cache" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string"
 * />
 *                           &lt;attribute name="method" use="required" type="{http://www.w3.org/2001/XMLSchema}string"
 * />
 *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="dao" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "impl"
})
public class CachedClass {

    @XmlElement(required = true)
    protected CachedClassImpl impl;
    @XmlAttribute(name = "dao", required = true)
    protected String dao;

    /**
     * Gets the value of the impl property.
     *
     * @return possible object is
     *         {@link CachedClassImpl }
     */
    public CachedClassImpl getImpl() {
        return impl;
    }

    /**
     * Sets the value of the impl property.
     *
     * @param value
     *         allowed object is
     *         {@link CachedClassImpl }
     */
    public void setImpl(CachedClassImpl value) {
        this.impl = value;
    }

    /**
     * Gets the value of the dao property.
     *
     * @return possible object is
     *         {@link String }
     */
    public
    @Nonnull
    String getDao() {
        return dao;
    }

    /**
     * Sets the value of the dao property.
     *
     * @param value
     *         allowed object is
     *         {@link String }
     */
    public void setDao(String value) {
        this.dao = value;
    }

}
