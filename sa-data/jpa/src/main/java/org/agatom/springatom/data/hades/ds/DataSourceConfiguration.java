package org.agatom.springatom.data.hades.ds;

import com.zaxxer.hikari.HikariDataSource;
import org.agatom.springatom.core.annotations.profile.DevProfile;
import org.agatom.springatom.core.annotations.profile.ProductionProfile;
import org.agatom.springatom.core.annotations.profile.TestProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-08</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@PropertySource(value = "classpath:org/agatom/springatom/data/hades/database.properties")
public class DataSourceConfiguration {
  @Autowired
  private Environment environment = null;
  private DataSource  ds          = null;

  @Bean(name = "dataSource")
  @DevProfile
  @ProductionProfile
  public HikariDataSource hikariDataSource() throws Exception {
    return (HikariDataSource) (this.ds = new HikariDataSourceProvider(this.environment).getDataSource());
  }

  @Bean(name = "dataSource")
  @TestProfile
  public EmbeddedDatabase embeddedDatabase() throws Exception {
    return (EmbeddedDatabase) (this.ds = new EmbeddedDataSourceProvider().getDataSource());
  }

  @PreDestroy
  protected void onDestroy() {
    if (this.ds != null && this.ds instanceof EmbeddedDatabase) {
      ((EmbeddedDatabase) this.ds).shutdown();
    }
  }

}
