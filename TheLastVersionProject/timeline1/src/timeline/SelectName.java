package timeline;

import com.mysql.jdbc.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectName extends Controller {

    protected String username;


    public SelectName(Text msg, String timeLineName, ActionEvent event) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected SelectName");
            Statement statement = conn.createStatement();
            String getUsername = "select username from log.session where id = (select max(id) from session)";
            ResultSet rs = statement.executeQuery(getUsername);
            while (rs.next()) {
                username = rs.getString(1);
            }
            String getTimeList = "select tl_name from log.tl where username =\"" + username + "\"";
            ResultSet getListOfTimeLine = statement.executeQuery(getTimeList);
            ResultSetMetaData rsmd = getListOfTimeLine.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            ArrayList<String> tl = new ArrayList<String>();
            while (getListOfTimeLine.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = getListOfTimeLine.getString(i);
                    tl.add(columnValue);
                }
            }
            if (timeLineName.isBlank()) {
                msg.setText("You have to choose a name for your TimeLine");
            } else if (timeLineName.length() + username.length() + 3 > 45) {
                msg.setText("The name is too long");
            } else if (!tl.contains(timeLineName + " - " + username)) {
                String sql = "INSERT INTO log.tl(username, tl_name) VALUES('" + username + "','" + timeLineName + " - " + username + "')";
                statement.executeUpdate(sql);
                createTimeLineDes(event);
            } else {
                msg.setText("This user has already this TimeLine name");
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            ex.getStackTrace();
        }
    }
}
