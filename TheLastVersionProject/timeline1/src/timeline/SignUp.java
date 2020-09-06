package timeline;

import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp {

    private String username;
    private String password;
    private Text message;

    public SignUp(Text msg, String name, String password) {

        this.username = name;
        this.password = password;
        this.message = msg;

        try {
            //EVERY USER THAT IS SIGNING UP SHOULD BE A NORMAL USER
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected SignUp");
            Statement statement = conn.createStatement();
            if (name.isBlank() || password.isBlank()){
                msg.setText("You have to select a username and password");
            }
            else {
                String sql = "INSERT INTO log.user(username, password) VALUES('" + name + "','" + password + "')";
                System.out.println(sql);
                statement.executeUpdate(sql);
                msg.setText("Your sign up is successfully completed");
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            msg.setText("Someone already has this username. Try another username.");
            ex.getStackTrace();
        }
    }
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Text getMessage() {
        return this.message;
    }


}
