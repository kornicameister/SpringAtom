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

@javax.xml.bind.annotation.XmlSchema(
        namespace = "http://www.example.org/SpringAtom",
        xmlns = {
                @XmlNs(prefix = "sa", namespaceURI = "http://www.example.org/SpringAtom")
        },
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
) package org.agatom.springatom.helpers.xml.beans;

import javax.xml.bind.annotation.XmlNs;