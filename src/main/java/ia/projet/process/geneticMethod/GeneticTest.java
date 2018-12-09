package ia.projet.process.geneticMethod;

import ia.projet.process.Context;
import ia.projet.process.hillClimberMethod.HillClimber;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

public class GeneticTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        /// Defining context
        defineContext();
        Population.target = Context.target;
        Population population = new Population(Context.populationSize,Context.genomSize);
        population.initialPopulation(Context.populationSize);
        ReproductionImage rep = new ReproductionImage();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<Context.NumberOfGeneration; i++ ){
            System.out.println("\nGeneration : " + (i) + " " + population);
            System.out.println("\t" + population.statistics());
            if(Context.SUS == 0) {
                population.decreaseSort();
                population.setNewPopulation(rep.nextGeneration(population));
            }else{
                population.addPopulation(rep.nextGeneration(population));
                population=Selection.SUS(population,Context.populationSize);
            }
            if(i%100 == 0){
                population.drawBestIndividual();
            }
        }
        System.out.println("  " + population);
        System.out.println("\t" + population.statistics() );
        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution of method callMethod() is :"+ (endTime-startTime));
        population.drawBestIndividual();
        System.out.println("Execution finish ! ");
    }
    public static void main(String[] args){
        launch(args);
    }

    private void defineContext(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Context.isExistingFile();
        System.out.print("Do you want to use default settings : " +
                        "\n   1. Yes" +
                        "\n   2. Non" + "\n Answer : ");
        String line = scanner.nextLine();
        int answer = Integer.parseInt(line);
        if(answer!=1){
            boolean condition = true;
            do{
                try {
                    System.out.print("Wich rate (exemple; 0.07): ");
                    line = scanner.nextLine();
                    Context.mutationRate = Double.parseDouble(line);
                    System.out.print("Genome size (exemple, 50): ");
                    line = scanner.nextLine();
                    Context.genomSize = Integer.parseInt(line);
                    System.out.print("Population size (exemple, 100): ");
                    line = scanner.nextLine();
                    Context.populationSize = Integer.parseInt(line);
                    System.out.print("Do you want to use SUS method  (1 . YES, 2. NON): ");
                    line = scanner.nextLine();
                    Context.SUS = Integer.parseInt(line);
                    System.out.print("How many generation : 2000 ");
                    line = scanner.nextLine();
                    Context.NumberOfGeneration = Integer.parseInt(line);
                    condition = false;
                }catch (Exception e){
                    System.err.println("You made a mistake please ty again");
                }
            }while(condition);
        }
    }
}
