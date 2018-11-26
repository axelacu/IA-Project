package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {

    static Random random=new Random();

    public static void mutation(Population population){

        for(IndividualSolution individualSolution:population){
            List<GenePolygon> childGenome = new ArrayList<>();

            if(individualSolution.getFitness()==population.getBestIndividual().getFitness()) {
                continue;
            }
            List<GenePolygon> list = individualSolution.getGenome();
            int sizeGenome=individualSolution.getNumberOfGenes();
                for(int i = 0; i <sizeGenome; i++ ){
                    GenePolygon geneChild ;
                    //may be add a evaluation gen.
                    geneChild = new GenePolygon (list.get(i));
                    //Mutation.

                    double ran = random.nextDouble();
                    if(Population.MUTATION_RATE > ran){
                        geneChild = geneChild.mutation();
                        childGenome.add(geneChild);
                        continue;
                    }
                    if(Population.MUTATION_RATE> random.nextDouble()) {
                        geneChild = geneChild.mutationRotation();
                    }
                    if(Population.MUTATION_RATE>random.nextDouble()){
                        geneChild = geneChild.mutationPoint();
                    }
                    if(Population.MUTATION_RATE>random.nextDouble()){
                        geneChild = geneChild.mutationOpacity();
                    }
                    if(Population.MUTATION_RATE>random.nextDouble()){
                        geneChild = geneChild.mutationFill();
                    }
                    childGenome.add(geneChild);

                }
                individualSolution.setGenome(childGenome);
            }

        }
    }



