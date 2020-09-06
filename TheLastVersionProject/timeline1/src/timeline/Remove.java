package timeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Remove extends Controller {

    public Remove(String selectAdmin, String selectUsername ) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    //It depend on your DB name, in my case the DB called log. However the password is also depend on you
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            String deleteAdmin = "DELETE FROM log.admin WHERE username='" + selectAdmin + "'";
            statement.executeUpdate(deleteAdmin);
            String deleteUser = "DELETE FROM log.user WHERE username='" + selectUsername + "'";
            statement.executeUpdate(deleteUser);
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
