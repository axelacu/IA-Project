package ia.projet.process.geneticMethod;

import java.awt.*;
import java.util.*;

public class Mutation {

    static Random random=new Random();

    public static ArrayList<GenePolygon> mutationGenome(ArrayList<GenePolygon> geneChilds){
        ArrayList<GenePolygon> newGenome = new ArrayList<>(geneChilds);
        ArrayList<GenePolygon> newGenes = new ArrayList<>();
        for(ListIterator<GenePolygon> it = newGenome.listIterator(); it.hasNext();){
            GenePolygon newGen = it.next();
            if(Population.MUTATION_RATE >random.nextDouble()){
                Population.numberOfMutation++;
                if(random.nextBoolean()) {
                    it.remove();
                    newGenes.add(newGen.mutation());
                }else{
                    if(random.nextBoolean()){
                       it.set(newGen.mutationTranslate());
                    }else{
                        switch (random.nextInt(7)){
                            case 0:
                                it.set(newGen.mutationOpacity());
                                break;
                            case 1:
                                it.set(newGen.mutationFill());
                                break;
                            case 2:
                                it.set(newGen.mutationScale());
                                break;
                            case 3:
                                it.set(newGen.mutationRotation());
                                break;
                            case 4:
                                it.set(newGen.mutationPoint());
                                break;
                            case 5:
                                it.set(newGen.mutationTranslatePoint());
                                break;
                            case 6:
                                if(newGen.getPoints().size()/2 >8) {
                                    it.set(newGen.mutationRemovePoint());
                                    break;
                                }else{
                                    it.set(newGen.mutationPoint());
                                    break;
                                }
                        }
                    }
                }
            }
        }
        newGenome.addAll(newGenes);
        return newGenome;
    }
    //TODO : Enlever
    public static GenePolygon mutation(GenePolygon genePolygon){
        if(Population.MUTATION_RATE >random.nextDouble()){
            //select the type of mutation
            Population.numberOfMutation++;
            GenePolygon newGen = genePolygon;
            if(random.nextBoolean()) {
                return newGen.mutation();
            }else{
                if(random.nextBoolean()){
                    return genePolygon.mutationTranslate();
                }else{
                    //TODO : Verifier si faire toute les mutation.
                    switch (random.nextInt(6    )){
                        case 0:
                            //System.out.println(0);
                            newGen = genePolygon.mutationOpacity();
                            break;
                        case 1:
                            //System.out.println(1);
                            newGen = genePolygon.mutationFill();
                            break;
                        case 2:
                            //System.out.println(2);
                            newGen = genePolygon.mutationScale();
                            break;
                        case 3:
                            //System.out.println(3);
                            newGen = genePolygon.mutationRotation();
                            break;
                        case 4:
                            //System.out.println(4);
                            if(newGen.getPoints().size()/2 >8) {
                                newGen = genePolygon.mutationRemovePoint();
                                break;
                            }else{
                                newGen = genePolygon.mutationPoint();
                                break;
                            }
                        case 5:
                            newGen = genePolygon.mutationPoint();
                            break;
                    }
                 }
                return newGen;
            }
        }else{
            return genePolygon;
        }
    }


    public static IndividualSolution individualMutation(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);

        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            if(random.nextBoolean()){
                individualSolution.removeGene(indexRan);
                individualSolution.addGene(gen.mutation());
                //individualSolution.setGen(indexRan, gen.mutation());
            }
            else{
                if(random.nextBoolean()){
                    individualSolution.setGen(indexRan,gen.mutationTranslate());
                }else{
                    individualSolution.setGen(indexRan,gen.mutationScale());
                }
            }
        }
        return individualSolution;
    }

    public static IndividualSolution individualMutation2(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);
        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            if(random.nextBoolean()){
                individualSolution.setGen(indexRan,gen.mutationTranslate());
            }
            else{
                if(random.nextBoolean()){
                    individualSolution.setGen(indexRan,gen.mutationScale());
                }else{
                    individualSolution.setGen(indexRan,gen.mutationOpacity());
                }
            }
        }
        return individualSolution;
    }

    public static IndividualSolution individualMutation3(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);
        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            if(random.nextBoolean()){
                individualSolution.setGen(indexRan,gen.mutationFill());
            }
            else{
                if(random.nextBoolean()){
                    individualSolution.setGen(indexRan,gen.mutationTranslatePoint());
                }else{
                    individualSolution.setGen(indexRan,gen.mutationPoint());
                }
            }
        }
        return individualSolution;
    }
    public static IndividualSolution individualMutation4(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);
        for(int j= 0;j<numberOfMutation;j++){
            individualSolution.setGenome(Mutation.mutationGenome(individual.getGenome()));
        }
        return individualSolution;
    }
}



