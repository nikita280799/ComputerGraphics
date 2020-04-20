package labs.lab13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab13Application extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lab13Scene.fxml"));
        Parent root = fxmlLoader.load();
        Lab13SceneController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Lab13");

        stage.show();
    }
}
