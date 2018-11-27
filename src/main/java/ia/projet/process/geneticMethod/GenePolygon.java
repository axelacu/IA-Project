package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenePolygon extends ConvexPolygon{
    private int numberOfPoints;
    private int id;
    private static int  numberOfInstance=0;
    static int MAX_NUMBER_OF_POINTS = 5;
    /**
     * Generate à gene in that case the gene correspond to a polygon
     * @param numberOfPoints number of point that compound the polygon.
     */
    public GenePolygon(int numberOfPoints){
        super(numberOfPoints);
        this.numberOfPoints = numberOfPoints;
        this.id=numberOfInstance++;
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
        Rotate rotate = new Rotate(1+random.nextInt(361),point.get(0),point.get(1));
        newGen.getTransforms().addAll(rotate);
        return newGen;
    }
    public int getNumberOfPoints() {
        return numberOfPoints;
    }


    public GenePolygon mutation() {
        Random random = new Random();
        return new GenePolygon(3 + random.nextInt(MAX_NUMBER_OF_POINTS));
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
