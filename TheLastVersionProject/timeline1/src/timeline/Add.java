package timeline;

import javafx.scene.text.Text;

import java.sql.*;

public class Add {
    public Add(String selectUsername) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    //It depend on your DB name, in my case the DB called log. However the password is also depend on you
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            if (!selectUsername.equals("Null")){
                String add = "INSERT INTO log.admin(username) VALUES('" + selectUsername + "')";
                statement.executeUpdate(add);
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }
}
