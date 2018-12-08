package ia.projet.process.geneticMethod;

import ia.projet.process.Context;
import javafx.scene.paint.Color;
import junit.framework.TestCase;

public class PopulationTest extends TestCase {
    public static void testNewPolygone(){
        Population pop = new Population(10,10);
        assertEquals(pop.similarityColorDark(80,160),(double) (1 - 160)/(1-80));
        assertEquals(pop.similarityLightColor(200,100),(double )100/200);
        Color t1 = Color.rgb(10,10,10);
        Color c2 = Color.rgb(10,10,10);
        assertEquals(pop.probabilityPixel(t1,t1),(double) 1);

        //TEST NON VALID
        String pathImage = "monaLisa-100.jpg";
        Context.setTarget(pathImage);
        Color[][] target =Context.target ;
        String pathImage2 = "bestMona.png";

        Color[][] target2 = Context.target;
        //System.err.println(Math.round(pop.fitness2(target,target2) * 100));
        //assertEquals(pop.fitness2(target,target2),(double) 1);


    }
}
