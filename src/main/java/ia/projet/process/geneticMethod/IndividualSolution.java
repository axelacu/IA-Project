package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndividualSolution  {
    private ArrayList<GenePolygon> genome;
    private int numberOfGenes;
	private double fitness = 10000;
	private int timeOfLife = 0;


    public IndividualSolution(int numberOfGenes){
        //TODO La fitness doit etre initialiser lors de sa construction
        genome=new ArrayList<>();
        this.numberOfGenes = numberOfGenes;

    }

    public IndividualSolution(IndividualSolution individualSolution){
        genome = new ArrayList<>();
        for(GenePolygon genePolygon : individualSolution.getGenome()){
            genome.add(new GenePolygon(genePolygon));
        }
        numberOfGenes  = individualSolution.getNumberOfGenes();
        fitness = individualSolution.getFitness();
    }

    public List<GenePolygon> getGenome() {
        return genome;
    }

    public void setGenome(List<GenePolygon> genome) {
        this.genome = new ArrayList<>(genome);
        this.numberOfGenes=genome.size();

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

    public int compareTo(Object o) {
        return 0;
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
    

    //Exemple de fitness du prof.
	/*
    public double getFitness(Color[][] target){
	Group image = new Group();
	for(ConvexPolygon p : this.genome){
	    image.getChildren().add(p);
	}

	int x = body.length;
	int y = body[].length;
	
        WritableImage image = new WritableImage(x,y);
	image.snapshot(null,wimg);
	PixelReader pr = wimg.getPixelReader();
	double res = 0;
	for(int i=0; i<x; i++){
	    for(int j=0; j<y; j++){
		Color c = pr.getcolor(i,j);
		res+= Math.pow(c.getBlue()-target[i][j].getBlue(),2)
		    + Math.pow(c.getRed()-target[i][j].getRed(),2)
		    + Math.pow(c.getGreen()-target[i][j].getGreen(),2);
	    }
	}

	return res;
    }
    */
}
