package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;

public class Individu<G> implements Individual<G> {
    public List<G> genome;
    public int nbGene;
    public double fitness;

    public Individu(int nbGene){
        genome=new ArrayList<>();
        this.nbGene=nbGene;
        this.fitness=this.calcul_fitnes();
    }

    @Override
    public List<G> getGenome() {
        return genome;
    }

    @Override
    public void setGenome(List<G> genome) {
        this.genome=genome;
    }

    public  double calcul_fitnes(){ //un individu peut calculer sa propre fitness
        return 0.01;

    }
}
