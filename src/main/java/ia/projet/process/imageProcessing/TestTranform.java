package ia.projet.process.imageProcessing;

import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.geneticMethod.ReproductionImage;
import ia.projet.process.geneticMethod.Selection;
import javafx.application.Application;
import javafx.application.Platform;
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
        //ConvexPolygon polygon = new ConvexPolygon(3);
        Double[] doubles = new Double[6];
        double[] doubles1 = new double[]{30,30,30,70,70,70};

        for(int i = 0; i<doubles.length;i++){
            doubles[i] = doubles1[i];
        }

        //Creer un polygone
        // tu peux le faire avec un tableau de double
        //fait un polygone.
        ConvexPolygon polygon = new GenePolygon(3,doubles);


        ConvexPolygon polygon1 = new GenePolygon((GenePolygon) polygon);

        polygon1 = new GenePolygon((GenePolygon) polygon).mutationScale();
        polygon1 = ((GenePolygon) polygon1).mutationFill();

        System.out.println(polygon1.getPoints());
        System.out.println(polygon.getPoints());
        //System.out.println(polygon2.getPoints());
        Group image = new Group();

        //ajouter polygone à la iste enfant.
        image.getChildren().add(polygon);
        image.getChildren().add(polygon1);
        //image.getChildren().add(polygon2);

        Scene scene = new Scene(image,maxX, maxY);
        myStage.setScene(scene);
        myStage.show();
        //Platform.exit();
    }
    public static void main(String[] arg){
        launch(arg);
    }
}
