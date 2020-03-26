package labs.lab09;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;


public class Lab09SceneController {

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox choicebox;

    @FXML
    private ColorPicker colorpick;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Button clear;

    private Point p1, p2 = null;

    private Set<Point> points = new HashSet<>();

    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (!checkbox.isSelected()) {
                        if (p1 == null) {
                            p1 = new Point((int) e.getX(), (int) e.getY());
                        } else {
                            p2 = new Point((int) e.getX(), (int) e.getY());
                            cdaLine(gc, p1, p2);
                            p1 = null;
                            p2 = null;
                        }
                    } else {
                        fillArea(gc, new Point((int) e.getX(), (int) e.getY()));
                    }
                });
        clear.setOnAction(event -> {
            points.clear();
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    private void cdaLine(GraphicsContext gc, Point start, Point end) {
        int py = end.y - start.y;
        int px = end.x - start.x;
        int n = abs(end.y - start.y) > abs(end.x - start.x) ? abs(py) : abs(px);
        double xi = start.x;
        double yi = start.y;
        for (int i = 1; i < n; i++) {
            xi = xi + (double) px / n;
            yi = yi + (double) py / n;
            points.add(new Point((int) xi, (int) yi));
            gc.fillRect((int) xi, (int) yi, 2, 2);
        }
    }

    private void setPixel(GraphicsContext gc, Point p) {
        points.add(p);
        gc.fillRect(p.x, p.y, 1, 1);
    }

    private boolean isInCanvas(Point p) {
        return (canvas.getHeight() > p.y && p.y > 0 && p.x < canvas.getWidth() && p.x > 0);
    }

    private void add4Points(Point p, HashSet<Point> candidates) {
        if (isInCanvas(new Point(p.x - 1, p.y)) && !points.contains(new Point(p.x - 1, p.y))) candidates.add(new Point(p.x - 1, p.y));
        if (isInCanvas(new Point(p.x + 1, p.y)) && !points.contains(new Point(p.x + 1, p.y))) candidates.add(new Point(p.x + 1, p.y));
        if (isInCanvas(new Point(p.x, p.y - 1)) && !points.contains(new Point(p.x, p.y - 1))) candidates.add(new Point(p.x, p.y - 1));
        if (isInCanvas(new Point(p.x, p.y + 1)) && !points.contains(new Point(p.x, p.y + 1))) candidates.add(new Point(p.x, p.y + 1));
    }

    private void add8Points(Point p, HashSet<Point> candidates) {
        if (isInCanvas(new Point(p.x - 1, p.y)) && !points.contains(new Point(p.x - 1, p.y))) candidates.add(new Point(p.x - 1, p.y));
        if (isInCanvas(new Point(p.x + 1, p.y)) && !points.contains(new Point(p.x + 1, p.y))) candidates.add(new Point(p.x + 1, p.y));
        if (isInCanvas(new Point(p.x, p.y - 1)) && !points.contains(new Point(p.x, p.y - 1))) candidates.add(new Point(p.x, p.y - 1));
        if (isInCanvas(new Point(p.x, p.y + 1)) && !points.contains(new Point(p.x, p.y + 1))) candidates.add(new Point(p.x, p.y + 1));
        if (isInCanvas(new Point(p.x - 1, p.y - 1)) && !points.contains(new Point(p.x - 1, p.y - 1))) candidates.add(new Point(p.x - 1, p.y - 1));
        if (isInCanvas(new Point(p.x + 1, p.y + 1)) && !points.contains(new Point(p.x + 1, p.y + 1))) candidates.add(new Point(p.x + 1, p.y + 1));
        if (isInCanvas(new Point(p.x + 1, p.y - 1)) && !points.contains(new Point(p.x + 1, p.y - 1))) candidates.add(new Point(p.x + 1, p.y - 1));
        if (isInCanvas(new Point(p.x - 1, p.y + 1)) && !points.contains(new Point(p.x - 1, p.y + 1))) candidates.add(new Point(p.x - 1, p.y + 1));
    }

    private void fillArea(GraphicsContext gc, Point p) {
        gc.setFill(colorpick.getValue());
        HashSet<Point> candidates = new HashSet<>();
        candidates.add(p);
        while (!candidates.isEmpty()) {
            HashSet<Point> newCandidates = new HashSet<>();
            for (Point point : candidates) {
                setPixel(gc, point);
                if (choicebox.getValue().toString().equals("4 точки")) add4Points(point, newCandidates);
                else add8Points(point, newCandidates);
            }
            candidates = new HashSet<>(newCandidates);
        }
    }
}