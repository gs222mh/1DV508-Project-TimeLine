package timeline;

import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;

public class createTimeLineEvent {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String tl_ID;
    private String pathOfimage;
    private LocalDate dateIntStart;
    private LocalDate dateIntEnd;
    protected boolean check;

    public createTimeLineEvent(Text msg, String name, String description, String startDate, String endDate, String pathOfImage, double size, int width, int hight) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pathOfimage = pathOfImage;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("connected EVENT2");
            Statement stat = conn.createStatement();
            String getId = "select max(id) from log.tl";
            ResultSet maxId = stat.executeQuery(getId);
            while (maxId.next()) {
                tl_ID = maxId.getString(1);
            }
            if (name.isBlank()) {
                check = false;
                msg.setText("You have to select a name");
            } else if (description.length() > 1000) {
                check = false;
                msg.setText("The description is too long");
            } else if (name.length() > 45) {
                check = false;
                msg.setText("The name is too long");
            } else if (startDate.length() > 55) {
                check = false;
                msg.setText("The Start date is too long");
            } else if (endDate.length() > 55) {
                msg.setText("The End date is too long");
            } else if (startDate.isEmpty() || endDate.isEmpty()) {
                check = false;
                msg.setText("You should select at least Date or Abstract");
            } else if (!pathOfImage.isEmpty()) {
                if (width < 100 && hight < 100 || width > 2000 && hight > 1000) {
                    check = false;
                    msg.setText("The dimensions should not be lower than 100x100 and larger than 2000x1000");
                } else {
                    check = true;
                    msg.setText("Added successfully");
                    String query = "INSERT INTO `log`.`entity` (`name`, `description`, `startDate`, `endDate`, `timeline_ID`, `path`, `size`, `resolution`) VALUES ('" + this.name + "','" + this.description + "','" + this.startDate + "','" + this.endDate + "','" + tl_ID + "','" + this.pathOfimage + "','" + size + "','" + width + "x" + hight + "');";
                    stat.executeUpdate(query);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Image is Selected and Saved");
                    alert.setContentText(pathOfImage.toString());
                    alert.show();
                }
            } else {
                check = true;
                msg.setText("Added successfully");
                String query = "INSERT INTO `log`.`entity` (`name`, `description`, `startDate`, `endDate`, `timeline_ID`, `path`, `size`, `resolution`) VALUES ('" + this.name + "','" + this.description + "','" + this.startDate + "','" + this.endDate + "','" + tl_ID + "','" + this.pathOfimage + "','" + size + "','" + width + "x" + hight + "');";
                stat.executeUpdate(query);
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            check = false;
            msg.setText("Duplicate Events!");
            ex.getStackTrace();
        }
    }

    public createTimeLineEvent(Text msg, String name, String description, LocalDate dateIntStart, LocalDate dateIntEnd, String pathOfImage, double size, int width, int hight) {
        this.name = name;
        this.description = description;
        this.dateIntStart = dateIntStart;
        this.dateIntEnd = dateIntEnd;
        this.pathOfimage = pathOfImage;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("connected EVENT2");
            Statement stat = conn.createStatement();
            String getId = "select max(id) from log.tl";
            ResultSet maxId = stat.executeQuery(getId);
            while (maxId.next()) {
                tl_ID = maxId.getString(1);
            }
            if (name.isBlank()) {
                check = false;
                msg.setText("You have to select a name");
            } else if (description.length() > 1000) {
                check = false;
                msg.setText("The description is too long");
            } else if (name.length() > 45) {
                check = false;
                msg.setText("The name is too long");
            } else if (dateIntEnd.isBefore(dateIntStart)) {
                check = false;
                msg.setText("The start date should be before the end Date");
            } else if (dateIntEnd.toString().isEmpty() || dateIntStart.toString().isEmpty()) {
                check = false;
                msg.setText("You should select at least Date or Abstract");
            } else if (!pathOfImage.isEmpty()) {
                if (width < 100 && hight < 100 || width > 2000 && hight > 1000) {
                    check = false;
                    msg.setText("The dimensions should not be lower than 100x100 and larger than 2000x1000");
                } else {
                    check = true;
                    msg.setText("Added successfully");
                    String query = "INSERT INTO `log`.`entity` (`name`, `description`, `timeline_ID`, `dateIntStart`, `dateIntEnd`, `path`, `size`, `resolution`) VALUES ('" + this.name + "','" + this.description + "','" + tl_ID + "','" + this.dateIntStart + "','" + this.dateIntEnd + "','" + this.pathOfimage + "','" + size + "','" + width + "x" + hight + "');";
                    stat.executeUpdate(query);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Image is Selected and Saved");
                    alert.setContentText(pathOfImage.toString());
                    alert.show();
                }
            } else {
                check = true;
                msg.setText("Added successfully");
                String query = "INSERT INTO `log`.`entity` (`name`, `description`, `timeline_ID`, `dateIntStart`, `dateIntEnd`, `path`, `size`, `resolution`) VALUES ('" + this.name + "','" + this.description + "','" + tl_ID + "','" + this.dateIntStart + "','" + this.dateIntEnd + "','" + this.pathOfimage + "','" + size + "','" + width + "x" + hight + "');";
                stat.executeUpdate(query);
            }
            stat.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            check = false;
            msg.setText("Duplicate Events!");
            ex.getStackTrace();
        }
    }

    public boolean check() {
        return check;
    }

}
