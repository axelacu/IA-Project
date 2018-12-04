package ia.projet.process.geneticMethod;

import java.awt.*;
import java.util.Random;

public class Mutation {

    static Random random=new Random();

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
        if(individual.getFitness()>0.61)
            numberOfMutation++;
        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            if(random.nextBoolean()){
                if(random.nextBoolean()) {
                    individualSolution.removeGene(indexRan);
                    individualSolution.getGenome().add(gen.mutation());
                    //individualSolution.setGen(indexRan, gen.mutation());
                }else{
                    individualSolution.setGen(indexRan, gen.mutation());
                }
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
        if(individual.getFitness()>0.61)
            numberOfMutation++;
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
                    individualSolution.setGen(indexRan,gen.mutation());
                }
            }
        }
        return individualSolution;
    }

    public static IndividualSolution individualMutation3(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);
        if(individual.getFitness()>0.61)
            numberOfMutation++;
        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            if(random.nextBoolean()){
                individualSolution.setGen(indexRan,gen.mutationFill());
            }
            else{
                if(random.nextBoolean()){
                    individualSolution.setGen(indexRan,gen.mutationOpacity());
                }else{
                    individualSolution.setGen(indexRan,gen.mutation());
                }
            }
        }
        return individualSolution;
    }
    public static IndividualSolution individualMutation4(IndividualSolution individual, int numberOfMutation){
        IndividualSolution individualSolution = new IndividualSolution(individual);
        if(individual.getFitness()>0.61)
            numberOfMutation++;
        for(int j= 0;j<numberOfMutation;j++){
            int indexRan = random.nextInt(individualSolution.getNumberOfGenes());
            GenePolygon gen = individualSolution.get(indexRan);
            individualSolution.setGen(indexRan,Mutation.mutation(gen));
        }
        return individualSolution;
    }

    public static void permutateGene(IndividualSolution individualSolution){
        Random random = new Random();
        int i = random.nextInt(individualSolution.getNumberOfGenes());
        int j = random.nextInt(individualSolution.getNumberOfGenes());
        GenePolygon geni = individualSolution.get(i);
        GenePolygon genj = individualSolution.get(j);
        individualSolution.setGen(i,genj);
        individualSolution.setGen(j,geni);

    }
    public static IndividualSolution permutateGene2(IndividualSolution individualSolution1){
        IndividualSolution individualSolution = new IndividualSolution(individualSolution1);
        Random random = new Random();
        int i = random.nextInt(individualSolution.getNumberOfGenes());
        int j = random.nextInt(individualSolution.getNumberOfGenes());
        GenePolygon geni = individualSolution.get(i);
        GenePolygon genj = individualSolution.get(j);
        individualSolution.setGen(i,genj);
        individualSolution.setGen(j,geni);
        return individualSolution;

    }



}



