package labs.lab12;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class Lab12Application extends Application {

    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    private final static int CENTERX = 0;
    private final static int CENTERY = 0;
    private final static int CENTERZ = 0;

    private final static int LENGTH = 500;
    private final static int LINEW = 3;

    private double anchorX, anchorY;

    private CheckBox cbX, cbY, cbZ;

    MeshView pyramid;

    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private TextField nField, hField, oX, oY, oZ;
    private Button paintButton;

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


        Label nLabel = new Label("Введите N");
        Label hLabel = new Label("Введите H");
        nField = new TextField();
        hField = new TextField();
        oX = new TextField();
        oY = new TextField();
        oZ = new TextField();
        oX.setPrefSize(50, 15);
        oY.setPrefSize(50, 15);
        oZ.setPrefSize(50, 15);
        paintButton = new Button("Нарисовать пирамиду");
        HBox hbox1 = new HBox(new Label("Координаты О  "), new Label("X:"), oX, new Label("Y:"), oY
                , new Label("Z:"), oZ);
        cbX = new CheckBox("X");
        cbY = new CheckBox("Y");
        cbZ = new CheckBox("Z");
        HBox hbox2 = new HBox(new Label("Вращение вокруг"), cbX, cbY, cbZ);

        VBox vbox = new VBox(nLabel, nField, hLabel, hField, hbox1, hbox2, paintButton);
        vbox.setTranslateX(300);
        vbox.setTranslateY(0);
        vbox.setTranslateZ(0);

        Group root = new Group();

        PointLight light = new PointLight();
        light.setTranslateX(-50);
        light.setTranslateY(-200);
        light.setTranslateZ(0);

        root.getChildren().add(light);
        root.getChildren().add(boxX);
        root.getChildren().add(boxY);
        root.getChildren().add(boxZ);
        root.getChildren().add(vbox);


        Scene scene = new Scene(root, WIDTH, HEIGHT, true);

        PerspectiveCamera camera = new PerspectiveCamera(false);// создание камеры
        camera.setTranslateX(-200);
        camera.setTranslateY(-200);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        initMouseControl(root, scene);

        stage.setScene(scene);
        stage.setTitle("Lab12");
        stage.show();
        initListeners(root);
    }

    private void initListeners(Group root) {
        paintButton.setOnAction(event -> addPyramide(Integer.parseInt(nField.getText()), Integer.parseInt(hField.getText()), root));
    }

    private void addPyramide(int n, int h, Group root) {
        TriangleMesh pyramidMesh = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0, 0);
        float s = 50;
        pyramidMesh.getPoints().addAll(0, -h, 0);
        pyramidMesh.getPoints().addAll(0, 0, 0);
        for (int i = 0; i < n; i++) {
            pyramidMesh.getPoints().addAll((float) (s * Math.cos((2 * Math.PI * i) / n)), 0,
                    (float) (s * Math.sin((2 * Math.PI * i) / n)));
        }

        for (int i = 2; i < n + 1; i++) {
            pyramidMesh.getFaces().addAll(0, 0, i, 0, i + 1, 0);
            pyramidMesh.getFaces().addAll(1, 0, i, 0, i + 1, 0);
        }
        pyramidMesh.getFaces().addAll(0, 0, n + 1, 0, 2, 0);
        pyramidMesh.getFaces().addAll(1, 0, n + 1, 0, 2, 0);
        pyramid = new MeshView(pyramidMesh);
        pyramid.setDrawMode(DrawMode.FILL);
        pyramid.setTranslateX(0); // синяя
        pyramid.setTranslateY(0); //высота с обратным знаком
        pyramid.setTranslateZ(0); //
        pyramid.setMaterial(new PhongMaterial(Color.YELLOW));
        root.getChildren().add(pyramid);

        Box oBox = new Box(5, 5, 5);
        oBox.setTranslateX(Integer.parseInt(oX.getText()));
        oBox.setTranslateY(Integer.parseInt(oY.getText()));
        oBox.setTranslateZ(Integer.parseInt(oZ.getText()));
        root.getChildren().add(oBox);

        prepareAnimation(new Point3D(Integer.parseInt(oX.getText()), Integer.parseInt(oY.getText()),
                Integer.parseInt(oZ.getText())));
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

    private void prepareAnimation(Point3D center) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double da = 0.015;
                Point3D p = new Point3D(pyramid.getTranslateX(), pyramid.getTranslateY(), pyramid.getTranslateZ());
                p = transToCenter(p, center);
                if (cbX.isSelected()) {
                    p = p.add(-p.getX() + p.getX() * cos(da) - p.getY() * sin(da),
                            -p.getY() + p.getX() * sin(da) + p.getY() * cos(da), 0);
                }
                if (cbY.isSelected()) {
                    p = p.add(0, -p.getY() + p.getY() * cos(da) - p.getZ() * sin(da),
                            -p.getZ() + p.getY() * sin(da) + p.getZ() * cos(da));
                }
                if (cbZ.isSelected()) {
                    p = p.add(-p.getX() + p.getX() * cos(da) - p.getZ() * sin(da), 0,
                            -p.getZ() + p.getX() * sin(da) + p.getZ() * cos(da));
                }
                p = transFromCenter(p, center);
                pyramid.setTranslateX(p.getX());
                pyramid.setTranslateY(p.getY());
                pyramid.setTranslateZ(p.getZ());
            }
        };
        timer.start();
    }

    private Point3D transToCenter(Point3D p, Point3D c) {
        return p.subtract(c);
    }

    private Point3D transFromCenter(Point3D p, Point3D c) {
        return p.add(c);
    }
}
