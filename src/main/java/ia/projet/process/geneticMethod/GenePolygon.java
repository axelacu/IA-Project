package ia.projet.process.geneticMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GenePolygon extends ConvexPolygon{
    private int numberOfPoints;
    private int id;
    private static int  numberOfInstance=0;
    static int MAX_NUMBER_OF_POINTS = 3;
    /**
     * Generate à gene in that case the gene correspond to a polygon
     * @param numberOfPoints number of point that compound the polygon.
     */
    public GenePolygon(int numberOfPoints){
        super(numberOfPoints);
        this.numberOfPoints = numberOfPoints;
        this.id=numberOfInstance++;
    }

    public GenePolygon(int numberOfPoints, List<Double> points,Color c,double opacity){
        super();
        this.getPoints().addAll(points);
        this.numberOfPoints = numberOfPoints;
        this.setFill(c);
        this.setOpacity(opacity);
        //System.err.println("FAUUTE !");
    }

    /**
     * Change the scale of the polygone
     * @return return a polygone base on this polygone but with different scale.
     */
    public GenePolygon mutationScale(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        //Random random = new Random();
        List<Double> points = newGen.getPoints();
        Scale scale = new Scale();
        double min = 0.5;
        double max = 1.2;
        switch (random.nextInt(3)){
            case 0:
                scale = new Scale(min + (max - min) *random.nextDouble(),1,points.get(0),points.get(1));
                break;
            case 1:
                scale = new Scale(1,min + (max - min) *random.nextDouble(),points.get(0),points.get(1));
                break;
            case 2:
                scale=new Scale(min + (max - min) *random.nextDouble(),min + (max - min) *random.nextDouble(),points.get(0),points.get(1));
                break;
        }
        updatePoints(scale,newGen);
        return newGen;
    }

    /**
     * Set up the point of this polygone and update them.
     * @param transform transformation who will chant the points
     * @param gen gen before tranformation
     * @return return new polygone who undergo the transformation
     */
    private GenePolygon updatePoints(Transform transform, GenePolygon gen){
            int i;
            int numberOfpoints = gen.numberOfPoints;
            if(transform instanceof  Rotate) {
                i = 2;
                while (i < numberOfpoints) {
                    calculPoint(transform,gen,i);
                    i += 2;
                }
            }
        if(transform instanceof  Translate || transform instanceof Scale) {
            i = 0;
            while (i < numberOfpoints ) {
                calculPoint(transform,gen,i);
                i += 2;
            }
        }
        return gen;
    }

    /**
     * set up the new x & y point after transformation
     * @param transform
     * @param gen
     * @param i index of the point
     */
    private void calculPoint(Transform transform,GenePolygon gen,int i){
        double t1 = gen.getPoints().get(i);
        double t2 = gen.getPoints().get(i + 1);

        Point2D point2D = transform.transform(t1, t2);

        gen.getPoints().set(i, point2D.getX());
        gen.getPoints().set(i + 1, point2D.getY());
    }

    public int getGeneById(){
        return this.id;
    }

    public GenePolygon(GenePolygon genePolygon){
        super();
        this.getPoints().addAll(genePolygon.getPoints());
        this.numberOfPoints = genePolygon.getPoints().size();
        this.setFill(genePolygon.getFill());
        this.setOpacity(genePolygon.getOpacity());
        this.id=numberOfInstance++;
        //this.getTransforms().setAll(genePolygon.getTransforms());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!(obj instanceof GenePolygon)){
            return false;
        }
        GenePolygon gene = (GenePolygon) obj;
        List<Double> list = gene.getPoints();
        List<Double> list2 = this.getPoints();
        if(list2.size() != list.size())
            return false;

        for(int i = 0; i<list2.size() ;i++){
            if(list.get(i) != list2.get(i)){
                return false;
            }
        }
        if(!this.getFill().equals(((GenePolygon) obj).getFill()))
            return false;
        if (this.getOpacity() != ((GenePolygon) obj).getOpacity()){
            return false;
        }
        return true;
    }

    /**
     * made a rotation of the actual gene.
     * @return return a GenePolygon with a random rotation
     */
    public GenePolygon mutationRotation(){
        GenePolygon newGen = new GenePolygon(this);
        Random random = new Random();
        List<Double> point = newGen.getPoints();
        Rotate rotate = new Rotate(1+random.nextInt(30) ,point.get(0),point.get(1));
        //newGen.getTransforms().addAll(rotate);
        updatePoints(rotate,newGen);
        return newGen;
    }

    public GenePolygon mutationTranslate(){
        GenePolygon newGen = new GenePolygon(this);
        double x1=newGen.getPoints().get(0);
        double y1=newGen.getPoints().get(1);
        Random random = new Random();
        Translate translate=new Translate();
        switch (random.nextInt(3)){
            case 0:
                translate=new Translate(translationX(x1),0);
                break;
            case 1:
                translate=new Translate(0,translationY(y1));
                break;
            case 2:
                translate=new Translate(translationX(x1),translationY(y1));
                break;
        }
        //newGen.getTransforms().addAll(translate);
        updatePoints(translate,newGen);
        return newGen;
   }
   private double translationX(double x){
       Random random = new Random();
       int tx;
       if(random.nextBoolean()) {
                if(ConvexPolygon.max_X - x>0)
                    tx = random.nextInt((int) (Math.round(ConvexPolygon.max_X - x)));
                else
                    tx = 0;
       }
       else {
           if (x > 0)
               tx = -random.nextInt((int) (x));
           else
               tx = 0;
       }
       return tx;
   }
   private double translationY(double y){
       Random random = new Random();
       double ty;
       if(random.nextBoolean()) {
           if(ConvexPolygon.max_Y - y>0)
               ty = random.nextInt((int) (Math.round(ConvexPolygon.max_Y - y)));
           else
               ty = 0;

       }
       else {
           if (y > 0)
               ty = -random.nextInt((int) y);
           else
               ty = 0;
       }
       return ty;
   }


    public int getNumberOfPoints() {
        return numberOfPoints;
    }


    public GenePolygon mutation() {
        Random random = new Random();
        //TODO : verifier si on garge que 3 points
        return new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
    }
    //http://www.personal.kent.edu/~rmuhamma/Compgeometry/MyCG/ConvexHull/incrementCH.htm
    //INSPIRE FROM GRAHAM
    public GenePolygon mutationPoint(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        //System.out.println("BEFORE : " + newGen);

        List<Double> points = newGen.getPoints();
        List<Point> points1 = new ArrayList<>();
        for(int i = 0; i<points.size();i+=2){
            points1.add(new Point((int)Math.round(points.get(i)),(int)Math.round(points.get(i+1))));
        }
        double px = random.nextInt(ConvexPolygon.max_X);
        double py =random.nextInt(ConvexPolygon.max_Y);
        while(newGen.contains(px,py)){
            px = random.nextInt(ConvexPolygon.max_X);
            py =random.nextInt(ConvexPolygon.max_Y);
        }

        points1.add(new Point((int)Math.round(px),(int)Math.round(py)));
        List<Point> auxList;
        try {
            auxList = GrahamScan.getConvexHull(points1);
        }catch (Exception e){
            return newGen.mutation();
        }

        //remove duplicate point.
        auxList.remove(auxList.size()-1);
        newGen.getPoints().clear();
        for(Point p : auxList){
            newGen.addPoint(p.getX(), p.getY());
        }

        newGen.numberOfPoints = points1.size();
        //xSystem.out.println(points1.size());
        //System.out.println("AFTER : " + newGen);
        return newGen;
    }

    public GenePolygon mutationRemovePoint(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        List<Double> points = newGen.getPoints();
        if(points.size()/2 >8) {
            List<Point> points1 = new LinkedList<>();
            for (int i = 0; i < points.size(); i += 2) {
                points1.add(new Point((int) Math.round(points.get(i)), (int) Math.round(points.get(i + 1))));
            }
            points.remove(random.nextInt(points.size()));
            newGen.getPoints().clear();
            for (Point p : points1) {
                newGen.addPoint(p.getX(), p.getY());
            }
            return newGen;
        }else {
            return newGen;
        }
    }
    //https://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/


    public GenePolygon mutationFill(){
        GenePolygon newGen = new GenePolygon(this);
        Color fill = (Color) newGen.getFill();
        Random ran = new Random();
        double r;
        double g;
        double b;
        if(ran.nextBoolean()){
            r = fill.getRed() + (1 - fill.getRed()) * ran.nextDouble();
        }else {
            r = (fill.getRed()) * ran.nextDouble();
        }
        if(ran.nextBoolean()){
            g = fill.getGreen() + (1 - fill.getGreen()) * ran.nextDouble();
        }else {
            g = (fill.getGreen()) * ran.nextDouble();
        }
        if(ran.nextBoolean()){
            b = fill.getBlue() + (1 - fill.getBlue()) * ran.nextDouble();
        }else {
            b = (fill.getGreen()) * ran.nextDouble();
        }

        newGen.setFill(Color.color(r, g, b));
        return newGen;
    }
    public GenePolygon mutationOpacity(){
        GenePolygon newGen = new GenePolygon(this);
        Random ran = new Random();
        double opacity = newGen.getOpacity();
        double newOpacity ;
        if(ran.nextBoolean()){
            newOpacity = opacity + (1 - opacity) * ran.nextDouble();
        }else{
            newOpacity = opacity * ran.nextDouble();
        }
        newGen.setOpacity(newOpacity);
        return newGen;
    }

    static ArrayList<GenePolygon> generateGenome(int numberOfGene){
        Random random = new Random();
        ArrayList<GenePolygon> genome = new ArrayList<>();
        for(int i = 0;i<numberOfGene;i++){
            //TODO : Verifier si on garde que 3 polygones
            GenePolygon gene = new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
            genome.add(gene);
        }
        return genome;
    }
}
