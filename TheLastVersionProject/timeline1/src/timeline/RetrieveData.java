package timeline;


import java.sql.*;
import java.util.ArrayList;

public class RetrieveData {

    private ArrayList<String> data = new ArrayList<>();

    public RetrieveData(int id) {
        data.clear();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected");

            String query = "select tl_name, description, dateStart, dateEnd, dateIntStart, dateIntEnd  from log.tl where id ='" + id + "'";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("tl_name"));
                if (rs.getString("description") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("description"));

                if (rs.getString("dateStart") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("dateStart"));

                if (rs.getString("dateEnd") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("dateEnd"));
                try {


                    if (rs.getDate("dateIntStart") == null)
                        data.add(" ");
                    else
                        data.add(rs.getDate("dateIntStart").toString());

                    if (rs.getDate("dateIntEnd") == null)
                        data.add(" ");
                    else
                        data.add(rs.getDate("dateIntEnd").toString());

                } catch (SQLException e) {
                    System.out.println("Timeline " + e.toString());
                    data.add(" ");
                    data.add(" ");
                }
            }
            data.add("placeholder");


            query = "select name, description, startDate, endDate, dateIntStart, dateIntEnd, path  from log.entity where timeline_id ='" + id + "'";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("name"));

                if (rs.getString("description") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("description"));

                if (rs.getString("startDate") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("startDate"));

                if (rs.getString("endDate") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("endDate"));

                try {
                    if (rs.getDate("dateIntStart") == null)
                        data.add(" ");
                    else
                        data.add(rs.getDate("dateIntStart").toString());

                    if (rs.getDate("dateIntEnd") == null)
                        data.add(" ");
                    else
                        data.add(rs.getDate("dateIntEnd").toString());
                } catch (SQLException e) {
                    data.add(" ");
                    data.add(" ");
                }

                if (rs.getString("path") == null)
                    data.add(" ");
                else
                    data.add(rs.getString("path"));
            }

            while (data.size() % 7 != 0) {
                data.add(" ");
            }

            System.out.println(data);

            pst.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getData() {
        return data;
    }
}
