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
import java.util.*;

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
        ArrayList<Circle> circles = new ArrayList<>(); /*Clusturing.creationPoints(target,maxX,maxY,5000)*/;

        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Circle c = new Circle(i,j,0);
                c.setFill(target[i][j]);
                circles.add(c);
            }

        }
        //ArrayList<Circle> circles = Clusturing.creationPoints(target,maxX,maxY,5000);


        for (Circle c:
             circles) {
            image.getChildren().add(c);
        }

        Map<Circle,List<Circle>> mapCentroids = Clusturing.kMeans(circles,400);

        for(Iterator<Circle> it = mapCentroids.keySet().iterator();it.hasNext();){
            Circle key = it.next();
            Color color =(Color) key.getFill();
            System.out.println(mapCentroids.get(key).size());
            if(mapCentroids.get(key).size()>3) {
                System.out.println(mapCentroids.get(key).size());
                Polygon polygon = Clusturing.creationPolygones(mapCentroids.get(key), color);
                image.getChildren().add(polygon);
            }
        }

        //image.getChildren().add(Clusturing.creationPolygones(circles,Color.color(0.5,0.5,0.5)));
    }
}
