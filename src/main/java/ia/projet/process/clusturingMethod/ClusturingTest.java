package ia.projet.process.clusturingMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.hillClimberMethod.HillClimber;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class ClusturingTest extends Application {
    @Override
    public void start(Stage myStage) throws Exception {
        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        BufferedImage bi = ImageIO.read(new File(pathImage));
        int maxX = bi.getWidth();
        int maxY = bi.getHeight();
        System.out.println(maxX + " " + maxY );
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

        //ArrayList<Circle> circles = new ArrayList<>(); /*Clusturing.creationPoints(target,maxX,maxY,5000)*/;
        /*
        for (int i=0;i<maxX-1;i++){
            for (int j=0;j<maxY-1;j++){
                Circle c = new Circle(i,j,0);
                c.setFill(target[i][j]);
                circles.add(c);
            }
        }*/
        ArrayList<Circle> circles = Clusturing.creationPoints(target,maxX,maxY,maxX*maxY);
        Map<Circle,List<Circle>> mapCentroids = Clusturing.kMeans(circles,200);

        ArrayList<Polygon> polygons = new ArrayList<>();
        Group image = new Group();
        for(Iterator<Circle> it = mapCentroids.keySet().iterator();it.hasNext();){
            Circle key = it.next();
            Color color =(Color) key.getFill();
            System.out.println(mapCentroids.get(key).size());
            if(mapCentroids.get(key).size()>3) {
                System.out.println(mapCentroids.get(key).size());
                try {
                    Polygon polygon = Clusturing.creationPolygones(mapCentroids.get(key), color);
                    polygons.add(polygon);
                    //image.getChildren().add(polygon);
                }catch (Exception e){

                }
            }
        }

        ///TEST ////
        IndividualSolution individualSolution = new IndividualSolution();
        ArrayList<GenePolygon> genes = new ArrayList<>();
        for (Polygon polygon:
             polygons) {
            List<Double> points = polygon.getPoints();
            genes.add(new GenePolygon(points.size()/2,points,(Color) polygon.getFill(),polygon.getOpacity()));
        }
        individualSolution.setGenome(genes);

        //finish setting genom
        ////
        WritableImage wimg = new WritableImage(maxX,maxY);
        image.snapshot(null,wimg);
        PixelReader pr = wimg.getPixelReader();
        System.out.println(ClusturingFitness.fitnessClusturing(target,pr,maxX,maxY));
        System.out.println(ClusturingFitness.fitnessClusturingProf(target,pr,maxX,maxY));
        //image.getChildren().add(Clusturing.creationPolygones(circles,Color.color(0.5,0.5,0.5)));
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(wimg, null);
        try {
            ImageIO.write(renderedImage, "png", new File("clust.png"));
            System.out.println("wrote image in " + "clust.png");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        individualSolution = HillClimber.hillClimber(individualSolution,target);
        individualSolution.forEach(z -> image.getChildren().addAll(z));

        Scene scene = new Scene(image,maxX,maxY );
        myStage.setScene(scene);
        myStage.show();

    }
}
