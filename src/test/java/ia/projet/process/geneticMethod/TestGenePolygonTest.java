package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import junit.framework.TestCase;

public class TestGenePolygonTest  extends TestCase {

    public static void testNewPolygone(){
        ConvexPolygon.max_X=100;
        ConvexPolygon.max_Y=140;

        GenePolygon genePolygon1=new GenePolygon(4);
        assertEquals(genePolygon1.getNumberOfPoints(),4);
        GenePolygon genePolygon2=new GenePolygon(genePolygon1);
        assertTrue(genePolygon1!=genePolygon2);
        assertTrue(genePolygon2.equals(genePolygon2));

    }

    public static void testMutation(){
        ConvexPolygon.max_X=100;
        ConvexPolygon.max_Y=140;
        GenePolygon genePolygon1=new GenePolygon(4);
        GenePolygon genepolygone2=genePolygon1.mutation();
        assertTrue(genePolygon1!=genepolygone2);
        assertTrue(!(genePolygon1.equals(genepolygone2)));

    }


}
