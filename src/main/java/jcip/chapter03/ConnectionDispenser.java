package jcip.chapter03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-10：使用ThreadLocal来维持线程封闭性
 */
public class ConnectionDispenser {

  static String DB_URL = "jdbc:mysql://localhost/mydatabase";
  private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>() {
    @Override
    protected Connection initialValue() {
      try {
        return DriverManager.getConnection(DB_URL);
      } catch (SQLException e) {
        throw new RuntimeException("Unable to acquire Connection, e");
      }
    }
  };

  public Connection getConnection() {
    return connectionThreadLocal.get();
  }
}
