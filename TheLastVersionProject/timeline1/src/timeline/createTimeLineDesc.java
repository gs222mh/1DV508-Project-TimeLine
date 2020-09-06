package timeline;

import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;

public class createTimeLineDesc {
    protected String tl_name;
    protected boolean check;

    public createTimeLineDesc(Text msg, String keys, LocalDate startInt, LocalDate endInt, String des) {
        try {
            System.out.println(keys);
            System.out.println(startInt.toString());
            System.out.println(endInt.toString());
            System.out.println(des);

            String start = " ";
            String end = " ";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("connected Update");
            Statement stat = conn.createStatement();
            String getUsername = "select tl_name from log.tl where id = (select max(id) from log.tl)";
            ResultSet rs = stat.executeQuery(getUsername);
            while (rs.next()) {
                tl_name = rs.getString(1);
            }
            if (endInt.isBefore(startInt)) {
                check = false;
                msg.setText("The start date should be before the end Date");
            } else if (des.length() > 1000) {
                check = false;
                msg.setText("The description is too long");
            } else if (keys.length() > 100) {
                check = false;
                msg.setText("The keyWords is too long");
            } else if (endInt.toString().isEmpty() || startInt.toString().isEmpty()) {
                check = false;
                msg.setText("You have to select a date or an abstract range");
            } else {
                check = true;
                msg.setText("Added successfully");
                String query = "UPDATE  log.tl set Keywords ='" + keys + "', dateStart ='" + start + "', dateEnd ='" + end + "' ,dateIntStart ='" + startInt + "', dateIntEnd ='" + endInt + "', description ='" + des + "' where tl_name ='" + tl_name + "'";
                stat.executeUpdate(query);
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.getStackTrace();
        }
    }

    public createTimeLineDesc(Text msg, String keys, String startW, String endW, String des) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("connected Update");
            Statement stat = conn.createStatement();
            String getUsername = "select tl_name from log.tl where id = (select max(id) from log.tl)";
            ResultSet rs = stat.executeQuery(getUsername);
            while (rs.next()) {
                tl_name = rs.getString(1);
            }
            if (des.length() > 1000) {
                check = false;
                msg.setText("The description is too long");
            } else if (keys.length() > 100) {
                check = false;
                msg.setText("The keyWords is too long");
            } else if (startW.length() > 55) {
                check = false;
                msg.setText("The Start date is too long");
            } else if (endW.length() > 55) {
                check = false;
                msg.setText("The End date is too long");
            } else if (startW.isEmpty() || endW.isEmpty()) {
                check = false;
                msg.setText("You have to select a date or an abstract range");
            } else {
                check = true;
                msg.setText("Added successfully");
                String query = "UPDATE  log.tl set Keywords ='" + keys + "', description='" + des + "', dateStart ='" + startW + "', dateEnd ='" + endW + "'  where tl_name ='" + tl_name + "'";
                stat.executeUpdate(query);
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.getStackTrace();
        }
    }

    public boolean check() {
        return check;
    }
}
