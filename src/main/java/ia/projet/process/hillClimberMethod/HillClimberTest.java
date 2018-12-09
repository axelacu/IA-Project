package ia.projet.process.hillClimberMethod;

import ia.projet.process.Context;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Mutation;
import ia.projet.process.geneticMethod.Population;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class HillClimberTest extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        defineContext();

        Population.target = Context.target;
        Population population = new Population(1,50);
        Population.setMutationRate(1);
        IndividualSolution individual = population.generateIndividual();
        population.setBestIndividual(HillClimber.hillClimber(individual,Population.target,Context.HillnumberIteration));
        population.drawBestIndividual();

    }


    private void defineContext() {
        Scanner sc = new Scanner(System.in);
        Context.isExistingFile();
        System.out.println("In which file do you want your test to appear?");
        String file = sc.nextLine();
        Context.out = file;
        System.out.println("How much iteration do you want");
        System.out.print("Enter number  : ");
        int number = Integer.parseInt(sc.nextLine());
        Context.HillnumberIteration = number;
    }
}
