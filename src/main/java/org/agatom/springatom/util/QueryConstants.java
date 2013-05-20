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
 * along with SpringAtom.  If not, see <http://www.gnu.org/licenses/gpl.html>.                    *
 **************************************************************************************************/

package org.agatom.springatom.util;

public class QueryConstants {

    public class QueryBody {
        public static final String FROM_TABLE_BY_TABLE_NAME = "from %s";
        public static final String DELETE_FROM_TABLE_BY_TABLE_NAME = "delete from %s";
        public static final String DELETE_FROM_TABLE_BY_TABLE_NAME_WHERE = "delete from %s where %s in (%s)";
    }

    public class QueryTrace {
        public static final String READ_ENTITY_FROM_TABLE = "Read entity %s from table %s";
    }

    public class QueryResult {
        public static final String FROM_TABLE_RESULT_WITH_CLASS_NAME = "Loaded %d objects from %s";
        public static final String FROM_WHERE_RESULT_WITH_CLASS_NAME = "Loaded %d objects from %s by where %s";
        public static final String SAVED_OBJECT_TO_TABLE = "Saved 1 one object of type %s";
        public static final String SAVED_N_OBJECTS_TO_TABLE = "Saved %d objects of type %s";
        public static final String DELETED_N_OBJECTS_TO_TABLE = "Saved %d objects of type %s";
    }

    public class Error {
        public static final String INACTIVE_SESSION = "Session is not active";
        public static final String MISSING_TARGET_CLASS = "No target class provided, can not execute query %s";
        public static final String FAILED_TO_SAVE_OBJECT = "Could not save object of type %s";
        public static final String FAILED_TO_SAVE_N_OBJECTS = "Could not save %d objects of type %s";
    }
}
