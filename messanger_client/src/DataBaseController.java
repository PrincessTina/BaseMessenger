import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DataBaseController {

  static Connection connection() throws SQLException {
    Connection connection;
    PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();
    dataSource.setDatabaseName("db");
    dataSource.setServerName("188.166.39.53");
    dataSource.setPortNumber(5432);

    connection = dataSource.getConnection("tina", "abjktnjdsq`;br1999");
    return connection;
  }
}
