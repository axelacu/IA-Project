package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndividualSolution<G> implements Individual<G>,Comparable {
    private List<G> genome;
    private int numberOfGenes;
	private double fitness = 0;
	private int timeOfLife = 0;

	public IndividualSolution(){};
    public IndividualSolution(int numberOfGenes){
        //TODO La fitness doit etre initialiser lors de sa construction
        genome=new ArrayList<>();
        this.numberOfGenes = numberOfGenes;
        this.fitness = 0;
    }

    @Override
    public List<G> getGenome() {
        return genome;
    }

    @Override
    public void setGenome(List<G> genome) {
        this.genome = new ArrayList<>(genome);

    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    @Override
    public String toString() {
        return "Genome size : " + genome.size() + ", fitness : " + fitness  + ", " + timeOfLife;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


    public static <G> void sort(List<Individual<G>> list){
        Collections.sort(list,
                new Comparator<Individual<G>>() {
                    @Override
                    public int compare(Individual<G> o1, Individual<G> o2) {
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
