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
        Double[] doubles = new Double[n];
        double[] doubles1 = new double[]{30,30,30,70,70,70};
        for(int i = 0 ; i<6;i++){
            doubles[i] = doubles1[i];
        }
        //fait un polygone.
        ConvexPolygon polygon = new GenePolygon(3,doubles);


        ConvexPolygon polygon1 = new GenePolygon((GenePolygon) polygon);

        //polygon1 = new GenePolygon((GenePolygon) polygon).mutationScale();
        polygon1 = ((GenePolygon) polygon1).mutationPoint();
        GenePolygon polygon2=((GenePolygon) polygon1).mutationPoint();

        System.out.println(polygon1.getPoints());
        System.out.println(polygon.getPoints());
        System.out.println(polygon2.getPoints());

        //System.out.println(polygon2.getPoints());
        Group image = new Group();

        //ajouter polygone à la iste enfant.
        //image.getChildren().add(polygon);
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

    public class Point {

        int x, y;

        // generate a random point
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void translate(int vx, int vy) {
            x += vx;
            y += vy;
        }

        public boolean equals(Object o) {
            if (o == null)
                return false;
            else if (o == this)
                return true;
            else if (o instanceof ConvexPolygon.Point)
                return ((ConvexPolygon.Point) o).x == this.x && ((ConvexPolygon.Point) o).y == this.y;
            else
                return false;
        }

        public String toString() {
            NumberFormat nf = new DecimalFormat("#.00");
            return "(" + x + "," + y + ")"; // + nf.format(Math.atan2(y, x))+")";
        }
    }
}
