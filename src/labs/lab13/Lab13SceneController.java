package labs.lab13;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


public class Lab13SceneController {

    @FXML
    private Button loadBut, changeHistBut;

    @FXML
    private ImageView imView;

    @FXML
    private BarChart<?, ?> redchart, bluechart, greenchart, genchart;

    @FXML
    private CategoryAxis redCatAxis, blueCatAxis, greenCatAxis, genCatAxis;

    private double[] redArr = new double[256], greenArr = new double[256], blueArr = new double[256], genArr = new double[256];

    private Stage primaryStage;

    public void initialize() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\Studying\\Computer graphics\\labs\\images"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg"));
        loadBut.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(loadBut.getScene().getWindow());
            if (file != null) {
                try {
                    imView.setImage(new Image(file.toURI().toURL().toString()));
                    prepareArrays(imView.getImage());
                    setCharts();
                } catch (MalformedURLException ex) {
                    System.out.println("File chooser error");
                }
            }
        });
        changeHistBut.setOnAction(event -> changeScene());
    }

    private void setCharts() {
        setOneChart(redchart, redCatAxis, redArr);
        setOneChart(greenchart, greenCatAxis, greenArr);
        setOneChart(bluechart, blueCatAxis, blueArr);
        setOneChart(genchart, genCatAxis, genArr);
    }

    private void setOneChart(BarChart chart, CategoryAxis catAxis, double[] arr) {
        ObservableList<String> obList = FXCollections.<String>observableArrayList();
        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < 256; i++) {
            obList.add(String.valueOf(i));
            series1.getData().add(new XYChart.Data(String.valueOf(i), arr[i]));
        }
        catAxis.setCategories(obList);
        chart.setData(FXCollections.observableArrayList(series1));
    }

    private void prepareArrays(Image image) {
        PixelReader pr = image.getPixelReader();
        double w = image.getWidth();
        double h = image.getHeight();
        double redMax = 0, blueMax = 0, greenMax = 0, genMax = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int redCurBright = (int) (pr.getColor(x, y).getRed() * 255);
                int greenCurBright = (int) (pr.getColor(x, y).getGreen() * 255);
                int blueCurBright = (int) (pr.getColor(x, y).getBlue() * 255);
                int genCurBright = (redCurBright + greenCurBright + blueCurBright) / 3;
                redArr[redCurBright]++;
                greenArr[greenCurBright]++;
                blueArr[blueCurBright]++;
                genArr[genCurBright]++;
                if (redArr[redCurBright] > redMax) redMax = redArr[redCurBright];
                if (greenArr[greenCurBright] > greenMax) greenMax = greenArr[greenCurBright];
                if (blueArr[blueCurBright] > blueMax) blueMax = blueArr[blueCurBright];
                if (genArr[genCurBright] > genMax) genMax = genArr[genCurBright];
            }
        }
        for (int i = 0; i < 256; i++) {
            redArr[i] = redArr[i] / redMax;
            greenArr[i] = greenArr[i] / greenMax;
            blueArr[i] = blueArr[i] / blueMax;
            genArr[i] = genArr[i] / genMax;
        }
    }

    void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }


    private void changeScene() {
        primaryStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondScene.fxml"));
            Parent root = fxmlLoader.load();
            SecondSceneController secController = fxmlLoader.getController();
            secController.setImage(imView.getImage());
            Scene second= new Scene(root);
            primaryStage.setScene(second);
            secController.setPrimaryStage(primaryStage);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        primaryStage.show();
    }

    void setImageAndBuildCharts(Image image) {
        imView.setImage(image);
        prepareArrays(imView.getImage());
        setCharts();
    }
}
