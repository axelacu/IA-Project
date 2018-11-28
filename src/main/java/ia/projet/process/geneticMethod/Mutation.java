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
            GenePolygon newGen ;
            if(random.nextBoolean())
                return genePolygon.mutation();
            else{
                if(random.nextBoolean()) {
                    if(random.nextBoolean())
                        newGen = genePolygon.mutationOpacity();
                    else
                        newGen = genePolygon.mutationFill();
                    return newGen.mutationTranslate();
                }
                else {
                    if(random.nextBoolean())
                        newGen = genePolygon.mutationOpacity();
                    else
                        newGen = genePolygon.mutationFill();

                    return newGen.mutationRotation();
                }
            }
        }else{
            return genePolygon;
        }
    }

}



