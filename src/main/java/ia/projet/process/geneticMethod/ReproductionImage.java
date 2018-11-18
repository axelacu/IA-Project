package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class ReproductionImage<Gene> implements Reproduction<Gene> {
    static int MUTATION_RATE =0;

    @Override
    public List crossover(Individual parent1, Individual parent2, int sizeGenome){
        List<Gene> childGenome = new ArrayList<>();
        List<Gene> genomeParent1 = parent1.getGenome();
        List<Gene> genomeParent2 = parent2.getGenome();

        for(int i = 0; i <sizeGenome; i++ ){
            Gene geneChild ;

            //may be add a evaluation gen.
            if(random.nextFloat() >= 0.5){
                //add gene parent1
                geneChild = (genomeParent1.get(i));
            }else{
                //add gene parent2
                geneChild = genomeParent2.get(i);
            }
            //Mutation.
            if(MUTATION_RATE < random.nextFloat()){
                childGenome.add(geneChild);
            }else{
                childGenome.add(mutation(geneChild));
            }
        }
        return childGenome;
    }

    @Override
    public Gene mutation(Gene gene) {
        //set x en haut.
        // set y en bas.

        return gene;
    }
}
