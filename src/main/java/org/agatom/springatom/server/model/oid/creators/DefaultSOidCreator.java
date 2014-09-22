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

package org.agatom.springatom.server.model.oid.creators;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import org.agatom.springatom.server.model.oid.SOid;
import org.agatom.springatom.server.model.oid.SOidCreator;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class DefaultSOidCreator<T>
        implements SOidCreator<T> {
    private static final Logger LOGGER            = Logger.getLogger(DefaultSOid.class);
    private static final int    TYPE_PREFIX_INDEX = 0;
    private static final int    CLASS_NAME_INDEX  = 1;
    private static final int    ID_INDEX          = 2;
    private static final String TYPE_PREFIX       = "typePrefix";
    private static final String CLASS_NAME        = "className";
    private static final String ID                = "id";
    private static final String OID_FORMAT        = String.format("%s:%s:%s", TYPE_PREFIX, CLASS_NAME, ID);

    @Override
    public final SOid fromString(final String from) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("fromString(from=%s)", from));
        }
        final SOid oid;
        final Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            final String split[] = from.split(":");
            Assert.isTrue(split.length == 3, String.format("OID[%s] is invalid, it should be in format A:B:C", from));
            Assert.isTrue(this.getTypePrefix().equals(split[0]), String.format("%s is not OID", from));

            final String oidPrefix = split[TYPE_PREFIX_INDEX];
            final Class<?> oidClass = Class.forName(split[CLASS_NAME_INDEX]);
            final String oidId = split[ID_INDEX];

            oid = this.getOidObject(oidPrefix, oidClass, oidId);
        } catch (Exception exp) {
            LOGGER.error(String.format("fromString(from=%s) failed...", from), Throwables.getRootCause(exp));
            throw exp;
        }

        stopwatch.stop();

        if (LOGGER.isTraceEnabled()) {
            final long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.trace(String.format("fromString(from=%s) to SOid(oid=%s) took %d ms", from, oid, elapsed));
        }

        return oid;
    }

    @Override
    public final <S extends T> SOid fromObject(final S from) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("fromObject(from=%s)", from));
        }
        return this.fromString(this.getOidString(from));
    }

    protected String getOidString(final T from) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getOidString(from=%s)", from));
        }
        final String typePrefix = this.getTypePrefix();
        final Class<?> typeClazz = ClassUtils.getUserClass(from);
        final String typeId = this.getTypeId(from).toString();
        return OID_FORMAT.replace(TYPE_PREFIX, typePrefix).replace(CLASS_NAME, typeClazz.getName()).replace(ID, typeId);
    }

    protected abstract SOid getOidObject(final String oidPrefix, final Class<?> oidClass, final String oidId);

    protected abstract Object getTypeId(final T from);

    protected abstract String getTypePrefix();

}
