package labs.lab08;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;

public class Lab08SceneController {

    @FXML
    private Canvas canvas;

    private Point p1, p2, p3;

    public void initialize() {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (p1 == null) {
                        p1 = new Point((int) e.getX(), (int) e.getY());
                        canvas.getGraphicsContext2D().fillRect(p1.x, p1.y, 1, 1);
                    } else if (p2 == null) {
                        p2 = new Point((int) e.getX(), (int) e.getY());
                        canvas.getGraphicsContext2D().fillRect(p2.x, p2.y, 1, 1);
                    } else if (p3 == null) {
                        p3 = new Point((int) e.getX(), (int) e.getY());
                        canvas.getGraphicsContext2D().fillRect(p3.x, p3.y, 1, 1);
                        fillTriangle(canvas.getGraphicsContext2D());
                        p1 = null;
                        p2 = null;
                        p3 = null;
                    }
                });
    }

    private void swap(Point p1, Point p2) {
        Point ptemp = new Point(p1);
        p1.y = p2.y;
        p1.x = p2.x;
        p2.y = ptemp.y;
        p2.x = ptemp.x;
    }

    private void fillTriangle(GraphicsContext gc) {
        gc.setFill(Paint.valueOf(Color.GREEN.toString()));
        if (p2.y < p1.y) {
            swap(p1, p2);
        }
        if (p3.y < p1.y) {
            swap(p1, p3);
        }
        if (p2.y > p3.y) {
            swap(p2, p3);
        }
        double dx13, dx12, dx23;
        if (p3.y != p1.y) {
            dx13 = p3.x - p1.x;
            dx13 /= p3.y - p1.y;
        }
        else
        {
            dx13 = 0;
        }

        if (p2.y != p1.y) {
            dx12 = p2.x - p1.x;
            dx12 /= p2.y - p1.y;
        }
        else
        {
            dx12 = 0;
        }

        if (p3.y != p2.y) {
            dx23 = p3.x - p2.x;
            dx23 /= p3.y - p2.y;
        }
        else
        {
            dx23 = 0;
        }
        double wx1 = p1.x;
        double wx2 = wx1;
        double dx13i = dx13;
        if (dx13 > dx12)
        {
            double t = dx12;
            dx12 = dx13;
            dx13 = t;
        }
        for (int i = p1.y; i < p2.y; i++){
            for (int j = (int) wx1; j <= (int) wx2; j++){
                gc.fillRect(j, i, 1, 1);
            }
            wx1 += dx13;
            wx2 += dx12;
        }
        if (p1.y == p2.y){
            wx1 = p1.x;
            wx2 = p2.x;
        }
        if (dx13i < dx23)
        {
            double t = dx13i;
            dx13i = dx23;
            dx23 = t;
        }
        gc.setFill(Paint.valueOf(Color.RED.toString()));
        for (int i = p2.y; i <= p3.y; i++){
            for (int j = (int) wx1; j <= (int) wx2; j++){
                gc.fillRect(j, i, 1, 1);
            }
            wx1 += dx13i;
            wx2 += dx23;
        }
    }
}