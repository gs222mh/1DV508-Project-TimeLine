package timeline;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class UpdateTimeline extends Controller {
    StringBuilder creater = new StringBuilder();

    public UpdateTimeline(Text feedBack, String name, String keys, String desc, String start, String end, String dateIntStart, String dataIntEnd, String tl) {
        try {
            int leng = tl.length();
            int count = 0;
            for (int i = 0; i < leng; i++) {
                if (tl.charAt(i) == '-') {
                    count = i;
                }
            }
            for (int i = count - 1; i < leng; i++) {
                creater.append(tl.charAt(i));
            }
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

            String sql = "Select * from log.tl Where tl_name='" + name + creater + "' and Keywords='" + keys + "' and description='" + desc + "' and dateStart='" + start + "' and dateEnd='" + end + "' and dateIntStart='" + dateIntStart + "' and dateIntEnd='" + dataIntEnd + "'";
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                feedBack.setText("NoThing been changed");
            } else {
                String query = "UPDATE  log.tl set tl_name ='" + name + creater + "', Keywords='" + keys + "', description ='" + desc + "', dateStart ='" + start + "', dateEnd ='" + end + "', dateIntStart ='" + dateIntStart + "', dateIntEnd = '" + dataIntEnd + "' where tl_name ='" + tl + "'";
                stat.executeUpdate(query);
                feedBack.setText("Saved successfully");
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            if (desc.length() > 1000) {
                feedBack.setText("The description is too long");
            }
            if (name.length() + creater.length() > 45) {
                feedBack.setText("The name is too long");
            }
            if (keys.length() > 100) {
                feedBack.setText("The keyWords is too long");
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
