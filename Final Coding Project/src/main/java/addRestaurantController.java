import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class addRestaurantController implements Initializable {
    @FXML
    TextField Name;
    @FXML
    TextField Seats;
    @FXML
    TextField resAddress;
    @FXML
    Button Submit;
    @FXML
    AnchorPane root1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Submit.setOnAction(actionEvent -> {
            if (Name.getText().isEmpty()) {
                Name.setText("Please Type a Name");
                return;
            }
            if (Seats.getText().isEmpty()) {
                Seats.setText("Please Input Correct Seats (1-999)");
                return;
            }

            try {

                int intValue = Integer.parseInt(Seats.getText());
                if (intValue < 1) {
                    Seats.setText("Please Input Correct Seats (1-999)");
                    return;
                }
            } catch (NumberFormatException e) {
                Seats.setText("Please Input Correct Seats (1-999)");
                return;
            }
            setupSQL sqlCommand = new setupSQL();
            try {
                sqlCommand.main();
                sqlCommand.insert(Name.getText(),Integer.parseInt(Seats.getText()),resAddress.getText());
                sqlCommand.readTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) root1.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/mainScreen.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Main Screen");
            Scene s1 = new Scene(root, 800,600);
            stage.setScene(s1);
            stage.show();
        });
    }
    public void back() {
        try {
            Stage stage = (Stage) root1.getScene().getWindow();
            Parent R = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/mainScreen.fxml")));
            stage.setTitle("Main Screen");
            Scene s1 = new Scene(R, 800,600);
            stage.setScene(s1);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void returnToPrev(ActionEvent actionEvent) {
        back();
    }
}