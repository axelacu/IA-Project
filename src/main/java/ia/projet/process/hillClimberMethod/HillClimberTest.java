package ia.projet.process.hillClimberMethod;

import ia.projet.process.Context;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Population;
import javafx.application.Application;
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


    private static void defineContext() {
        Context.isExistingFile();
        Context.settingOut();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to use default settings : " +
                "\n   1. for non" +
                "\n   2. for yes" + "\n Answer : ");
        String line = scanner.nextLine();
        int answer = Integer.parseInt(line);
        if(answer == 1){
            fillContext();
        }
    }

    public static void fillContext(){
        Scanner sc = new Scanner(System.in);
        System.out.println("How much iteration do you want");
        boolean condition = true;
        do {
            try {
                System.out.print("Enter number  : ");
                Context.HillnumberIteration = Integer.parseInt(sc.nextLine());
                System.out.print("Genome size (exemple, 50): ");
                Context.genomSize = Integer.parseInt(sc.nextLine());
                condition = false;
            }catch (Exception e){
                System.err.println("You made a mistake please ty again");
            }
        }while (condition);

    }
}
