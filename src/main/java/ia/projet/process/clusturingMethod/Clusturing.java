package ia.projet.process.clusturingMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.Population;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;


public class Clusturing {
    static Random random = new Random();
    public static ArrayList<Circle> creationPoints(Color[][] target, int maxX, int maxY,int nbCircle){
        ArrayList<Circle> circles = new ArrayList<>();
        for(int i = 0; i<nbCircle;i++){
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            Color color = target[x][y];
            Circle circle = new Circle(x,y,4);
            circle.setFill(color);
            circles.add(circle);
        }
        return circles;
    }

    public static Polygon creationPolygones(List<Point> points,Color color){
        Polygon polygon = new Polygon();
        polygon.setFill(color);
        List<Point> hull = GrahamScan.getConvexHull(points);
        for(int i = 0; i<hull.size()-1;i++){
            polygon.getPoints().addAll(hull.get(i).getX(),hull.get(i).getY());
        }
        return polygon;
    }

    public static Map<Circle,ArrayList<Circle>> kMeans(ArrayList<Circle> circles,int numberOfCentroids){
        Map<Circle,ArrayList<Circle>> mapRes = new HashMap<>(numberOfCentroids);

        for(int i = 0; i<numberOfCentroids;i++){
            Circle centroid = circles.get(random.nextInt(circles.size()));
            mapRes.put(centroid,new ArrayList<>());
        }
        for(Circle circle : circles){

        }
        return null;
    }

    private Circle nearestCentroid(Set<Circle> centroids,Circle c){

        Iterator<Circle> itCentroid = centroids.iterator();
        Circle nerestCentroid  = itCentroid.next();
        //double nearestDistance =
        for(;itCentroid.hasNext();){
            Circle centroid = itCentroid.next();
            //double colorCente = P
            double euclidianDistance = Math.pow(c.getCenterX() - centroid.getCenterX(),2)
                    + Math.pow(c.getCenterY() - centroid.getCenterY(),2) ;
           // if()

        }
        return null;
    }

}