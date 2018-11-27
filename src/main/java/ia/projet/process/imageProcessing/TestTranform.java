package ia.projet.process.imageProcessing;

import ia.projet.process.geneticMethod.Population;
import ia.projet.process.geneticMethod.ReproductionImage;
import ia.projet.process.geneticMethod.Selection;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTranform extends Application {
    @Override
    public void start(Stage myStage) throws Exception {
        //////////
        int initialPopulation = 100;
        int numberOfGeneByIndividual = 50;
        Population.setMutationRate(0.1);
        Selection.setNumberOfIndividualByGeneration(150);
        ///////
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Random random = new Random();
        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(initialPopulation,numberOfGeneByIndividual);
        ReproductionImage rep = new ReproductionImage();

    }
}
