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
        population.setBestIndividual(HillClimber.hillClimber(individual,Population.target,100000));
        population.drawBestIndividual();
        Platform.exit();
    }
}
