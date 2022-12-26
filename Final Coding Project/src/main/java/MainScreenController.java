import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MainScreenController implements Initializable {
    @FXML
    VBox restaurantVBox;
    @FXML
    AnchorPane root;
    @FXML
    MenuItem addRestaurants;
    @FXML
    MenuItem modifyRestaurants;
    @FXML
    MenuItem userRestaurants;
    @FXML
    MenuItem ownerRestaurants;
    @FXML
    MenuItem aboutRestaurants;
    @FXML
    TextField searchBar;
    @FXML
    Button Search;
    reservationController resController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                HashMap<String, ArrayList<Object>> resData = null;
                try{
                    setupSQL sqlCommand = new setupSQL();
                    resData = sqlCommand.readTable();
                    System.out.println(resData);
                }catch (SQLException e){}

                for (Map.Entry<String,ArrayList<Object>> entry : resData.entrySet()){
                    Label resName = new Label(entry.getKey());
                    ArrayList<Object> tmpArr = entry.getValue();
                    int restSeat = (int) tmpArr.get(0);
                    int availSeats = (int) tmpArr.get(1);
                    String addy = (String) tmpArr.get(2);
                    Label availableSeats = new Label("Seats Available: " + restSeat);
                    Label address = new Label(addy);
                    address.setStyle("-fx-text-fill: WHITE; -fx-font-size: 14; -fx-alignment: Center;");
                    availableSeats.setStyle("-fx-text-fill: WHITE; -fx-font-size: 20; -fx-alignment: Center;");
                    resName.setStyle("-fx-text-fill: WHITE; -fx-font-size: 22;");

                    VBox resInfo = new VBox(address,availableSeats);
                    resInfo.setAlignment(Pos.CENTER);

                    Button selectBtn = new Button("Select");
                    selectBtn.setStyle("-fx-background-color:#00A8A8;-fx-font-size:25;-fx-text-fill: WHITE;");
                    if (restSeat == 0){
                        selectBtn.setDisable(true);
                    }
                    String totalSeats = Integer.toString(restSeat);
                    selectBtn.setOnAction(actionEvent -> {
                        try {
                            Stage stage = (Stage) selectBtn.getScene().getWindow();
                            //  Load SceneBuilder FXML File
                            FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/FXML/reservationScreen.fxml"));
                            Parent mainMenuPane = mainMenu.load();
                            resController = mainMenu.getController();
                            resController.setResInfo(entry.getKey(),restSeat,availSeats,addy);
                            Scene resScene = new Scene(mainMenuPane,800,600);
                            stage.setTitle("Restaurant Menu");
                            stage.setScene(resScene);
                            stage.setResizable(false);
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    selectBtn.setMinSize(245,85);
                    selectBtn.setAlignment(Pos.CENTER);
                    Region region1 = new Region();
                    HBox.setHgrow(region1, Priority.ALWAYS);
                    Region region2 = new Region();
                    HBox.setHgrow(region2, Priority.ALWAYS);
                    HBox resRow = new HBox(resName,region1,resInfo,region2,selectBtn);
                    resRow.setAlignment(Pos.CENTER);
                    resRow.setMinSize(725,100);
                    resRow.setMaxSize(725,100);
                    resRow.setSpacing(5);
                    resRow.setStyle("-fx-border-color:black; -fx-border-width: 2;");
                    restaurantVBox.getChildren().add(resRow);
                }
                addRestaurants.setOnAction(actionEvent -> {
                    Stage stage = (Stage) root.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/addRestaurant.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("Add Restaurant");
                    Scene s1 = new Scene(root, 800,600);
                    stage.setScene(s1);
                    stage.show();
                });
                modifyRestaurants.setOnAction(actionEvent -> {
                    Stage stage = (Stage) root.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/modifyRestaurant.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("Modify Restaurants");
                    assert root != null;
                    Scene s1 = new Scene(root, 800,600);
                    stage.setScene(s1);
                    stage.show();
                });
                userRestaurants.setOnAction(actionEvent -> {
                    addRestaurants.setDisable(true);
                    modifyRestaurants.setDisable(true);
                });
                ownerRestaurants.setOnAction(actionEvent -> {
                    addRestaurants.setDisable(false);
                    modifyRestaurants.setDisable(false);
                });
                aboutRestaurants.setOnAction(actionEvent -> {
                    Stage stage = (Stage) root.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/aboutScreen.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("About");
                    assert root != null;
                    Scene s1 = new Scene(root, 800,600);
                    stage.setScene(s1);
                    stage.show();
                });

    }

    public void searchRes(ActionEvent actionEvent) throws SQLException {
        String resSearched  = searchBar.getText();
        setupSQL newCommand = new setupSQL();
        HashMap<String,ArrayList<Object>> tmp = newCommand.getRes(resSearched);
        if (tmp.isEmpty()){
            return;
        }
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            //  Load SceneBuilder FXML File
            FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/FXML/reservationScreen.fxml"));
            Parent mainMenuPane = mainMenu.load();
            resController = mainMenu.getController();
            for (Map.Entry<String,ArrayList<Object>> entry : tmp.entrySet()) {
                ArrayList<Object> tmpArr = entry.getValue();
                int restSeat = (int) tmpArr.get(0);
                int availSeats = (int) tmpArr.get(1);
                String addy = (String) tmpArr.get(2);
                resController.setResInfo(entry.getKey(),restSeat,availSeats,addy);

            }
            Scene resScene = new Scene(mainMenuPane,800,600);
            stage.setTitle("Main Menu");
            stage.setScene(resScene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
