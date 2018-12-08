package ia.projet.process;

import ia.projet.process.geneticMethod.*;
import ia.projet.process.hillClimberMethod.HillClimber;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //////////
        int initialPopulation = 100;
        int numberOfGeneByIndividual = 50;
        Population.setMutationRate(0.07);
        //Selection.setNumberOfIndividualByGeneration(100);

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        String pathImage = "monaLisa-100.jpg";
        Context.setTarget(pathImage);
        Color[][] target = Context.target;
        Population.target = target;
        Population population = new Population(initialPopulation,numberOfGeneByIndividual);
        population.initialPopulation(initialPopulation);
        ReproductionImage rep = new ReproductionImage();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<90000; i++ ){
            System.out.println("\nGeneration : " + (i) + " " + population);
            System.out.println("\t" + population.statistics());
            population.decreaseSort();
            population.setNewPopulation(rep.nextGeneration(population));
            if(i%100 == 0){
                population.drawBestIndividual();
            }
        }
        System.out.println("Hill Climbing Methode : ");
        population.setBestIndividual(HillClimber.hillClimber(population.getBestIndividual(),target));
        System.out.println("  " + population);
        System.out.println("\t" + population.statistics() );
        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution of method callMethod() is :"+ (endTime-startTime));
        population.drawBestIndividual();
        Platform.exit();
    }
    static void main(String[] args){launch(args); }
}
