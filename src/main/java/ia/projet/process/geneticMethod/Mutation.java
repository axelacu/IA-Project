package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {

    static Random random=new Random();

    public static GenePolygon mutation(GenePolygon genePolygon){
        if(Population.MUTATION_RATE>random.nextDouble()){
            //select the type of mutation
            Population.numberOfMutation++;
            GenePolygon newGen = genePolygon;
            if(random.nextBoolean())
                return newGen.mutation();
            if(random.nextBoolean()) {
                if(random.nextDouble()<0.25)
                    newGen = genePolygon.mutationOpacity();
                if(random.nextDouble()<0.25)
                    newGen = genePolygon.mutationFill();
                return newGen.mutationTranslate();
            }else if(random.nextBoolean()){
                return newGen.mutationScale();
            }
            else {
                if(random.nextDouble()<0.25)
                    newGen = genePolygon.mutationOpacity();
                if(random.nextDouble()<0.25)
                    newGen = genePolygon.mutationFill();
                return newGen.mutationRotation();
            }
        }else{
            return genePolygon;
        }
    }

}



