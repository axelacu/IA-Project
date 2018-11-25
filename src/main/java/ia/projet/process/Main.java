package ia.projet.process;

import ia.projet.process.geneticMethod.*;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //////////
        int initialPopulation = 100;
        int numberOfGeneByIndividual = 40;
        Population.setMutationRate(0.1);
        Selection.setNumberOfIndividualByGeneration(100);

        ///////
        Random random = new Random();
        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(initialPopulation,numberOfGeneByIndividual);
        ReproductionImage rep = new ReproductionImage();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i <10000000; i++){
            System.out.println("Generation : " + i + " Pop : " + population);
            System.out.println("\t" + population.statistics());
            rep.reproduction(population);
            Selection.reaper(population);
            int actualIndex = i;
            Thread th = new Thread(){
                @Override
                public void run() {
                    if(actualIndex%100 == 0) {
                        population.drawBestIndividual();
                    }
                }
            };
            th.run();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution of method callMethod() is :"+ (endTime-startTime));
        population.drawBestIndividual();
        Platform.exit();

    }
    static void main(String[] args){launch(args); }
}
