package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.paint.Paint;

public class GenePolygon extends ConvexPolygon implements Gene{
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

    public GenePolygon(GenePolygon genePolygon){}

    /**
     * Return gene who should mutate
     * TODO : Improve the mutation.
     * @return a gene mutate
     */
    @Override
    public GenePolygon mutation() {
        return new GenePolygon(numberOfPoints);
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

}
