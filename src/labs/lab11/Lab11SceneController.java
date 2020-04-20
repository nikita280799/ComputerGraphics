package labs.lab11;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;


public class Lab11SceneController {

    @FXML
    private TextField text;

    @FXML
    private Button loadbut1, loadbut2, but3;

    @FXML
    private ImageView imview1, imview2, imview3;

    public void initialize() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\Studying\\Computer graphics\\labs\\images"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg"));
        loadbut1.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(loadbut1.getScene().getWindow());
            if (file != null) {
                try {
                    imview1.setImage(new Image(file.toURI().toURL().toString()));
                } catch (MalformedURLException ex) {
                    System.out.println("File chooser error");
                }
            }
        });
        loadbut2.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(loadbut1.getScene().getWindow());
            if (file != null) {
                try {
                    imview2.setImage(new Image(file.toURI().toURL().toString()));
                } catch (MalformedURLException ex) {
                    System.out.println("File chooser error");
                }
            }
        });
        but3.setOnAction(event -> {
            overlayImage(imview1.getImage(), imview2.getImage(), Integer.parseInt(text.getText()));

        });
    }

    private void overlayImage(Image im1, Image im2, int percent) {
        PixelReader pr1 = im1.getPixelReader();
        PixelReader pr2 = im2.getPixelReader();
        double w = Math.min(im1.getWidth(), im2.getWidth());
        double h = Math.min(im1.getHeight(), im2.getHeight());
        WritableImage img = new WritableImage((int) w, (int) h);
        PixelWriter pw = img.getPixelWriter();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double k = (double) percent / 100;
                double r = pr1.getColor(x, y).getRed() * (1 - k) + pr2.getColor(x, y).getRed() * k;
                double g = pr1.getColor(x, y).getGreen() * (1 - k) + pr2.getColor(x, y).getGreen() * k;
                double b = pr1.getColor(x, y).getBlue() * (1 - k) + pr2.getColor(x, y).getBlue() * k;
                r = r > 1.0 ? 1.0 : r;
                g = g > 1.0 ? 1.0 : g;
                b = b > 1.0 ? 1.0 : b;
                pw.setColor(x, y, Color.color(r, g, b));
            }
        }
        imview3.setImage(img);
    }
}
