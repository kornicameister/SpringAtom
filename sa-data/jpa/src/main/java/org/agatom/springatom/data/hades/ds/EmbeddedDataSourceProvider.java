package org.agatom.springatom.data.hades.ds;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2015-03-02</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */

class EmbeddedDataSourceProvider
    implements DataSourceProvider<EmbeddedDatabase> {
  @Override
  public EmbeddedDatabase getDataSource() {
    final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

    builder.setType(EmbeddedDatabaseType.H2)
        .setScriptEncoding("UTF-8")
        .setName("springatom")
        .continueOnError(true);

    return builder.build();
  }
}
