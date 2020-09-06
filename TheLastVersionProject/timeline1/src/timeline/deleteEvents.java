package timeline;

import java.sql.*;

public class deleteEvents {
    public deleteEvents(String eventName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            String sqlAdmin = "DELETE FROM log.entity WHERE name='" + eventName + "'";
            statement.executeUpdate(sqlAdmin);
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
        }
    }
}
