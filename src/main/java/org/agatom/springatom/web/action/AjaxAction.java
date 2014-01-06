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

package org.agatom.springatom.web.action;

import com.google.common.base.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class AjaxAction
        extends LinkAction {
    private boolean       async       = Boolean.TRUE;
    private boolean       cache       = Boolean.FALSE;
    private Object        data        = null;
    private MediaType     contentType = MediaType.APPLICATION_JSON;
    private MediaType     dataType    = MediaType.APPLICATION_JSON;
    private RequestMethod type        = RequestMethod.POST;

    public AjaxAction setAsync(final boolean async) {
        this.async = async;
        return this;
    }

    public AjaxAction setCache(final boolean cache) {
        this.cache = cache;
        return this;
    }

    public AjaxAction setData(final Object data) {
        this.data = data;
        return this;
    }

    public AjaxAction setContentType(final MediaType contentType) {
        this.contentType = contentType;
        return this;
    }

    public AjaxAction setDataType(final MediaType dataType) {
        this.dataType = dataType;
        return this;
    }

    public AjaxAction setType(final RequestMethod type) {
        this.type = type;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public boolean isCache() {
        return cache;
    }

    public Object getData() {
        return data;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public MediaType getDataType() {
        return dataType;
    }

    public RequestMethod getType() {
        return type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(async)
                      .addValue(cache)
                      .addValue(data)
                      .addValue(contentType)
                      .addValue(dataType)
                      .addValue(type)
                      .addValue(url)
                      .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AjaxAction that = (AjaxAction) o;

        return Objects.equal(this.async, that.async) &&
                Objects.equal(this.cache, that.cache) &&
                Objects.equal(this.data, that.data) &&
                Objects.equal(this.contentType, that.contentType) &&
                Objects.equal(this.dataType, that.dataType) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(async, cache, data, contentType, dataType, type,
                url);
    }
}
