package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;

public interface Gene {
    /**
     * Return gene who should mutate
     * @return a gene mutate
     */
    public Gene mutation();

}
