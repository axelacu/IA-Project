package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReproductionImage implements Reproduction<Gene> {

    Random random=new Random();


    public Population reproduction(Population population,int numberOfGene){
        ArrayList<Individual<Gene>> childs = new ArrayList<>();
        int numberReproduction=random.nextInt(population.getPopulation().size());
        List<Integer> populationIndex=selectionIndividualToReproduce(population);
        for(int i=0; i<numberReproduction;i++){
            int indexParent1=random.nextInt(populationIndex.size()+1);
            int indexParent2=random.nextInt(populationIndex.size()+1);
            Individual<Gene> parent1 = population.getPopulation().get(indexParent1);
            Individual<Gene> parent2 = population.getPopulation().get(indexParent2);
            Individual<Gene> child=new IndividualSolution<>(numberOfGene);
            child.setGenome(crossover(parent1,parent2,numberOfGene));
            childs.add(child);
        }
        population.getPopulation().addAll(childs);
        return population;







    }
    /**
     * this static method allow to sort our population according to their fitness
     * @param population
     * @return list of integer according to their probabiliy
     */


    public   List<Integer> selectionIndividualToReproduce(Population population){
        //TODO : faire une liste trier PriorityQueue
        List<Integer> populationIndex=new ArrayList<>();
        IndividualSolution.sort(population.getPopulation());
        //Une fois arriver ici, la liste est bien triée
        //TODO : Ajouter un variable dans population qui contient la somme de de toute les fitness. Mettre a jour après selection et après repoduction.
        double sommeFitness=0;
        for(Individual<Gene> individual:population.getPopulation()){
            sommeFitness=sommeFitness+individual.getFitness();
        }
        //ToDO Ameliorer 2*for ??
        //TODO: Améliorer les problème de priorité en 0.9 et 0.99
        int i=0;
        for(Individual<Gene> individual:population.getPopulation()){
            double probability=individual.getFitness()/sommeFitness;
            double departure=0.1; //à modifier en fonction des fitness
            while (departure<probability){
                populationIndex.add(i);
                departure=departure+0.1;
                i++;
            }
        }
        return  populationIndex;
    }

    /**
     * reproduction between two solutions
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @return
     */
    @Override
    public  List crossover(Individual<Gene> parent1, Individual<Gene> parent2, int sizeGenome){
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
            if(Population.MUTATION_RATE < random.nextFloat()){
                childGenome.add(geneChild);
            }else{
                childGenome.add(geneChild.mutation());
            }
        }
        return childGenome;
    }

}
