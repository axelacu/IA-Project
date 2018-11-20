package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class ReproductionImage<G extends  Gene> implements Reproduction<Gene> {
    static private int MUTATION_RATE =0;

    /**
     * reproduction between two solutions
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @param sizeGenome    size of the genome
     * @return
     */
    @Override
    public List crossover(Individual parent1, Individual parent2, int sizeGenome){
        List<Gene> childGenome = new ArrayList<>();
        List<Gene> genomeParent1 = parent1.getGenome();
        List<Gene> genomeParent2 = parent2.getGenome();

        for(int i = 0; i <sizeGenome; i++ ){
            Gene geneChild ;
            //may be add a evaluation gen.
            if(random.nextBoolean()){
                //add gene parent1
                geneChild = (genomeParent1.get(i));
            }else{
                //add gene parent2
                geneChild = genomeParent2.get(i);
            }
            //Mutation.
            if(MUTATION_RATE < random.nextFloat()){
                childGenome.add(geneChild.mutation());
            }else{
                childGenome.add(geneChild);
            }
        }
        return childGenome;
    }

}
