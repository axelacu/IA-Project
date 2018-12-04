package ia.projet.process.clusturingMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.imageProcessing.ConvexPolygon;
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

    public static List<List<Point>> k_Means (List<Circle> circles, int numberOfPolygones,int numberOfIteration){
        List<Circle> center_cluster=new ArrayList<>();
        Random random=new Random();
        int f=0;
        do {
            //calcul des points aléatoire

            for (int i = 0; i < numberOfPolygones; i++) {
                Circle point = new Circle();
                point.setCenterX(random.nextInt(ConvexPolygon.max_X));
                point.setCenterY(random.nextInt(ConvexPolygon.max_Y));
                center_cluster.add(point);
            }
            HashMap<Circle, List<Circle>> map = new HashMap<>();
            if (f == 0) {
                for (Circle points_aleatoire : center_cluster) {
                    map.put(points_aleatoire, new ArrayList<Circle>());

                }
            }

            for (Circle circle : circles) {
                Circle cluster = new Circle();
                for (Circle points_aleatoire : map.keySet()) {
                    double distance = 1000000;
                    double myDist = Clusturing.distanceEuclidienne(circle, points_aleatoire);
                    if (myDist < distance) {
                        distance = myDist;
                        cluster = points_aleatoire;
                    }
                }
                map.get(cluster).add(circle);

            }
            HashMap<Circle, List<Circle>> newMap = new HashMap<>();
            // calcul des barycentres
            for (Circle circle : map.keySet()) {
                Circle cercle = barycentre(map.get(circle));
                List<Circle> tmp = map.get(circle);
                newMap.put(cercle, map.get(circle));
            }
            map = newMap;
        } while(f<numberOfIteration);




        return null;

    }

    public static double distanceEuclidienne(Circle c1, Circle c2){
        double a=c1.getCenterX()-c1.getCenterY();
        double b=c2.getCenterX()-c2.getCenterY();
        double c=Population.probabilityPixel((Color)c1.getFill(),(Color)c2.getFill())*100;
        return Math.sqrt((a*a)+(b*b)+(c*c));
    }

    public static Circle barycentre(List<Circle> circles){
        double sum_x=0;
        double sum_y=0;
        for(Circle circle:circles){
            sum_x+=circle.getCenterX();
            sum_y+=circle.getCenterY();
        }
        double x= (1/ circles.size())*(sum_x);
        double y=((1/circles.size())*sum_y);
        Circle retour=new Circle();
        retour.setCenterX(x);
        retour.setCenterY(y);
        return retour;
    }

}