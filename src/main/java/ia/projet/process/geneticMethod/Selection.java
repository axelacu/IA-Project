package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class Selection {
    static int oldest = 1;
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
}
