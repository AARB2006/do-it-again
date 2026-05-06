package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil{
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(AppConfig.dbUrl, AppConfig.dbUser, AppConfig.dbPass);
    }
}
