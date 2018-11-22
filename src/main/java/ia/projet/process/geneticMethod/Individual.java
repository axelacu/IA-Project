package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface Individual<G> {
    public List<G> getGenome();
    void setGenome(List<G> genome);
    double getFitness();
    void setFitness(double fitness);
    int getNumberOfGenes();
    void setFitness(double fitness);

}
