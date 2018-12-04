package ia.projet.process.clusturingMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClusturingTest extends Application {
    @Override
    public void start(Stage myStage) throws Exception {
        String pathImage = "monaLisa-200.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        BufferedImage bi = ImageIO.read(new File(pathImage));
        int maxX = bi.getWidth();
        int maxY = bi.getHeight();
        Group image = new Group();
        Scene scene = new Scene(image,maxX,maxY );
        myStage.setScene(scene);
        myStage.show();
        Random random = new Random();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //Platform.startup(runnable);
        //TODO : Rendre adaptable.
        ArrayList<Circle> circles = new ArrayList<>();/*Clusturing.creationPoints(target,maxX,maxY,50);*/
        circles.add(new Circle(20,20,4));
        circles.add(new Circle(40,40,4));
        circles.add(new Circle(20,40,4));
        circles.add(new Circle(40,20,4));
        circles.add(new Circle(80,20,4));
        ArrayList<Point> points = new ArrayList<>();
        for (Circle c:
             circles) {
            image.getChildren().add(c);
            points.add(new Point(( int) c.getCenterX(),(int)c.getCenterY()));
        }
        image.getChildren().add(Clusturing.creationPolygones(points,Color.color(0.5,0.5,0.5)));
    }
}
