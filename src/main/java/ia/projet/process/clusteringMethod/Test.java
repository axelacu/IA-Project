package ia.projet.process.clusteringMethod;

import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.hillClimberMethod.HillClimber;
import ia.projet.process.imageProcessing.ConvexPolygon;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        String pathImage = "monaLisa-200.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        BufferedImage bi = ImageIO.read(new File(pathImage));
        int maxX = bi.getWidth();
        int maxY = bi.getHeight();
        ConvexPolygon.max_X=  maxX;
        ConvexPolygon.max_Y = maxY;
        System.out.println(maxX + " " + maxY );
        Population population = new Population();
        Population.target = target;
        Population.setMutationRate(0.07);
        int sizeGenome = 50;
        population.setMAX_NUMBER_OF_GENES_BY_INDIVIDUALS(sizeGenome);
        for(int i = 0; i<30;i++){
            System.out.println("N° of individuals  : " + i + ", best : " + population.getBestIndividual().getFitness());
            IndividualSolution individualSolution = Clustering.createIndividual(target,sizeGenome,maxX,maxY);
            population.add(individualSolution);
            if(i%5==0){
                population.drawBestIndividual();
            }
        }
        population.drawBestIndividual();
        /*

        ReproductionImage rep = new ReproductionImage();
        //algorithme genetic.
        double bestFit = 0;
        for(int i = 0; i<1000;i++){
            System.out.println("\nGeneration : " + (i) + " " + population);
            System.out.println("\t" + population.statistics());
            population.decreaseSort();
            population.setNewPopulation(rep.nextGeneration(population));
            if(i%50 == 0){
                population.drawBestIndividual();
            }
        }*/
        //hill climbing
        Population.setMutationRate(1);
        IndividualSolution individualSolution = HillClimber.hillClimber(population.getBestIndividual(),target);
        //drawing
        population.setBestIndividual(individualSolution);
        population.drawBestIndividual();
        Platform.exit();
    }
    public static void main(String[] arg){
        launch(arg);
    }
}
