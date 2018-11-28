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
        //ajouter les polygone
        //definir Max X et Y de convexpolygone
        int maxX = 100;
        int maxY = 149;
        ConvexPolygon.max_X = 100;
        ConvexPolygon.max_Y = 149;
        ConvexPolygon polygon = new ConvexPolygon(3);
        //Creer un polygone
        // tu peux le faire avec un tableau de double
        //fait un polygone.
        Group image = new Group();
        //ajouter polygone à la iste enfant.
        image.getChildren().add(polygon);

        Scene scene = new Scene(image,maxX, maxY);
        myStage.setScene(scene);
        myStage.show();
    }
    public static void main(String[] arg){
        launch(arg);
    }
}
