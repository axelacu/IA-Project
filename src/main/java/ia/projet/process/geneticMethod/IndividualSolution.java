package ia.projet.process.geneticMethod;

import java.util.*;

public class IndividualSolution implements Iterable<GenePolygon>,Comparable<IndividualSolution>  {
    private ArrayList<GenePolygon> genome;
    private int numberOfGenes;
	private double fitness= -1;
	private int timeOfLife = 0;
    private int id;
    private static int numberOfInstance=0;

    /**
     * Builder who creates IndividualSolution with an empty polygon list
     * @param numberOfGenes number of polygons
     */

    public IndividualSolution(int numberOfGenes){
        //TODO La fitness doit etre initialiser lors de sa construction
        genome=new ArrayList<>();
        this.numberOfGenes = numberOfGenes;
        this.id=numberOfInstance++;
    }

    /**
     * Builder who creates IndividualSolution
     * @param numberOfGenes number of polygons in this solution
     * @param genePolygons list of polygons to create a potential solution
     */


    public IndividualSolution(int numberOfGenes, ArrayList<GenePolygon> genePolygons){
        //TODO La fitness doit etre initialiser lors de sa construction
        if(genePolygons.size()!=numberOfGenes){
            System.out.println("Error in Individual Solution creation; numberOfGenes must be equals to genePolygons.size() ");
            return;
        }
        genome=new ArrayList<>(genePolygons);
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

    /**
     * to test if an individualSolution equals to this object
     * @param obj other individualSolution
     * @return boolean true if equals; false if not
     */

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

    /**
     * getter for list of polygons(we call this "genome")
     * @return genome
     */

    public ArrayList<GenePolygon> getGenome() {
        return genome;
    }

    /**
     * setter for list genome
     * @param genome new list of polygons with new a new address
     */

    public void setGenome(List<GenePolygon> genome) {
        this.genome = new ArrayList<>(genome);
        this.numberOfGenes=genome.size();

    }

    /**
     * replace a polygon in the genome list
     * @param index position of this polygons in this genome
     * @param genePolygon  polygon to add instead of the old
     */

    public void setGen(int index,GenePolygon genePolygon){
        this.genome.set(index,genePolygon);
    }

    /**
     * remove polygon in list of polygon(genome)
     * @param i  index of place
     */

    public void removeGene(int i){
        genome.remove(i);
    }

    /**
     * to add a polygon in solution
     * @param genePolygon this genePolygon
     */

    public void addGene(GenePolygon genePolygon ){
        this.numberOfGenes++;
        this.genome.add(genePolygon);
    }

    /**
     * get a polygon in genome
     * @param i index
     * @return the genePolygon in the in this index
     */

    public GenePolygon get(int i){
        if(i>=genome.size()){
            return null;
        }
        return genome.get(i);
    }

    /**
     *
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * to modify a fitness
     * @param fitness
     */

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     *
     * @return
     */

    public int getNumberOfGenes() {
        return numberOfGenes;
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
