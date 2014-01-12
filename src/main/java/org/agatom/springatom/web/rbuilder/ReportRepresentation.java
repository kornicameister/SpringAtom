/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.rbuilder;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.springframework.hateoas.Identifiable;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.jasperreports.JasperReportsCsvView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ReportRepresentation
        implements Serializable,
                   Comparable<ReportRepresentation> {

    private static final long                              serialVersionUID = 3606510385045622962L;
    private static       Map<String, ReportRepresentation> REP              = null;
    private final String type;
    private final String key;

    private ReportRepresentation(final Representation representation) {
        this.key = representation.getId();
        this.type = representation.name().toLowerCase();
    }

    public static Map<String, ReportRepresentation> getRepresentation() {
        if (REP == null) {
            final Map<String, ReportRepresentation> map = Maps.newHashMap();
            for (final Representation representation : Representation.values()) {
                if (representation.isPresent()) {
                    map.put(representation.getId(),
                            new ReportRepresentation(representation)
                    );
                }
            }
            REP = Collections.unmodifiableMap(map);
        }
        return REP;
    }


    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(type)
                      .addValue(key)
                      .toString();
    }

    @Override
    public int compareTo(@Nonnull final ReportRepresentation o) {
        return this.key.compareTo(o.key);
    }

    public static enum Representation
            implements Serializable,
                       Identifiable<String> {
        CSV(JasperReportsCsvView.class, "csv"),
        HTML(JasperReportsHtmlView.class, "html"),
        PDF(JasperReportsPdfView.class, "pdf"),
        EXCEL(JasperReportsXlsView.class, "xls");

        private final boolean present;
        private final String  mappingKey;

        Representation(final Class<?> exporter, final String mappingKey) {
            this.present = ClassUtils.isPresent(exporter.getName(), ReportRepresentation.class.getClassLoader());
            this.mappingKey = mappingKey;
        }

        public boolean isPresent() {
            return present;
        }

        @Override
        public String getId() {
            return this.mappingKey;
        }
    }

}
