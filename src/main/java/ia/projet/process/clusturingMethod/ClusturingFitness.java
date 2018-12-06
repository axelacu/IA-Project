package ia.projet.process.clusturingMethod;

import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ClusturingFitness {
    public static double fitnessClusturing(Color[][] target, PixelReader individual,int maxX, int maxY) throws IllegalStateException{

        double rate  = 0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color ci = individual.getColor(i,j);
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
    public static double fitnessClusturingProf(Color[][] target, PixelReader individual,int maxX, int maxY) throws IllegalStateException{
        double res  = 0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color ci = individual.getColor(i,j);
                Color ct = target[i][j];
                //System.err.println(c);
                res += Math.pow(ci.getBlue()-ct.getBlue(),2)
                        +Math.pow(ci.getRed()-ct.getRed(),2)
                        +Math.pow(ci.getGreen()-ct.getGreen(),2);

            }
        }
        return Math.sqrt(res);
    }
}
