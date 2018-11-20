package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class Population{

    private List<Individual> population;
    private int numberOfIndividuals ;
    /**
     * returns the polygon number of each individual
     */
    private final int NUMBER_OF_GENES_BY_INDIVIDUALS;

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.numberOfIndividuals=numberOfIndividuals;
        this.initialPopulation();
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    public Individual<Gene> generateIndividual(){
        return new IndividualPolygon(NUMBER_OF_GENES_BY_INDIVIDUALS);
    }

    /**
     * initializes the population with individuals
     */
    private void initialPopulation() {
        for(int i=0;i<numberOfIndividuals;i++){
            population.add(generateIndividual());
        }
    }

    // ??? SHALLOW/DEEP


    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() +
                ", Number of gene by individual : " + NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    /// FUNCTION TEST BY MAIN METHOD !

    public static void main(String[] args){
        Population population = new Population(10,50);
        System.out.println(population);
        //ameliration to DO
        for(Individual i : population.getPopulation()){
            System.out.println(i);
        }

        System.out.println(new IndividualPolygon<>(20));
    }
}
