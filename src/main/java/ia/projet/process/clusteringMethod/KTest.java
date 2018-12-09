package ia.projet.process.clusteringMethod;

import ia.projet.process.Context;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.hillClimberMethod.HillClimber;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

public class KTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        defineContext();
        IndividualSolution bestIndividual = new IndividualSolution();
        Population population = new Population();

        for(int i = 0; i<Context.ClustNumberOfTries;i++){
            System.out.println("Iteration N° : " + i);
            IndividualSolution individualSolution =
                    Clustering.createIndividual(Context.target,Context.genomSize,Context.maxX,Context.maxY);
            individualSolution.setFitness(population.fitness2(Context.target,individualSolution));
            if(bestIndividual.getFitness()<individualSolution.getFitness()){
                bestIndividual = individualSolution;
                population.setBestIndividual(bestIndividual);
                System.out.println("Best individual : " + bestIndividual);
            }
        }

        population.drawBestIndividual();

        System.out.println("-----Fin de l'execution-----");
    }
    public static void main(String[] arg){
        launch(arg);
    }

    public void defineContext(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Context.isExistingFile();
        System.out.print("Do you want to use default settings : " +
                "\n   1. for Yes" +
                "\n   2. for Non" + "\n Answer : ");

        String line = scanner.nextLine();
        int answer = Integer.parseInt(line);
        if(answer==1){
            fillContext();
        }
    }

    public static void fillContext(){
        boolean condition = true;
        Scanner scanner = new Scanner(System.in);
        String line;
        do{
            try {
                System.out.print("Genome size (exemple, 50): ");
                line = scanner.nextLine();
                Context.genomSize = Integer.parseInt(line);
                System.out.print("How many tries to find best clust: (advice : less that 100)  ");
                line = scanner.nextLine();
                Context.ClustNumberOfTries = Integer.parseInt(line);
                condition = false;
            }catch (Exception e){
                System.err.println("You made a mistake please ty again");
            }
        }while(condition);
    }
}
