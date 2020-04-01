package labs.lab10;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.MalformedURLException;


public class Lab10SceneController {

    @FXML
    private TextField f3t1, f3t2, f3t3, f3t4, f3t5, f3t6, f3t7, f3t8, f3t9, f5t1, f5t2, f5t3, f5t4, f5t5, f5t6, f5t7,
     f5t8, f5t9, f5t10, f5t11, f5t12, f5t13, f5t14, f5t15, f5t16, f5t17, f5t18, f5t19, f5t20, f5t21, f5t22, f5t23,
     f5t24, f5t25;

    @FXML
    private Button findimage;

    @FXML
    private ToggleButton tb3, tb5;

    @FXML
    private ImageView imview1, imview2;

    public void initialize() {
        ToggleGroup tg = new ToggleGroup();
        tb3.setToggleGroup(tg);
        tb5.setToggleGroup(tg);
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\Studying\\Computer graphics\\labs\\images"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg"));
        findimage.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(findimage.getScene().getWindow());
            if (file != null) {
                try {
                    imview1.setImage(new Image(file.toURI().toURL().toString()));
                } catch (MalformedURLException ex) {
                    System.out.println("File chooser error");
                }
            }
            if (tb3.isSelected()) {
                int[][] filter = new int[3][3];
                filter[0][0] = Integer.parseInt(f3t1.getText());
                filter[0][1] = Integer.parseInt(f3t2.getText());
                filter[0][2] = Integer.parseInt(f3t3.getText());
                filter[1][0] = Integer.parseInt(f3t4.getText());
                filter[1][1] = Integer.parseInt(f3t5.getText());
                filter[1][2] = Integer.parseInt(f3t6.getText());
                filter[2][0] = Integer.parseInt(f3t7.getText());
                filter[2][1] = Integer.parseInt(f3t8.getText());
                filter[2][2] = Integer.parseInt(f3t9.getText());
                imageFilter3x3(imview1.getImage(), filter);
            } else {
                int[][] filter = new int[5][5];
                filter[0][0] = Integer.parseInt(f5t1.getText());
                filter[0][1] = Integer.parseInt(f5t2.getText());
                filter[0][2] = Integer.parseInt(f5t3.getText());
                filter[0][3] = Integer.parseInt(f5t4.getText());
                filter[0][4] = Integer.parseInt(f5t5.getText());
                filter[1][0] = Integer.parseInt(f5t6.getText());
                filter[1][1] = Integer.parseInt(f5t7.getText());
                filter[1][2] = Integer.parseInt(f5t8.getText());
                filter[1][3] = Integer.parseInt(f5t9.getText());
                filter[1][4] = Integer.parseInt(f5t10.getText());
                filter[2][0] = Integer.parseInt(f5t11.getText());
                filter[2][1] = Integer.parseInt(f5t12.getText());
                filter[2][2] = Integer.parseInt(f5t13.getText());
                filter[2][3] = Integer.parseInt(f5t14.getText());
                filter[2][4] = Integer.parseInt(f5t15.getText());
                filter[3][0] = Integer.parseInt(f5t16.getText());
                filter[3][1] = Integer.parseInt(f5t17.getText());
                filter[3][2] = Integer.parseInt(f5t18.getText());
                filter[3][3] = Integer.parseInt(f5t19.getText());
                filter[3][4] = Integer.parseInt(f5t20.getText());
                filter[4][0] = Integer.parseInt(f5t21.getText());
                filter[4][1] = Integer.parseInt(f5t22.getText());
                filter[4][2] = Integer.parseInt(f5t23.getText());
                filter[4][3] = Integer.parseInt(f5t24.getText());
                filter[4][4] = Integer.parseInt(f5t25.getText());
                imageFilter5x5(imview1.getImage(), filter);
            }
        });
    }

    private void imageFilter5x5(Image image, int[][] filter) {
        PixelReader pr = image.getPixelReader();
        WritableImage img = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pw = img.getPixelWriter();
        double w = img.getWidth();
        double h = img.getHeight();
        for (int y = 2; y < h-2; y++) {
            for (int x = 2; x < w-2; x++) {
                double r = (pr.getColor(x-2, y-2).getRed() * filter[0][0] + pr.getColor(x-1, y-2).getRed() * filter[0][1] +
                        pr.getColor(x, y-2).getRed() * filter[0][2] + pr.getColor(x+1, y-2).getRed() * filter[0][3] +
                        pr.getColor(x+2, y-2).getRed() * filter[0][4] + pr.getColor(x-2, y-1).getRed() * filter[1][0] +
                        pr.getColor(x-1, y-1).getRed() * filter[1][1] + pr.getColor(x, y-1).getRed() * filter[1][2] +
                        pr.getColor(x+1, y-1).getRed() * filter[1][3] + pr.getColor(x+2, y-1).getRed() * filter[1][4] +
                        pr.getColor(x-2, y).getRed() * filter[2][0] + pr.getColor(x-1, y).getRed() * filter[2][1] +
                        pr.getColor(x, y).getRed() * filter[2][2] + pr.getColor(x+1, y).getRed() * filter[2][3] +
                        pr.getColor(x+2, y).getRed() * filter[2][4] + pr.getColor(x-2, y+1).getRed() * filter[3][0] +
                        pr.getColor(x-1, y+1).getRed() * filter[3][1] + pr.getColor(x, y+1).getRed() * filter[3][2] +
                        pr.getColor(x+1, y+1).getRed() * filter[3][3] + pr.getColor(x+2, y+1).getRed() * filter[3][4] +
                        pr.getColor(x-2, y+2).getRed() * filter[4][0] + pr.getColor(x-1, y+2).getRed() * filter[4][1] +
                        pr.getColor(x, y+2).getRed() * filter[4][2] + pr.getColor(x+1, y+2).getRed() * filter[4][3] +
                        pr.getColor(x+2, y+2).getRed() * filter[4][4]) / 25;
                double g = (pr.getColor(x-2, y-2).getGreen() * filter[0][0] + pr.getColor(x-1, y-2).getGreen()  * filter[0][1] +
                        pr.getColor(x, y-2).getGreen()  * filter[0][2] + pr.getColor(x+1, y-2).getGreen() * filter[0][3] +
                        pr.getColor(x+2, y-2).getGreen() * filter[0][4] + pr.getColor(x-2, y-1).getGreen() * filter[1][0] +
                        pr.getColor(x-1, y-1).getGreen() * filter[1][1] + pr.getColor(x, y-1).getGreen() * filter[1][2] +
                        pr.getColor(x+1, y-1).getGreen() * filter[1][3] + pr.getColor(x+2, y-1).getGreen() * filter[1][4] +
                        pr.getColor(x-2, y).getGreen() * filter[2][0] + pr.getColor(x-1, y).getGreen() * filter[2][1] +
                        pr.getColor(x, y).getGreen() * filter[2][2] + pr.getColor(x+1, y).getGreen() * filter[2][3] +
                        pr.getColor(x+2, y).getGreen() * filter[2][4] + pr.getColor(x-2, y+1).getGreen() * filter[3][0] +
                        pr.getColor(x-1, y+1).getGreen() * filter[3][1] + pr.getColor(x, y+1).getGreen() * filter[3][2] +
                        pr.getColor(x+1, y+1).getGreen() * filter[3][3] + pr.getColor(x+2, y+1).getGreen() * filter[3][4] +
                        pr.getColor(x-2, y+2).getGreen() * filter[4][0] + pr.getColor(x-1, y+2).getGreen() * filter[4][1] +
                        pr.getColor(x, y+2).getGreen() * filter[4][2] + pr.getColor(x+1, y+2).getGreen() * filter[4][3] +
                        pr.getColor(x+2, y+2).getGreen() * filter[4][4]) / 25;
                double b = (pr.getColor(x-2, y-2).getBlue() * filter[0][0] + pr.getColor(x-1, y-2).getBlue() * filter[0][1] +
                        pr.getColor(x, y-2).getBlue() * filter[0][2] + pr.getColor(x+1, y-2).getBlue() * filter[0][3] +
                        pr.getColor(x+2, y-2).getBlue() * filter[0][4] + pr.getColor(x-2, y-1).getBlue() * filter[1][0] +
                        pr.getColor(x-1, y-1).getBlue() * filter[1][1] + pr.getColor(x, y-1).getBlue() * filter[1][2] +
                        pr.getColor(x+1, y-1).getBlue() * filter[1][3] + pr.getColor(x+2, y-1).getBlue() * filter[1][4] +
                        pr.getColor(x-2, y).getBlue() * filter[2][0] + pr.getColor(x-1, y).getBlue() * filter[2][1] +
                        pr.getColor(x, y).getBlue() * filter[2][2] + pr.getColor(x+1, y).getBlue() * filter[2][3] +
                        pr.getColor(x+2, y).getBlue() * filter[2][4] + pr.getColor(x-2, y+1).getBlue() * filter[3][0] +
                        pr.getColor(x-1, y+1).getBlue() * filter[3][1] + pr.getColor(x, y+1).getBlue() * filter[3][2] +
                        pr.getColor(x+1, y+1).getBlue() * filter[3][3] + pr.getColor(x+2, y+1).getBlue() * filter[3][4] +
                        pr.getColor(x-2, y+2).getBlue() * filter[4][0] + pr.getColor(x-1, y+2).getBlue() * filter[4][1] +
                        pr.getColor(x, y+2).getBlue() * filter[4][2] + pr.getColor(x+1, y+2).getBlue() * filter[4][3] +
                        pr.getColor(x+2, y+2).getBlue() * filter[4][4]) / 25;
                r = r > 1.0 ? 1.0 : r;
                g = g > 1.0 ? 1.0 : g;
                b = b > 1.0 ? 1.0 : b;
                pw.setColor(x, y, Color.color(r, g, b));
            }
        }
        imview2.setImage(img);
    }

    private void imageFilter3x3(Image image, int[][] filter) {
        PixelReader pr = image.getPixelReader();
        WritableImage img = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pw = img.getPixelWriter();
        double w = img.getWidth();
        double h = img.getHeight();
        System.out.println(w + " " + h);
        for (int y = 1; y < h-1; y++) {
            for (int x = 1; x < w-1; x++) {
                double r = (pr.getColor(x-1, y-1).getRed() * filter[0][0] + pr.getColor(x, y-1).getRed() * filter[0][1] +
                        pr.getColor(x+1, y-1).getRed() * filter[0][2] + pr.getColor(x-1, y).getRed() * filter[1][0] +
                        pr.getColor(x, y).getRed() * filter[1][1] + pr.getColor(x+1, y).getRed() * filter[1][2] +
                        pr.getColor(x-1, y+1).getRed() * filter[2][0] + pr.getColor(x, y+1).getRed() * filter[2][1] +
                        pr.getColor(x+1, y+1).getRed() * filter[2][2]) / 9;
                double g = (pr.getColor(x-1, y-1).getGreen() * filter[0][0] + pr.getColor(x, y-1).getGreen() * filter[0][1] +
                        pr.getColor(x+1, y-1).getGreen() * filter[0][2] + pr.getColor(x-1, y).getGreen() * filter[1][0] +
                        pr.getColor(x, y).getGreen() * filter[1][1] + pr.getColor(x+1, y).getGreen() * filter[1][2] +
                        pr.getColor(x-1, y+1).getGreen() * filter[2][0] + pr.getColor(x, y+1).getGreen() * filter[2][1] +
                        pr.getColor(x+1, y+1).getGreen() * filter[2][2]) / 9;
                double b = (pr.getColor(x-1, y-1).getBlue() * filter[0][0] + pr.getColor(x, y-1).getBlue() * filter[0][1] +
                        pr.getColor(x+1, y-1).getBlue() * filter[0][2] + pr.getColor(x-1, y).getBlue() * filter[1][0] +
                        pr.getColor(x, y).getBlue() * filter[1][1] + pr.getColor(x+1, y).getBlue() * filter[1][2] +
                        pr.getColor(x-1, y+1).getBlue() * filter[2][0] + pr.getColor(x, y+1).getBlue() * filter[2][1] +
                        pr.getColor(x+1, y+1).getBlue() * filter[2][2]) / 9;
                r = r > 1.0 ? 1.0 : r;
                g = g > 1.0 ? 1.0 : g;
                b = b > 1.0 ? 1.0 : b;
                pw.setColor(x, y, Color.color(r, g, b));
            }
        }
        imview2.setImage(img);
    }
}