import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ClientGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        setupSQL sqlSetup = new setupSQL();
        sqlSetup.main();
        Parent root = FXMLLoader.load((getClass().getResource("/FXML/mainScreen.fxml")));
        primaryStage.setTitle("Main Screen");
        primaryStage.setResizable(false);
        Scene s1 = new Scene(root, 800,600);
        primaryStage.setScene(s1);
        primaryStage.show();
    }
}