/**
 * @author inforkgodara
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    private Connection con;
    private static DbConnection dbc;
    private DbConnection() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","schema_name","password");
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DbConnection getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DbConnection();
        }
        return dbc;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public static void main(String[] args) {
        new DbConnection();
    }
}
