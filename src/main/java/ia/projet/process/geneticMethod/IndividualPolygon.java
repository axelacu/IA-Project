package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class IndividualPolygon<G> implements Individual<G>,Comparable {
    private List<G> genome;
    private int numberOfGenes;
	public double fitness;


	public IndividualPolygon(int numberOfGenes){
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
		return 0;
	}

	@Override
    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
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
