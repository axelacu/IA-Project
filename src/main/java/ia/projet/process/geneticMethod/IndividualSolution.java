package ia.projet.process.geneticMethod;

import java.util.*;

public class IndividualSolution implements Iterable<GenePolygon>,Comparable<IndividualSolution>  {
    private ArrayList<GenePolygon> genome;
    private int numberOfGenes;
	private double fitness = 300000000;
	private int timeOfLife = 0;
    private int id;
    private static int  numberOfInstance=0;


    public IndividualSolution(int numberOfGenes){
        //TODO La fitness doit etre initialiser lors de sa construction
        genome=new ArrayList<>();
        this.numberOfGenes = numberOfGenes;
        this.id=numberOfInstance++;
    }
    public IndividualSolution(int numberOfGenes, ArrayList<GenePolygon> genePolygons){
        //TODO La fitness doit etre initialiser lors de sa construction
        genome=genePolygons;
        this.numberOfGenes = numberOfGenes;
        this.id=numberOfInstance++;
    }

    /**
     * Deep copy of the individual
     * @param individualSolution
     */
    public IndividualSolution(IndividualSolution individualSolution){
        genome = new ArrayList<>();
        for(GenePolygon genePolygon : individualSolution){
            genome.add(new GenePolygon(genePolygon));
        }
        numberOfGenes  = individualSolution.getNumberOfGenes();
        fitness = individualSolution.getFitness();
        this.id=numberOfInstance++;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!(obj instanceof IndividualSolution)){
            return false;
        }
        IndividualSolution individual = (IndividualSolution) obj;
        if(this.getNumberOfGenes() != individual.getNumberOfGenes())
            return false;
        if(individual.getGenome() == genome)
            return true;
        for(int i = 0; i<individual.getNumberOfGenes();i++){
            if(!genome.contains(individual.get(i)))
                return false;
        }
         return true;
    }

    public ArrayList<GenePolygon> getGenome() {
        return genome;
    }

    public void setGenome(List<GenePolygon> genome) {
        this.genome = new ArrayList<>(genome);
        this.numberOfGenes=genome.size();

    }
    public GenePolygon get(int i){
        if(i>=genome.size()){
            return null;
        }
        return genome.get(i);
    }
    public double getFitness() {
        return fitness;
    }

    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String toString() {
        return "Genome size : " + genome.size() + ", fitness : " + fitness  + ", " + timeOfLife;
    }

    public static void sort(List<IndividualSolution> list){
        Collections.sort(list,
                new Comparator<IndividualSolution>() {
                    @Override
                    public int compare(IndividualSolution o1, IndividualSolution o2) {
                        if(o1.getFitness()<o2.getFitness()){
                            return 1;
                        }
                        if(o1.getFitness()>o2.getFitness()){
                            return -1;
                        }

                        return 0;
                    }
                });
    }

    public static <G> void sort2(List<IndividualSolution> list){
        Collections.sort(list,
                new Comparator<IndividualSolution>() {
                    public int compare(IndividualSolution o1, IndividualSolution o2) {
                        if(o1.getFitness()<o2.getFitness()){
                            return -1;
                        }
                        if(o1.getFitness()>o2.getFitness()){
                            return 1;
                        }

                        return 0;
                    }
                });
    }

    /**
     * Add one year old to this individual
     */
    public void anniversary(){
        timeOfLife+=1;
    }

    public int getTimeOfLife() {
        return timeOfLife;
    }

    @Override
    public Iterator<GenePolygon> iterator() {
        return genome.iterator();
    }

    @Override
    public int compareTo(IndividualSolution o2) {

        if(this.getFitness()<o2.getFitness()){
            return -1;
        }
        if(this.getFitness()>o2.getFitness()){
            return 1;
        }

        return 0;
    }
}
