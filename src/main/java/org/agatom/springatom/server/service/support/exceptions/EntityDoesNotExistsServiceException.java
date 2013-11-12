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

package org.agatom.springatom.server.service.support.exceptions;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class EntityDoesNotExistsServiceException
        extends ServiceException {
    private static final String MSG_1            = "No entry exists for %s with PK=%s";
    private static final String MSG_2            = "No entry exists for %s for ARGS=%s";
    private static final long   serialVersionUID = -7853712771424308741L;

    public EntityDoesNotExistsServiceException(final Class<? extends Persistable> entityClazz, final Serializable pk) {
        super(entityClazz, String.format(MSG_1, entityClazz.getName(), pk));
    }

    public EntityDoesNotExistsServiceException(final Class<? extends Persistable> entityClazz, final Object... args) {
        super(entityClazz, String.format(MSG_2, entityClazz.getName(), Arrays.deepToString(args)));
    }
}
