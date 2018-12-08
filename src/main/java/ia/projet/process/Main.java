package ia.projet.process;

import ia.projet.process.geneticMethod.*;
import ia.projet.process.hillClimberMethod.HillClimber;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

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
        Random random = new Random();
        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(initialPopulation,numberOfGeneByIndividual);
        population.initialPopulation(initialPopulation);
        ReproductionImage rep = new ReproductionImage();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<90000; i++ ){
            System.out.println("\nGeneration : " + (i) + " " + population);
            System.out.println("\t" + population.statistics());
            population.decreaseSort();
            //TODO : Verifier
            population.setNewPopulation(rep.nextGeneration(population));
            /*
            if(population.getBestIndividual().getFitness()>0.66){
                IndividualSolution in = HillClimber.hillClimber(population.get(population.size()-1),target);
                population.removeIndividual(population.size()-1);
                population.add(in);
            }*/
            //Selection.reaper4(population);
            if(i%5 == 0){
                population.drawBestIndividual();
            }
            /*
            if(population.statistics().contains("sigma : 0")) {
                population.drawBestIndividual();
                System.out.println("\nGeneration : " + (i) + " " + population);
                System.out.println("\t" + population.statistics());
                System.exit(-1);
            }*/
        }
        System.out.println("Hill Climbing Methode : ");
        population.setBestIndividual(HillClimber.hillClimber(population.getBestIndividual(),target));
        ////// PLUSIEUR POP
        /**
         //Population precedentPop = null;
        for(int i = 2; i <numberOfGeneByIndividual; i++){
            System.out.println("Generation : " + (i));
            //System.out.println("\t" + population.statistics());

            //population.severalStranger(10);
            TheardPop theardPop = new TheardPop(100,i,100,10);
            if(population != null){
                theardPop.addOldPop(population);
                //System.out.println(precedentPop.size());
            }
            theardPop.run();
            //System.out.println(theardPop.toString());
            //theardPop.population.drawBestIndividual();
            population = theardPop.population;
            //System.out.println("  " + precedentPop);
            //System.out.println("\t" + precedentPop.statistics() );
        } */
        System.out.println("  " + population);
        System.out.println("\t" + population.statistics() );
        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution of method callMethod() is :"+ (endTime-startTime));
        population.drawBestIndividual();
        Platform.exit();
    }

    class TheardPop implements Runnable {
        int numberOfIndividualBygen;
        int numberStrangerByGen;
        Population population;

        public TheardPop(int initialPopulation,int numberOfGeneByIndividuals,int numberOfIndividualBygen, int numberStrangerByGen){
            this.population = new Population(initialPopulation,numberOfGeneByIndividuals);
            this.numberOfIndividualBygen = numberOfIndividualBygen;
            this.numberStrangerByGen =numberStrangerByGen;

        }
        @Override
        public void run() {
            ReproductionImage rep = new ReproductionImage();
            for(int i = 0;i<301;i++){
                population.severalStranger(numberStrangerByGen);
                //rep.reproduction3(population);
                Selection.reaper4(population);
                if(i%150 == 0){
                    System.out.println("  " + population);
                    System.out.println("\t" + population.statistics() );
                    population.drawBestIndividual();
                }
            }
            //System.out.println(population);
        }

        @Override
        public String toString() {
            String res = " Pop : " + population + "\n" +
                    "\t" + population.statistics();
            return res;
        }
        public void addOldPop(Population population){
            this.population.addPopulation(population.getPopulation());
        }
    }
    static void main(String[] args){launch(args); }
}
