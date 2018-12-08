package ia.projet.process.imageProcessing;

import ia.projet.process.geneticMethod.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class TestTranform extends Application {
    @Override
    public void start(Stage myStage) throws Exception {
        //ajouter les polygone
        //definir Max X et Y de convexpolygone
        ConvexPolygon.max_X = 100;
        ConvexPolygon.max_Y = 149;
        int maxX = 100;
        int maxY = 149;
        //ConvexPolygon polygon = new ConvexPolygon(3);
        int n = 6;
        List<Double> doubles = new ArrayList<>();
        double[] doubles1 = new double[]{30,30,30,70,70,70};
        for(int i = 0 ; i<6;i++){
            doubles.add(doubles1[i]);
        }
        //fait un polygone.
        ConvexPolygon polygon = new GenePolygon(3,doubles,Color.rgb(0,0,50),1);


        ConvexPolygon polygon1 = new GenePolygon((GenePolygon) polygon);
        for(int i = 0;i<12;i++){
            polygon1 = ((GenePolygon) polygon1).mutationFill();
        }
        //polygon.setFill(Color.rgb(255,255,255));
        //polygon1 = new GenePolygon((GenePolygon) polygon).mutationScale();
        //polygon1 = ((GenePolygon) polygon1).mutationPoint();
        //GenePolygon polygon2=((GenePolygon) polygon1).mutationPoint();

        System.out.println(polygon1.getPoints());
        System.out.println(polygon.getPoints());
        //System.out.println(polygon2.getPoints());

        //System.out.println(polygon2.getPoints());
        Group image = new Group();

        //ajouter polygone à la iste enfant.
        //image.getChildren().add(polygon);
        //for(int i = 0; i <7; i++)
        //    polygon = ((GenePolygon) polygon).mutationPoint();
        System.out.println("nombre de point : " + polygon.getPoints().size());
        image.getChildren().add(polygon1);
        image.getChildren().add(polygon);

        Scene scene = new Scene(image,maxX, maxY);
        myStage.setScene(scene);
        myStage.show();
        //Platform.exit();
    }
    public static void main(String[] arg){
        launch(arg);
    }

}
