package timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.paint.Color.*;

public class Controller implements Initializable {
    //ObservableList should be used to store all the users from the DB
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    protected TextField name;
    @FXML
    protected TextField SearchField;
    @FXML
    protected PasswordField pass;
    @FXML
    protected Text msg;
    @FXML
    protected ListView<String> AdminList = new ListView();
    @FXML
    protected ListView<String> UserList = new ListView();
    @FXML
    protected ListView<String> TimeLineList = new ListView();
    @FXML
    protected ListView<String> SearchList = new ListView();
    @FXML
    protected ListView<String> EventList = new ListView();
    @FXML
    protected Label totalTl = new Label();
    @FXML
    protected Label totalEvents = new Label();
    @FXML
    protected TextField TimeLineNameCreate;
    @FXML
    protected TextField eventName;
    @FXML
    protected TextField path;
    @FXML
    protected TextField TimeLineNameEdit;
    @FXML
    protected TextField EventNameEdit;
    @FXML
    protected TextField keyWordCreate;
    @FXML
    protected TextField keyWordEdit;
    @FXML
    protected TextField startWordCreate;
    @FXML
    protected TextField startWordEdit;
    @FXML
    protected TextField EventstartWordEdit;
    @FXML
    protected TextField endWordCreate;
    @FXML
    protected TextField endWordEdit;
    @FXML
    protected TextField EventendWordEdit;
    @FXML
    protected DatePicker startDateCreate;
    @FXML
    protected DatePicker startDateEdit;
    @FXML
    protected DatePicker EventstartDateEdit;
    @FXML
    protected DatePicker endDateCreate;
    @FXML
    protected DatePicker endDateEdit;
    @FXML
    protected DatePicker EventendDateEdit;
    @FXML
    protected TextArea descCreate;
    @FXML
    protected TextArea descCreateEvent;
    @FXML
    protected TextArea descEdit;
    @FXML
    protected TextArea EventdescEdit;
    @FXML
    protected TextField EventpathEdit;
    @FXML
    protected Text startTxt;
    @FXML
    protected Text endTxt;
    @FXML
    protected Button finish;
    @FXML
    protected Button NextToEvent;
    @FXML
    protected Button AddToTimeLine;
    @FXML
    protected Button AddEventToTimeLine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Here we initialize the loadData-method to list all the users
        loadData();
        if (TimeLineList.getSelectionModel().getSelectedItems() != null) {
            loadEditTimeline();
        }

        if (EventList.getSelectionModel().getSelectedItems() != null) {
            loadEditEvent();
        }

        Scanner sc = null;
        try {
            sc = new Scanner(new File("starwars.txt"));
        } catch (FileNotFoundException e) {

        }
        String str = "";
        try {
            while (sc.hasNextLine()) {
                str = sc.nextLine();
            }
        } catch (NullPointerException e) {

        }

        try {
            sc.close();
        } catch (NullPointerException e) {

        }

        FileInputStream inputstream = null;
        if (str.equalsIgnoreCase("true")) {
            String path = System.getProperty("user.dir");
            try {
                path += "/src/timeline/images/back-ground2.jpg";
                inputstream = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image im = new Image(inputstream);
            try {
                BG.setImage(im);
            } catch (NullPointerException e) {

            }
        }
    }

    // Methods for start page
    @FXML
    private void GoToLogin(ActionEvent event) throws IOException {
        System.out.println("Go To Login");
        Parent loginParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Timeline Login");
        window.setScene(loginScene);
        window.show();

    }

    @FXML
    private void GoToSignUp(ActionEvent event) throws IOException {
        System.out.println("Go To Sign Up");
        Parent loginParent = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Timeline Sign Up");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToHome(ActionEvent event) throws IOException {
        System.out.println("Go To Home");
        Parent loginParent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToAdminMain(ActionEvent event) throws IOException {
        System.out.println("Go To AdminMain");
        Parent loginParent = FXMLLoader.load(getClass().getResource("mainAdmin.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Admin Main");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToUserMain(ActionEvent event) throws IOException {
        System.out.println("Go To UserMain");
        Parent loginParent = FXMLLoader.load(getClass().getResource("mainUser.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("User Main");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToAdminPriv(ActionEvent event) throws IOException {
        System.out.println("Go To AdminPriv");
        Parent loginParent = FXMLLoader.load(getClass().getResource("AdminPriv.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Admin Privileges");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToCreateEvent(ActionEvent event) {
        if (startDateCreate.getValue() != null && endDateCreate.getValue() != null) {
            createTimeLineDesc create = new createTimeLineDesc(msg, keyWordCreate.getText(), startDateCreate.getValue(), endDateCreate.getValue(), descCreate.getText());
            if (create.check()) {
                NextToEvent.setOpacity(1);
                NextToEvent.setDisable(false);
                AddToTimeLine.setDisable(true);
            }
        } else {
            createTimeLineDesc create = new createTimeLineDesc(msg, keyWordCreate.getText(), startWordCreate.getText(), endWordCreate.getText(), descCreate.getText());
            if (create.check()) {
                NextToEvent.setOpacity(1);
                NextToEvent.setDisable(false);
                AddToTimeLine.setDisable(true);
            }
        }
    }

    @FXML
    protected void createTimeLineDes(ActionEvent event) throws IOException {
        System.out.println("Go To TimeLineDes");
        Parent loginParent = FXMLLoader.load(getClass().getResource("createTimeLineDesc.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("TimeLine Description");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToCreateTimeLine(ActionEvent event) throws IOException {
        System.out.println("Go To Create TimeLine");
        Parent loginParent = FXMLLoader.load(getClass().getResource("createTimeLine.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Create TimeLine");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void EventFX(ActionEvent event) throws IOException {
        System.out.println("Go To Create Event");
        Parent loginParent = FXMLLoader.load(getClass().getResource("createEvent.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Create Event");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void EventFXAdd(ActionEvent event) throws IOException {
        System.out.println("Go To Create Event");
        Parent loginParent = FXMLLoader.load(getClass().getResource("addEvent.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Add Event");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToViewTimeLines(ActionEvent event) throws IOException {
        System.out.println("Go To View TimeLines");
        Parent loginParent = FXMLLoader.load(getClass().getResource("viewTimelinesUser.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("View TimeLine");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    protected void GoToViewEvents(ActionEvent event) throws IOException {
        System.out.println("Go To View Events");
        Parent loginParent = FXMLLoader.load(getClass().getResource("viewEvents.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("View Events");
        window.setScene(loginScene);
        window.show();
    }

    private void loadData() {
        //This needs to be updated to load all the users in the ListView's
        AllList lists = new AllList();
        AllList ls = new AllList(timeold);
        int arrSize = lists.TimeLineList.size() - 1;
        totalTl.setText("TIMELINES: " + lists.numbOfTl);
        for (String admin : lists.AdminList)
            AdminList.getItems().add(admin);
        for (String user : lists.UserList)
            UserList.getItems().add(user);
        for (int i = arrSize; i >= 0; i--) {
            TimeLineList.getItems().add(lists.TimeLineList.get(i));
        }
        for (String events : ls.Events)
            EventList.getItems().add(events);
        list.addAll(totalTl);
        list.addAll(TimeLineList);
        list.addAll(UserList);
        list.addAll(AdminList);
        list.addAll(EventList);
    }

    private void loadEditTimeline() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            String select_name = "SELECT DISTINCT tl_name from log.tl where tl_name ='" + timeline + "'";
            ResultSet name = statement.executeQuery(select_name);
            while (name.next()) {
                String user = name.getString("tl_name");
                int count = 0;
                for (int i = 0; i < user.length(); i++) {
                    if (user.charAt(i) == '-') {
                        count = i;
                    }
                }
                StringBuilder creator = new StringBuilder();
                for (int i = 0; i < count - 1; i++) {
                    creator.append(user.charAt(i));
                }

                try {
                    TimeLineNameEdit.setText(creator.toString());
                } catch (NullPointerException e) {
                    System.out.println(e.toString());
                }
            }
            String select_key = "SELECT DISTINCT Keywords from log.tl where tl_name ='" + timeline + "'";
            ResultSet key = statement.executeQuery(select_key);
            while (key.next()) {
                try {
                    keyWordEdit.setText(key.getString("Keywords"));
                } catch (NullPointerException e) {

                }
            }
            String select_start = "SELECT DISTINCT dateStart from log.tl where tl_name ='" + timeline + "'";
            ResultSet start = statement.executeQuery(select_start);
            while (start.next()) {
                try {
                    startWordEdit.setText(start.getString("dateStart"));
                } catch (NullPointerException e) {

                }
            }
            String select_end = "SELECT DISTINCT dateEnd from log.tl where tl_name ='" + timeline + "'";
            ResultSet end = statement.executeQuery(select_end);
            while (end.next()) {
                try {
                    endWordEdit.setText(end.getString("dateEnd"));
                } catch (NullPointerException e) {

                }
            }
            String select_des = "SELECT DISTINCT description from log.tl where tl_name ='" + timeline + "'";
            ResultSet des = statement.executeQuery(select_des);
            while (des.next()) {
                try {
                    descEdit.setText(des.getString("description"));
                } catch (NullPointerException e) {

                }
            }

            String select_startInt = "SELECT DISTINCT dateIntStart from log.tl where tl_name ='" + timeline + "'";
            ResultSet startInt = statement.executeQuery(select_startInt);
            while (startInt.next()) {
                try {
                    startDateEdit.setValue(startInt.getDate("dateIntStart").toLocalDate());
                } catch (NullPointerException e) {

                }
            }
            String select_endtInt = "SELECT DISTINCT dateIntEnd from log.tl where tl_name ='" + timeline + "'";
            ResultSet endtInt = statement.executeQuery(select_endtInt);
            while (endtInt.next()) {
                try {
                    endDateEdit.setValue(endtInt.getDate("dateIntEnd").toLocalDate());
                } catch (NullPointerException e) {

                }
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }


    private void loadEditEvent() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            String select_name = "SELECT DISTINCT name from log.entity where name ='" + eventsEdit + "'";
            ResultSet name = statement.executeQuery(select_name);
            while (name.next()) {
                String user = name.getString("name");
                try {
                    EventNameEdit.setText(user);
                } catch (NullPointerException e) {
                    System.out.println(e.toString());
                }
            }
            String select_start = "SELECT DISTINCT startDate from log.entity where name ='" + eventsEdit + "'";
            ResultSet start = statement.executeQuery(select_start);
            while (start.next()) {
                try {
                    EventstartWordEdit.setText(start.getString("startDate"));
                } catch (NullPointerException e) {

                }
            }
            String select_end = "SELECT DISTINCT endDate from log.entity where name ='" + eventsEdit + "'";
            ResultSet end = statement.executeQuery(select_end);
            while (end.next()) {
                try {
                    EventendWordEdit.setText(end.getString("endDate"));
                } catch (NullPointerException e) {

                }
            }
            String select_des = "SELECT DISTINCT description from log.entity where name ='" + eventsEdit + "'";
            ResultSet des = statement.executeQuery(select_des);
            while (des.next()) {
                try {
                    EventdescEdit.setText(des.getString("description"));
                } catch (NullPointerException e) {

                }
            }
            String select_startInt = "SELECT DISTINCT dateIntStart from log.entity where name ='" + eventsEdit + "'";
            ResultSet startInt = statement.executeQuery(select_startInt);
            while (startInt.next()) {
                try {
                    EventstartDateEdit.setValue(startInt.getDate("dateIntStart").toLocalDate());
                } catch (NullPointerException e) {

                }
            }
            String select_endtInt = "SELECT DISTINCT dateIntEnd from log.entity where name ='" + eventsEdit + "'";
            ResultSet endtInt = statement.executeQuery(select_endtInt);
            while (endtInt.next()) {
                try {
                    EventendDateEdit.setValue(endtInt.getDate("dateIntEnd").toLocalDate());
                } catch (NullPointerException e) {
                }
            }

            String select_path = "SELECT DISTINCT path from log.entity where name ='" + eventsEdit + "'";
            ResultSet path = statement.executeQuery(select_path);
            while (path.next()) {
                try {
                    EventpathEdit.setText(path.getString("path"));
                } catch (NullPointerException e) {

                }
            }
            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void select(ActionEvent event) {
        SelectName select = new SelectName(msg, TimeLineNameCreate.getText(), event);
    }

    // Methods for sign up / login
    @FXML
    private void signUp(ActionEvent event) {
        System.out.println("Sending values username: \"" + name.getText() + "\" and password: \"" + pass.getText() + "\" to sign up");
        SignUp signUp = new SignUp(msg, name.getText(), pass.getText());
    }

    @FXML
    private void login(ActionEvent event) {
        System.out.println("Sending values username: \"" + name.getText() + "\" and password: \"" + pass.getText() + "\" to AdminLogin");
        Login login = new Login(msg, name.getText(), pass.getText(), event);
    }

    @FXML
    private void deleteEvent(ActionEvent event) throws IOException {
        deleteEvents ev = new deleteEvents(EventList.getSelectionModel().getSelectedItem());
        Parent loginParent = FXMLLoader.load(getClass().getResource("viewEvents.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }


    @FXML
    private void addAdmin(ActionEvent event) throws IOException {
        if (UserList.getSelectionModel().getSelectedItem() != null) {
            Add add = new Add(UserList.getSelectionModel().getSelectedItem());
            Parent loginParent = FXMLLoader.load(getClass().getResource("AdminPriv.fxml"));
            Scene loginScene = new Scene(loginParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
        }
    }

    private static String timeline = "not yet defined";

    @FXML
    private void removeAdmin(ActionEvent event) throws IOException {
        Remove remove = new Remove(AdminList.getSelectionModel().getSelectedItem(), UserList.getSelectionModel().getSelectedItem());
        Parent loginParent = FXMLLoader.load(getClass().getResource("AdminPriv.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }


    @FXML
    protected void ConfirmDeleteTimeline(ActionEvent event) throws IOException {
        if (TimeLineList.getSelectionModel().getSelectedItem() != null) {
            //Saving Timeline name
            timeline = TimeLineList.getSelectionModel().getSelectedItem();
            System.out.println("Go To Confirmation Dialog with timeline " + timeline);
            //Go to the confirmation page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmationDialog.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Sure you want to delete \"" + timeline + "\"?");
            window.show();
        }
    }

    @FXML
    protected void DeleteTimeline(ActionEvent event) throws IOException {
        System.out.println("Trying to delete " + timeline);
        //Trying to delete timeline
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    //It depend on your DB name, in my case the DB called log. However the password is also depend on you
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            Statement statement = conn.createStatement();
            String delete = "DELETE FROM log.tl WHERE tl_name='" + timeline + "'";
            statement.executeUpdate(delete);
            statement.close();
            conn.close();
            System.out.println("Deleted " + timeline);
        } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
            System.out.println("Something went wrong while deleting " + timeline);
            ex.printStackTrace();
        }
        //Going back to timelines
        System.out.println("Go To View Timelines");
        Parent loginParent = FXMLLoader.load(getClass().getResource("AdminPriv.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.setTitle("View Timelines");
        window.show();
    }

    @FXML
    private void Search(ActionEvent event) {
        Search search = new Search(SearchField.getText(), msg);
        SearchList.getItems().clear();
        for (String searchs : search.tlNames)
            SearchList.getItems().add(searchs);
    }

    private static String timeold;

    @FXML
    private void EditPage(ActionEvent event) throws IOException {
        if (TimeLineList.getSelectionModel().getSelectedItem() != null) {
            timeline = TimeLineList.getSelectionModel().getSelectedItem();
            timeold = timeline;
            System.out.println("Go To Edit Timelines");
            Parent editParent = FXMLLoader.load(getClass().getResource("editTimeline.fxml"));
            Scene loginScene = new Scene(editParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.setTitle("Editing " + "\"" + timeline + "\"");
            window.show();
        } else if (timeold != null) {
            System.out.println("Go To Edit Timelines");
            Parent editParent = FXMLLoader.load(getClass().getResource("editTimeline.fxml"));
            Scene loginScene = new Scene(editParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.setTitle("Editing " + "\"" + timeline + "\"");
            window.show();
        }
    }

    private static String eventsEdit = "not yet defined";
    private static String Eventold;


    @FXML
    private void EditEvent(ActionEvent event) throws IOException {
        if (EventList.getSelectionModel().getSelectedItem() != null) {
            eventsEdit = EventList.getSelectionModel().getSelectedItem();
            Eventold = eventsEdit;
            System.out.println("Go To Edit Event");
            Parent editParent = FXMLLoader.load(getClass().getResource("editEvent.fxml"));
            Scene loginScene = new Scene(editParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.setTitle("Editing " + "\"" + eventsEdit + "\"");
            window.show();
        }
    }

    @FXML
    private void update(ActionEvent event) {
        UpdateTimeline update = new UpdateTimeline(msg, TimeLineNameEdit.getText(), keyWordEdit.getText(), descEdit.getText(), startWordEdit.getText(), endWordEdit.getText(), startDateEdit.getValue().toString(), endDateEdit.getValue().toString(), timeold);
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        UpdateEvent events = new UpdateEvent(msg, EventNameEdit.getText(), EventdescEdit.getText(), EventstartWordEdit.getText(), EventendWordEdit.getText(), EventstartDateEdit.getValue().toString(), EventendDateEdit.getValue().toString(), EventpathEdit.getText(), Eventold);
    }

    private static double size;
    private static int widthOfImage;
    private static int heightOfImage;

    @FXML
    private void selectImage(ActionEvent event) throws IOException {
        Main ctc = new Main();
        FileChooser file = new FileChooser();
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPGE", "*.jpge"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File select = file.showOpenDialog(ctc.classStage);
        double sizeByte = select.length();
        double sizeKB = sizeByte / 1024;
        DecimalFormat df = new DecimalFormat("#.#");
        size = Double.parseDouble(df.format(sizeKB));
        BufferedImage bimg = ImageIO.read(new File(String.valueOf(select)));
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        widthOfImage = width;
        heightOfImage = height;
        Image image = new Image(select.toURI().toString());
        if (select != null) {
            path.setText(select.getAbsolutePath());
        }
    }


    @FXML
    private void selectImageEdit(ActionEvent event) throws IOException {
        Main ctc = new Main();
        FileChooser file = new FileChooser();
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPGE", "*.jpge"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File select = file.showOpenDialog(ctc.classStage);
        double sizeByte = select.length();
        double sizeKB = sizeByte / 1024;
        DecimalFormat df = new DecimalFormat("#.#");
        size = Double.parseDouble(df.format(sizeKB));
        BufferedImage bimg = ImageIO.read(new File(String.valueOf(select)));
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        widthOfImage = width;
        heightOfImage = height;
        Image image = new Image(select.toURI().toString());
        if (select != null) {
            EventpathEdit.setText(select.getAbsolutePath());
        }
    }

    @FXML
    private void createTimeLineEvent(ActionEvent event) {
        if (startDateCreate.getValue() != null && endDateCreate.getValue() != null) {
            createTimeLineEvent create = new createTimeLineEvent(msg, eventName.getText(), descCreateEvent.getText(), startDateCreate.getValue(), endDateCreate.getValue(), path.getText(), size, widthOfImage, heightOfImage);
            if (create.check()) {
                finish.setOpacity(1);
                finish.setDisable(false);
                AddEventToTimeLine.setDisable(true);
            }
        } else {
            createTimeLineEvent create = new createTimeLineEvent(msg, eventName.getText(), descCreateEvent.getText(), startWordCreate.getText(), endWordCreate.getText(), path.getText(), size, widthOfImage, heightOfImage);
            if (create.check()) {
                finish.setOpacity(1);
                finish.setDisable(false);
                AddEventToTimeLine.setDisable(true);
            }
        }
    }

    @FXML
    private void addTimeLineEvent(ActionEvent event) {
        if (startDateCreate.getValue() != null && endDateCreate.getValue() != null) {
            addTimeLineEvent create = new addTimeLineEvent(msg, eventName.getText(), descCreateEvent.getText(), startDateCreate.getValue(), endDateCreate.getValue(), path.getText(), size, widthOfImage, heightOfImage, timeline);
            if (create.check()) {
                finish.setOpacity(1);
                finish.setDisable(false);
                AddEventToTimeLine.setDisable(true);
            }
        } else {
            addTimeLineEvent create = new addTimeLineEvent(msg, eventName.getText(), descCreateEvent.getText(), startWordCreate.getText(), endWordCreate.getText(), path.getText(), size, widthOfImage, heightOfImage, timeline);
            if (create.check()) {
                finish.setOpacity(1);
                finish.setDisable(false);
                AddEventToTimeLine.setDisable(true);
            }
        }
    }

    @FXML
    private void ShowDate(ActionEvent event) {
        //Showing text
        startTxt.setOpacity(1);
        endTxt.setOpacity(1);

        // Disabling abstract
        startWordCreate.setDisable(true);
        startWordCreate.setOpacity(0);
        startWordCreate.setText("");

        endWordCreate.setDisable(true);
        endWordCreate.setOpacity(0);
        endWordCreate.setText("");

        // Showing Date
        startDateCreate.setDisable(false);
        startDateCreate.setOpacity(0.7);

        endDateCreate.setDisable(false);
        endDateCreate.setOpacity(0.7);
    }

    @FXML
    private void ShowAbstract(ActionEvent event) {
        //showing text
        startTxt.setOpacity(1);
        endTxt.setOpacity(1);

        // Disabling Date
        startDateCreate.setDisable(true);
        startDateCreate.setOpacity(0);
        startDateCreate.setValue(null);

        endDateCreate.setDisable(true);
        endDateCreate.setOpacity(0);
        endDateCreate.setValue(null);

        // Showing abstract
        startWordCreate.setDisable(false);
        startWordCreate.setOpacity(0.7);

        endWordCreate.setDisable(false);
        endWordCreate.setOpacity(0.7);
    }

    //fx:id="selectedtimelineimage"
    @FXML
    protected Pane root = new Pane();
    @FXML
    protected Label SelectedTimelineName;
    @FXML
    protected Line selectedTimelineHorizontalLine;
    @FXML
    protected Line selectedTimelineHorizontalLine2;
    @FXML
    protected TextArea selectedtimelinedesc;
    /*@FXML
    protected ImageView selectedtimelineimage;*/
    @FXML
    protected Label selectedtimelinestart;
    @FXML
    protected Label selectedtimelineend;
    @FXML
    protected Button AddEvent;
    @FXML
    protected ScrollPane space;
    @FXML
    protected ImageView LightSaberHandle;

    private Rectangle[] rec = new Rectangle[1000];
    private int createcount = 0;
    private ArrayList<String> data;
    private int events = 0;
    private String imageurl = "";


    public void addEventButtonPushed(ActionEvent actionEvent) throws MalformedURLException, FileNotFoundException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("starwars.txt"));
        } catch (FileNotFoundException e) {

        }
        String StarWarsString = "";
        try {
            while (sc.hasNextLine()) {
                StarWarsString = sc.nextLine();
            }
        } catch (NullPointerException e) {

        }

        if (StarWarsString.contains("true")) {
            LightSaberHandle.setOpacity(1);
        } else {
            LightSaberHandle.setOpacity(0);
        }

        String timelinename;

        if (SearchList.getSelectionModel().getSelectedItem() == null) {
            timelinename = TimeLineList.getSelectionModel().getSelectedItem();

        } else {
            timelinename = SearchList.getSelectionModel().getSelectedItem();
        }
        int timelineid = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected");
            String query = "select id from log.tl where tl_name ='" + timelinename + "'";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                timelineid = rs.getInt("id");
            }
            pst.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }

        this.SelectedTimelineID = timelineid;

        String user = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/log?characterEncoding=utf8&useSSL=false&useUnicode=true", "root", "AaAcFgJmQsYz12.");
            System.out.println("Connected");
            Statement stat = conn.createStatement();
            String getUsername = "select username from log.session where id = (select max(id) from session)";
            ResultSet rs = stat.executeQuery(getUsername);
            while (rs.next()) {
                user = rs.getString(1);
            }
            String timeline_ID = "";

            String askID = "select rating from log.user where username ='" + user + "'";
            ResultSet test = stat.executeQuery(askID);
            while (test.next()) {
                timeline_ID = test.getString("rating");
            }

            String query = "Select * from log.user Where rating LIKE '%" + this.SelectedTimelineID + "%' and username ='" + user + "'";
            ResultSet test1 = stat.executeQuery(query);
            if (!test1.next()) {
                SubmitRatingButton.setDisable(false);
                SubmitRatingButton.setOpacity(0.7);
                ratingValue.setDisable(false);
                ratingValue.setOpacity(1);
                RateTimelineLabel.setOpacity(1);
            }
            double rate = 0;

            String getTheRate = "select rate from log.tl where id =" + this.SelectedTimelineID + "";
            ResultSet test2 = stat.executeQuery(getTheRate);
            while (test2.next()) {
                rate = test2.getInt("rate");
            }

            ArrayList<Object> arr = new ArrayList<Object>();
            String selectId = "Select * from log.user Where rating LIKE '%" + this.SelectedTimelineID + "%'";
            ResultSet test3 = stat.executeQuery(selectId);
            while (test3.next()) {
                arr.add(test3.getString("rating"));
            }
            double sz = arr.size();
            double avrage = rate / sz;
            String avv = String.format("%.1f", avrage);
            msg.setText(avv + " / 5");
            arr.clear();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
        }


        RetrieveData fe = new RetrieveData(timelineid);
        data = fe.getData();


        //first event
        for (int k = 7; k < data.size(); k++) {
            if (k % 7 == 0) {
                events++;
                draw(k, events);
            }
        }

        AddEvent.setOpacity(0.3);
        AddEvent.setDisable(true);

    }

    private static String StarWarsString = "";

    private void draw(int k, int events) throws MalformedURLException, FileNotFoundException {

        SelectedTimelineName.setText(data.get(0));
        SelectedTimelineName.setLayoutX(50);
        SelectedTimelineName.setLayoutY(400);

        selectedtimelinedesc.setText(data.get(1));

        root.setPrefWidth(((data.size() + 1) / 7) * 200 + 585);
        space.setPrefWidth(root.getPrefWidth());
        space.setMaxWidth(1189);
        space.setOpacity(0.8);
        SelectedTimelineName.setOpacity(1);
        SelectedTimelineName.setLayoutX(365);

        Scanner sc = null;
        try {
            sc = new Scanner(new File("starwars.txt"));
        } catch (FileNotFoundException e) {

        }
        StarWarsString = "";
        try {
            while (sc.hasNextLine()) {
                StarWarsString = sc.nextLine();
            }
        } catch (NullPointerException e) {

        }

        if (!StarWarsString.contains("true")) {
            selectedTimelineHorizontalLine.setEndX(((data.size() + 1) / 7) * 200 + 585);
            selectedTimelineHorizontalLine.setStartY(170);
            selectedTimelineHorizontalLine.setEndY(170);
            selectedTimelineHorizontalLine.setOpacity(1);

            if (data.get(2).equalsIgnoreCase(" ") && data.get(3).equalsIgnoreCase(" ")) {
                selectedtimelinestart.setText(data.get(4));
                selectedtimelinestart.setOpacity(1);
                selectedtimelinestart.setLayoutY(170);

                selectedtimelineend.setText(data.get(5));
                selectedtimelineend.setOpacity(1);
                selectedtimelineend.setLayoutX((((data.size() + 1) / 7) * 200 + 563) - selectedtimelineend.getText().length() * 5.5);
                selectedtimelineend.setLayoutY(170);
            } else {
                selectedtimelinestart.setText(data.get(2));
                selectedtimelinestart.setOpacity(1);
                selectedtimelinestart.setLayoutY(170);


                selectedtimelineend.setText(data.get(3));
                selectedtimelineend.setOpacity(1);
                selectedtimelineend.setLayoutX((((data.size() + 1) / 7) * 200 + 550) - selectedtimelineend.getText().length() * 5.5);
                selectedtimelineend.setLayoutY(170);
            }
        } else {

            selectedTimelineHorizontalLine.setEndX(((data.size() + 1) / 7) * 200 + 500);
            selectedTimelineHorizontalLine.setStartY(170);
            selectedTimelineHorizontalLine.setStartX(180);
            selectedTimelineHorizontalLine.setEndY(170);
            selectedTimelineHorizontalLine.setOpacity(1);
            selectedTimelineHorizontalLine.setStrokeWidth(50);
            Color c = new Color(1, 0, 1, 1);
            BoxBlur bb = new BoxBlur();
            bb.setWidth(50);
            bb.setHeight(50);
            bb.setIterations(2);
            selectedTimelineHorizontalLine.setStroke(c);
            selectedTimelineHorizontalLine.setEffect(bb);

            BoxBlur bb2 = new BoxBlur();
            bb2.setWidth(70);
            bb2.setHeight(20);
            bb2.setIterations(2);
            selectedTimelineHorizontalLine2.setEndX(((data.size() + 1) / 7) * 200 + 470);
            selectedTimelineHorizontalLine2.setStartX(150);
            selectedTimelineHorizontalLine2.setOpacity(1);
            selectedTimelineHorizontalLine2.setStrokeWidth(10);
            selectedTimelineHorizontalLine2.setEffect(bb2);
            selectedTimelineHorizontalLine2.setStartY(175);
            selectedTimelineHorizontalLine2.setEndY(175);
            selectedTimelineHorizontalLine2.setStroke(c);


            if (data.get(2).equalsIgnoreCase(" ") && data.get(3).equalsIgnoreCase(" ")) {
                selectedtimelinestart.setText(data.get(4));
                selectedtimelinestart.setOpacity(1);
                selectedtimelinestart.setLayoutY(100);

                selectedtimelineend.setText(data.get(5));
                selectedtimelineend.setOpacity(1);
                selectedtimelineend.setLayoutX((((data.size() + 1) / 7) * 200 + 563) - selectedtimelineend.getText().length() * 5.5);
                selectedtimelineend.setLayoutY(100);
            } else {
                selectedtimelinestart.setText(data.get(2));
                selectedtimelinestart.setOpacity(1);
                selectedtimelinestart.setLayoutY(100);

                selectedtimelineend.setText(data.get(3));
                selectedtimelineend.setOpacity(1);
                selectedtimelineend.setLayoutX((((data.size() + 1) / 7) * 200 + 540) - selectedtimelineend.getText().length() * 5.5);
                selectedtimelineend.setLayoutY(100);
            }
        }


        Tooltip tt = new Tooltip();
        tt.setFont(new Font("Arial BLACK", 24));
        tt.getStyleClass().add("ttip");

        int eventWidthRadius = 100;

        final StackPane pane = new StackPane();
        pane.setId("Event" + events);
        Tooltip.install(pane, tt);
        if (data.get(k + 6).equalsIgnoreCase("") || data.get(k + 6).isEmpty() || data.get(k + 6) == null) {
            rec[createcount] = new Rectangle(50, 50, GREY);
            rec[createcount].setId("" + events);
            rec[createcount].setArcWidth(30.0);
            rec[createcount].setArcHeight(30.0);
            pane.setLayoutY(170 - 24);
            pane.setLayoutX((events) * 200 + 250);
            pane.getChildren().add(rec[createcount]);
            createcount++;
            imageurl = data.get(k + 6);
        } else {
            //If event have image
            rec[createcount] = new Rectangle(eventWidthRadius, eventWidthRadius, BLACK);
            rec[createcount].setId("" + events);
            rec[createcount].setArcWidth(30.0);
            rec[createcount].setArcHeight(30.0);
            FileInputStream inputstream = new FileInputStream(data.get(k + 6));
            Image im = new Image(inputstream);
            rec[createcount].setFill(new ImagePattern(im));
            pane.setLayoutY(170 - 50);
            pane.setLayoutX(((events) * 200 + 250));
            pane.getChildren().add(rec[createcount]);
            //root.getChildren().add(iw);
            imageurl = data.get(k + 6);
        }

        pane.setOnMouseMoved(e -> {
            ScrollPane scroller = new ScrollPane();
            tt.setStyle("-fx-font-size: 13");
            tt.maxWidthProperty().setValue(200);
            scroller.setTooltip(tt);
            tt.setWrapText(true);
            tt.setText("Name: " + data.get(k));

        });

        pane.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setPrefWidth(540);
        alert.setHeaderText(data.get(k) + " Details");
            if (!data.get(k + 2).equals(" ") && !data.get(k + 3).equals(" ")) {
                alert.setTitle(data.get(k + 2) + " - " + data.get(k + 3));

            } else {
                alert.setTitle(data.get(k + 4) + " - " + data.get(k + 5));

            }


            Text text = new Text();
            text.setWrappingWidth(500);
            text.setText("DESCRIPTION: \n \n" + data.get(k + 1));

            alert.getDialogPane().setContent(text);
            alert.show();

            String idS = e.getSource().toString();
            String str = idS.replaceAll("\\D+", "");
            int x = Integer.parseInt(str);

            if (!data.get((x * 7) + 6).isBlank() && alert.isShowing()) {
                imageurl = data.get(k + 6);
                FileInputStream inputstream = null;
                try {
                    inputstream = new FileInputStream(imageurl);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                Image bg = new Image(inputstream);
                BG.setImage(bg);
            } else if (alert.isShowing() && data.get((x * 7) + 6).isBlank()) {
                if (StarWarsString.contains("true")) {
                    imageurl = "src/timeline/images/back-ground2.jpg";
                } else {
                    imageurl = "src/timeline/images/back-ground.jpg";
                }
                FileInputStream inputstream = null;
                try {
                    inputstream = new FileInputStream(imageurl);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                Image bg = new Image(inputstream);
                BG.setImage(bg);
            }
        });

        pane.setOnMouseExited(e -> {
            tt.hide();
        });

        root.getChildren().addAll(pane);
        // NAME


    }


    @FXML
    protected Slider ratingValue;

    @FXML
    protected Button SubmitRatingButton;

    @FXML
    protected Label RateTimelineLabel;

    private int SelectedTimelineID = 0;

    private Boolean StarWars = false;

    @FXML
    protected ImageView BG;


    public void SubmitRating(ActionEvent event) {
        int rate = (int) ratingValue.getValue();
        TimelineRate tr = new TimelineRate(this.SelectedTimelineID, rate);
        RateTimelineLabel.setText("Rating Sent!");
        SubmitRatingButton.setDisable(true);
        SubmitRatingButton.setOpacity(0.3);
        ratingValue.setDisable(true);
        ratingValue.setOpacity(0.3);
    }

    public void StarWars(ActionEvent event) throws IOException, URISyntaxException {

        Scanner sc = null;
        try {
            sc = new Scanner(new File("starwars.txt"));
        } catch (FileNotFoundException e) {

        }
        String str = "";
        try {
            while (sc.hasNextLine()) {
                str = sc.nextLine();
            }
        } catch (NullPointerException e) {

        }

        try {
            sc.close();
        } catch (NullPointerException e) {

        }

        FileWriter fw = null;
        try {
            fw = new FileWriter("starwars.txt");
        } catch (FileNotFoundException e2) {

        }

        try {
            if (str.contains("true")) {
                fw.write("false");
                System.out.println("Enable Regular Theme");

            } else {
                fw.write("true");
                System.out.println("Enable StarWars Theme");

            }
        } catch (NullPointerException e) {

        }

        try {
            fw.flush();
            fw.close();
        } catch (NullPointerException e) {

        }

        GoToHome(event);
    }
}
