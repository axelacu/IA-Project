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
        Population population = new Population(10,4);
        System.out.println(population);
        //ameliration to DO
        for(Individual i : population.getPopulation()){
            // BAD

            i.setFitness(Population.fitness(target,i));
            System.out.println(i);
        }

        System.out.println(ConvexPolygon.max_X + " " + ConvexPolygon.max_Y);

        Individual<Gene> individual1 = population.getPopulation().get(0);
        Individual<Gene> individual2 = population.getPopulation().get(1);

        ReproductionImage rep = new ReproductionImage();
        System.out.println("Parent1 : ");
        Population.stringGene((ArrayList<Gene>) individual1.getGenome());
        System.out.println("Parent2 : ");
        Population.stringGene((ArrayList<Gene>) individual2.getGenome());
        System.out.println("Genome Enfant : ");
        Population.stringGene((ArrayList<Gene>) rep.crossover(individual1,individual2, population.getNUMBER_OF_GENES_BY_INDIVIDUALS()));
        Selection.selection(population);
        Platform.exit();

    }
    static void main(String[] args){launch(args); }
}
