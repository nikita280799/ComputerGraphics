package labs.lab06;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Lab06Application extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    private Box box = new Box(100, 100, 100);

    private CheckBox cbX, cbY, cbZ;

    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    private final static int LENGTH = 500;
    private final static int LINEW = 3;

    private final static int CENTERX = 400;
    private final static int CENTERY = 300;
    private final static int CENTERZ = 250;

    private double anchorX, anchorY;

    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage stage) {
        Box boxX = new Box(LENGTH, LINEW, LINEW);
        boxX.setTranslateX(CENTERX);
        boxX.setTranslateY(CENTERY);
        boxX.setTranslateZ(CENTERZ);
        boxX.setMaterial(new PhongMaterial(Color.BLUE));

        Box boxY = new Box(LINEW, LINEW, LENGTH);
        boxY.setTranslateX(CENTERX);
        boxY.setTranslateY(CENTERY);
        boxY.setTranslateZ(CENTERZ);
        boxY.setMaterial(new PhongMaterial(Color.GREEN));

        Box boxZ = new Box(LINEW, LENGTH, LINEW);
        boxZ.setTranslateX(CENTERX);
        boxZ.setTranslateY(CENTERY);
        boxZ.setTranslateZ(CENTERZ);
        boxZ.setMaterial(new PhongMaterial(Color.LIGHTCYAN));

        box.setTranslateX(250);
        box.setTranslateY(100);
        box.setTranslateZ(200);

        cbX = new CheckBox("X");
        cbY = new CheckBox("Y");
        cbZ = new CheckBox("Z");

        cbX.setTranslateX(550);
        cbX.setTranslateY(0);
        cbX.setTranslateZ(500);

        cbY.setTranslateX(590);
        cbY.setTranslateY(0);
        cbY.setTranslateZ(500);

        cbZ.setTranslateX(630);
        cbZ.setTranslateY(0);
        cbZ.setTranslateZ(500);

        PointLight light = new PointLight();
        light.setTranslateX(350);
        light.setTranslateY(100);
        light.setTranslateZ(300);

        Group root = new Group();
        root.getChildren().add(box);
        root.getChildren().add(light);
        root.getChildren().add(cbX);
        root.getChildren().add(cbY);
        root.getChildren().add(cbZ);
        root.getChildren().add(boxX);
        root.getChildren().add(boxY);
        root.getChildren().add(boxZ);
        prepareAnimation();
        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        initMouseControl(root, scene);

        PerspectiveCamera camera = new PerspectiveCamera(false);// создание камеры
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        stage.setScene(scene);
        stage.setTitle("Lab06");
        stage.show();
    }

    private void initMouseControl(Group root, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        root.getTransforms().addAll(
          xRotate = new Rotate(0, Rotate.X_AXIS),
          yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(e -> {
           anchorX = e.getSceneX();
           anchorY = e.getSceneY();
           anchorAngleX = angleX.get();
           anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(e -> {
            angleX.set(anchorAngleX - (anchorY - e.getSceneY()));
            angleY.set(anchorAngleY + anchorX - e.getSceneX());
        });
    }

    private void prepareAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double da = 0.015;
                Point3D p = new Point3D(box.getTranslateX(), box.getTranslateY(), box.getTranslateZ());
                p = transToCenter(p);
                if (cbX.isSelected()) {
                    p = p.add(-p.getX() + p.getX() * cos(da) - p.getY() * sin(da),
                            -p.getY() + p.getX() * sin(da) + p.getY() * cos(da), 0);
                    System.out.println(p.toString());
                }
                if (cbY.isSelected()) {
                    p = p.add(0, -p.getY() + p.getY() * cos(da) - p.getZ() * sin(da),
                            -p.getZ() + p.getY() * sin(da) + p.getZ() * cos(da));
                }
                if (cbZ.isSelected()) {
                    p = p.add(-p.getX() + p.getX() * cos(da) - p.getZ() * sin(da), 0,
                            -p.getZ() + p.getX() * sin(da) + p.getZ() * cos(da));
                }
                p = transFromCenter(p);
                box.setTranslateX(p.getX());
                box.setTranslateY(p.getY());
                box.setTranslateZ(p.getZ());
            }
        };
        timer.start();
    }

    private Point3D transToCenter(Point3D p) {
        return p.subtract(CENTERX, CENTERY, CENTERZ);
    }

    private Point3D transFromCenter(Point3D p) {
        return p.add(CENTERX, CENTERY, CENTERZ);
    }
}