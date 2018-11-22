package ia.projet.process.imageProcessing;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ImageExtractor {
    // take an path and extraite image from this path.
    public static Color[][] getTarget(String pathImage){
        Color[][] target=null;
        int maxX=0;
        int maxY=0;
        try{
            BufferedImage bi = ImageIO.read(new File(pathImage));
            maxX = bi.getWidth();
            maxY = bi.getHeight();
            ConvexPolygon.max_X= maxX;
            ConvexPolygon.max_Y= maxY;
            //System.out.println(ConvexPolygon.max_X + " " + ConvexPolygon.max_Y);
            target = new Color[maxX][maxY];
            for (int i=0;i<maxX;i++){
                for (int j=0;j<maxY;j++){
                    int argb = bi.getRGB(i, j);
                    int b = (argb)&0xFF;
                    int g = (argb>>8)&0xFF;
                    int r = (argb>>16)&0xFF;
                    int a = (argb>>24)&0xFF;
                    //ajout pixel par pixel.
                    target[i][j] = Color.rgb(r,g,b);
                }
            }
        }
        catch(IOException e){
            System.err.println(e);
            System.exit(9);
        }
        return target;
    }
}
