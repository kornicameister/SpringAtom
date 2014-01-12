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

package org.agatom.springatom.server.populators;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

/**
 * {@code EnumDatabasePopulator} meant to used with either {@link org.springframework.jdbc.datasource.init.CompositeDatabasePopulator}
 * or as standalone populator. {@code EnumDatabasePopulator} populates entries from given {@link Enum}s which extends
 * {@link DatabaseEnumPopulable}.
 * Each entry is populated in the separate insert statement using {@link org.agatom.springatom.server.populators.DatabaseEnumPopulable#getColumns()}
 * to determine
 * target columns and {@link org.agatom.springatom.server.populators.DatabaseEnumPopulable#getData()} to set the data
 * for the
 * columns. It is crucial for success of the operation that
 * order of the <b>data</b> matches order of the <b>columns</b> and XML defined <b>target table</b> match {@link
 * org.agatom.springatom.server.populators.DatabaseEnumPopulable#getTable()} value.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EnumDatabasePopulator
        implements DatabasePopulator {
    private static final Logger LOGGER     = Logger.getLogger(EnumDatabasePopulator.class);
    private static final String CLASS_NAME = EnumDatabasePopulator.class.getSimpleName();
    private Properties enums;

    @Override
    public void populate(final Connection connection) throws SQLException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Enums to process = %d", this.enums.size()));
        }

        final long startTime = System.currentTimeMillis();
        for (final Map.Entry<Object, Object> entry : this.enums.entrySet()) {
            this.populateEnum((String) entry.getKey(), (String) entry.getValue(), connection);
        }
        final long endTime = System.currentTimeMillis() - startTime;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String
                    .format("Processed %d enums in %d ms", this.enums.size(), endTime));
        }
    }

    private void populateEnum(final String targetTable, final String enumClazz, final Connection connection) throws
            SQLException {

        Enum[] enums;

        try {
            final Class<?> clazzTarget = Class.forName(enumClazz);
            enums = (Enum[]) clazzTarget.getEnumConstants();
        } catch (ClassNotFoundException cnfe) {
            LOGGER.fatal(String.format("Failed to populate from %s", enumClazz), cnfe);
            return;
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Executing %s script for pair %s/%s",
                    CLASS_NAME, targetTable, enums[0].getClass().getCanonicalName()));
        }

        final Statement stmt = connection.createStatement();
        try {
            int enumNumber = 0;
            for (Enum<?> enumSource : enums) {
                final DatabaseEnumPopulable databasePopulate = (DatabaseEnumPopulable) enumSource;

                if (!targetTable.equals(databasePopulate.getTable())) {
                    LOGGER.warn(String
                            .format("Invalid target table=%s, DatabaseEnumPopulable#getTable() must be consistent with the property defined in the XML config=%s", databasePopulate
                                    .getTable(), targetTable));
                    return;
                }

                final String statementStr = this
                        .buildStatement(databasePopulate.getTable(), databasePopulate.getColumns(), databasePopulate
                                .getData());
                try {
                    stmt.execute(statementStr);
                    final int rowsAffected = stmt.getUpdateCount();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(rowsAffected + " returned as updateCount for SQL: " + statementStr);
                    }
                    enumNumber++;
                } catch (SQLException ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Failed to execute SQL script statement at line " + enumNumber +
                                " of resource " + enumSource + ": " + statementStr, ex);
                    }
                }
            }
        } finally {
            try {
                stmt.close();
            } catch (Throwable ex) {
                LOGGER.debug("Could not close JDBC Statement", ex);
            }
        }
    }

    private String buildStatement(final String table, final String[] columns, final String[] data) {
        final StringBuilder builder = new StringBuilder(String.format("insert into `%s` (", table));
        for (final String column : columns) {
            builder.append(String.format("`%s` ,", column));
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(") values (");
        for (final String dataStr : data) {
            builder.append(String.format("\'%s\', ", dataStr));
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.append(");").toString().trim();
    }

    public void setEnums(Properties enums) {
        this.enums = enums;
    }
}
