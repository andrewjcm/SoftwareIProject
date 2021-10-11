package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// JavaDocs can be found in javadoc folder.
/**
 * Main class of the program.
 * <p><br>
 * <b>FUTURE ENHANCEMENTS</b>
 * Add Part & Add Product views:
 * Catch the exception at each input field and highlighting the
 * field with an asterisk and individual error message.
 * </p>
 * @author Andrew Cesar-Metzgus
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        primaryStage.setTitle("IMS");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    /**
     * Main method that drives program.
     * @param args None.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
