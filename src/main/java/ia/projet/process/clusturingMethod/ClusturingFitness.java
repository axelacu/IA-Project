package ia.projet.process.clusturingMethod;

import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ClusturingFitness {
    public double fitnessClusturing(Color[][] target, Color[][] individual,int maxX, int maxY) throws IllegalStateException{

        double rate  = 0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color ci = individual[i][j];
                Color ct = target[i][j];
                //System.err.println(c);
                double pixelRate = Population.probabilityPixel(ct,ci);
                if(pixelRate>1){
                    System.out.println("Error On rate : " + pixelRate);
                    System.exit(-1);
                }
                rate +=pixelRate;
            }
        }
        return rate/(double)(maxX*maxY);
    }
}
