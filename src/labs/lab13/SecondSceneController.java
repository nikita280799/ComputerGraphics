package labs.lab13;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SecondSceneController {

    @FXML
    Button clearBut, preViewBut, applyBut;

    @FXML
    Canvas canvas;

    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private CategoryAxis catAxis;

    @FXML
    private ChoiceBox choiceBox;

    private double[] newArr = new double[256];

    private int[] newAbsArr = new int[256];

    private int[] oldAbsArr = new int[256];

    private List<Point>[] listsArr = new ArrayList[256];

    private List<Point>[] newListsArr = new ArrayList[256];

    private Image image;

    private Stage primaryStage;

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {

        for (int i = 0; i < 256; i++) {
            listsArr[i] = new ArrayList<>();
            newListsArr[i] = new ArrayList<>();
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();

        paintBorder(gc);
        canvas.setOnMouseDragged((e) -> {
            setPixel(gc, new Point((int) e.getX(), (int) e.getY()));
        });
        canvas.setOnMouseClicked(e -> {
            setPixel(gc, new Point((int) e.getX(), (int) e.getY()));
        });
        clearBut.setOnAction(e -> {
            newArr = new double[256];
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            paintBorder(gc);
        });
        preViewBut.setOnAction(e -> {
            double max = 0;
            for (int i = 0; i < 256; i++) if (newArr[i] > max) max = newArr[i];
            for (int i = 0; i < 256; i++) newArr[i] = newArr[i] / max;
            setOneChart(chart, catAxis, newArr);
        });
        applyBut.setOnAction(e -> {
            prepareArrays();
            rebuildArrays();
            Image resImage = makeNewImage();
            changeScene(resImage);
        });

    }

    private Image makeNewImage() {
        PixelReader pr = image.getPixelReader();
        WritableImage img = new WritableImage(pr, (int) image.getWidth(), (int) image.getHeight());
        PixelWriter pw = img.getPixelWriter();
        switch (choiceBox.getValue().toString()) {
            case "red":
                for (int i = 0; i < newListsArr.length; i++) {
                    for (Point p: newListsArr[i]) {
                        pw.setColor(p.x, p.y, Color.color((double) i / 255, pr.getColor(p.x, p.y).getGreen(),
                                pr.getColor(p.x, p.y).getBlue()));
                    }
                }
                break;
            case "green":
                for (int i = 0; i < newListsArr.length; i++) {
                    for (Point p: newListsArr[i]) {
                        pw.setColor(p.x, p.y, Color.color( pr.getColor(p.x, p.y).getRed(),(double) i / 255,
                                pr.getColor(p.x, p.y).getBlue()));
                    }
                }
                break;
            case "blue":
                for (int i = 0; i < newListsArr.length; i++) {
                    for (Point p: newListsArr[i]) {
                        pw.setColor(p.x, p.y, Color.color(pr.getColor(p.x, p.y).getRed(),
                                pr.getColor(p.x, p.y).getGreen(), (double) i / 255));
                    }
                }
                break;
        }
        return img;
    }

    private void rebuildArrays() {
        for (int i = 0; i < 256; i++) {
            if (oldAbsArr[i] >= newAbsArr[i]) {
                while (newAbsArr[i] != 0) {
                    int r = random(0, listsArr[i].size() - 1);
                    newListsArr[i].add(listsArr[i].get(r));
                    listsArr[i].remove(r);
                    newAbsArr[i]--;
                }
            } else {
                newListsArr[i] = new ArrayList<>(listsArr[i]);
                newAbsArr[i] -= listsArr[i].size();
                listsArr[i].clear();
            }
        }
        int i = getIndexOfMax(newAbsArr);
        int d = 1;
        while (newAbsArr[i] > 0) {
            growListOneStep(i, i - d);
            growListOneStep(i, i + d);
            d++;
            if (newAbsArr[i] == 0) {
                i = getIndexOfMax(newAbsArr);
                d = 1;
            }
        }
    }

    private void growListOneStep(int i, int index) {
        if (isItRightIndex(index) && !listsArr[index].isEmpty() && newAbsArr[i] > 0) {
            if (newAbsArr[i] >= listsArr[index].size()) {
                newListsArr[i].addAll(listsArr[index]);
                newAbsArr[i] -= listsArr[index].size();
                listsArr[index].clear();
            } else {
                List<Point> listArrCopy = new ArrayList<Point>(listsArr[index]);
                for (Point p: listArrCopy) {
                    newListsArr[i].add(p);
                    listsArr[index].remove(p);
                    newAbsArr[i]--;
                    if (newAbsArr[i] == 0) break;
                }
            }
        }
    }


    private int random(int from, int to) {
        return (int) (Math.random() * (to - from) + from);
    }

    private boolean isItRightIndex(int i) {
        return i >= 0 && i < 256;
    }

    private int getIndexOfMax(int[] arr) {
        int maxInd = 0;
        int maxVal = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > maxVal) {
                maxVal = arr[i];
                maxInd = i;
            }
        }
        return maxInd;
    }

    private void prepareArrays() {
        PixelReader pr = image.getPixelReader();
        double w = image.getWidth();
        double h = image.getHeight();
        int pixelCount = (int) (w * h);
        switch (choiceBox.getValue().toString()) {
            case "red":
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        int bright = (int) (pr.getColor(x, y).getRed() * 255);
                        oldAbsArr[bright]++;
                        listsArr[bright].add(new Point(x, y));
                    }
                }
                break;
            case "green":
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        int bright = (int) (pr.getColor(x, y).getGreen() * 255);
                        oldAbsArr[bright]++;
                        listsArr[bright].add(new Point(x, y));
                    }
                }
                break;
            case "blue":
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        int bright = (int) (pr.getColor(x, y).getBlue() * 255);
                        oldAbsArr[bright]++;
                        listsArr[bright].add(new Point(x, y));
                    }
                }
                break;
        }
        int curPixelCount = 0;
        double sumOfMaxInArray = 0.0;
        for (int i = 0; i < 256; i++) {
            sumOfMaxInArray += newArr[i];
        }
        double maxBright = pixelCount / sumOfMaxInArray;
        for (int i = 0; i < 256; i++) {
            newAbsArr[i] = (int) (maxBright * newArr[i]);
            curPixelCount += newAbsArr[i];
        }
        int i = 0;
        while (curPixelCount > pixelCount) {
            newAbsArr[i % 256]--;
            curPixelCount--;
            i++;
        }
        while (curPixelCount < pixelCount) {
            newAbsArr[i % 256]++;
            curPixelCount++;
            i++;
        }
    }

    private void paintBorder(GraphicsContext gc) {
        gc.fillRect(0, 0, 258, 1);
        gc.fillRect(0, 0, 1, 202);
        gc.fillRect(0, 201, 258, 1);
        gc.fillRect(257, 0, 1, 202);
    }

    private void setPixel(GraphicsContext gc, Point p) {
        if (p.x < 256 && p.x > -1) {
            newArr[p.x] = (canvas.getHeight() - p.y) / canvas.getHeight();
            if (newArr[p.x] < 0) newArr[p.x] = 0;
            gc.fillRect(p.x, p.y, 1, 1);
        }
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

    public void setImage(Image image) {
        this.image = image;
    }

    private void changeScene(Image newImage) {
        primaryStage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lab13Scene.fxml"));
            Parent root = fxmlLoader.load();
            Lab13SceneController controller = fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            controller.setImageAndBuildCharts(newImage);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        primaryStage.show();
    }
}