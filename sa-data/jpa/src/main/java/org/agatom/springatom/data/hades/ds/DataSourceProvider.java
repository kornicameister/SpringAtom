package org.agatom.springatom.data.hades.ds;

import javax.sql.DataSource;

/**
 * {@code DataSourceProvider} is an internal interface for classes providing {@link javax.sql.DataSource}
 * instance
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2015-03-02</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
interface DataSourceProvider<T extends DataSource> {
  T getDataSource();
}
