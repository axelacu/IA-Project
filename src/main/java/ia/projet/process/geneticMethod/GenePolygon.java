package ia.projet.process.geneticMethod;

import ia.projet.process.ConvexPolygonIncrementation.GrahamScan;
import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import java.awt.Point;
import java.util.*;

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
               tx = random.nextInt((int) (Math.round(ConvexPolygon.max_X - x)));
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
           ty = random.nextInt((int) (Math.round(ConvexPolygon.max_Y - y)));
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
        return new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
    }
    //http://www.personal.kent.edu/~rmuhamma/Compgeometry/MyCG/ConvexHull/incrementCH.htm
    //INSPIRE FROM GRAHAM
    public GenePolygon mutationPoint(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        List<Double> points = newGen.getPoints();
        List<Point> ls = new ArrayList<>();
        for(int i = 0; i<points.size();i+=2){
            ls.add(new Point((int)Math.round(points.get(i)),(int)Math.round(points.get(i+1))));
        }
        double px = random.nextInt(ConvexPolygon.max_X);
        double py = random.nextInt(ConvexPolygon.max_Y);
        while(newGen.contains(px,py)){
            List<Point> l = new ArrayList<>(ls);
            px = random.nextInt(ConvexPolygon.max_X);
            py = random.nextInt(ConvexPolygon.max_Y);
            Point newPoint = new Point((int) px, (int) py);
            l.add(newPoint);

        }
        ls = GrahamScan.getConvexHull(ls);
        return newGen;
    }
    //https://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/
    List<Point2D> graham(List<Point2D> lp){
        System.out.println("beginin " + lp);
        List<Point2D> env = new ArrayList<>();
        Point2D pBasGauche = lp.get(0);
        int iBasGauche = 0;
        for( int i = 1; i < lp.size(); ++i) {
            Point2D p = lp.get(i);
            if(p.getY()> pBasGauche.getY() || (p.getY()==pBasGauche.getY() && p.getX() < pBasGauche.getX())){
                pBasGauche = p;
                iBasGauche = i;
            }
        }
        lp.remove(iBasGauche);
        System.out.println("Before sort " + lp);
        Collections.sort(lp, (x,y) ->  Math.atan2(x.getY(), x.getX())  < Math.atan2(y.getY(), y.getX()) ? -1 :
                Math.atan2(x.getY(), x.getX())  == Math.atan2(y.getY(), y.getX()) ? 0 : 1);
        lp.add(0, pBasGauche);
        System.out.println("lp after sort" +  lp);
        Point2D p0 = lp.get(lp.size()-1);
        Point2D p1 = lp.get(0);
        env.add(p0);env.add(p1);

        for( int i = 1; i < lp.size(); ++i) {
            Point2D p2 = lp.get(i);
            for( ; ;){
                p0 = env.get(env.size()-2);
                p1 = env.get(env.size()-1);
                // si ça tourne pas dans le bon sens on enlève
                if(pointSegment(p2, p1, p0) >= 0 && env.size()>2){
                    env .remove(env.size()-1);
                }
                else break;
            }
            env.add(p2);
        }
        System.out.println(" Env  : " + env);

        return env;
    }

    double pointSegment(Point2D p1, Point2D p2, Point2D p){
        return ((p2.getX()-p1.getX())*(p1.getY()-p1.getY()) - (p2.getY()-p2.getY())*(p.getX()-p1.getX()));
    }


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
            GenePolygon gene = new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
            genome.add(gene);
        }
        return genome;
    }
}
