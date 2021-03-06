package labs.lab05;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab05Application extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("lab5Scene.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Lab05");

        stage.show();
    }
}
