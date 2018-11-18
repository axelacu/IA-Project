package ia.projet.process.geneticMethod;

import java.util.*;

public interface Reproduction<G> {
    Random random = new Random();

    /**
     * Return a List<G> that contain the genome of the Child.
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @param sizeGenome    size of the genome
     * @return  genome of the child.
     */
    List<G> crossover(Individual parent1, Individual parent2, int sizeGenome);

    /**
     * Return gene who has mutate
     * @param gene gene who will mutate
     * @return a gene mutate
     */
    G mutation(G gene);
}
