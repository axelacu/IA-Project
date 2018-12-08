package ia.projet.process;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Context {
    public static double mutationRate;
    public static int maxX ;
    public static int maxY;
    public static int genomSize;
    public static int populationSize;
    public static Color[][] target;
    public static void setTarget(String pathImage){
        Color[][] target=null;
        int maxX=0;
        int maxY=0;
        try{
            BufferedImage bi = ImageIO.read(new File(pathImage));
            maxX = bi.getWidth();
            maxY = bi.getHeight();
            //setting context
            Context.maxX = maxX;
            Context.maxY = maxY;
            ConvexPolygon.max_X = Context.maxX;
            ConvexPolygon.max_Y = Context.maxY;

            target = new Color[maxX][maxY];
            for (int i=0;i<maxX;i++){
                for (int j=0;j<maxY;j++){
                    int argb = bi.getRGB(i, j);
                    int b = (argb)&0xFF;
                    int g = (argb>>8)&0xFF;
                    int r = (argb>>16)&0xFF;
                    int a = (argb>>24)&0xFF;
                    target[i][j] = Color.rgb(r,g,b);
                }
            }
        }
        catch(IOException e){
            System.err.println(e);
            System.exit(9);
        }
        Context.target = target;
    }
}
