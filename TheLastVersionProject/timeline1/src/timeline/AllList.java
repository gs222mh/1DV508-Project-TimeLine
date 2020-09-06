package timeline;


import java.sql.*;
import java.util.ArrayList;

public class AllList {


    ArrayList<String> AdminList = new ArrayList<String>();
    ArrayList<String> UserList = new ArrayList<String>();
    ArrayList<String> TimeLineList = new ArrayList<String>();
    ArrayList<Integer> TimeLineID = new ArrayList<Integer>();
    ArrayList<String> Events = new ArrayList<String>();
    int numbOfTl = 0;


    public AllList() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    //It depend on your DB name, in my case the DB called log. However the password is also depend on you
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected adminList");
            Statement statement = conn.createStatement();
            String sqlAdmin = "SELECT DISTINCT username from log.admin";
            ResultSet resultAdmin = statement.executeQuery(sqlAdmin);
            while (resultAdmin.next()) {
                AdminList.add(resultAdmin.getString(1));
            }
            String sqlUser = "SELECT DISTINCT username from log.user";
            ResultSet resultUser = statement.executeQuery(sqlUser);
            while (resultUser.next()) {
                UserList.add(resultUser.getString(1));
            }
            //Counts how many Timelines there is...
            String sqlNumbOfTl = "SELECT DISTINCT id from log.tl";
            ResultSet resultNumbOfTl = statement.executeQuery(sqlNumbOfTl);
            while (resultNumbOfTl.next()) {
                TimeLineID.add(resultNumbOfTl.getInt(1));
                numbOfTl++;
            }
            for (int i = 0; i < TimeLineID.size(); i++) {
                String sqlTimelinesName = "SELECT tl_name from log.tl WHERE id = '" + TimeLineID.get(i) + "'";
                ResultSet resultTimelinesNames = statement.executeQuery(sqlTimelinesName);
                while (resultTimelinesNames.next()) {
                    TimeLineList.add(resultTimelinesNames.getString(1));
                }
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
        }
    }

    public AllList(String tl) {
        int id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    //It depend on your DB name, in my case the DB called log. However the password is also depend on you
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected adminList");
            Statement statement = conn.createStatement();
            String sqlAdmin = "SELECT id from log.tl where tl_name='" + tl + "'";
            ResultSet resultAdmin = statement.executeQuery(sqlAdmin);
            while (resultAdmin.next()) {
                id = resultAdmin.getInt("id");
            }
            String selectEvents = "SELECT DISTINCT name from log.entity where timeline_ID=" + id + "";
            ResultSet viewEvent = statement.executeQuery(selectEvents);
            while (viewEvent.next()) {
                Events.add(viewEvent.getString("name"));
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
        }
    }
}
