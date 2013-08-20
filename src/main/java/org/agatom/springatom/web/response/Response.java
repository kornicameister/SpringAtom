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

package org.agatom.springatom.web.response;

import com.google.common.base.Objects;
import org.joda.time.DateTime;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Response {
    private final List<? extends Object> root;
    private       DateTime               timestamp;
    private       String                 rootClazz;
    private       long                   total;
    private       boolean                success;
    private       long                   time;
    private       String                 message;

    public Response(final List<?> root) {
        this.root = root;
        this.rootClazz = root.get(0).getClass().getName();
        this.total = root.size();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(timestamp)
                      .addValue(root)
                      .addValue(rootClazz)
                      .addValue(total)
                      .addValue(success)
                      .addValue(time)
                      .addValue(message)
                      .toString();
    }

    public Response getResponse() {
        return this;
    }
}
