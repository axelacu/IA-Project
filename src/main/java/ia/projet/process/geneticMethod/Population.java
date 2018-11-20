package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> thispopulation;
    private int nbrIndividu;
    /**
     * returns the polygon number of each individual
     */
    private int nbrPolygone;

    public Population(int nbrIndividu) {
        thispopulation=new ArrayList<>();
        this.nbrIndividu=nbrIndividu;
    }

    public Individual<Gene> generateIndividu(){
        return new IndividualPolygon<Gene>(nbrPolygone);
    }

    /**
     * initializes the population with individuals
     */
    public void initialpopulation() {
        for(int i=0;i<nbrIndividu;i++){
            thispopulation.add(generateIndividu());
        }
    }

    public List<Individual> getThispopulation() {
        return thispopulation;
    }

    public void setThispopulation(List<Individual> thispopulation) {
        this.thispopulation = thispopulation;
    }
}
