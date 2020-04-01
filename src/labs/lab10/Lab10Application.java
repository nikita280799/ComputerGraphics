package labs.lab10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab10Application extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("lab10Scene.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Lab09");


        stage.show();
    }
}
