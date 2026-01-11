package connection;

import java.sql.*;

public class DBConnection {

    private static Connection con;

    public static Connection getConnection() {
        if (con == null) {
            try{
                String url = "jdbc:mysql://localhost:3306/gestion_ocp";
                con = DriverManager.getConnection(url, "root", "");
                System.out.println("Conection établie");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }
}
