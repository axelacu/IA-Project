package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface Individual<G> {
    public List<G> getGenome();
    public void setGenome(List<G> genome);
    public double getFitness(Color[][] target);
}
