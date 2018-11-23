package ia.projet.process.geneticMethod;

import javafx.scene.Parent;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReproductionImage extends Thread implements Reproduction<Gene> {
    Random random=new Random();

    /**
     * Generalisation of reproduction
     * @param population
     * @param numberOfGene
     * @return new population with child
     */

    public Population reproduction(Population population,int numberOfGene){
        ArrayList<Individual<Gene>> childs = new ArrayList<>();
        int numberReproduction=random.nextInt(population.getPopulation().size());
        List<Integer> populationIndex=selectionIndividualToReproduce(population);
        for(int i=0; i<numberReproduction;i++){
            //TODO : Ajouter le pb si la liste est vide
            int indexParent1=random.nextInt(populationIndex.size());
            int indexParent2=random.nextInt(populationIndex.size());
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
     * @return list of integer according to their probability
     */


    private    List<Integer> selectionIndividualToReproduce(Population population){
        //TODO : faire une liste trier PriorityQueue
        List<Integer> populationIndex=new ArrayList<>();
        //IndividualSolution.sort(population.getPopulation());
        //Une fois arriver ici, la liste est bien triée
        double sommeFitness=population.getSumFitness();
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
    public  List<Gene> crossover(Individual<Gene> parent1, Individual<Gene> parent2, int sizeGenome){
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

    public void reproduction2(Population population){
        double bestScore = population.getBestIndividual().getFitness();
        int size = population.size();
        double sumFitness = population.getSumFitness();
        ArrayList<MateThread> nextGeneration = new ArrayList<>();
        //peut être fait sur forme d'iterator et permettre de remove pour gagner du temps dans la selection
        for(int i = 0; i<size;i++){
            //Autre façon mettre random le nombre d'enfant et l'individu qui pourra être parent.
            Individual<Gene> individual = population.getPopulation().get(i);
            double fitness = individual.getFitness();
            if(fitness<bestScore)
                    population.setBestIndividual(individual);
            double probability = fitness / sumFitness;
            if((1-probability)>random.nextDouble()){
                MateThread mate = new MateThread(individual,population);
                mate.start();
                nextGeneration.add(mate);
            }
            individual.anniversary();
        }
        for(MateThread thread : nextGeneration){
            try {
                thread.join();
                population.add(thread.getChild());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this class mate two parent and add on the population
     */
    class MateThread extends Thread{
        Individual<Gene> parent1;
        Individual<Gene> parent2;
        Population population;
        Individual<Gene> child;
        public MateThread(Individual<Gene> parent1,Population population){
            this.population = population;
            this.parent1 = parent1;
            boolean couple = false;
            double sumFitness = population.getSumFitness();
            //search from his love.
            while(!couple){
                int index = random.nextInt(population.size() - 1);
                Individual<Gene> possibleParent = population.getPopulation().get(index);
                double probability =  possibleParent.getFitness()/sumFitness;
                if((1-probability)>random.nextDouble()){ //pas nextINT??
                    parent2 = parent1;
                    couple = true;
                }
            }
        }

        /**
         * add create the child and add him on population
         */
        @Override
        public void run() {
            List<Gene> genome = crossover(parent1,parent2,population.getNumberOfGenesByIndividuals());
            IndividualSolution<Gene> child = new IndividualSolution<>(population.getNumberOfGenesByIndividuals());
            child.setGenome(genome);
            this.child = child;
        }

        public Individual<Gene> getChild() {
            return child;
        }
    }
}
