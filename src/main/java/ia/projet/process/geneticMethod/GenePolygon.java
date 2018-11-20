package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.paint.Paint;

public class GenePolygon extends ConvexPolygon implements Gene{
    private int numberOfPoints;
    private Paint color;
    //TODO : Add arrayPoints. ajouter

    public GenePolygon(int numberOfPoints){
        super(numberOfPoints);
        this.numberOfPoints = numberOfPoints;
        color = this.getFill();
    }

    public GenePolygon(GenePolygon genePolygon){}

    /**
     * Return gene who should mutate
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
