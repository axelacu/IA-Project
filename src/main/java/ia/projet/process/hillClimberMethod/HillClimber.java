package ia.projet.process.hillClimberMethod;

import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Mutation;
import ia.projet.process.geneticMethod.Population;
import javafx.scene.paint.Color;

import java.util.Random;

public class HillClimber {
    /**
     * Use hillClimber Method to find a individual that have high similarity to the target
     *
     * @param individual
     * @param target
     * @return
     */
    public  static IndividualSolution hillClimber(IndividualSolution individual, Color[][] target,int numberIteration){
        Population population = new Population(1,1);

        individual.setFitness(population.fitness2(target,individual));
        population.setBestIndividual(individual);
        Random random = new Random();

        for(int i = 0; i < numberIteration; i++){
            IndividualSolution newIndividual  = new IndividualSolution();

            int r = random.nextInt(4);
            switch (r){
                case 0:
                    newIndividual = Mutation.individualMutation(individual,1+random.nextInt(2));
                    break;
                case 1:
                    newIndividual = Mutation.individualMutation2(individual,1+ random.nextInt(2));
                    break;
                case 2:
                    newIndividual = Mutation.individualMutation3(individual,1+random.nextInt(2));
                    break;
                case 3:
                    newIndividual = Mutation.individualMutation4(individual,1+ random.nextInt(1));
                    break;
            }

            newIndividual.setFitness(population.fitness2(target,newIndividual));
            if(individual.getFitness()<newIndividual.getFitness()){
                individual = new IndividualSolution(newIndividual);
                population.setBestIndividual(newIndividual);
                System.out.println("New best fitness : "  + individual.getFitness());
            }
            if(i%1000 == 0){
                population.drawBestIndividual();
            }
        }
        return individual;
    }
}
