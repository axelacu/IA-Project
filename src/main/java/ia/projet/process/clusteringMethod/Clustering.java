package ia.projet.process.clusteringMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.IndividualSolution;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Clustering {

    static Random random = new Random();

    public static GenePolygon geneClust(Color[][] target,int genomeSize, int maxX, int maxY){
        ArrayList<Polygon> polygons = new ArrayList<>();
        ArrayList<Circle> circles = Clustering.pixelCircle(target,maxX,maxY);
        Map<Circle,List<Circle>> mapCentroids = Clustering.kMeans(circles,genomeSize);

        for(Iterator<Circle> it = mapCentroids.keySet().iterator();it.hasNext();){
            Circle key = it.next();
            Color color =(Color) key.getFill();
            //System.out.println(mapCentroids.get(key).size());
            if(mapCentroids.get(key).size()>=3) {
                Polygon polygon = Clustering.creationPolygones(mapCentroids.get(key), color);
                polygons.add(polygon);
            }
        }
        Polygon polygon = polygons.get(random.nextInt(polygons.size()));
        List<Double> points = polygon.getPoints();
        return new GenePolygon(points.size()/2,points,(Color) polygon.getFill(),polygon.getOpacity());
    }

    public static IndividualSolution createIndividual(Color[][] target,int genomeSize, int maxX, int maxY){
        //ArrayList<Circle> circles = Clustering.creationPoints(target,maxX,maxY,maxX*maxY);
        ArrayList<Circle> circles;
        if(random.nextBoolean())
            circles = Clustering.pixelCircle(target,maxX,maxY);
        else
            circles = Clustering.creationPoints(target,maxX,maxY,(int) Math.round(maxX*maxY*0.9));
        ArrayList<Polygon> polygons = new ArrayList<>();

        Map<Circle,List<Circle>> mapCentroids = Clustering.kMeans(circles,genomeSize);
        for(Iterator<Circle> it = mapCentroids.keySet().iterator();it.hasNext();){
            Circle key = it.next();
            Color color =(Color) key.getFill();
            //System.out.println(mapCentroids.get(key).size());
            if(mapCentroids.get(key).size()>=3) {
                try {
                    Polygon polygon = Clustering.creationPolygones(mapCentroids.get(key), color);
                    polygons.add(polygon);
                }catch (Exception e){

                }
            }
        }
        while (polygons.size()<genomeSize){
            polygons.add(0,new GenePolygon(3));
        }
        IndividualSolution individualSolution = new IndividualSolution();
        ArrayList<GenePolygon> genes = new ArrayList<>();
        for (Polygon polygon:
                polygons) {
            List<Double> points = polygon.getPoints();
            genes.add(new GenePolygon(points.size()/2,points,(Color) polygon.getFill(),polygon.getOpacity()));
        }
        individualSolution.setGenome(genes);
        return individualSolution;
    }



    public static ArrayList<Circle> pixelCircle(Color[][] target,int maxX,int maxY){
        ArrayList<Circle> circles = new ArrayList<>();
        for (int i=0;i<maxX-1;i++){
            for (int j=0;j<maxY-1;j++){
                Circle c = new Circle(i,j,0);
                c.setFill(target[i][j]);
                circles.add(c);
            }
        }
        return circles;
    }

    public static ArrayList<Circle> creationPoints(Color[][] target, int maxX, int maxY,int nbCircle){
        ArrayList<Circle> circles = new ArrayList<>();
        for(int i = 0; i<nbCircle;i++){
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            Color color = target[x][y];
            Circle circle;
            circle = new Circle(x, y, 0);
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
            //System.out.println("Debut du while");
            for (Circle circle : circles) {
                Circle centroid = nearestCentroid(mapRes.keySet(), circle);
                mapRes.get(centroid).add(circle);
            }

            for (Iterator<Circle> it = mapRes.keySet().iterator(); it.hasNext(); ) {
                Circle centroid = it.next();
                Circle newCentroid = barycentre(mapRes.get(centroid));
                if (centroid.getCenterX()!=newCentroid.getCenterX() && centroid.getCenterY()!=newCentroid.getCenterY()) {
                    mapRes.get(centroid).clear();
                    centroid.setCenterX(newCentroid.getCenterX());
                    centroid.setCenterY(newCentroid.getCenterY());
                    centroid.setFill(newCentroid.getFill());
                    //System.out.println("\t i'm false");
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
        double cr = Math.pow((color1.getBlue() -color2.getBlue())*100,2);
        double cg = Math.pow((color1.getRed()-color2.getRed())*100,2);
        double cb = Math.pow((color1.getGreen()-color2.getGreen()*100),2);
        return Math.sqrt((a)+(b)+(cr)+(cg)+(cb));
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
            sum_r+=color.getRed();
            sum_g+=color.getGreen();
            sum_b+=color.getBlue();
        }
        double x= (sum_x)/n ;
        double y= sum_y/n;
        double r = sum_r/n;
        double g = sum_g/n;
        double b = sum_b/n;

        Circle retour=new Circle();
        retour.setCenterX((int) Math.round(x));
        retour.setCenterY((int) Math.round(y));
        retour.setFill(Color.color(r,g,b));

        return retour;
    }

}