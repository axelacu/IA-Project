package ia.projet.process.geneticMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReproductionImage extends Thread{
    Random random=new Random();

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
        //population.getPopulation().addAll(childs);
        //return population;
    }
    //slow version of repoduction
    public void reproduction3(Population population){
        List<IndividualSolution> listPop = population.getPopulation();
        double sumFit = population.getSumFitness();
        IndividualSolution.sort(population.getPopulation());
        for(int i = 0; i<population.size()/2; i++){
            IndividualSolution parent1 = getIndividualToReproduction(listPop,sumFit);
            IndividualSolution parent2 = getIndividualToReproduction(listPop,sumFit);
            int numberOfGene=Math.max(parent1.getNumberOfGenes(),parent2.getNumberOfGenes());
            IndividualSolution child=new IndividualSolution(random.nextInt(numberOfGene+1)+1);
            child.setGenome(crossover(parent1,parent2,numberOfGene));
            population.add(child);
        }

    }

    public IndividualSolution getIndividualToReproduction(List<IndividualSolution> individuals, double sumFit){
        Random random = new Random();
        double val = - random.nextDouble();
        double prob = 0;
        double previousProb = 0;

        for(int i = 0; i<individuals.size();i++){
            prob= - (individuals.get(i).getFitness())/sumFit;
            previousProb +=prob;
            if(previousProb<val){
                return individuals.get(i);
            }
        }
        return individuals.get(individuals.size()-1);
    }
    /**
     * reproduction between two solutions
     * @param parent1   parent1 choose during the selection.
     * @param parent2   parent2 choose during the selection.
     * @return
     */
    public  List<GenePolygon> crossover(IndividualSolution parent1, IndividualSolution parent2, int sizeGenome){
        List<GenePolygon> childGenome = new ArrayList<>();
        List<GenePolygon> list = new ArrayList<>();
        list.addAll(parent1.getGenome());
        list.addAll(parent2.getGenome());
        if(sizeGenome>(parent1.getNumberOfGenes() + parent2.getNumberOfGenes())){
            return list;
        }
        Collections.shuffle(list);
        for(int i = 0; i <sizeGenome; i++ ){
            GenePolygon geneChild ;
            //may be add a evaluation gen.
            geneChild = new GenePolygon ((GenePolygon)(list.get(i)));
                        //Mutation.

            double ran = random.nextDouble();
            if(Population.MUTATION_RATE > ran){
                childGenome.add(geneChild.mutation());
            }
            else if(Population.MUTATION_RATE> random.nextDouble()){
                childGenome.add(((GenePolygon) geneChild).mutationTranslate());
            }
            else if(Population.MUTATION_RATE> random.nextDouble()) {
                childGenome.add(((GenePolygon) geneChild).mutationRotation());
            }
            else if(Population.MUTATION_RATE>random.nextDouble()){
                childGenome.add(((GenePolygon) geneChild).mutationPoint());
            }
            else if(Population.MUTATION_RATE>random.nextDouble()){
                childGenome.add(((GenePolygon) geneChild).mutationOpacity());
            }
            else if(Population.MUTATION_RATE>random.nextDouble()){
                childGenome.add(((GenePolygon) geneChild).mutationFill());
            }else {
                childGenome.add(geneChild);
            }
        }

        return childGenome;
    }

     public List<GenePolygon> crossover2(IndividualSolution parent1, IndividualSolution parent2, int sizeGenome){
         List<GenePolygon> childGenome = new ArrayList<>();
         ArrayList<GenePolygon> list = new ArrayList<>();
         if(sizeGenome>(parent1.getNumberOfGenes() + parent2.getNumberOfGenes())){
             list.addAll(parent1.getGenome());
             list.addAll(parent2.getGenome());
            return list;
         }
         int intervalSize1 = sizeGenome/2;
         int intervalSize2 = sizeGenome - intervalSize1;

         if(intervalSize1<parent1.getNumberOfGenes()){
            int lbound = random.nextInt(parent1.getNumberOfGenes() - intervalSize1);
            list.addAll(parent1.getGenome().subList(lbound,lbound + intervalSize1));
         }

         return null;
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
