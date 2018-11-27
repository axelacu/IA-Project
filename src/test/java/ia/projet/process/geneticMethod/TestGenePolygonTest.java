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
        assertTrue(genePolygon1.getGeneById()!=genepolygone2.getGeneById());
        assertTrue(!(genePolygon1.equals(genepolygone2)));;
    }

    public static void testMutationPoint() {
        ConvexPolygon.max_X=100;
        ConvexPolygon.max_Y=140;
        GenePolygon genePolygon1=new GenePolygon(4);
        GenePolygon genepolygone2=genePolygon1.mutation();
        assertFalse(genePolygon1.equals(genepolygone2));
        assertTrue(!(genePolygon1.equals(genepolygone2)));
        assertFalse(genePolygon1==genepolygone2);

        GenePolygon genePolygon3=genePolygon1.mutationFill();
        assertFalse(genePolygon1.getFill()==genePolygon3.getFill());
        GenePolygon genePolygon4=genePolygon1.mutationOpacity();
        assertFalse(genePolygon1.getOpacity()==genePolygon4.getOpacity());
        assertFalse(genePolygon1==genePolygon3);
        assertFalse(genePolygon1==genePolygon4);
        assertFalse(genePolygon1.equals(genePolygon3));
        assertFalse(genePolygon1.equals(genePolygon4));
        GenePolygon genePolygon5=genePolygon1.mutationRotation();
        assertFalse(genePolygon1==genePolygon5);



    }




}
