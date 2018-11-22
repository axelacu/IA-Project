package ia.projet.process.geneticMethod;

import java.util.*;

public interface Reproduction<G> {

    Random random= new Random();

    /**
     * Return a List<G> that contain the genome of the Child.
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @param sizeGenome    size of the genome
     * @return  genome of the child.
     */
    public List crossover(Individual<Gene> parent1, Individual<Gene> parent2, int sizeGenome);

}
