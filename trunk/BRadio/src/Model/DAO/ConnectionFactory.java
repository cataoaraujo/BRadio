/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Configuration;
import Model.Logger.GeradorLog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection conn;
    private final static String DRIVER = "org.apache.mysql.jdbc.EmbeddedDriver";
    private static String serverName = Configuration.getInstance().getIpDataBase();
    private static String db = "BRadio";
    private static String url = "jdbc:mysql://" + serverName + "/" + db;
    private static String usuario = "root";
    private static String senha = "1234";
    
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                try {
                    conn = DriverManager.getConnection(url, usuario, senha);
                } catch (SQLException e) {
                    //GeradorLog.getLoggerFull().severe(e.toString());
                }
            }
        } catch (SQLException e) {
            //GeradorLog.getLoggerFull().severe(e.toString());
        }
        return conn;
    }

    private static Connection createConnection(String driver, String url, String usuario, String senha) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, usuario, senha);
    }
    public static void commit(){
        try {
            conn.commit();
        } catch (SQLException e) {
            //GeradorLog.getLoggerFull().severe(e.toString());
        }
    }

}
