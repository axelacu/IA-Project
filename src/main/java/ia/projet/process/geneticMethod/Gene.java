package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;

public class Gene {
    private ConvexPolygon CP; //un gene est un polygone convexe

    public ConvexPolygon getCP(){
	return this.CP;
    }
}
