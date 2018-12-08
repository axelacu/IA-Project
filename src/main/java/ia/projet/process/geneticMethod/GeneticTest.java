package ia.projet.process.geneticMethod;

import ia.projet.process.Context;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GeneticTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        /// Defining context
        defineContext();


    }
    public static void main(String[] args){
        launch(args);

    }

    private void defineContext(){
        Context.isExistantFile();

    }
}
