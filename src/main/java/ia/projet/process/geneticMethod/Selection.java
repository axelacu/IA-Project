package ia.projet.process.geneticMethod;
import java.util.Random;
import java.util.*;

public class Selection {
    static Random random = new Random();
    static int numberOfIndividualByGeneration = 100;
    /**
     * This methode purge the population.
     * @param population
     */

    public static void reaper2(Population population,List<Integer> list) {
        ReproductionImage rep=new ReproductionImage();
        list=rep.selectionIndividualToReproduce(population);
        int suppression=population.getPopulation().size();
        while(suppression!=population.maxIndividual){

            int indexSuppression=random.nextInt(list.size());
            if(list.get(indexSuppression)>=population.getPopulation().size()){
                continue;
            }
            if(population.getPopulation().get(list.get(indexSuppression)).getFitness()==population.getBestIndividual().getFitness()){
                continue;
            }
            population.removeIndividual(population.getPopulation().get(list.get(indexSuppression)));
            List<Integer> Intermediaire =new ArrayList<>();
            int j=0;
            for (Integer i:list){
                if(i==list.get(indexSuppression)){
                    Intermediaire.add(j);

                j++;
                }
            }

            for(Integer inte:Intermediaire){
                list.remove(inte);
            }
            suppression--;
        }
    }
    //slow version of reaper
    public static void reaper4(Population population){
        List<IndividualSolution> list = new ArrayList<>(population.getPopulation());
        IndividualSolution.sort(list);
        double sumFit = population.getSumFitness();
        while(population.size()>numberOfIndividualByGeneration){
            IndividualSolution individual = getIndividualSelect(list,sumFit);
            population.removeIndividual(individual);
        }
    }
    //slow selection individual
    public static IndividualSolution getIndividualSelect(List<IndividualSolution> individuals, double sumFit){
        Random random = new Random();
        double val;
        val = random.nextDouble();
        double prob = 0;
        double previousProb = 0;

        for(int i = 0; i<individuals.size();i++){
            prob=  (individuals.get(i).getFitness())/sumFit;
            previousProb +=prob;
            if(previousProb>val){
                return individuals.get(i);
            }
        }
//        if(previousProb<1){
//            previousProb =0;
//            for(IndividualSolution individualSolution : individuals){
//                prob=  (individualSolution.getFitness())/sumFit;
//                previousProb +=prob;
//            }
//            System.out.println("dans if : "  +previousProb + " " + val ) ;
//        }
        System.out.println("Dernier individu");
        return individuals.get(individuals.size()-1);
    }






    public static void setNumberOfIndividualByGeneration(int number){
        numberOfIndividualByGeneration =number;
    }
}
