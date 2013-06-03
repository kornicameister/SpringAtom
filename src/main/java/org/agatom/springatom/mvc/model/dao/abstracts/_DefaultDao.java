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

package org.agatom.springatom.mvc.model.dao.abstracts;

import org.agatom.springatom.model.beans.Persistable;
import org.agatom.springatom.util.QueryConstants;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.validation.constraints.NotNull;

import static org.agatom.springatom.util.QueryConstants.Error.INACTIVE_SESSION;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public abstract class _DefaultDao {
    protected static final String DELETE_METHOD             = "delete(%s)";

    protected static final String CREATE_METHOD             = "create(%s)";

    protected static final String UPDATE_METHOD             = "update(%s)";

    protected static final String LOAD_METHOD               = "load(%s)";

    protected static final String FIND_METHOD               = "find(%s)";

    protected static final String FIND_BY_NATURAL_ID_METHOD = "find_by_natural_id(%s)";

    protected static final String EXISTS_METHOD             = "exists(%s)";

    protected static final String COUNT_METHOD              = "count";

    private static final   Logger LOGGER                    = Logger.getLogger(_DefaultDao.class);

    protected Class<? extends Persistable> target;

    protected abstract Class<? extends Persistable> getTargetClazz();

    public abstract void setTarget(final Class<? extends Persistable> target);

    /**
     * Method pushes runtime checkup against ensuring that both session and target class
     * has been provided. Returns false otherwise.
     *
     * @param method
     *         method name that requires access to session at the moment
     *
     * @return false if something is missing, true otherwise
     */
    protected boolean isQueryPossible(@NotNull final String method) {
        boolean status = true;
        if (this.target == null) {
            LOGGER.warn(String.format(QueryConstants.Error.MISSING_TARGET_CLASS, method));
            status = false;
        }
        if (this.getSession() == null) {
            LOGGER.warn(INACTIVE_SESSION);
            status = false;
        }
        return status;
    }

    protected abstract Session getSession();
}
