package ia.projet.process.hillClimberMethod;

import ia.projet.process.Context;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Mutation;
import ia.projet.process.geneticMethod.Population;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HillClimberTest extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String pathImage = "monaLisa-200.jpg";
        Context.setTarget(pathImage);
        Population.target = Context.target;
        Population population = new Population(1,50);
        Population.setMutationRate(1);
        IndividualSolution individual = population.generateIndividual();

        population.setBestIndividual(HillClimber.hillClimber(individual,Population.target));
        /*
        individual.setFitness(population.fitness2(Context.target,individual));
        population.setBestIndividual(individual);
        Random random = new Random();
        for(int i = 0; i < 100000000; i++){
            IndividualSolution newIndividual ;
            if(random.nextBoolean())
                newIndividual = Mutation.individualMutation(individual, 1 +random.nextInt(2));
            else
                newIndividual = Mutation.individualMutation4(individual, 1 +random.nextInt(2));
            newIndividual.setFitness(population.fitness2(Context.target,newIndividual));
            //System.out.println(newIndividual.getFitness() + "  " + individual.getFitness());
            if(individual.getFitness()<newIndividual.getFitness()){
                individual = new IndividualSolution(newIndividual);
                population.setBestIndividual(newIndividual);
                System.out.println("Best fit : "  + individual.getFitness());
            }
            if(i%500 == 0){
                population.drawBestIndividual();
            }
        }
        AtomicInteger count = new AtomicInteger();
        individual.forEach(z -> count.getAndIncrement());
        System.out.println(count);*/
        population.drawBestIndividual();
        Platform.exit();
    }
}
