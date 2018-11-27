package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {

    static Random random=new Random();

    public static void mutation(Population population){
        IndividualSolution bestIndivual = new IndividualSolution(population.getBestIndividual());
        ArrayList<IndividualSolution> newGenerationMutate = new ArrayList<>();
        for(IndividualSolution individualSolution:population){
            List<GenePolygon> childGenome = new ArrayList<>();
            List<GenePolygon> list = new ArrayList<>(individualSolution.getGenome());
            int sizeGenome=individualSolution.getNumberOfGenes();
            //System.out.println(individualSolution.getGenome().size());
            for(int i = 0; i <sizeGenome; i++ ){
                GenePolygon geneChild ;
                //may be add a evaluation gen.
                geneChild = new GenePolygon (list.get(i));
                //Mutation.

                double ran = random.nextDouble();
                if(Population.MUTATION_RATE > ran){
                    //System.out.println(geneChild);
                    geneChild = geneChild.mutation();
                    childGenome.add(geneChild);
                    Population.numberOfMutation++;
                    continue;
                }
                /*
                if(Population.MUTATION_RATE> random.nextDouble()) {
                    geneChild = geneChild.mutationRotation();
                    Population.numberOfMutation++;
                }
                if(Population.MUTATION_RATE>random.nextDouble()){
                    geneChild = geneChild.mutationPoint();
                    Population.numberOfMutation++;
                }
                if(Population.MUTATION_RATE>random.nextDouble()){
                    geneChild = geneChild.mutationOpacity();
                    Population.numberOfMutation++;
                }
                if(Population.MUTATION_RATE>random.nextDouble()){
                    geneChild = geneChild.mutationFill();
                    Population.numberOfMutation++;
                } */
                childGenome.add(geneChild);
            }
        //fixe gene
        individualSolution.setGenome(childGenome);
        newGenerationMutate.add(individualSolution);
        }
        population.removeAllPopulation();
        population.add(bestIndivual);
        population.addPopulation(newGenerationMutate);

    }

}



