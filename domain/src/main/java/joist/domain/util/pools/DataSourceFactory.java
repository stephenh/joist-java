package joist.domain.util.pools;

import javax.sql.DataSource;

import joist.domain.util.ConnectionSettings;

public interface DataSourceFactory {

  DataSource create(ConnectionSettings settings);

  void destroy(DataSource dataSource);

}
