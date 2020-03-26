package labs.lab05;

import javafx.animation.PathTransition;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static java.lang.Math.*;

public class Lab05SceneController {

    @FXML
    private Pane canvas;

    @FXML
    private javafx.scene.control.Button but;

    @FXML
    private TextField tfield;

    private Rectangle r = new Rectangle(50, 50);

    public void initialize() {
        canvas.getChildren().add(r);
        r.setX(150);
        r.setY(150);
        but.setOnAction(event -> {
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(4000));
            Path path = new Path();
            affineTransform(Integer.parseInt(tfield.getText()), path);
            pathTransition.setPath(path);
            pathTransition.setNode(r);
            pathTransition.play();
        });
    }

    private Point2D transToCenter(Point2D p) {
       return p.subtract(500.0 / 2, 2 * p.getY() - 450.0 / 2);
    }

    private Point2D transFromCenter(Point2D p) {
        return p.add(500.0 / 2, -2 * p.getY() + 450.0 / 2);
    }

    private void affineTransform(double angle, Path path) {
        double a = angle * PI / 180;
        double da = 0.1;
        double acur = 0.0;
        Point2D p = new Point2D(r.getX(), r.getY());
        path.getElements().add(new MoveTo(p.getX(), p.getY()));
        while (acur < a) {
            p = transToCenter(p);
            p = p.add(-p.getX() + p.getX() * cos(da) - p.getY() * sin(da),
                    -p.getY() + p.getX() * sin(da) + p.getY() * cos(da));
            p = transFromCenter(p);
            path.getElements().add(new LineTo(p.getX(), p.getY()));
            acur += da;
        }
        r.setX(p.getX());
        r.setY(p.getY());
    }
}