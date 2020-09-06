package timeline;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Login extends Controller {

    private String username;
    private String password;
    private Text message;
    protected ArrayList<String> lastUsername;

    public Login(Text msg, String name, String password, ActionEvent event) {

        this.username = name;
        try {
            //CONNECTING TO THE ADMIN-TABLE IN MYSQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected");
            Statement stat = conn.createStatement();
            String sql = "Select * from log.user Where username='" + name + "' and password='" + password + "'";
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                String session = "INSERT INTO log.session(username) VALUES(\"" + username + "\")";
                stat.executeUpdate(session);
                if (adminCheck()) {
                    GoToAdminMain(event);
                } else {
                    GoToUserMain(event);
                }
            } else {
                msg.setText("WRONG USERNAME OR PASSWORD");
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
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

    public boolean adminCheck() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
        System.out.println("Connected Login");
        Statement stat = conn.createStatement();
        String sql = "Select * from log.admin Where username='" + username + "'";
        ResultSet rs = stat.executeQuery(sql);
        return rs.next();
    }
}
