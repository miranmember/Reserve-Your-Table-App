import com.dlsc.gmapsfx.javascript.object.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import com.sothawo.mapjfx.*;
import javafx.scene.web.WebView;
public class reservationController implements Initializable {
    @FXML
    AnchorPane root2;
    @FXML
    Text available;
    @FXML
    Text total;
    @FXML
    Text reservationName;
    @FXML
    Button makeReservation;
    @FXML
    Button Done;
    @FXML
    Button Cancel;
    @FXML
    TextField numberOfReservation;
    @FXML
    WebView mapView;
    @FXML
    VBox mapBox;
    @FXML
    Text resAddy;
    String resName;
    String resAddress;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cancel.setOnAction(actionEvent -> {
            back();
        });
        Done.setDisable(true);
    }
    public void setResInfo(String resName, int seats,int availSeats,String resAddress){
        reservationName.setText(resName);
        this.resName = resName;
        available.setText(String.valueOf(availSeats));
        total.setText(String.valueOf(seats));
        resAddy.setText(resAddress);
        this.resAddress = resAddress;
        mapView.getEngine().load("https://www.openstreetmap.org/search?query="+resAddress);

    }
    public void back() {
        try {
            Stage stage = (Stage) root2.getScene().getWindow();
            Parent R = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/mainScreen.fxml")));
            stage.setTitle("Main Screen");
            Scene s1 = new Scene(R, 800,600);
            stage.setScene(s1);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSeating(ActionEvent actionEvent) throws SQLException {
        Done.setDisable(false);
        if (Integer.parseInt(numberOfReservation.getText()) > Integer.parseInt(total.getText())) {
            numberOfReservation.setText("Exceeded Total Seats");
            return;
        }
        makeReservation.setDisable(true);
        int currTotal = Integer.parseInt(total.getText());
        int toSubtract = Integer.parseInt(numberOfReservation.getText());
        available.setText(String.valueOf(currTotal-toSubtract));
    }

    public void sendUpdate(ActionEvent actionEvent) throws SQLException {
        setupSQL query = new setupSQL();
        query.update(resName,Integer.parseInt(available.getText()));
        back();

    }
}
