import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class aboutScreenController implements Initializable {
    @FXML
    Button Back;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Back.setOnAction(actionEvent -> {
            Stage stage = (Stage) Back.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/MainScreen.fxml")));
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
}
