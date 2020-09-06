package timeline;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class UpdateEvent extends Controller {

    public UpdateEvent(Text feedBack, String name, String desc, String start, String end, String dateIntStart, String dataIntEnd, String path, String oldEvent) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement stat = conn.createStatement();
            if (start.isBlank() || end.isBlank()) {
                start = " ";
                end = " ";
            }
            if (dateIntStart.isBlank() || dataIntEnd.isBlank()) {
                dateIntStart = " ";
                dataIntEnd = " ";
            }

            String sql = "Select * from log.entity Where name='" + name + "' and description='" + desc + "' and startDate='" + start + "' and endDate='" + end + "' and dateIntStart='" + dateIntStart + "' and dateIntEnd='" + dataIntEnd + "' and path ='" + path + "'";
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                feedBack.setText("NoThing been changed");
            } else {
                String query = "UPDATE  log.entity set name ='" + name + "', description ='" + desc + "', startDate ='" + start + "', endDate ='" + end + "', dateIntStart ='" + dateIntStart + "', dateIntEnd = '" + dataIntEnd + "', path ='" + path + "' where name ='" + oldEvent + "'";
                stat.executeUpdate(query);
                feedBack.setText("Saved successfully");
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            if (desc.length() > 1000) {
                feedBack.setText("The description is too long");
            }
            if (name.length() > 45) {
                feedBack.setText("The name is too long");
            }
            if (start.length() > 55) {
                feedBack.setText("The Start date is too long");
            }
            if (end.length() > 55) {
                feedBack.setText("The End date is too long");
            }
            ex.getStackTrace();
        }
    }
}
