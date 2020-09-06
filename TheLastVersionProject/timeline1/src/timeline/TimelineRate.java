package timeline;

import java.sql.*;

public class TimelineRate {
    protected String user;

    public TimelineRate(int ID, int rate) {
        int oldrate = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected");
            Statement statement = conn.createStatement();
            String getOldRate = "SELECT DISTINCT rate from log.tl where id=" + ID + "";
            ResultSet getTheRate = statement.executeQuery(getOldRate);
            while (getTheRate.next()) {
                oldrate = getTheRate.getInt("rate");
            }
            int theNewRate = oldrate + rate;

            String query = "UPDATE log.tl set rate = " + theNewRate + " where id =" + ID;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.executeUpdate();

            String getUsername = "select username from log.session where id = (select max(id) from session)";
            ResultSet rs = statement.executeQuery(getUsername);
            while (rs.next()) {
                user = rs.getString(1);
            }

            String allId = "";

            String getAllID = "select rating from log.user where username ='" + user + "'";
            ResultSet allID = statement.executeQuery(getAllID);
            while (allID.next()) {
                allId = allID.getString("rating");
            }

            String id_string = String.valueOf(ID);

            String setTimeLine = "UPDATE log.user set rating = '" + allId + "," + id_string + " ' where username ='" + user + "'";
            PreparedStatement test = conn.prepareStatement(setTimeLine);
            test.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
