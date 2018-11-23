package ia.projet.process;

import ia.projet.process.geneticMethod.*;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        String pathImage = "monaLisa-100.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(100,50);
        ReproductionImage rep = new ReproductionImage();
        for(int i = 0; i <1000; i++){
            System.out.println("Generation : " + i + " Pop : " + population);
            rep.reproduction(population,population.getNumberOfGenesByIndividuals());
            Selection.ripper(population);

            int actualIndex = i;
            Thread th = new Thread(){
                @Override
                public void run() {
                    if(actualIndex%4 == 0) {
                        population.drawBestIndividual();
                    }
                }
            };
            th.run();
        }

        population.drawBestIndividual();
        Platform.exit();

    }
    static void main(String[] args){launch(args); }
}
