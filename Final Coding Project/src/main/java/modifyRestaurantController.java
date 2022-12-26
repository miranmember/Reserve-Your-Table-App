import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class modifyRestaurantController implements Initializable {
    @FXML
    Button modifyName;
    @FXML
    Button modifySeats;
    @FXML
    Text Text1;
    @FXML
    Text Text2;
    @FXML
    TextField UserInput1;
    @FXML
    TextField UserInput2;
    @FXML
    Button modifyDone;
    @FXML
    Button modifyCancel;
    @FXML
    TextField searchField;
    @FXML
    TextField totalSeatField;
    @FXML
    TextField availableSeatField;
    @FXML
    TextField addressField;
    @FXML
    Text errorMsg;



    int check = -1; // 0 = Name Change, 1 = Seats Change, -1 = Not Selected
    public void back(){
        Stage stage = (Stage) Text1.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/mainScreen.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Main Screen");
        assert root != null;
        Scene s1 = new Scene(root, 800,600);
        stage.setScene(s1);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyCancel.setOnAction(actionEvent -> {
            back();
        });
    }

    public void sendQuery(ActionEvent actionEvent) throws SQLException {
        if (!searchField.getText().isEmpty()){
            setupSQL query = new setupSQL();
            HashMap<String, ArrayList<Object>> tmp = query.getRes(searchField.getText());
            if (!tmp.isEmpty()){
                searchField.setDisable(true);
                totalSeatField.setDisable(false);
                availableSeatField.setDisable(false);
                addressField.setDisable(false);
                modifyDone.setDisable(false);
                errorMsg.setVisible(false);

                for (Map.Entry<String,ArrayList<Object>> entry : tmp.entrySet()) {
                    ArrayList<Object> tmpArr = entry.getValue();
                    int restSeat = (int) tmpArr.get(0);
                    int availSeats = (int) tmpArr.get(1);
                    String addy = (String) tmpArr.get(2);
                    totalSeatField.setText(String.valueOf(restSeat));
                    availableSeatField.setText(String.valueOf(availSeats));
                    addressField.setText(addy);
                }
                }else{
                errorMsg.setVisible(true);
            }
        }else{
            errorMsg.setVisible(true);
        }
    }

    public void submitUpdate(ActionEvent actionEvent) throws SQLException {
        setupSQL query = new setupSQL();
        if (Integer.parseInt(availableSeatField.getText()) <= Integer.parseInt(totalSeatField.getText())) {
            query.updateAll(searchField.getText(), Integer.parseInt(availableSeatField.getText()), Integer.parseInt(totalSeatField.getText()), addressField.getText());
            back();
        }else{
            errorMsg.setText("Error: Available Seats Exceeds Total Seats!");
            errorMsg.setVisible(true);
        }
    }
}
