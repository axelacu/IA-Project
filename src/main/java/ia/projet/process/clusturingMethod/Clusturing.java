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
            Circle circle = new Circle(x,y,0);
            circle.setFill(color);
            circles.add(circle);
        }
        return circles;
    }

    public static Polygon creationPolygones(List<Circle> circles,Color color){
        Polygon polygon = new Polygon();
        polygon.setFill(color);
        List<Point> points = new ArrayList<>();
        for (Circle circle : circles){
            points.add(new Point((int)circle.getCenterX(),(int)circle.getCenterY()));
        }

        List<Point> hull = GrahamScan.getConvexHull(points);
        for(int i = 0; i<hull.size()-1;i++){
            polygon.getPoints().addAll(hull.get(i).getX(),hull.get(i).getY());
        }
        return polygon;
    }

    public static Map<Circle,List<Circle>> kMeans(ArrayList<Circle> circles,int numberOfCentroids){
        Map<Circle,List<Circle>> mapRes = new HashMap<>(numberOfCentroids);

        for(int i = 0; i<numberOfCentroids;i++){
            Circle centroid = circles.get(random.nextInt(circles.size()));
            mapRes.put(centroid,new ArrayList<>());
        }
        boolean condition = false;
        while(!condition) {
            condition = true;
            System.out.println("Debut du while");
            for (Circle circle : circles) {
                Circle centroid = nearestCentroid(mapRes.keySet(), circle);
                mapRes.get(centroid).add(circle);
            }
            for (Iterator<Circle> it = mapRes.keySet().iterator(); it.hasNext(); ) {
                Circle centroid = it.next();
                Circle newCentroid = barycentre(mapRes.get(centroid));
                if (centroid.getCenterX()!=newCentroid.getCenterX() && centroid.getCenterY()!=newCentroid.getCenterY()) {
                    centroid.setCenterX(newCentroid.getCenterX());
                    centroid.setCenterY(newCentroid.getCenterY());
                    mapRes.get(centroid).clear();
                    //centroid.setFill(centroid.getFill());
                    System.out.println("\t i'm false");
                    condition = false;
                }
            }
        }
        /*for(Iterator<Circle> it = mapRes.keySet().iterator();it.hasNext();){
            System.out.println(mapRes.get(it.next()).size());
        }*/
        return mapRes;
    }

    private static Circle nearestCentroid(Set<Circle> centroids,Circle c){
        Iterator<Circle> itCentroid = centroids.iterator();
        Circle nearestCentroid  = itCentroid.next();
        double nearestDistance = distanceEuclidean(nearestCentroid,c);
        for(;itCentroid.hasNext();){
            Circle centroid = itCentroid.next();
            double euclidianDistance = distanceEuclidean(centroid,c);
            if(euclidianDistance<nearestDistance){
                nearestDistance = euclidianDistance;
                nearestCentroid = centroid;
            }
        }
        return nearestCentroid;
    }

    public static HashMap<Circle,List<Circle>> k_Means (List<Circle> circles, int numberOfCentroids,int numberOfIteration){
        List<Circle> center_cluster=new ArrayList<>();
        Random random=new Random();
        int iterations=0;
        HashMap<Circle, List<Circle>> map = new HashMap<>();

        do {
            //calcul des points aléatoire
            if(iterations==0) {
                for (int i = 0; i < numberOfCentroids; i++) {
                    Circle point = circles.get(random.nextInt(circles.size()));
                    center_cluster.add(point);
                }
            }
            if (iterations == 0) {
                for (Circle points_aleatoire : center_cluster) {
                    map.put(points_aleatoire, new ArrayList<Circle>());

                }
            }

            for (Circle circle : circles) {
                Circle cluster = new Circle();
                for (Circle points_aleatoire : map.keySet()) {
                    double distance = 1000000000;
                    double myDist = Clusturing.distanceEuclidean(circle, points_aleatoire);
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
                newMap.put(cercle, new ArrayList<>());
            }
            map = newMap;
            iterations++;
        } while(iterations<numberOfIteration);


        return map;

    }

    /**
     * calculate euclidian distance from  two vector (x,y,R,G,V).
     * @param c1 color1
     * @param c2 color2
     * @return
     */
    public static double distanceEuclidean(Circle c1, Circle c2){
        double a=Math.pow(c1.getCenterX()-c2.getCenterX(),2);
        double b=Math.pow(c1.getCenterY()-c2.getCenterY(),2);
        Color color1 = (Color) c1.getFill();
        Color color2 = (Color) c2.getFill();
        double c = Math.pow((color1.getBlue() -color2.getBlue())*100,2)
                +Math.pow((color1.getRed()-color2.getRed()) * 100,2)
                +Math.pow((color1.getGreen()-color2.getGreen())*100,2);
        return Math.sqrt((a)+(b)+(c));
    }

    public static Circle barycentre(List<Circle> circles){
        double sum_x=0;
        double sum_y=0;
        double sum_r=0;
        double sum_g =0;
        double sum_b =0;
        double n = circles.size();
        for(Circle circle:circles){
            Color color = (Color) circle.getFill();
            sum_x+=circle.getCenterX();
            sum_y+=circle.getCenterY();
            //sum_r+=color.getRed();
            //sum_g+=color.getGreen();
            //sum_b+=color.getBlue();
        }
        double x= (sum_x)/n ;
        double y= sum_y/n;
        double r = sum_r/n;
        double g = sum_g/n;
        double b = sum_b/n;

        Circle retour=new Circle();
        retour.setCenterX((int) Math.round(x));
        retour.setCenterY((int) Math.round(y));
        //retour.setFill(Color.color(r,g,b));

        return retour;
    }

}