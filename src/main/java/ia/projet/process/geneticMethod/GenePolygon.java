package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.List;
import java.util.Random;

public class GenePolygon extends ConvexPolygon{
    private int numberOfPoints;
    private Paint color;

    /**
     * Generate à gene in that case the gene correspond to a polygon
     * @param numberOfPoints number of point that compound the polygon.
     */
    public GenePolygon(int numberOfPoints){
        super(numberOfPoints);
        this.numberOfPoints = numberOfPoints;
        this.color = this.getFill();

    }

    public int getNumberOfPoint(){
        return this.numberOfPoints;
    }

    public GenePolygon(GenePolygon genePolygon){
        this.getPoints().addAll(genePolygon.getPoints());
        this.numberOfPoints = genePolygon.getPoints().size();
        this.setFill(genePolygon.getFill());
        this.setOpacity(genePolygon.getOpacity());
    }


    public GenePolygon mutationTranslate() {
        //System.out.println("MUTATION");
        GenePolygon newGen = new GenePolygon(this);
        Random random = new Random();

        Translate translate = new Translate();
        double intervalX =ConvexPolygon.max_X - newGen.getTranslateX();
        double intervalY =ConvexPolygon.max_Y - newGen.getTranslateY();
        translate.setX(random.nextInt((int) intervalX));
        translate.setY(random.nextInt((int) intervalY));
        newGen.getTransforms().addAll(translate);

        return newGen;
    }

    public GenePolygon mutationRotation(){
        GenePolygon newGen = new GenePolygon(this);
        Random random = new Random();
        List<Double> point = newGen.getPoints();
        Rotate rotate = new Rotate(1+random.nextInt(361),point.get(0),point.get(1));
        newGen.getTransforms().addAll(rotate);
        return newGen;
    }
    public int getNumberOfPoints() {
        return numberOfPoints;
    }


    public GenePolygon mutation() {
        Random random = new Random();
        return new GenePolygon(3 + random.nextInt(8));
    }

    public GenePolygon mutationPoint(){
        Random random = new Random();
        GenePolygon newGen = new GenePolygon(this);
        int x = random.nextInt(ConvexPolygon.max_X);
        int y= random.nextInt(ConvexPolygon.max_Y);
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
}
