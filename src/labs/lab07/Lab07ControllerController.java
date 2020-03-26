package labs.lab07;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Lab07ControllerController {

    @FXML
    private TextField red, green, blue, cyan1, cyan2, magenta1, magenta2, yellow1, yellow2, hue, satur, value, key;

    @FXML
    private Circle rgbCir, cmyCir, hsyCir, cmykCir;

    boolean isRGB, isCMY, isHSV, isCMYK;

    @FXML
    private void click() {
        if (isCMY) {
            cmyToRGB(Integer.parseInt(cyan1.getText()), Integer.parseInt(magenta1.getText()),
                    Integer.parseInt(yellow1.getText()));
        }
        if (isCMYK) {
            cmykToRGB(Integer.parseInt(cyan1.getText()), Integer.parseInt(magenta1.getText()),
                    Integer.parseInt(yellow1.getText()), Integer.parseInt(key.getText()));
        }
        if (isHSV) {
            hsvToRGB(Double.parseDouble(hue.getText()), Double.parseDouble(satur.getText()),
                    Double.parseDouble(value.getText()));
        }
        int r = Integer.parseInt(red.getText());
        int g = Integer.parseInt(green.getText());
        int b = Integer.parseInt(blue.getText());
        rgbCir.setFill(Paint.valueOf(Color.rgb(r, g, b).toString()));
        if (!isCMYK) rgbToCMYK(r,g,b);
        if (!isHSV) rgbToHSV(r, g, b);
        if (!isCMY) rgbToCMY(r, g, b);
    }

    private void addOnTextListeners() {
        blue.textProperty().addListener(
                (observable, oldValue, newValue) -> changeBool(false, false, true, false));
        yellow1.textProperty().addListener(
                (observable, oldValue, newValue) -> changeBool(true, false, false, false));
        value.textProperty().addListener(
                (observable, oldValue, newValue) -> changeBool(false, false, false, true));
        key.textProperty().addListener(
                (observable, oldValue, newValue) -> changeBool(false, true, false, false));
    }

    private void changeBool(boolean isCMY, boolean isCMYK, boolean isRGB, boolean isHSV) {
        this.isCMY = isCMY;
        this.isCMYK = isCMYK;
        this.isHSV = isHSV;
        this.isRGB = isRGB;
    }

    private void rgbToHSV(int r, int g, int b) {
        Color col = Color.rgb(r, g, b);
        hue.setText(String.valueOf(col.getHue()));
        satur.setText(String.valueOf(col.getSaturation()));
        value.setText(String.valueOf(col.getBrightness()));
        hsyCir.setFill(Paint.valueOf(col.toString()));
    }

    private void hsvToRGB(double h, double s, double v) {
        Color col = Color.hsb(h, s, v);
        red.setText(String.valueOf((int) (col.getRed() * 255)));
        green.setText(String.valueOf((int) (col.getGreen() * 255)));
        blue.setText(String.valueOf((int) (col.getBlue() * 255)));
        hsyCir.setFill(Paint.valueOf(col.toString()));
    }

    private void rgbToCMYK(int r, int g, int b) {
        int k   = Math.min(255-r, Math.min(255-g,255-b));
        key.setText(String.valueOf(k));
        cyan2.setText(String.valueOf((255-r-k) * 255/(255-k)));
        magenta2.setText(String.valueOf((255-g-k) * 255/(255-k)));
        yellow2.setText(String.valueOf((255-b-k) * 255/(255-k)));
        cmykCir.setFill(Paint.valueOf(Color.rgb(r, g, b).toString()));
    }

    private void cmykToRGB(int c, int m, int y, int k) {
        int r = (255 - c) / (255 - k) * 255;
        int g = (255 - m) / (255 - k) * 255;
        int b = (255 - y) / (255 - k) * 255;
        red.setText(String.valueOf(r));
        green.setText(String.valueOf(g));
        blue.setText(String.valueOf(b));
        cmykCir.setFill(Paint.valueOf(Color.rgb(r, g, b).toString()));
    }

    private void rgbToCMY(int r, int g, int b) {
        cyan1.setText(String.valueOf(255 - r));
        magenta1.setText(String.valueOf(255 - g));
        yellow1.setText(String.valueOf(255 - b));
        cmyCir.setFill(Paint.valueOf(Color.rgb(r, g, b).toString()));
    }

    private void cmyToRGB(int c, int m, int y) {
        int r = 255 - c;
        int g = 255 - m;
        int b = 255 - y;
        red.setText(String.valueOf(r));
        green.setText(String.valueOf(g));
        blue.setText(String.valueOf(b));
        cmykCir.setFill(Paint.valueOf(Color.rgb(r, g, b).toString()));
    }

    public void initialize() {
        addOnTextListeners();
    }
}