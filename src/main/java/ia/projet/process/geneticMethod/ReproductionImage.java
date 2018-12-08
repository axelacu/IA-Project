package ia.projet.process.geneticMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReproductionImage extends Thread{
    Random random=new Random();

    public ArrayList<IndividualSolution> nextGeneration(Population population){
        ArrayList<IndividualSolution> nextGenerationPopulation = new ArrayList<>();
        int sizeGenome = population.getMaxNumberOfGenesByIndividuals();
        while(nextGenerationPopulation.size()<population.size()){
            IndividualSolution parent1 = getIndividualSelect(population);
            IndividualSolution parent2 = getIndividualSelect(population);
            IndividualSolution child = new IndividualSolution(sizeGenome,
                                         crossover(parent1,parent2,sizeGenome));

            nextGenerationPopulation.add(child);
        }
        //Take count of parent TODO : Verifier performance de l'ajout de parent.
        return nextGenerationPopulation;
    }

    /**
     * Receive à sorted population to chose select one individual from that list.
     * @param population a sorted population
     * @return
     */
    public IndividualSolution getIndividualSelect(Population population){
        double size = population.size();
        double biais = size * 0.6;
        double denominator = size + biais;
        int index = 1;
        for(IndividualSolution individual : population){
            double numerator = (size - index);
            if(numerator/denominator>random.nextDouble()){
                return individual;
            }
            index++;
        }
        //if priorityqeueu faire une peek;
        return population.getBestIndividual();
    }

    public void reproduction(Population population){
        ArrayList<IndividualSolution> childs = new ArrayList<>();

        int numberReproduction=random.nextInt(population.getPopulation().size());

        List<Integer> populationIndex=selectionIndividualToReproduce(population);
        for(int i=0; i<numberReproduction;i++){
            int indexParent1=random.nextInt(populationIndex.size());
            int indexParent2=random.nextInt(populationIndex.size());

            IndividualSolution parent1 = population.getPopulation().get(populationIndex.get(indexParent1));
            IndividualSolution parent2 = population.getPopulation().get(populationIndex.get(indexParent2));
            int numberOfGene=Math.max(parent1.getNumberOfGenes(),parent2.getNumberOfGenes());
            IndividualSolution child=new IndividualSolution(random.nextInt(numberOfGene+1)+1);

            child.setGenome(crossover(parent1,parent2,numberOfGene));
            population.add(child);
        }
    }

    public void reproduction4(Population population){
        List<IndividualSolution> listPop = new ArrayList<>(population.getPopulation());
        IndividualSolution.sort2(listPop);
        IndividualSolution bestPrecedent = population.getBestIndividual();
        ArrayList<IndividualSolution> nextGen = new ArrayList<>();
        population.removeAllPopulation();
        //faire ça tant que la nouvelle gen à une taille inf à 100
        while(nextGen.size()<149){
            IndividualSolution parent1;
            IndividualSolution parent2;
            if(0.8>random.nextDouble()){
                //get person from best 20
                parent1 = listPop.get(random.nextInt(20));
            }else{
                parent1 = listPop.get(random.nextInt(listPop.size()));
            }
            if(0.8>random.nextDouble()){
                //get person from best 20
                parent2 = listPop.get(random.nextInt(21));
            }else{
                parent2 = listPop.get(random.nextInt(listPop.size()));
            }
            nextGen.addAll(crossover3(parent1,parent2,population.getMaxNumberOfGenesByIndividuals()));
        }
        //population.add(population.getBestIndividual());

        if(nextGen.size()>149){

            nextGen = new ArrayList<>(nextGen.subList(0,149));
            nextGen.add(bestPrecedent);
        }
        population.removeAllPopulation();
        //System.out.println(nextGen.size());
        population.addPopulation(nextGen);
    }


    /**
     * reproduction between two solutions
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @return
     */
    public  ArrayList<GenePolygon> crossover(IndividualSolution parent1, IndividualSolution parent2, int sizeGenome){
        ArrayList<GenePolygon> childGenome = new ArrayList<>();
        for(int i=0;i<sizeGenome;i++){
            if(random.nextBoolean()){
                GenePolygon genePolygon = new GenePolygon(parent1.getGenome().get(i));
                childGenome.add(genePolygon);
            } else {
                GenePolygon genePolygon = new GenePolygon(parent2.getGenome().get(i));
                childGenome.add(genePolygon);
            }
        }
        //TODO : changer le nom et améliorer si possible
        childGenome = Mutation.mutationGenome(childGenome);
        return  childGenome;
    }
    /** this crossover select randomly part of genom of the parent. **/
     public List<GenePolygon> crossover2(IndividualSolution parent1, IndividualSolution parent2, int sizeGenome){
         List<GenePolygon> childGenome = new ArrayList<>();
         ArrayList<GenePolygon> list = new ArrayList<>();
         ArrayList<GenePolygon> genomPArent1 = new ArrayList<>(parent1.getGenome());
         ArrayList<GenePolygon> genomPArent2 = new ArrayList<GenePolygon> (parent2.getGenome());
         if(sizeGenome>(parent1.getNumberOfGenes() + parent2.getNumberOfGenes())){
             list.addAll(parent1.getGenome());
             list.addAll(parent2.getGenome());
            return list;
         }
         int intervalSize1 = sizeGenome/2;
         int intervalSize2 = sizeGenome - intervalSize1;

         if(intervalSize1<parent1.getNumberOfGenes() && intervalSize2<parent2.getNumberOfGenes() ){
             // add part genom parent1
            int lbound = random.nextInt(parent1.getNumberOfGenes() - intervalSize1);
            for(int i = lbound;i<lbound + intervalSize1;i++){
                GenePolygon gene = new GenePolygon(genomPArent1.get(i));
                list.add(gene);
            }
             // add part genom parent2
             lbound = random.nextInt(parent2.getNumberOfGenes() - intervalSize2);
             for(int i = lbound;i<lbound + intervalSize2;i++){
                 GenePolygon gene = new GenePolygon(genomPArent2.get(i));
                 list.add(gene);
             }

             //list.addAll(parent2.getGenome().subList(lbound,lbound + intervalSize2));
         }else{

             int lbound = random.nextInt(parent2.getNumberOfGenes() - intervalSize1);
             for(int i = lbound;i<lbound + intervalSize1;i++){
                 GenePolygon gene = new GenePolygon(genomPArent2.get(i));
                 list.add(gene);
             }
             // add part genom parent2
             lbound = random.nextInt(parent1.getNumberOfGenes() - intervalSize2);
             for(int i = lbound;i<lbound + intervalSize2;i++){
                 GenePolygon gene = new GenePolygon(genomPArent1.get(i));
                 list.add(gene);
             }
             /*
             int lbound = random.nextInt(parent2.getNumberOfGenes() - intervalSize1);
             list.addAll(new ArrayList<>(parent2.getGenome().subList(lbound,lbound + intervalSize1)));
             // add part genom parent2
             lbound = random.nextInt(parent1.getNumberOfGenes() - intervalSize2);
             list.addAll(parent1.getGenome().subList(lbound,lbound + intervalSize2));*/
         }
         return list;
     }


     //this return à list with two individual that are the result of two parent.
     public ArrayList<IndividualSolution> crossover3(IndividualSolution parent1, IndividualSolution parent2,int sizeGenome){
         ArrayList<IndividualSolution> result = new ArrayList<>();
         if(parent1.getNumberOfGenes() != parent2.getNumberOfGenes()){
             return null;
         }
         int middle = sizeGenome/2;
         //child1 Genome
         ArrayList<GenePolygon> genomeChild1 = new ArrayList<>();
         genomeChild1.addAll(parent1.getGenome().subList(0,middle));
         genomeChild1.addAll(parent2.getGenome().subList(middle,sizeGenome));
         IndividualSolution child1 = new IndividualSolution(sizeGenome);
         child1.setGenome(genomeChild1);
         //child2 Genome
         ArrayList<GenePolygon> genomeChild2 = new ArrayList<>();
         genomeChild2.addAll(parent2.getGenome().subList(0,middle));
         genomeChild2.addAll(parent1.getGenome().subList(middle,sizeGenome));
         IndividualSolution child2 = new IndividualSolution(sizeGenome);
         child2.setGenome(genomeChild2);
         //
         result.add(child1);
         result.add(child2);
         return result;
     }

     /**
     * this static method allow to sort our population according to their fitness
     * @param population
     * @return list of integer according to their probabiliy
     */

    public    List<Integer> selectionIndividualToReproduce(Population population){
        //TODO : faire une liste trier PriorityQueue
        List<Integer> populationIndex=new ArrayList<>();
        //IndividualSolution.sort(population.getPopulation());
        //Une fois arriver ici, la liste est bien triée
        //TODO : Ajouter un variable dans population qui contient la somme de de toute les fitness. Mettre a jour après selection et après repoduction.

        double sommeFitness=0;

        //ToDO Ameliorer 2*for ??
        //TODO: Améliorer les problème de priorité en 0.9 et 0.99
        int i=0;
        for(IndividualSolution individual:population.getPopulation()){
            double probability=individual.getFitness()/population.getSumFitness();
            double departure=0.01; //à modifier en fonction des fitness
            while (departure<1-probability){
                populationIndex.add(i);
                departure=departure+0.01;

            }
            i++;
        }




        return  populationIndex;
    }
    /**
     *
     * @param population
     */
    public void reproduction2(Population population){
        double bestScore = population.getBestIndividual().getFitness();
        int size = population.size();
        double sumFitness = population.getSumFitness();
        //peut être fait sur forme d'iterator et permettre de remove pour gagner du temps dans la selection
        for(int i = 0; i<size;i++){ //pour chaque individu
            //Autre façon mettre random le nombre d'enfant et l'individu qui pourra être parent.
            IndividualSolution individual = population.get(i);
            double fitness = individual.getFitness();

            double probability = 1 - (fitness / sumFitness);
            if(probability> random.nextDouble()){
                boolean couple = false; //s'accouple pas
                //recherche partenaire
                while(!couple){
                    int index = random.nextInt(population.size() - 1);
                    IndividualSolution possibleParent = population.get(index);
                    probability =  1 - possibleParent.getFitness()/sumFitness;
                    if(probability> random.nextDouble()){
                        couple = true;
                        int numberOfGene=Math.max(individual.getNumberOfGenes(),possibleParent.getNumberOfGenes());
                        IndividualSolution child = new IndividualSolution(random.nextInt(numberOfGene+1)+1);
                        List<GenePolygon> genome = crossover(individual,possibleParent,population.getMaxNumberOfGenesByIndividuals());

                        child.setGenome(genome);
                        population.add(child);
                    }
                }
            }
            individual.anniversary();
        }
    }
}
