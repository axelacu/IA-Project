package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface Individual<G> {
    List<G> getGenome();
    void setGenome(List<G> genome);
}
