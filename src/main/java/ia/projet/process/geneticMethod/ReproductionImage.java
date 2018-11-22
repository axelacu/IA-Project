package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class ReproductionImage implements Reproduction<Gene> {


    /**
     * reproduction between two solutions
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @return
     */
    @Override
    public List crossover(Individual parent1, Individual parent2, int sizeGenome){
        List<Gene> childGenome = new ArrayList<>();
        List<Gene> genomeParent1 = parent1.getGenome();
        List<Gene> genomeParent2 = parent2.getGenome();
        System.out.println(genomeParent1.size());
        System.out.println(genomeParent1.size());

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
            if(Population.MUTATION_RATE < random.nextFloat()){
                childGenome.add(geneChild);
            }else{
                childGenome.add(geneChild.mutation());
            }
        }
        return childGenome;
    }

}
