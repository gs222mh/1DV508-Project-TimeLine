package timeline;

import javafx.scene.text.Text;

import java.sql.*;
import java.util.ArrayList;

public class Search {

    private String timeline;
    private String username;
    ArrayList<String> tlNames = new ArrayList<>();

    public Search(String timelineName, Text msg) {
        this.timeline = timelineName;

        try {
            tlNames.clear();
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected Search");
            Statement statement = conn.createStatement();
            String query = "Select * from log.tl Where tl_name LIKE '%" + timeline + "%'";
            ResultSet rs = statement.executeQuery(query);
            if (timelineName.isEmpty()){
              msg.setText("Write something to search");
            }
            else {
                msg.setText("");
                while (rs.next())
                    tlNames.add(rs.getString("tl_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTimeline() {
        return this.timeline;
    }

    public String getUsername() {
        return this.username;
    }

}
