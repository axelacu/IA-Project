package ia.projet.process.clusturingMethod;

import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ClusturingTest extends Application {
    @Override
    public void start(Stage myStage) throws Exception {
        String pathImage = "monaLisa-200.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        BufferedImage bi = ImageIO.read(new File(pathImage));
        int maxX = bi.getWidth();
        int maxY = bi.getHeight();
        Group image = new Group();
        Scene scene = new Scene(image,maxX,maxY );
        myStage.setScene(scene);
        myStage.show();
        Random random = new Random();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //Platform.startup(runnable);
        for(int i = 0; i<50;i++){
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            Color c = target[x][y];
            Circle circle = new Circle(x,y,4);
            circle.setFill(c);
            image.getChildren().add(circle);
        }
    }
}
