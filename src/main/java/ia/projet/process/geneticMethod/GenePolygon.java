package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    public GenePolygon(int numberOfPoints, Double[] points){
        super();
        this.getPoints().addAll(points);
        this.numberOfPoints = numberOfPoints;
        //this.setOpacity(genePolygon.getOpacity());
        System.err.println("FAUUTE !");
    }

    public GenePolygon mutationScale(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        //Random random = new Random();
        List<Double> points = newGen.getPoints();

        Scale scale=new Scale(random.nextDouble(),random.nextDouble(),points.get(0),points.get(1));
        updatePoints(scale,newGen);
        return newGen;
    }

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
        //TODO: voir si enlever le transform
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

    public GenePolygon mutationRotation(){
        GenePolygon newGen = new GenePolygon(this);
        Random random = new Random();
        List<Double> point = newGen.getPoints();
        Rotate rotate = new Rotate(1+random.nextInt(361) ,point.get(0),point.get(1));
        //newGen.getTransforms().addAll(rotate);
        updatePoints(rotate,newGen);
        return newGen;
    }

    public GenePolygon mutationTranslate(){
        GenePolygon newGen = new GenePolygon(this);
        Random random = new Random();
        double x1=newGen.getPoints().get(0);
        double y1=newGen.getPoints().get(1);
        double tx ;
        double ty ;
        if(random.nextBoolean()) {
            tx = random.nextInt((int) (ConvexPolygon.max_X - x1));
        }
        else {
            if (x1 > 0)
                tx = -random.nextInt((int) (x1));
            else
                tx = 0;
        }
        if(random.nextBoolean()) {
            ty = random.nextInt((int) (ConvexPolygon.max_Y - y1));
        }
        else {
            if (y1 > 0)
                ty = -random.nextInt((int) y1);
            else
                ty = 0;
        }
        Translate translate=new Translate(tx,ty);
        //newGen.getTransforms().addAll(translate);
        updatePoints(translate,newGen);
        return newGen;
   }


    public int getNumberOfPoints() {
        return numberOfPoints;
    }


    public GenePolygon mutation() {
        Random random = new Random();
        return new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
    }
    //http://www.personal.kent.edu/~rmuhamma/Compgeometry/MyCG/ConvexHull/incrementCH.htm
    //pseudo
    public GenePolygon mutationPoint(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        double x = random.nextInt(ConvexPolygon.max_X);
        double y= random.nextInt(ConvexPolygon.max_Y);

        while(newGen.contains(x,y)){
            x = random.nextInt(ConvexPolygon.max_X);
            y = random.nextInt(ConvexPolygon.max_Y);
        }
        newGen.addPoint(x,y);
        return newGen;
    }

    public GenePolygon mutationFill(){
        GenePolygon newGen = new GenePolygon(this);

        Random gen = new Random();
        int r = gen.nextInt(256);
        int g = gen.nextInt(256);
        int b = gen.nextInt(256);
        newGen.setFill(Color.rgb(r, g, b));
        return newGen;
    }
    public GenePolygon mutationOpacity(){
        GenePolygon newGen = new GenePolygon(this);
        Random gen = new Random();
        this.setOpacity(gen.nextDouble());
        return newGen;
    }

    static ArrayList<GenePolygon> generateGenome(int numberOfGene){
        Random random = new Random();
        ArrayList<GenePolygon> genome = new ArrayList<>();
        for(int i = 0;i<numberOfGene;i++){
            GenePolygon gene = new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
            genome.add(gene);
        }
        return genome;
    }
}
