package ia.projet.process.geneticMethod;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IndividualSolutionTest extends TestCase{

    public static void testNewIndividual(){
        IndividualSolution individual=new IndividualSolution(50);
        assertEquals(50,individual.getNumberOfGenes());
        assertEquals(10000.0,individual.getFitness());
    }
}
