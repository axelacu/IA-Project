package ia.projet.process.geneticMethod;

import junit.framework.TestCase;

public class TestGenePolygonTest  extends TestCase {

    public static void testNewPolygone(){

        GenePolygon genePolygon1=new GenePolygon(4);
        assertEquals(genePolygon1.getNumberOfPoints(),4);
        GenePolygon genePolygon2=new GenePolygon(genePolygon1);
        assertTrue(genePolygon1.getNumberOfPoints()==genePolygon2.getNumberOfPoints());
        assertTrue(genePolygon1.getFill()==genePolygon2.getFill());
        assertTrue(genePolygon1.getOpacity()==genePolygon2.getOpacity());
        assertTrue(genePolygon1==genePolygon2);

    }


}
