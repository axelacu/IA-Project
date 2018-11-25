package ia.projet.process.geneticMethod;

import junit.framework.TestCase;

public class TestGenePolygonTest  extends TestCase {

    public static void testNewPolygone(){
        GenePolygon gene=new GenePolygon(6);
        gene.mutationPoint();
        assertEquals(7,gene.getNumberOfPoints());
    }
}
