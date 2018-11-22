package ia.projet.process;

import ia.projet.process.geneticMethod.*;
import ia.projet.process.imageProcessing.ConvexPolygon;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(10,4);
        System.out.println(population);
        //ameliration to DO
        for(Individual i : population.getPopulation()){
            i.setFitness(population.fitness(target,i));
            System.out.println(i);
        }
        ReproductionImage rep = new ReproductionImage();
        rep.reproduction(population,population.getNUMBER_OF_GENES_BY_INDIVIDUALS());
        System.out.println("__________________________________________");
        for(Individual i : population.getPopulation()){
            i.setFitness(population.fitness(target,i));
            System.out.println(i);
        }
        Platform.exit();

    }
    static void main(String[] args){launch(args); }
}
