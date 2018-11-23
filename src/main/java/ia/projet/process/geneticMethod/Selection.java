package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;

public class Selection {
    static int oldest = 1;
    Random random=new Random();



    public static void ripper(Population population){
        List<Individual<Gene>> pop = population.getPopulation();
        Iterator popIt = population.iterator();
        while(popIt.hasNext()){
            Individual<Gene> individual = (Individual<Gene>) popIt.next();
            if(individual.getTimeOfLife()>1) {
                popIt.remove();
                population.removeIndividual(individual);
            }
        }
        System.out.println(pop.size());
    }

    /**
     * selection according to their fitness
     * @param population
     */
    public static void ripper2(Population population){
        Random random=new Random();

        double bestScore = population.getBestIndividual().getFitness();
        int size = population.size();
        double sumFitness = population.getSumFitness();
        //peut être fait sur forme d'iterator et permettre de remove pour gagner du temps dans la selection
        for(int i = 0; i<size;i++) {
            //Autre façon mettre random le nombre d'enfant et l'individu qui pourra être parent.
            Individual<Gene> individual = population.getPopulation().get(i);
            double fitness = individual.getFitness();
            if (fitness < bestScore)
                population.setBestIndividual(individual);
            double probability = fitness / sumFitness;
            if ((1 - probability) > Math.random()) {
                population.removeIndividual(individual);
            }
        }
    }
}
