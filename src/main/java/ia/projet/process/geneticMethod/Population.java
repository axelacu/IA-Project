package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;

import java.util.ArrayList;
import java.util.List;

public class Population{

    private List<Individual<Gene>> population;
    private int numberOfIndividuals ;
    static public double MUTATION_RATE =0.09;
    /**
     * returns the polygon number of each individual
     */
    private int NUMBER_OF_GENES_BY_INDIVIDUALS;

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.numberOfIndividuals=numberOfIndividuals;
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
        this.initialPopulation();

    }


    public Individual<Gene> generateIndividual(){
        Individual<Gene> individual = new IndividualSolution<>(this.NUMBER_OF_GENES_BY_INDIVIDUALS);
        ArrayList<Gene> genome = new ArrayList<>();
        for(int i = 0;i<NUMBER_OF_GENES_BY_INDIVIDUALS;i++){
            GenePolygon gene = new GenePolygon(3);
            genome.add(gene);
        }
        individual.setGenome(genome);
        return individual;
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


    public List<Individual<Gene>> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual<Gene>> population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() +
                ", Number of gene by individual : " + NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    /// FUNCTION TEST BY MAIN METHOD !

    public static void main(String[] args){
        ConvexPolygon.max_X = 100;
        ConvexPolygon.max_Y = 149;
        Population population = new Population(10,4);
        System.out.println(population);
        //ameliration to DO
        for(Individual i : population.getPopulation()){
            System.out.println(i);
        }
        population.getPopulation().get(1).setFitness(1);
        population.getPopulation().get(2).setFitness(2);
        population.getPopulation().get(3).setFitness(1);
        population.getPopulation().get(4).setFitness(3);
        population.getPopulation().get(6).setFitness(4);





/*
        System.out.println(ConvexPolygon.max_X + " " + ConvexPolygon.max_Y);

        Individual<Gene> individual1 = population.getPopulation().get(0);
        Individual<Gene> individual2 = population.getPopulation().get(1);

        ReproductionImage rep = new ReproductionImage();
        System.out.println("Parent1 : ");
        stringGene((ArrayList<Gene>) individual1.getGenome());
        System.out.println("Parent2 : ");
        stringGene((ArrayList<Gene>) individual2.getGenome());
        System.out.println("Genome Enfant : ");
        stringGene((ArrayList<Gene>) rep.crossover(individual1,individual2, population.NUMBER_OF_GENES_BY_INDIVIDUALS));
     */   Selection.selection(population);

        System.out.println(population);
        for(Individual i : population.getPopulation()){
            System.out.println(i);
        }


    }

    public static void stringGene(ArrayList<Gene> genome){
        for(Gene g : genome){
            System.out.println(g);
        }
    }

}
